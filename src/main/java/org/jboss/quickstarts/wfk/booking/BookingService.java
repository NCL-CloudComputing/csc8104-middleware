package org.jboss.quickstarts.wfk.booking;

import org.jboss.quickstarts.wfk.booking.Booking;
import org.jboss.quickstarts.wfk.booking.BookingRepository;
import org.jboss.quickstarts.wfk.booking.BookingValidator;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface BookingService {


    /**
     * <p>Returns a List of all persisted {@link Booking} objects, sorted alphabetically by OrderDate.<p/>
     *
     * @return List of Booking objects
     */
    List<Booking> findAllOrderedByOrderDate();

    /**
     * <p>Returns a single Booking object, specified by a Long id.<p/>
     *
     * @param id The id field of the Booking to be returned
     * @return The Booking with the specified id
     */
    Booking findById(Long id);

    /**
     * <p>Returns a single Booking object, specified by a Long customerId.</p>
     *
     *
     * @param customerId The customerId field of the Booking to be returned
     * @return The  Booking with the specified customerId
     */
    List<Booking> findByUserId(Long customerId);

    /**
     * <p>Returns a single Booking object, specified by a Long hotelId.</p>
     *
     *
     * @param hotelId The customerId field of the Booking to be returned
     * @return The  Booking with the specified hotelId
     */
    List<Booking> findByHotelId(Long hotelId);


    /**
     * <p>Writes the provided Booking object to the application database.<p/>
     *
     * <p>Validates the data in the provided Booking object using a {@link BookingValidator} object.<p/>
     *
     * @param booking The Booking object to be written to the database using a {@link BookingRepository} object
     * @return The Booking object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Booking create(Booking booking) throws Exception;

    /**
     * <p>Updates an existing Booking object in the application database with the provided Booking object.<p/>
     *
     * <p>Validates the data in the provided Booking object using a BookingValidator object.<p/>
     *
     * @param booking The Booking object to be passed as an update to the application database
     * @return The Booking object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Booking update(Booking booking) throws Exception;

    /**
     * <p>Deletes the provided Booking object from the application database if found there.<p/>
     *
     * @param booking The Booking object to be removed from the application database
     * @return The Booking object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Booking delete(Booking booking) throws Exception;

}
