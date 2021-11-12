package org.jboss.quickstarts.wfk.customer;

import org.jboss.quickstarts.wfk.area.Area;
import org.jboss.quickstarts.wfk.area.AreaService;
import org.jboss.quickstarts.wfk.area.InvalidAreaCodeException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.logging.Logger;

public class CustomerServiceImpl implements CustomerService{

    @Inject
    private @Named("logger")
    Logger log;

    @Inject
    private CustomerValidator validator;

    @Inject
    private CustomerRepository crud;

    private ResteasyClient client;


    /**
     * <p>Create a new client which will be used for our outgoing REST client communication</p>
     */
    public CustomerServiceImpl() {
        // Create client service instance to make REST requests to upstream service
        client = new ResteasyClientBuilder().build();
    }

    /**
     * <p>Returns a List of all persisted {@link Customer} objects, sorted alphabetically by last name.<p/>
     *
     * @return List of Customer objects
     */
    @Override
    public List<Customer> findAllOrderedByName() {
        return crud.findAllOrderedByName();
    }

    /**
     * <p>Returns a single Customer object, specified by a Long id.<p/>
     *
     * @param id The id field of the Customer to be returned
     * @return The Customer with the specified id
     */
    @Override
    public Customer findById(Long id) {
        return crud.findById(id);
    }



    /**
     * <p>Returns a single Customer object, specified by a String email.</p>
     *
     * <p>If there is more than one Customer with the specified email, only the first encountered will be returned.<p/>
     *
     * @param email The email field of the Customer to be returned
     * @return The first Customer with the specified email
     */
    @Override
    public Customer findByEmail(String email) {
        return crud.findByEmail(email);
    }

    /**
     * <p>Returns a single Customer object, specified by a String firstName.<p/>
     *
     * @param firstName The firstName field of the Customer to be returned
     * @return The first Customer with the specified firstName
     */
    @Override
    public List<Customer> findAllByFirstName(String firstName) {
        return crud.findAllByFirstName(firstName);
    }

    /**
     * <p>Returns a single Customer object, specified by a String lastName.<p/>
     *
     * @param lastName The lastName field of the Customers to be returned
     * @return The Customers with the specified lastName
     */
    @Override
    public List<Customer> findAllByLastName(String lastName) {
        return crud.findAllByLastName(lastName);
    }

    /**
     * <p>Writes the provided Customer object to the application database.<p/>
     *
     * <p>Validates the data in the provided Customer object using a {@link CustomerValidator} object.<p/>
     *
     * @param customer The Customer object to be written to the database using a {@link CustomerRepository} object
     * @return The Customer object that has been successfully written to the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Customer create(Customer customer) throws ConstraintViolationException, ValidationException, Exception {
        log.info("CustomerService.create() - Creating " + customer.getFirstName() + " " + customer.getLastName());

        // Check to make sure the data fits with the parameters in the Customer model and passes validation.
        validator.validateCustomer(customer);

        //Create client service instance to make REST requests to upstream service
        ResteasyWebTarget target = client.target("http://ec2-18-119-125-232.us-east-2.compute.amazonaws.com/");
        AreaService service = target.proxy(AreaService.class);

        try {
            Area area = service.getAreaById(Integer.parseInt(customer.getPhoneNumber().substring(1, 4)));
            customer.setState(area.getState());
        } catch (ClientErrorException e) {
            if (e.getResponse().getStatusInfo() == Response.Status.NOT_FOUND) {
                throw new InvalidAreaCodeException("The area code provided does not exist", e);
            } else {
                throw e;
            }
        }

        // Write the customer to the database.
        return crud.create(customer);
    }

    /**
     * <p>Updates an existing Customer object in the application database with the provided Customer object.<p/>
     *
     * <p>Validates the data in the provided Customer object using a CustomerValidator object.<p/>
     *
     * @param customer The Customer object to be passed as an update to the application database
     * @return The Customer object that has been successfully updated in the application database
     * @throws ConstraintViolationException, ValidationException, Exception
     */
    @Override
    public Customer update(Customer customer) throws ConstraintViolationException, ValidationException, Exception{
        log.info("CustomerService.update() - Updating " + customer.getFirstName() + " " + customer.getLastName());

        // Check to make sure the data fits with the parameters in the Customer model and passes validation.
        validator.validateCustomer(customer);

        // Set client target location and define the proxy API class
        ResteasyWebTarget target = client.target("http://ec2-18-119-125-232.us-east-2.compute.amazonaws.com/");
        AreaService service = target.proxy(AreaService.class);

        try {
            Area area = service.getAreaById(Integer.parseInt(customer.getPhoneNumber().substring(1, 4)));
            customer.setState(area.getState());
        } catch (ClientErrorException e) {
            if (e.getResponse().getStatusInfo() == Response.Status.NOT_FOUND) {
                throw new InvalidAreaCodeException("The area code provided does not exist", e);
            } else {
                throw e;
            }
        }

        // Either update the customer or add it if it can't be found.
        return crud.update(customer);
    }

    /**
     * <p>Deletes the provided Customer object from the application database if found there.<p/>
     *
     * @param customer The Customer object to be removed from the application database
     * @return The Customer object that has been successfully removed from the application database; or null
     * @throws Exception
     */
    @Override
    public Customer delete(Customer customer) throws Exception{
        log.info("delete() - Deleting " + customer.toString());

        Customer deletedCustomer = null;

        if (customer.getId() != null) {
            deletedCustomer = crud.delete(customer);
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedCustomer;
    }
}
