package org.jboss.quickstarts.wfk.guestBooking;


import io.swagger.annotations.*;
import org.jboss.quickstarts.wfk.booking.Booking;
import org.jboss.quickstarts.wfk.booking.BookingService;
import org.jboss.quickstarts.wfk.customer.Customer;
import org.jboss.quickstarts.wfk.customer.CustomerService;
import org.jboss.quickstarts.wfk.util.RestServiceException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * <p>This class produces a RESTful service exposing the functionality of {@link BookingService}.</p>
 *
 * <p>The Path annotation defines this as a REST Web Service using JAX-RS.</p>
 *
 * <p>By placing the Consumes and Produces annotations at the class level the methods all default to JSON.  However, they
 * can be overriden by adding the Consumes or Produces annotations to the individual methods.</p>
 *
 * <p>It is Stateless to "inform the container that this RESTful web service should also be treated as an EJB and allow
 * transaction demarcation when accessing the database." - Antonio Goncalves</p>
 *
 * <p>The full path for accessing endpoints defined herein is: api/bookings/*</p>
 *
 * @author Chenjie Li
 * @see BookingService
 * @see Response
 */
@Path("/guestBookings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "/guestBookings", description = "Operations about guestBookings")
@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class GuestBookingRestService {

    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private BookingService bookingService;

    @Inject
    private CustomerService customerService;

    @Resource
    UserTransaction transaction;
    /**
     * <p>Creates a Guestbooking from the values provided. Performs validation and will return a JAX-RS response with
     * either 201 (Resource created) or with a map of fields, and related errors.</p>
     *
     * @param guestBooking The Booking object, constructed automatically from JSON input, to be <i>created</i> via
     * {@link BookingService#create(Booking)}
     * {@link CustomerService#create(Customer)}
     * @return A Response indicating the outcome of the create operation
     */
    @SuppressWarnings("unused")
    @POST
    @ApiOperation(value = "Add a new Booking to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Booking created successfully."),
            @ApiResponse(code = 400, message = "Invalid Booking supplied in request body"),
            @ApiResponse(code = 409, message = "Booking supplied in request body conflicts with an existing Booking"),
            @ApiResponse(code = 500, message = "An unexpected error occurred whilst processing the request")
    })
    public Response createBooking(
            @ApiParam(value = "JSON representation of Booking object to be added to the database", required = true)
                    GuestBooking guestBooking) {


        if (guestBooking == null) {
            throw new RestServiceException("Bad Request", Response.Status.BAD_REQUEST);
        }

        Response.ResponseBuilder builder;

        try {

            transaction.begin();
            //create a new customer
            Booking booking = guestBooking.getBooking();
            Customer customer = guestBooking.getCustomer();

            Customer existedCustomer = customerService.create(customer);

            booking.setCustomer(existedCustomer);
            booking.setCusId(existedCustomer.getId());
            // Go add the new Booking.
            Booking booking1 = bookingService.create(booking);

            transaction.commit();


            // Create a "Resource Created" 201 Response and pass the booking back in case it is needed.
            builder = Response.status(Response.Status.CREATED).entity(guestBooking.getBooking());

        } catch (ConstraintViolationException ce) {
            //Handle bean validation issues
            Map<String, String> responseObj = new HashMap<>();

            for (ConstraintViolation<?> violation : ce.getConstraintViolations()) {
                responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new RestServiceException("Bad Request", responseObj, Response.Status.BAD_REQUEST, ce);

        }   catch (Exception e) {

            try {
                transaction.rollback();
                log.info("the transaction failed");
            } catch (SystemException systemException) {
                systemException.printStackTrace();
            }
            // Handle generic exceptions
            throw new RestServiceException(e);
        }

        log.info("createGuestBooking completed. GuestBooking = " + guestBooking.getBooking().toString());
        return builder.build();
    }

}
