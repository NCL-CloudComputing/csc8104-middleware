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


    private String depaturePoint;


    private String destination;


}
