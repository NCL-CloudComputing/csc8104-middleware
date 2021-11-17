package org.jboss.quickstarts.wfk.booking;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jboss.quickstarts.wfk.customer.Customer;
import org.jboss.quickstarts.wfk.hotel.Hotel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * this is the pojo for the booking
 *
 * The class also specifies how a booking are retrieved from the database
 *
 * @author Chenjie Li
 */

@Entity
@NamedQueries({
        @NamedQuery(name = Booking.FIND_ALL, query = "SELECT c FROM Booking c ORDER BY c.futureDate DESC"),
        @NamedQuery(name = Booking.FIND_BY_CUSTOMER_ID, query = "SELECT c FROM Booking c WHERE c.customer.id = :customerId ORDER BY c.futureDate DESC"),
        @NamedQuery(name = Booking.FIND_BY_HOTEL_ID, query = "SELECT c FROM Booking c WHERE c.hotel.id = :hotelId ORDER BY c.futureDate DESC")
})
@XmlRootElement
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Booking implements Serializable {

    public static final String FIND_ALL = "Booking.findAll";
    public static final String FIND_BY_CUSTOMER_ID = "Booking.findByCustomerId";
    public static final String FIND_BY_HOTEL_ID = "Booking.findByHotelId";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @Column(name = "future_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date futureDate;

    @ManyToOne()
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name="hotel_id")
    private Hotel hotel;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }


    public Date getFutureDate() {
        return futureDate;
    }

    public void setFutureDate(Date futureDate) {
        this.futureDate = futureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;

        Booking booking = (Booking) o;

        if (getId() != null ? !getId().equals(booking.getId()) : booking.getId() != null) return false;
        if (getFutureDate() != null ? !getFutureDate().equals(booking.getFutureDate()) : booking.getFutureDate() != null)
            return false;
        if (getCustomer() != null ? !getCustomer().equals(booking.getCustomer()) : booking.getCustomer() != null)
            return false;
        return getHotel() != null ? getHotel().equals(booking.getHotel()) : booking.getHotel() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFutureDate() != null ? getFutureDate().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getHotel() != null ? getHotel().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", futureDate=" + futureDate +
                ", customer=" + customer +
                ", hotel=" + hotel +
                '}';
    }
}
