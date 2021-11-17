package org.jboss.quickstarts.wfk.travelAgent.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.jboss.quickstarts.wfk.booking.Booking;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenjieLi
 * @create 2021-11-13 9:57
 */

public class Flight implements Serializable {

    private Long id;


    private String number;

    private String pointOfDeparture;


    private String destination;



    public String getPointOfDeparture() {
        return pointOfDeparture;
    }

    public void setPointOfDeparture(String pointOfDeparture) {
        this.pointOfDeparture = pointOfDeparture;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", pointOfDeparture='" + pointOfDeparture + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
