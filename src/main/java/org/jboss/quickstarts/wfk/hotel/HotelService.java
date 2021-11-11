package org.jboss.quickstarts.wfk.hotel;




import javax.validation.ConstraintViolationException;
import java.util.List;


public interface HotelService {

    /**
     * <p>Returns a List of all persisted {@link Hotel} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Hotel objects
     */
    List<Hotel> findAllOrderedByName(String hName);

    /**
     * <p>Returns a single Hotel object, specified by a Long id.<p/>
     *
     * @param id The id field of the Hotel to be returned
     * @return The Hotel with the specified id
     */
    Hotel findById(Long id);

    /**
     * <p>Writes the provided Hotel object to the application database.<p/>
     *
     * <p>Validates the data in the provided Hotel object using a {@link HotelValidator} object.<p/>
     *
     * @param Hotel The Hotel object to be written to the database using a {@link HotelRepository} object
     * @return The Hotel object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Hotel create(Hotel Hotel) throws Exception;

    /**
     * <p>Updates an existing Hotel object in the application database with the provided Hotel object.<p/>
     *
     * <p>Validates the data in the provided Hotel object using a HotelValidator object.<p/>
     *
     * @param Hotel The Hotel object to be passed as an update to the application database
     * @return The Hotel object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Hotel update(Hotel Hotel) throws Exception;

    /**
     * <p>Deletes the provided Hotel object from the application database if found there.<p/>
     *
     * @param Hotel The Hotel object to be removed from the application database
     * @return The Hotel object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Hotel delete(Hotel Hotel) throws Exception;
}
