package org.jboss.quickstarts.wfk.customer;


import org.jboss.quickstarts.wfk.contact.Contact;

import javax.validation.ValidationException;

/**
 * <p>ValidationException caused if a name has an invalid length.</p>
 *
 * <p>This violates the minimum length of a name.</p>
 *
 * @author Chenjie Li
 * @see Contact
 */

public class InvalidNameException extends ValidationException {
    public InvalidNameException(String message) {
        super(message);
    }
}
