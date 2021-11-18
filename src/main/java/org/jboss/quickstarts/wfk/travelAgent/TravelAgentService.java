package org.jboss.quickstarts.wfk.travelAgent;


import org.jboss.quickstarts.wfk.booking.Booking;
import org.jboss.quickstarts.wfk.booking.BookingService;
import org.jboss.quickstarts.wfk.customer.Customer;
import org.jboss.quickstarts.wfk.customer.CustomerService;
import org.jboss.quickstarts.wfk.hotel.Hotel;
import org.jboss.quickstarts.wfk.hotel.HotelService;
import org.jboss.quickstarts.wfk.travelAgent.models.*;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import javax.ws.rs.core.Response;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;


@Dependent
public class TravelAgentService {

    final static String FLIGHT_BOOKING = "http://csc8104-build-stream-minghaotian-dev.apps.sandbox.x8i5.p1.openshiftapps.com";
    final static String TAXI_BOOKING = "http://csc8104-build-stream-chenbryan0707-dev.apps.sandbox.x8i5.p1.openshiftapps.com";

    @Inject
    private @Named("logger")
    Logger log;


    @Inject
    private CustomerService customerService;

    @Inject
    private TravelAgentBookingRepository crud;

    @Inject
    private HotelService hotelService;

    @Inject
    private BookingService hotelBookingService;



    private ResteasyClient client;

    /**
     * <p>Create a new client which will be used for our outgoing REST client communication</p>
     */
    public TravelAgentService() {
        // Create client service instance to make REST requests to upstream service
        client = new ResteasyClientBuilder().build();
    }


