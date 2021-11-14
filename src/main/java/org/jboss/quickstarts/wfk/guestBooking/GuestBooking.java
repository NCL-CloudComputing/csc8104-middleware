package org.jboss.quickstarts.wfk.guestBooking;

import org.jboss.quickstarts.wfk.booking.Booking;
import org.jboss.quickstarts.wfk.customer.Customer;

import java.io.Serializable;

public class GuestBooking implements Serializable {

    private Booking booking;

    private Customer customer;

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
