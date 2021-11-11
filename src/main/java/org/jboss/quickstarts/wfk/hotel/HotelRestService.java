package org.jboss.quickstarts.wfk.hotel;


import io.swagger.annotations.*;
import org.jboss.quickstarts.wfk.area.InvalidAreaCodeException;
import org.jboss.quickstarts.wfk.contact.UniqueEmailException;

import org.jboss.quickstarts.wfk.customer.CustomerService;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>This class produces a RESTful service exposing the functionality of {@link HotelService}.</p>
 *
 * <p>The Path annotation defines this as a REST Web Service using JAX-RS.</p>
 *
 * <p>By placing the Consumes and Produces annotations at the class level the methods all default to JSON.  However, they
 * can be overriden by adding the Consumes or Produces annotations to the individual methods.</p>
 *
 * <p>It is Stateless to "inform the container that this RESTful web service should also be treated as an EJB and allow
 * transaction demarcation when accessing the database." - Antonio Goncalves</p>
 *
 * <p>The full path for accessing endpoints defined herein is: api/contacts/*</p>
 *
 * @author Chenjie Li
 * @see HotelService
 * @see javax.ws.rs.core.Response
 */

@Path("/hotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/hotels", description = "Operations about hotels")
@Stateless
public class HotelRestService {
    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private HotelService service;

    /**
     * <p>Return all the Hotels.  They are sorted alphabetically by name.</p>
     *
     * <p>The url may optionally include query parameters specifying a Contact's name</p>
     *
     * <p>Examples: <pre>GET api/contacts?firstname=John</pre>, <pre>GET api/contacts?firstname=John&lastname=Smith</pre></p>
     *
     * @return A Response containing a list of Contacts
     */
    @GET
    @ApiOperation(value = "Fetch all Hotel", notes = "Returns a JSON array of all stored Hotel objects.")
    public Response retrieveAllCustomers(@QueryParam("hName") String hName) {
        //Create an empty collection to contain the intersection of Consumers to be returned
        List<Hotel> hotels = null;

        try {
            hotels = service.findAllOrderedByName(hName);
        } catch (NoResultException e) {
            // Verify that the hotel exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the name " + hName + " was found!", Response.Status.NOT_FOUND);
        }
            return Response.ok(hotels).build();
        }



