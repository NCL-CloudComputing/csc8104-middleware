package org.jboss.quickstarts.wfk.travelAgent;

import javax.persistence.Column;

public class TravelAgent {


    private Long flightId;


    private Long taxiId;


    private Long hotelId;


    private Long customerId;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getTaxiId() {
        return taxiId;
    }

    public void setTaxiId(Long taxiId) {
        this.taxiId = taxiId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TravelAgent)) return false;

        TravelAgent that = (TravelAgent) o;

        if (getFlightId() != null ? !getFlightId().equals(that.getFlightId()) : that.getFlightId() != null)
            return false;
        if (getTaxiId() != null ? !getTaxiId().equals(that.getTaxiId()) : that.getTaxiId() != null) return false;
        if (getHotelId() != null ? !getHotelId().equals(that.getHotelId()) : that.getHotelId() != null) return false;
        return getCustomerId() != null ? getCustomerId().equals(that.getCustomerId()) : that.getCustomerId() == null;
    }

    @Override
    public int hashCode() {
        int result = getFlightId() != null ? getFlightId().hashCode() : 0;
        result = 31 * result + (getTaxiId() != null ? getTaxiId().hashCode() : 0);
        result = 31 * result + (getHotelId() != null ? getHotelId().hashCode() : 0);
        result = 31 * result + (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return result;
    }
}
