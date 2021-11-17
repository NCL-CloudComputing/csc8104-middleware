package org.jboss.quickstarts.wfk.hotel;

import org.jboss.quickstarts.wfk.customer.Customer;
import sun.jvm.hotspot.opto.HaltNode;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>This is a Repository class and connects the Service/Control layer (see {@link HotelService} with the
 * Domain/Entity Object (see {@link Hotel}).<p/>
 *
 * <p>There are no access modifiers on the methods making them 'package' scope.  They should only be accessed by a
 * Service/Control object.<p/>
 *
 * @author Chenjie Li
 * @see Hotel
 * @see javax.persistence.EntityManager
 */
public class HotelRepository {
    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private EntityManager em;

    /**
     * <p>Returns a List of all persisted {@link Hotel} objects, sorted alphabetically by last name.</p>
     *
     * @return List of Hotel objects
     */
    List<Hotel> findAllOrderedByName(String hName) {
        TypedQuery<Hotel> query = em.createNamedQuery(Hotel.FIND_ALL, Hotel.class);
        return query.getResultList();
    }

    /**
     * <p>Returns a single Hotel object, specified by a Long id.<p/>
     *
     * @param id The id field of the Hotel to be returned
     * @return The Hotel with the specified id
     */
    Hotel findById(Long id) {
        return em.find(Hotel.class, id);
    }


    /**
     * <p>Persists the provided Hotel object to the application database using the EntityManager.</p>
     *
     * <p>{@link javax.persistence.EntityManager#persist(Object) persist(Object)} takes an entity instance, adds it to the
     * context and makes that instance managed (ie future updates to the entity will be tracked)</p>
     *
     * <p>persist(Object) will set the @GeneratedValue @Id for an object.</p>
     *
     * @param hotel The Hotel object to be persisted
     * @return The Hotel object that has been persisted
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Hotel create(Hotel hotel) throws ConstraintViolationException, ValidationException, Exception {
        log.info("HotelRepository.create() - Creating " + hotel.getName());

        // Write the hotel to the database.
        em.persist(hotel);

        return hotel;
    }

    /**
     * <p>Updates an existing Hotel object in the application database with the provided Hotel object.</p>
     *
     * <p>{@link javax.persistence.EntityManager#merge(Object) merge(Object)} creates a new instance of your entity,
     * copies the state from the supplied entity, and makes the new copy managed. The instance you pass in will not be
     * managed (any changes you make will not be part of the transaction - unless you call merge again).</p>
     *
     * <p>merge(Object) however must have an object with the @Id already generated.</p>
     *
     * @param hotel The Hotel object to be merged with an existing Hotel
     * @return The Hotel that has been merged
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Hotel update(Hotel hotel) throws ConstraintViolationException, ValidationException, Exception {
        log.info("HotelRepository.update() - Updating " + hotel.getName());

        // Either update the hotel or add it if it can't be found.
        em.merge(hotel);

        return hotel;
    }

    /**
     * <p>Deletes the provided Hotel object from the application database if found there</p>
     *
     * @param hotel The Hotel object to be removed from the application database
     * @return The Hotel object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Hotel delete(Hotel hotel) throws Exception {
        log.info("HotelRepository.delete() - Deleting " + hotel.getName());

        if (hotel.getId() != null) {
            /*
             * The Hibernate session (aka EntityManager's persistent context) is closed and invalidated after the commit(),
             * because it is bound to a transaction. The object goes into a detached status. If you open a new persistent
             * context, the object isn't known as in a persistent state in this new context, so you have to merge it.
             *
             * Merge sees that the object has a primary key (id), so it knows it is not new and must hit the database
             * to reattach it.
             *
             * Note, there is NO remove method which would just take a primary key (id) and a entity class as argument.
             * You first need an object in a persistent state to be able to delete it.
             *
             * Therefore we merge first and then we can remove it.
             */
            em.remove(em.merge(hotel));

        } else {
            log.info("HotelRepository.delete() - No ID was found so can't Delete.");
        }

        return hotel;
    }
}