    /**
     * <p>Search for and return a Hotel identified by id.</p>
     *
     * @param id The long parameter value provided as a Hotel's id
     * @return A Response containing a single Hotel
     */
    @GET
    @Cache
    @Path("/{id:[0-9]+}")
    @ApiOperation(
            value = "Fetch a Hotel by id ",
            notes = "Returns a JSON representation of the Hotel object with the provided id."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Hotel found"),
            @ApiResponse(code = 404, message = "Hotel with id not found")
    })
    public Response retrieveHotelById(
            @ApiParam(value = "Id of Customer to be fetched", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
                    long id){

        Hotel hotel = service.findById(id);
        if (hotel == null) {
            // Verify that the contact exists. Return 404, if not present.
            throw new RestServiceException("No Hotel with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found Hotel = " + hotel.toString());

        return Response.ok(hotel).build();
    }


    /**
     * <p>Creates a new contact from the values provided. Performs validation and will return a JAX-RS response with
     * either 201 (Resource created) or with a map of fields, and related errors.</p>
     *
     * @param hotel The Hotel object, constructed automatically from JSON input, to be <i>created</i> via
     * {@link HotelService#create(Hotel)}
     * @return A Response indicating the outcome of the create operation
     */
    @SuppressWarnings("unused")
    @POST
    @ApiOperation(value = "Add a new Customer to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Customer created successfully."),
            @ApiResponse(code = 400, message = "Invalid Customer supplied in request body"),
            @ApiResponse(code = 409, message = "Customer supplied in request body conflicts with an existing Customer"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response createContact(
            @ApiParam(value = "JSON representation of Customer object to be added to the database", required = true)
                    Hotel hotel) {


        if (hotel == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {
            // Go add the new Hotel.
            service.create(hotel);

            // Create a "Resource Created" 201 Response and pass the hotel back in case it is needed.
            builder = Response.status(Response.Status.CREATED).entity(hotel);


        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.CONFLICT, e);
        } catch (InvalidAreaCodeException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("area_code", "The telephone area code provided is not recognised, please provide another");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createContact completed. Hotel = " + hotel.toString());
        return builder.build();
    }

    /**
     * <p>Updates the customer with the ID provided in the database. Performs validation, and will return a JAX-RS response
     * with either 200 (ok), or with a map of fields, and related errors.</p>
     *
     * @param hotel The Contact object, constructed automatically from JSON input, to be <i>updated</i> via
     * {@link HotelService#update(Hotel)}
     * @param id The long parameter value provided as the id of the Contact to be updated
     * @return A Response indicating the outcome of the create operation
     */
    @PUT
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Update a Customer in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Customer updated successfully"),
            @ApiResponse(code = 400, message = "Invalid Customer supplied in request body"),
            @ApiResponse(code = 404, message = "Customer with id not found"),
            @ApiResponse(code = 409, message = "Customer details supplied in request body conflict with another existing Customer"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response updateContact(
            @ApiParam(value = "Id of Customer to be updated", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
                    long id,
            @ApiParam(value = "JSON representation of Customer object to be updated in the database", required = true)
                    Hotel hotel) {

        if (hotel == null || hotel.gethId() == null) {
            throw new RestServiceException("Invalid Contact supplied in request body", Response.Status.BAD_REQUEST);
        }

        if (hotel.gethId() != null && hotel.gethId() != id) {
            // The client attempted to update the read-only Id. This is not permitted.
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("id", "The Customer ID in the request body must match that of the Customer being updated");
            throw new RestServiceException("Customer details supplied in request body conflict with another Customer",
                    responseObj, Response.Status.CONFLICT);
        }

        if (service.findById(hotel.gethId()) == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        Response.ResponseBuilder builder;

        try {
            // Apply the changes the Customer.
            service.update(hotel);

            // Create an OK Response and pass the customer back in case it is needed.
            builder = Response.ok(hotel);


        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);
        } catch (UniqueEmailException e) {
            // Handle the unique constraint violation
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("email", "That email is already used, please use a unique email");
            throw new RestServiceException("Contact details supplied in request body conflict with another Customer",
                    responseObj, Response.Status.CONFLICT, e);
        } catch (InvalidAreaCodeException e) {
            Map<String, String> responseObj = new HashMap<>();
            responseObj.put("area_code", "The telephone area code provided is not recognised, please provide another");
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, e);
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("updateContact completed. Hotel = " + hotel.toString());
        return builder.build();
    }

    /**
     * <p>Deletes a hotel using the ID provided. If the ID is not present then nothing can be deleted.</p>
     *
     * <p>Will return a JAX-RS response with either 204 NO CONTENT or with a map of fields, and related errors.</p>
     *
     * @param id The Long parameter value provided as the id of the Hotel to be deleted
     * @return A Response indicating the outcome of the delete operation
     */
    @DELETE
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Delete a Hotel from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The hotel has been successfully deleted"),
            @ApiResponse(code = 400, message = "Invalid Hotel id supplied"),
            @ApiResponse(code = 404, message = "Hotel with id not found"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response deleteContact(
            @ApiParam(value = "Id of Hotel to be deleted", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
                    long id) {

        Response.ResponseBuilder builder;

        Hotel hotel = service.findById(id);
        if (hotel == null) {
            // Verify that the customer exists. Return 404, if not present.
            throw new RestServiceException("No Customer with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }

        try {
            service.delete(hotel);

            builder = Response.noContent();

        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("deleteContact completed. Hotel = " + hotel.toString());
        return builder.build();
    }
}