    public TravelAgentBooking create(TravelAgent travelAgent) throws Exception {


        Date date1 = new Date();
        Calendar c1 = new GregorianCalendar();
        c1.setTime(date1);
        c1.add(Calendar.MONTH,200);
        date1=c1.getTime();
        TravelAgentBooking travelAgentBooking= new TravelAgentBooking();

        travelAgentBooking.setCustomerId(travelAgent.getCustomerId());
        //call other colleagues' flight server
        ResteasyWebTarget target = client.target(FLIGHT_BOOKING);
        FlightBookingService flightBookingService = target.proxy(FlightBookingService.class);

        //call other colleagues' taxi server
        ResteasyWebTarget target1 = client.target(TAXI_BOOKING);
        TaxiBookingService taxiBookingService = target1.proxy(TaxiBookingService.class);

        FlightBooking flightBooking = new FlightBooking();
        FlightCustomer flightCustomer = new FlightCustomer();


        TaxiBooking taxiBooking = new TaxiBooking();
        TaxiCustomer taxiCustomer = new TaxiCustomer();



        if (travelAgent.getHotelId()==null|| travelAgent.getHotelId()==0){
            throw new IllegalArgumentException("the hotel Id is null");
        }

        if (travelAgent.getCustomerId()==null|| travelAgent.getCustomerId()==0){
            throw new IllegalArgumentException("the Customer Id is null");
        }

        Long hotelId = travelAgent.getHotelId();

        Long hotelCustomerId = travelAgent.getCustomerId();


        Customer hotelCustomer = customerService.findById(hotelCustomerId);
        Hotel hotel = hotelService.findById(hotelId);

        if (hotelCustomer ==null || hotelCustomer.getId() ==0 ){
            throw new IllegalArgumentException("please create the customer firstly !!!");
        }
        Booking hotelBooking = new Booking();

        hotelBooking.setCustomer(hotelCustomer);
        hotelBooking.setHotel(hotel);
        hotelBooking.setFutureDate(new Date());

        Booking booking1 = hotelBookingService.create(hotelBooking);


        if (booking1==null||booking1.getId()==0){
            hotelBookingService.delete(booking1);
            throw new Exception(" the hotel service failed ,please contact the administration!!!");
        }

        travelAgentBooking.setHotelId(booking1.getId());
//        //get flightBookingId
//        Long flightId = travelAgent.getFlightId();
//
//        //find ALREADY exists flight
//        Flight flightById = flightBookingService.getFlightById(flightId);
//        FlightCustomer consumerByEmail =null;
//        try {
//            consumerByEmail= flightBookingService.getConsumerByEmail(hotelCustomer.getEmail());
//        }catch (Exception e){
//
//        }
//
//        if (consumerByEmail!=null&&consumerByEmail.getId()!=0){
//            flightBooking.setCustomerId(consumerByEmail.getId());
//        }else {
//            flightCustomer.setName(hotelCustomer.getName());
//            flightCustomer.setEmail(hotelCustomer.getEmail());
//            flightCustomer.setPhoneNumber(hotelCustomer.getPhoneNumber());
//            log.info("request body:"+flightCustomer.toString());
//            FlightCustomer flightCustomer1 = flightBookingService.createCustomer(flightCustomer);
//            flightBooking.setCustomerId(flightCustomer1.getId());
//        }
//
//
//
//
//
//        flightBooking.setFutureDate("2034-07-07");
//
//        flightBooking.setFlightId(flightById.getId());
//
//        //create flightBooking
//        log.info(flightBooking.toString());
//        FlightBooking flightBooking1 = null;
//        try {
//            FlightBooking flightBooking2 =new FlightBooking();
//
//            flightBooking2.setFlightId(7L);
//            flightBooking2.setCustomerId(12L);
//            flightBooking2.setFutureDate("2022-09-08");
//
//            flightBooking1 = flightBookingService.createFlightBooking(flightBooking2);
//
//            //if the flight has error, it will roll back all bookings has created.
//            if (flightBooking1==null||flightBooking1.getId()==0){
//                hotelBookingService.delete(booking1);
//
//            }
//        }catch (Exception e){
//            throw new Exception(e);
//        }
//
//
//
//        travelAgentBooking.setFlightId(flightBooking1.getId());

        //get flightBookingId
//        Long flightId = travelAgent.getFlightId();
//
//
//
//        Flight flightById = flightBookingService.getFlightById(flightId);
//        FlightCustomer flightConsumerByEmail =null;
//        try{
//            flightConsumerByEmail= flightBookingService.getConsumerByEmail(hotelCustomer.getEmail());
//        }catch (Exception e){
//        }
//
//        if (flightConsumerByEmail != null&& flightConsumerByEmail.getId()!=0){
//            flightBooking.setCustomerId(flightConsumerByEmail.getId());
//        }else {
//            flightCustomer.setName(hotelCustomer.getName());
//            flightCustomer.setEmail(hotelCustomer.getEmail());
//            flightCustomer.setPhoneNumber(hotelCustomer.getPhoneNumber());
//
//            log.info(taxiCustomer.toString());
//            FlightCustomer flightCustomer1 = flightBookingService.createCustomer(flightCustomer);
//            flightBooking.setCustomerId(flightCustomer1.getId());
//        }
//
//
//
//        flightBooking.setFutureDate("2023-01-01");
//        flightBooking.setFlightId(flightById.getId());
//        //create flightBooking
//        FlightBooking flightBooking1 = null;
//        try{
//            log.info(flightBooking.toString());
//            flightBooking1 = flightBookingService.createFlightBooking(flightBooking);
//            if (flightBooking1 == null || flightBooking1.getId()==0){
//                hotelBookingService.delete(booking1);
//                throw new Exception(" the flight service failed ,please contact the administration!!!");
//            }
//        }catch (Exception e){
//            hotelBookingService.delete(booking1);
//            throw new Exception(" the flight service failed ,please contact the administration!!!",e);
//        }
//
//
//        travelAgentBooking.setFlightId(flightBooking1.getId());

        //get taxiBookingId
        Long taxiId = travelAgent.getTaxiId();



        Taxi taxiById = taxiBookingService.getTaxiById(taxiId);
        TaxiCustomer taxiConsumerByEmail =null;
        try{
            taxiConsumerByEmail= taxiBookingService.getConsumerByEmail(hotelCustomer.getEmail());
        }catch (Exception e){
        }

        if (taxiConsumerByEmail != null&& taxiConsumerByEmail.getId()!=0){
            taxiBooking.setCustomer(taxiConsumerByEmail);
        }else {
            taxiCustomer.setName(hotelCustomer.getName());
            taxiCustomer.setEmail(hotelCustomer.getEmail());
            taxiCustomer.setPhoneNumber(hotelCustomer.getPhoneNumber());

            log.info(taxiCustomer.toString());
            TaxiCustomer taxiCustomer1 = taxiBookingService.createCustomer(taxiCustomer);
            taxiBooking.setCustomer(taxiCustomer1);
        }



        taxiBooking.setBookingDate(date1);
        taxiBooking.setTaxi(taxiById);
        //create taxiBooking
        TaxiBooking taxiBooking1 = null;
        try{
            log.info(taxiBooking.toString());
            taxiBooking1 = taxiBookingService.createTaxiBooking(taxiBooking);
            if (taxiBooking1 == null || taxiBooking1.getId()==0){
                hotelBookingService.delete(booking1);
                //flightBookingService.deleteFlightBooking(flightBooking1.getId());
                throw new Exception(" the taxi service failed ,please contact the administration!!!");
            }
        }catch (Exception e){
            hotelBookingService.delete(booking1);
            //flightBookingService.deleteFlightBooking(flightBooking1.getId());
            throw new Exception(" the taxi service failed ,please contact the administration!!!");
        }


        travelAgentBooking.setTaxiId(taxiBooking1.getId());


        // Write the contact to the database.
        return crud.create(travelAgentBooking);

    }

