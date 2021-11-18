package org.jboss.quickstarts.wfk.travelAgent.models;

import java.io.Serializable;
import java.util.Date;

public class FlightBooking implements Serializable {

	private Long id;
	private Long flightId;
	private Long customerId;
	private String futureDate;

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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFutureDate() {
		return futureDate;
	}

	public void setFutureDate(String futureDate) {
		this.futureDate = futureDate;
	}

	@Override
	public String toString() {
		return "FlightBooking{" +
				"id=" + id +
				", flightId=" + flightId +
				", customerId=" + customerId +
				", futureDate=" + futureDate +
				'}';
	}
}
