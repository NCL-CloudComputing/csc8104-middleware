package org.jboss.quickstarts.wfk.booking;


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
        @NamedQuery(name = Booking.FIND_ALL, query = "SELECT a FROM Booking a ORDER BY a.orderDate DESC"),
        @NamedQuery(name = Booking.FIND_BY_USERID, query = "SELECT a FROM Booking a WHERE a.cId = :cId"),
        @NamedQuery(name = Booking.FIND_BY_HOTELID, query = "SELECT a FROM Booking a WHERE a.hId = :hId"),
        @NamedQuery(name = Booking.FIND_BY_ID, query = "SELECT a FROM Booking a WHERE a.id = :id")
})
@XmlRootElement
@Table(name = "booking", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Booking implements Serializable {

    public static final String FIND_ALL = "Booking.findAll";
    public static final String FIND_BY_USERID = "Booking.findByUserId";
    public static final String FIND_BY_HOTELID = "Booking.findByHotelId";
    public static final String FIND_BY_ID = "Booking.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull
    @Column(name = "order_date")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;



    @NotNull
    @Column(name = "qty_item")
    private Integer qtyItem;

    @Column(name = "tol_price")
    private Double tolPrice;

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @Column(name = "cId")
    private Long cId;

    @Column(name = "hId")
    private Long hId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getQtyItem() {
        return qtyItem;
    }

    public void setQtyItem(Integer qtyItem) {
        this.qtyItem = qtyItem;
    }

    public Double getTolPrice() {
        return tolPrice;
    }

    public void setTolPrice(Double tolPrice) {
        this.tolPrice = tolPrice;
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

    public Long getcId() {
        return cId;
    }

    public void setcId(Long cId) {
        this.cId = cId;
    }

    public Long gethId() {
        return hId;
    }

    public void sethId(Long hId) {
        this.hId = hId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;

        Booking booking = (Booking) o;

        if (getId() != null ? !getId().equals(booking.getId()) : booking.getId() != null) return false;
        if (getOrderDate() != null ? !getOrderDate().equals(booking.getOrderDate()) : booking.getOrderDate() != null)
            return false;
        if (getQtyItem() != null ? !getQtyItem().equals(booking.getQtyItem()) : booking.getQtyItem() != null)
            return false;
        if (getTolPrice() != null ? !getTolPrice().equals(booking.getTolPrice()) : booking.getTolPrice() != null)
            return false;
        if (getCustomer() != null ? !getCustomer().equals(booking.getCustomer()) : booking.getCustomer() != null)
            return false;
        if (getHotel() != null ? !getHotel().equals(booking.getHotel()) : booking.getHotel() != null) return false;
        if (getcId() != null ? !getcId().equals(booking.getcId()) : booking.getcId() != null) return false;
        return gethId() != null ? gethId().equals(booking.gethId()) : booking.gethId() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        result = 31 * result + (getQtyItem() != null ? getQtyItem().hashCode() : 0);
        result = 31 * result + (getTolPrice() != null ? getTolPrice().hashCode() : 0);
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getHotel() != null ? getHotel().hashCode() : 0);
        result = 31 * result + (getcId() != null ? getcId().hashCode() : 0);
        result = 31 * result + (gethId() != null ? gethId().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", qtyItem=" + qtyItem +
                ", tolPrice=" + tolPrice +
                ", customer=" + customer +
                ", hotel=" + hotel +
                ", cId=" + cId +
                ", hId=" + hId +
                '}';
    }
}
