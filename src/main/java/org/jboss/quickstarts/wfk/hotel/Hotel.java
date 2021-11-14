package org.jboss.quickstarts.wfk.hotel;



import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.quickstarts.wfk.booking.Booking;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Hotel.FIND_ALL, query = "SELECT h FROM Hotel h ORDER BY h.name ASC"),
        @NamedQuery(name = Hotel.FIND_BY_NAME, query = "SELECT h FROM Hotel h WHERE h.name = :name"),
        @NamedQuery(name = Hotel.FIND_BY_EMAIL, query = "SELECT h FROM Hotel h WHERE h.email = :email")
})
@XmlRootElement
@Table(name = "hotel")

public class Hotel implements Serializable {

    public static final String FIND_ALL = "Hotel.findAll";
    public static final String FIND_BY_NAME = "Hotel.findByName";
    public static final String FIND_BY_EMAIL = "Hotel.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "name")
    private String name;

    @NotNull
    @Pattern(regexp = "^\\([2-9][0-8][0-9]\\)\\s?[0-9]{3}\\-[0-9]{4}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @NotEmpty
    @Email(message = "The email address must be in the format of name@domain.com")
    private String email;


    @Min(0)
    @Column(name = "price")
    private Double price;

    @NotNull
    @NotEmpty
    @Column(name = "addr")
    private String addr;

    @Column(name = "state")
    private String state;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.REMOVE,mappedBy="hotel",orphanRemoval = true)
    private Set<Booking> bookings;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;

        Hotel hotel = (Hotel) o;

        if (getId() != null ? !getId().equals(hotel.getId()) : hotel.getId() != null) return false;
        if (getName() != null ? !getName().equals(hotel.getName()) : hotel.getName() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(hotel.getPhoneNumber()) : hotel.getPhoneNumber() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(hotel.getEmail()) : hotel.getEmail() != null) return false;
        if (getPrice() != null ? !getPrice().equals(hotel.getPrice()) : hotel.getPrice() != null) return false;
        if (getAddr() != null ? !getAddr().equals(hotel.getAddr()) : hotel.getAddr() != null) return false;
        return getState() != null ? getState().equals(hotel.getState()) : hotel.getState() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getAddr() != null ? getAddr().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", price=" + price +
                ", addr='" + addr + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
