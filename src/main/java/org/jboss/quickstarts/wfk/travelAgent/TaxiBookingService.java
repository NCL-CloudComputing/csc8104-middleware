package org.jboss.quickstarts.wfk.travelAgent;

import org.jboss.quickstarts.wfk.area.Area;
import org.jboss.quickstarts.wfk.travelAgent.models.Taxi;
import org.jboss.quickstarts.wfk.travelAgent.models.TaxiBooking;
import org.jboss.quickstarts.wfk.travelAgent.models.TaxiCustomer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
public interface TaxiBookingService {

    @GET
    @Path("/taxis/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    Taxi getTaxiById(@PathParam("id") Long id);

    @GET
    @Path("/taxis")
    @Produces(MediaType.APPLICATION_JSON)
    List<Taxi> getAllTaxis();

    @POST
    @Path("/bookings")
    @Consumes(MediaType.APPLICATION_JSON)
    TaxiBooking createTaxiBooking(TaxiBooking taxiBooking);

    @POST
    @Path("/customers")
    @Consumes(MediaType.APPLICATION_JSON)
    TaxiCustomer createCustomer(TaxiCustomer taxiCustomer);

    @DELETE
    @Path("/bookings/{id:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    void deleteTaxiBooking(@PathParam("id") Long id);


    @GET
    @Path("/customers/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    TaxiCustomer getConsumerById(@PathParam("id") Long id);




    @GET
    @Path("/customers/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    TaxiCustomer getConsumerByEmail(@PathParam("email") String email);
}