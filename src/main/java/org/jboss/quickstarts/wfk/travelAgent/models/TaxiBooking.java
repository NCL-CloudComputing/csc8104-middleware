package org.jboss.quickstarts.wfk.travelAgent.models;

import java.util.Date;

public class TaxiBooking {


    private Long id;


    private Date bookingDate;


    private TaxiCustomer customer;


    private Taxi taxi;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public TaxiCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(TaxiCustomer customer) {
        this.customer = customer;
    }

    public Taxi getTaxi() {
        return taxi;
    }

    public void setTaxi(Taxi taxi) {
        this.taxi = taxi;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxiBooking)) return false;
        TaxiBooking booking = (TaxiBooking) o;
        if (!bookingDate.equals(booking.bookingDate) || !customer.equals(booking.customer) || !taxi.equals(booking.taxi)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = Math.toIntExact(id);
        result = 31 * result + bookingDate.hashCode();
        result = 31 * result + customer.hashCode();
        result = 31 * result + taxi.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" + "id=" + id + ", bookingDate=" + bookingDate + ", customer=" + customer + ", taxi=" + taxi + '}';
    }
}
