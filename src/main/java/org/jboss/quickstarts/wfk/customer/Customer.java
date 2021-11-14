package org.jboss.quickstarts.wfk.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.quickstarts.wfk.booking.Booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * this is the pojo for the user
 *
 * The class also specifies how a customer are retrieved from the database
 *
 * @author Chenjie Li
 */

@Entity
@NamedQueries({
        @NamedQuery(name = Customer.FIND_ALL, query = "SELECT a FROM Customer a ORDER BY a.lastName ASC, a.firstName ASC"),
        @NamedQuery(name = Customer.FIND_BY_EMAIL, query = "SELECT a FROM Customer a WHERE a.email = :email")
})
@XmlRootElement
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Customer implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
   // private static final long serialVersionUID = 1L;

    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_BY_EMAIL = "Customer.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-']+", message = "Please use a name without numbers or specials")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-']+", message = "Please use a name without numbers or specials")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotEmpty
    @Email(message = "The email address must be in the format of name@domain.com")
    private String email;

    @NotNull
    @Pattern(regexp = "^\\([2-9][0-8][0-9]\\)\\s?[0-9]{3}\\-[0-9]{4}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Past(message = "Birthdates can not be in the future. Please choose one from the past")
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "state")
    private String state;

    @JsonIgnore
    @OneToMany(cascade=CascadeType.REMOVE,mappedBy="customer",orphanRemoval = true)
    private Set<Booking> bookings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        if (getId() != null ? !getId().equals(customer.getId()) : customer.getId() != null) return false;
        if (getFirstName() != null ? !getFirstName().equals(customer.getFirstName()) : customer.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(customer.getLastName()) : customer.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(customer.getEmail()) : customer.getEmail() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(customer.getPhoneNumber()) : customer.getPhoneNumber() != null)
            return false;
        if (getBirthDate() != null ? !getBirthDate().equals(customer.getBirthDate()) : customer.getBirthDate() != null)
            return false;
        return getState() != null ? getState().equals(customer.getState()) : customer.getState() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getBirthDate() != null ? getBirthDate().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", state='" + state + '\'' +
                '}';
    }
}
