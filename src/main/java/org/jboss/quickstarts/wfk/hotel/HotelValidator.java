package org.jboss.quickstarts.wfk.hotel;

import org.jboss.quickstarts.wfk.contact.UniqueEmailException;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>This class provides methods to check Contact objects against arbitrary requirements.</p>
 *
 * @author Chenjie Li
 */
public class HotelValidator {
    @Inject
    private Validator validator;

    @Inject
    private HotelRepository crud;

    /**
     * <p>Validates the given Contact object and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.<p/>
     *
     *
     * <p>If the error is caused because an existing contact with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.</p>
     *
     *
     * @param hotel The Contact object to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If contact with the same email already exists
     */
    void validateContact( Hotel hotel) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Hotel>> violations = validator.validate(hotel);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (emailAlreadyExists(hotel.getEmail(), hotel.getId())) {
            throw new UniqueEmailException("Unique Email Violation");
        }
    }

    /**
     * <p>Checks if a contact with the same email address is already registered. This is the only way to easily capture the
     * "@UniqueConstraint(columnNames = "email")" constraint from the Contact class.</p>
     *
     * <p>Since Update will being using an email that is already in the database we need to make sure that it is the email
     * from the record being updated.</p>
     *
     * @param email The email to check is unique
     * @param id The user id to check the email against if it was found
     * @return boolean which represents whether the email was found, and if so if it belongs to the user with id
     */
    boolean emailAlreadyExists(String email, Long id) {
        Hotel hotel = null;
        Hotel hotelWithID = null;
        try {
            hotel = crud.findByEmail(email);
        } catch (NoResultException e) {
            // ignore
        }

        if (hotel != null && id != null) {
            try {
                hotelWithID = crud.findById(id);
                if (hotelWithID != null && hotelWithID.getEmail().equals(email)) {
                    hotel = null;
                }
            } catch (NoResultException e) {
                // ignore
            }
        }
        return hotel != null;
    }
}
