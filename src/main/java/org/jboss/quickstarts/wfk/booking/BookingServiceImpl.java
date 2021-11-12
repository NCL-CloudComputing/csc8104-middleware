package org.jboss.quickstarts.wfk.booking;


import org.jboss.quickstarts.wfk.area.AreaService;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Logger;

public class BookingServiceImpl implements BookingService{
    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private BookingValidator validator;

    @Inject
    private BookingRepository crud;

    private ResteasyClient client;


    /**
     * <p>Create a new client which will be used for our outgoing REST client communication</p>
     */
    public BookingServiceImpl() {
        // Create client service instance to make REST requests to upstream service
        client = new ResteasyClientBuilder().build();
    }

    /**
     * <p>Returns a List of all persisted {@link Booking} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Booking objects
     */
    @Override
    public List<Booking> findAllOrderedByOrderDate() {
        return crud.findAllOrderedByOrderDate();
    }

    /**
     * <p>Returns a single Booking object, specified by a Long id.<p/>
     *
     * @param id The id field of the Booking to be returned
     * @return The Booking with the specified id
     */
    @Override
    public Booking findById(Long id) {
        return crud.findById(id);
    }

    /**
     * <p>Returns a single Booking object, specified by a Long userId.</p>
     *
     *
     * @param userId The userId field of the Booking to be returned
     * @return The  Booking with the specified userId
     */
    @Override
    public Booking findByUserId(Long userId) {
        return crud.findByUserId(userId);
    }

    /**
     * <p>Returns a single Booking object, specified by a Long hotelId.</p>
     *
     *
     * @param hotelId The userId field of the Booking to be returned
     * @return The  Booking with the specified hotelId
     */
    @Override
    public Booking findByHotelId(Long hotelId) {
        return crud.findByHotelId(hotelId);
    }


    /**
     * <p>Writes the provided Booking object to the application database.<p/>
     *
     * <p>Validates the data in the provided Booking object using a {@link BookingValidator} object.<p/>
     *
     * @param booking The Booking object to be written to the database using a {@link BookingRepository} object
     * @return The Booking object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Booking create(Booking booking) throws ConstraintViolationException, ValidationException, Exception {
        log.info("BookingService.create() - Creating Booking, Booking Id: " + booking.getId());

        // Check to make sure the data fits with the parameters in the Booking model and passes validation.
        validator.validateBooking(booking);

        //Create client service instance to make REST requests to upstream service
        ResteasyWebTarget target = client.target("http://ec2-18-119-125-232.us-east-2.compute.amazonaws.com/");
        AreaService service = target.proxy(AreaService.class);

        // Write the booking to the database.
        return crud.create(booking);
    }

    /**
     * <p>Updates an existing Booking object in the application database with the provided Booking object.<p/>
     *
     * <p>Validates the data in the provided Booking object using a BookingValidator object.<p/>
     *
     * @param booking The Booking object to be passed as an update to the application database
     * @return The Booking object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Booking update(Booking booking) throws ConstraintViolationException, ValidationException, Exception{
        log.info("BookingService.update() - Updating Booking, Booking Id: " + booking.getId());

        // Check to make sure the data fits with the parameters in the Booking model and passes validation.
        validator.validateBooking(booking);

        // Set client target location and define the proxy API class
        ResteasyWebTarget target = client.target("http://ec2-18-119-125-232.us-east-2.compute.amazonaws.com/");
        AreaService service = target.proxy(AreaService.class);


        // Either update the booking or add it if it can't be found.
        return crud.update(booking);
    }

    /**
     * <p>Deletes the provided Booking object from the application database if found there.<p/>
     *
     * @param booking The Booking object to be removed from the application database
     * @return The Booking object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    @Override
    public Booking delete(Booking booking) throws Exception{
        log.info("delete() - Deleting " + booking.toString());

        Booking deletedBooking = null;

        if (booking.getId() != null) {
            deletedBooking = crud.delete(booking);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedBooking;
    }
}
