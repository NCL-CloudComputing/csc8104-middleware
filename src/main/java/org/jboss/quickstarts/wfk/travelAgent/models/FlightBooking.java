package org.jboss.quickstarts.wfk.travelAgent.models;

import org.jboss.quickstarts.wfk.customer.Customer;

public class FlightBooking {


    private Long id;


    private FlightCustomer customer;


    private Flight flight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(FlightCustomer customer) {
        this.customer = customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
