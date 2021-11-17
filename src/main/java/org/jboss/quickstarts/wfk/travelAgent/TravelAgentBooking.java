package org.jboss.quickstarts.wfk.travelAgent;

import org.jboss.quickstarts.wfk.customer.Customer;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


@Entity
@NamedQueries({
        @NamedQuery(name = TravelAgentBooking.FIND_ALL, query = "SELECT a FROM TravelAgentBooking a")
})
@XmlRootElement
@Table(name = "travelAgentBooking")
public class TravelAgentBooking implements Serializable {

    public static final String FIND_ALL = "TravelAgentBooking.findAll";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    @Column(name = "flight_booking_id")
    private Long flightId;

    @Column(name = "taxi_booking_id")
    private Long taxiId;

    @Column(name = "hotel_booking_id")
    private Long hotelId;

    @Column(name = "customer_id")
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        if (!(o instanceof TravelAgentBooking)) return false;

        TravelAgentBooking that = (TravelAgentBooking) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getFlightId() != null ? !getFlightId().equals(that.getFlightId()) : that.getFlightId() != null)
            return false;
        if (getTaxiId() != null ? !getTaxiId().equals(that.getTaxiId()) : that.getTaxiId() != null) return false;
        if (getHotelId() != null ? !getHotelId().equals(that.getHotelId()) : that.getHotelId() != null) return false;
        return getCustomerId() != null ? getCustomerId().equals(that.getCustomerId()) : that.getCustomerId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFlightId() != null ? getFlightId().hashCode() : 0);
        result = 31 * result + (getTaxiId() != null ? getTaxiId().hashCode() : 0);
        result = 31 * result + (getHotelId() != null ? getHotelId().hashCode() : 0);
        result = 31 * result + (getCustomerId() != null ? getCustomerId().hashCode() : 0);
        return result;
    }
}
