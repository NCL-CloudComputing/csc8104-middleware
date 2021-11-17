package org.jboss.quickstarts.wfk.hotel;



import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.quickstarts.wfk.booking.Booking;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Set;

@Entity
@NamedQueries({
        @NamedQuery(name = Hotel.FIND_ALL, query = "SELECT h FROM Hotel h ORDER BY h.name ASC"),
        @NamedQuery(name = Hotel.FIND_BY_NAME, query = "SELECT h FROM Hotel h WHERE h.name = :name")
})
@XmlRootElement
@Table(name = "hotel",uniqueConstraints = @UniqueConstraint(columnNames = "phone_number"))

public class Hotel implements Serializable {

    public static final String FIND_ALL = "Hotel.findAll";
    public static final String FIND_BY_NAME = "Hotel.findByName";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotEmpty
    @Size(min = 1, max = 50)
    @Pattern(regexp = "[A-Za-z-' .]+", message = "Please use a name without numbers or specials")
    @Column(name = "name")
    private String name;

    @NotNull
    @Pattern(regexp = "^0[0-9]{10}$")
    @Column(name = "phone_number")
    private String phoneNumber;


    @Size(min = 1, max = 6)
    @NotEmpty
    @Column(name = "post_code")
    private String postCode;


    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL,mappedBy="hotel",orphanRemoval = true)
    private Set<Booking> bookings;



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


    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
        return getPostCode() != null ? getPostCode().equals(hotel.getPostCode()) : hotel.getPostCode() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getPostCode() != null ? getPostCode().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
