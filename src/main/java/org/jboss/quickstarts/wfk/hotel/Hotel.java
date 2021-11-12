package org.jboss.quickstarts.wfk.hotel;



import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.quickstarts.wfk.customer.Customer;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@NamedQueries({
        @NamedQuery(name = Hotel.FIND_ALL, query = "SELECT h FROM Hotel h ORDER BY h.hName ASC"),
        @NamedQuery(name = Hotel.FIND_BY_HNAME, query = "SELECT h FROM Hotel h WHERE h.hName = :hName"),
        @NamedQuery(name = Hotel.FIND_BY_EMAIL, query = "SELECT h FROM Hotel h WHERE h.email = :email")
})
@XmlRootElement
@Table(name = "hotel")

public class Hotel implements Serializable {

    public static final String FIND_ALL = "Hotel.findAll";
    public static final String FIND_BY_HNAME = "Hotel.findByHname";
    public static final String FIND_BY_EMAIL = "Hotel.findByEmail";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long hId;

    @Min(0)
    @Column(name = "remaining_number")
    private Integer hRemainingNum;

    @NotNull
    @NotEmpty
    @Column(name = "hName")
    private String hName;

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




    @Column(name = "type")
    private String type;

    @Column(name = "state")
    private String state;


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public Long gethId() {
        return hId;
    }

    public void sethId(Long hId) {
        this.hId = hId;
    }

    public Integer gethRemainingNum() {
        return hRemainingNum;
    }

    public void sethRemainingNum(Integer hRemainingNum) {
        this.hRemainingNum = hRemainingNum;
    }

    public String gethName() {
        return hName;
    }

    public void sethName(String hName) {
        this.hName = hName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;

        Hotel hotel = (Hotel) o;

        if (gethId() != null ? !gethId().equals(hotel.gethId()) : hotel.gethId() != null) return false;
        if (gethRemainingNum() != null ? !gethRemainingNum().equals(hotel.gethRemainingNum()) : hotel.gethRemainingNum() != null)
            return false;
        if (gethName() != null ? !gethName().equals(hotel.gethName()) : hotel.gethName() != null) return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(hotel.getPhoneNumber()) : hotel.getPhoneNumber() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(hotel.getEmail()) : hotel.getEmail() != null) return false;
        if (getPrice() != null ? !getPrice().equals(hotel.getPrice()) : hotel.getPrice() != null) return false;
        if (getAddr() != null ? !getAddr().equals(hotel.getAddr()) : hotel.getAddr() != null) return false;
        if (getType() != null ? !getType().equals(hotel.getType()) : hotel.getType() != null) return false;
        return getState() != null ? getState().equals(hotel.getState()) : hotel.getState() == null;
    }

    @Override
    public int hashCode() {
        int result = gethId() != null ? gethId().hashCode() : 0;
        result = 31 * result + (gethRemainingNum() != null ? gethRemainingNum().hashCode() : 0);
        result = 31 * result + (gethName() != null ? gethName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (getAddr() != null ? getAddr().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hId=" + hId +
                ", hRemainingNum=" + hRemainingNum +
                ", hName='" + hName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", price=" + price +
                ", addr='" + addr + '\'' +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
