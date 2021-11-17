package org.jboss.quickstarts.wfk.travelAgent.models;

import org.jboss.quickstarts.wfk.customer.Customer;

import java.util.Date;

public class FlightBooking {


    private Long id;


    private Long customerId;


    private Long flightId;

    private Date futureDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Date getFutureDate() {
        return futureDate;
    }

    public void setFutureDate(Date futureDate) {
        this.futureDate = futureDate;
    }

    @Override
    public String toString() {
        return "FlightBooking{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", flightId='" + flightId + '\'' +
                ", futureDate=" + futureDate +
                '}';
    }
}
