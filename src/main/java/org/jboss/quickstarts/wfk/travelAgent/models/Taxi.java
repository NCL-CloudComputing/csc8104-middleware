
package org.jboss.quickstarts.wfk.travelAgent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Email;
import org.jboss.quickstarts.wfk.booking.Booking;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModelProperty;

/**

 */

public class Taxi implements Serializable {


	private Long id;


	private String registration;


	private int seats;


	private List<Booking> bookings;

	public Long getId() {
		return id;
	}

	public String getRegistration() {
		return registration;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Taxi)) return false;
		Taxi taxi = (Taxi) o;
		if (!registration.equals(taxi.registration)) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(registration);
	}

	@Override
	public String toString() {
		return "Taxi{" + "id=" + id + ", registration='" + registration + '\'' + ", seats=" + seats + ", bookings=" + bookings + '}';
	}
}
