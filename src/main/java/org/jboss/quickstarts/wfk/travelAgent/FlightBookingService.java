package org.jboss.quickstarts.wfk.travelAgent;

import org.jboss.quickstarts.wfk.area.Area;
import org.jboss.quickstarts.wfk.travelAgent.models.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface FlightBookingService {

    @GET
    @Path("/flights/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Flight getFlightById(@PathParam("id") Long id);


    @GET
    @Path("/flights")
    @Produces(MediaType.APPLICATION_JSON)
    List<Flight> getAllFlights();

    @POST
    @Path("/booking")
    @Consumes(MediaType.APPLICATION_JSON)
    FlightBooking createFlightBooking(FlightBooking flightBooking);

    @DELETE
    @Path("/booking/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteFlightBooking(@PathParam("id") Long id);

    @POST
    @Path("/customers")
    @Consumes(MediaType.APPLICATION_JSON)
    TaxiCustomer createCustomer(TaxiCustomer taxiCustomer);

    @GET
    @Path("/customers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    TaxiCustomer getConsumerById(@PathParam("id") Long id);

}