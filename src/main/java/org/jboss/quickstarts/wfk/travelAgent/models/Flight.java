package org.jboss.quickstarts.wfk.travelAgent.models;

import java.io.Serializable;


;

/**
 * @author ChenjieLi
 * @create 2021-11-13 9:57
 */

public class Flight implements Serializable {

    private Long id;

    private String number;

    private String pointOfDeparture;

    private String destination;

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
}