    TravelAgentBooking delete(TravelAgentBooking travelAgentBooking) throws Exception {
        log.info("delete() - Deleting " + travelAgentBooking.toString());


        //Create client service instance to make REST requests to upstream service
        ResteasyWebTarget target = client.target(FLIGHT_BOOKING);
        FlightBookingService flightBookingService = target.proxy(FlightBookingService.class);

        ResteasyWebTarget target1 = client.target(TAXI_BOOKING);
        TaxiBookingService taxiBookingService = target1.proxy(TaxiBookingService.class);

        TravelAgentBooking deletedContact = null;

        Booking byId = hotelBookingService.findById(travelAgentBooking.getHotelId());

        if (byId == null) {
            // Verify that the booking exists. Return 404, if not present.
            throw new RestServiceException("No Booking with the id " + byId.getId() + " was found!", Response.Status.NOT_FOUND);
        }

        if (travelAgentBooking.getId() != null) {
            deletedContact = crud.delete(travelAgentBooking);
            hotelBookingService.delete(byId);
            flightBookingService.deleteFlightBooking(travelAgentBooking.getFlightId());
            taxiBookingService.deleteTaxiBooking(travelAgentBooking.getTaxiId());
        } else {
            log.info("delete() - No ID was found so can't Delete.");
        }

        return deletedContact;
    }

    public TravelAgentBooking findById(long id) {
        return crud.findById(id);
    }

    public List<TravelAgentBooking> findAllBookings() {
        return crud.findAllAgentBookings();
    }

    public List<Flight> findAllFlights() {
        //Create client service instance to make REST requests to upstream service
        ResteasyWebTarget target = client.target(FLIGHT_BOOKING);
        FlightBookingService flightBookingService = target.proxy(FlightBookingService.class);

        return flightBookingService.getAllFlights();
    }

    public List<Taxi> findAllTaxis() {
        //call other colleagues' taxi server
        ResteasyWebTarget target1 = client.target(TAXI_BOOKING);
        TaxiBookingService taxiBookingService = target1.proxy(TaxiBookingService.class);
        return taxiBookingService.getAllTaxis();
    }
}
