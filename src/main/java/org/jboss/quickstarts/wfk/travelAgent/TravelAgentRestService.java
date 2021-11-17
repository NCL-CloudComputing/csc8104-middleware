package org.jboss.quickstarts.wfk.travelAgent;


import io.swagger.annotations.*;
import org.jboss.quickstarts.wfk.booking.Booking;
import org.jboss.quickstarts.wfk.hotel.HotelService;
import org.jboss.quickstarts.wfk.travelAgent.models.Flight;
import org.jboss.quickstarts.wfk.travelAgent.models.Taxi;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
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
 * <p>The full path for accessing endpoints defined herein is: api/hotels/*</p>
 *
 * @author Chenjie Li
 * @see HotelService
 * @see javax.ws.rs.core.Response
 */

@Path("/travelAgent")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/travelAgent", description = "Operations about travelAgent")
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
@Stateless
public class TravelAgentRestService {
    @Inject
    private @Named("logger") Logger log;

    @Inject
    private TravelAgentService travelAgentService;

    @Inject
    private UserTransaction userTransaction;

    @POST
    @ApiOperation(value = "Add a new Booking to the database")
    @ApiResponses(value = { @ApiResponse(code = 201, message = "Booking created successfully."),
            @ApiResponse(code = 400, message = "Invalid Booking supplied in request body"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
    public Response createTravelAgentBooking(
            @ApiParam(value = "JSON representation of Booking object to be added to the database", required = true)
                    TravelAgent travelAgent) {

        if (travelAgent == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        TravelAgentBooking travelAgentBooking = null;

        Response.ResponseBuilder builder;
        try{
            userTransaction.begin();
            travelAgentBooking = travelAgentService.create(travelAgent);

            userTransaction.commit();
        } catch(Exception e) {
            e.printStackTrace();
            try {
                travelAgentService.delete(travelAgentBooking);
                userTransaction.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new RestServiceException(e);
        }

        log.info("created travelAgent = " + travelAgent.toString());
        return Response.status(Response.Status.CREATED).entity(travelAgentBooking).build();

    }

    @DELETE
    @Path("/{id:[0-9]+}")
    @ApiOperation(value = "Delete a Booking from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The booking has been successfully deleted"),
            @ApiResponse(code = 400, message = "Invalid Booking id supplied"),
            @ApiResponse(code = 404, message = "Booking with id not found"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request") })
    public Response deleteBooking(
            @ApiParam(value = "Id of Booking to be deleted", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id") long id) {

        Response.ResponseBuilder builder = null;
        TravelAgentBooking booking = travelAgentService.findById(id);
        if (booking == null) {
            // Verify booking exists. if not present return 404.
            throw new RestServiceException("No TravelAgentBooking with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("TravelAgentBooking found!!!!!!!!!!!!!!!!!!!!!!!"+booking.toString());
        try {
            travelAgentService.delete(booking);
            builder = Response.noContent();
        } catch (Exception e) {
            // Handle generic exceptions
            throw new RestServiceException(e);
        }
        log.info("delete Booking completed. Booking : " + booking.toString());
        return builder.build();
    }

    /**
     * <p> BookingRestService retrieve all TravelAgentBookings</p>
     *
     * @return javax.ws.rs.core.Response
     */
    @GET
    @Path("/all")
    @ApiOperation(value = "Fetch all travelAgent Booking", notes = "Returns a JSON array of all stored Booking objects.")
    public Response retrieveAllBookings() {
        List<TravelAgentBooking> allBookings = new ArrayList<>();
        try {
            allBookings = travelAgentService.findAllBookings();
        }catch (NoResultException ne){
            ne.printStackTrace();
            log.severe("NoResultException while retrieving all TravelAgentBookings : " + ne.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(allBookings).build();
        }
        return Response.ok(allBookings).build();
    }

    /**
     * <p>Search for and return a Booking identified by id.</p>
     *
     * @param id The long parameter value provided as a Booking's id
     * @return A Response containing a single Booking
     */
    @GET
    @Cache
    @Path("/{id:[0-9]+}")
    @ApiOperation(
            value = "Fetch a TravelBooking by id",
            notes = "Returns a JSON representation of the Booking object with the provided id."
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message ="Booking found"),
            @ApiResponse(code = 404, message = "Booking with id not found")
    })
    public Response retrieveBookingById(
            @ApiParam(value = "Id of Booking to be fetched", allowableValues = "range[0, infinity]", required = true)
            @PathParam("id")
                    long id) {

        TravelAgentBooking booking = travelAgentService.findById(id);
        if (booking == null) {
            // Verify that the booking exists. Return 404, if not present.
            throw new RestServiceException("No Booking with the id " + id + " was found!", Response.Status.NOT_FOUND);
        }
        log.info("findById " + id + ": found travelAgentBooking = " + booking.toString());

        return Response.ok(booking).build();
    }


    /**
     * <p> BookingRestService retrieve all TravelAgentBookings</p>
     *
     * @return javax.ws.rs.core.Response
     */
    @GET
    @Path("/flights")
    @ApiOperation(value = "Fetch all Flights", notes = "Returns a JSON array of all stored Flights objects.")
    public Response retrieveAllFlights() {
        List<Flight> allflights = new ArrayList<>();
        try {
            allflights = travelAgentService.findAllFlights();
        }catch (NoResultException ne){
            ne.printStackTrace();
            log.severe("NoResultException while retrieving all TravelAgentBookings : " + ne.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(allflights).build();
        }
        return Response.ok(allflights).build();
    }

    /**
     * <p> BookingRestService retrieve all TravelAgentBookings</p>
     *
     * @return javax.ws.rs.core.Response
     */
    @GET
    @Path("/taxis")
    @ApiOperation(value = "Fetch all taxis", notes = "Returns a JSON array of all stored taxis objects.")
    public Response retrieveAllTaxis() {
        List<Taxi> allTaxis = new ArrayList<>();
        try {
            allTaxis = travelAgentService.findAllTaxis();
        }catch (NoResultException ne){
            ne.printStackTrace();
            log.severe("NoResultException while retrieving all TravelAgentBookings : " + ne.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(allTaxis).build();
        }
        return Response.ok(allTaxis).build();
    }
}
