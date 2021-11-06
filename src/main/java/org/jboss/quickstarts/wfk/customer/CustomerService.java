package org.jboss.quickstarts.wfk.customer;

import org.jboss.quickstarts.wfk.contact.Contact;
import org.jboss.quickstarts.wfk.contact.ContactRepository;
import org.jboss.quickstarts.wfk.contact.ContactValidator;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface CustomerService {


    /**
     * <p>Returns a List of all persisted {@link Customer} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Customer objects
     */
    List<Customer> findAllOrderedByName();

    /**
     * <p>Returns a single Customer object, specified by a Long id.<p/>
     *
     * @param id The id field of the Customer to be returned
     * @return The Customer with the specified id
     */
    Customer findById(Long id);

    /**
     * <p>Returns a single Customer object, specified by a String email.</p>
     *
     * <p>If there is more than one Customer with the specified email, only the first encountered will be returned.<p/>
     *
     * @param email The email field of the Contact to be returned
     * @return The first Customer with the specified email
     */
    Customer findByEmail(String email);

    /**
     * <p>Returns a single Contact object, specified by a String firstName.<p/>
     *
     * @param firstName The firstName field of the Customer to be returned
     * @return The first Customer with the specified firstName
     */
    List<Customer> findAllByFirstName(String firstName);

    /**
     * <p>Returns a single Customer object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Customers to be returned
     * @return The Customers with the specified lastName
     */
    List<Customer> findAllByLastName(String lastName);

    /**
     * <p>Writes the provided Contact object to the application database.<p/>
     *
     * <p>Validates the data in the provided Contact object using a {@link CustomerValidator} object.<p/>
     *
     * @param customer The Customer object to be written to the database using a {@link CustomerRepository} object
     * @return The Customer object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Customer create(Customer customer) throws Exception;

    /**
     * <p>Updates an existing Customer object in the application database with the provided Customer object.<p/>
     *
     * <p>Validates the data in the provided Customer object using a CustomerValidator object.<p/>
     *
     * @param customer The Contact object to be passed as an update to the application database
     * @return The Customer object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    Customer update(Customer customer) throws Exception;

    /**
     * <p>Deletes the provided Customer object from the application database if found there.<p/>
     *
     * @param customer The Customer object to be removed from the application database
     * @return The Customer object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    Customer delete(Customer customer) throws Exception;

}
