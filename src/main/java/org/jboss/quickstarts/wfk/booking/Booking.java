package org.jboss.quickstarts.wfk.booking;


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
        @NamedQuery(name = Booking.FIND_BY_USERID, query = "SELECT a FROM Booking a WHERE a.userId = :userId"),
        @NamedQuery(name = Booking.FIND_BY_HOTELID, query = "SELECT a FROM Booking a WHERE a.hotelId = :hotelId"),
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
    @Temporal(TemporalType.DATE)
    private Date orderDate;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "hotel_id")
    private Long hotelId;

    @Column(name = "qty_item")
    private int qtyItem;

    @Column(name = "tol_price")
    private double tolPrice;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public int getQtyItem() {
        return qtyItem;
    }

    public void setQtyItem(int qtyItem) {
        this.qtyItem = qtyItem;
    }

    public double getTolPrice() {
        return tolPrice;
    }

    public void setTolPrice(double tolPrice) {
        this.tolPrice = tolPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;

        Booking booking = (Booking) o;

        if (getQtyItem() != booking.getQtyItem()) return false;
        if (Double.compare(booking.getTolPrice(), getTolPrice()) != 0) return false;
        if (getId() != null ? !getId().equals(booking.getId()) : booking.getId() != null) return false;
        if (getOrderDate() != null ? !getOrderDate().equals(booking.getOrderDate()) : booking.getOrderDate() != null)
            return false;
        if (getUserId() != null ? !getUserId().equals(booking.getUserId()) : booking.getUserId() != null) return false;
        return getHotelId() != null ? getHotelId().equals(booking.getHotelId()) : booking.getHotelId() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getHotelId() != null ? getHotelId().hashCode() : 0);
        result = 31 * result + getQtyItem();
        temp = Double.doubleToLongBits(getTolPrice());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", userId=" + userId +
                ", hotelId=" + hotelId +
                ", qtyItem=" + qtyItem +
                ", tolPrice=" + tolPrice +
                '}';
    }
}
