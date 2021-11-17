package org.jboss.quickstarts.wfk.hotel;


import org.jboss.quickstarts.wfk.area.Area;
import org.jboss.quickstarts.wfk.area.AreaService;
import org.jboss.quickstarts.wfk.area.InvalidAreaCodeException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

public class HotelServiceImpl implements HotelService{


    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private HotelValidator validator;

    @Inject
    private HotelRepository crud;

    private ResteasyClient client;


    /**
     * <p>Create a new client which will be used for our outgoing REST client communication</p>
     */
    public HotelServiceImpl() {
        // Create client service instance to make REST requests to upstream service
        client = new ResteasyClientBuilder().build();
    }

    /**
     * <p>Returns a List of all persisted {@link Hotel} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Hotel objects
     */
    @Override
    public List<Hotel> findAllOrderedByName(String hName) {
        return crud.findAllOrderedByName(hName);
    }

    /**
     * <p>Returns a single Hotel object, specified by a Long id.<p/>
     *
     * @param id The id field of the Hotel to be returned
     * @return The Hotel with the specified id
     */
    @Override
    public Hotel findById(Long id) {
        return crud.findById(id);
    }

    /**
     * <p>Writes the provided Contact object to the application database.<p/>
     *
     * <p>Validates the data in the provided Contact object using a {@link HotelValidator} object.<p/>
     *
     * @param hotel The Hotel object to be written to the database using a {@link Hotel} object
     * @return The Hotel object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Hotel create(Hotel hotel) throws Exception {
        log.info("HotelService.create() - Creating " + hotel.getName());

        // Check to make sure the data fits with the parameters in the Contact model and passes validation.
        validator.validateContact(hotel);


        // Write the contact to the database.
        return crud.create(hotel);
    }

    /**
     * <p>Updates an existing Hotel object in the application database with the provided Hotel object.<p/>
     *
     * <p>Validates the data in the provided Hotel object using a Hotel object.<p/>
     *
     * @param hotel The Hotel object to be passed as an update to the application database
     * @return The Hotel object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Hotel update(Hotel hotel) throws Exception {
        log.info("HotelService.update() - Updating " + hotel.getName());

        // Check to make sure the data fits with the parameters in the Contact model and passes validation.
        validator.validateContact(hotel);

        // Either update the contact or add it if it can't be found.
        return crud.update(hotel);
    }


    /**
     * <p>Deletes the provided Hotel object from the application database if found there.<p/>
     *
     * @param hotel The Hotel object to be removed from the application database
     * @return The Hotel object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    @Override
    public Hotel delete(Hotel hotel) throws Exception {
        log.info("delete() - Deleting " + hotel.toString());

        Hotel deletedHotel = null;

        if (hotel.getId() != null) {
            deletedHotel = crud.delete(hotel);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedHotel;
    }
}
