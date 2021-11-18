///*
// * JBoss, Home of Professional Open Source
// * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
// * contributors by the @authors tag. See the copyright.txt in the
// * distribution for a full listing of individual contributors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.jboss.quickstarts.wfk.booking;
//
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.arquillian.junit.InSequence;
//import org.jboss.quickstarts.wfk.contact.UniqueEmailException;
//import org.jboss.quickstarts.wfk.customer.Customer;
//import org.jboss.quickstarts.wfk.customer.CustomerRestService;
//import org.jboss.quickstarts.wfk.customer.CustomerService;
//import org.jboss.quickstarts.wfk.hotel.Hotel;
//import org.jboss.quickstarts.wfk.hotel.HotelRestService;
//import org.jboss.quickstarts.wfk.hotel.HotelService;
//import org.jboss.quickstarts.wfk.travelAgent.models.Flight;
//import org.jboss.quickstarts.wfk.util.RestServiceException;
//import org.jboss.shrinkwrap.api.Archive;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import org.jboss.shrinkwrap.resolver.api.maven.Maven;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.ws.rs.core.Response;
//import java.io.File;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Logger;
//
//import static org.junit.Assert.*;
//
///**
// * <p>A suite of tests, run with {@link org.jboss.arquillian Arquillian} to test the JAX-RS endpoint for
// * Customer creation functionality
// * (see {@link CustomerRestService# createCustomer(Customer)}).<p/>
// *
//
// * @author Chenjie Li
// * @see CustomerRestService
// */
//@RunWith(Arquillian.class)
//public class BookingRegistrationTest {
//
//    /**
//     * <p>Compiles an Archive using Shrinkwrap, containing those external dependencies necessary to run the tests.</p>
//     *
//     * <p>Note: This code will be needed at the start of each Arquillian test, but should not need to be edited, except
//     * to pass *.class values to .addClasses(...) which are appropriate to the functionality you are trying to test.</p>
//     *
//     * @return Micro test war to be deployed and executed.
//     */
//    @Deployment
//    public static Archive<?> createTestArchive() {
//        // This is currently not well tested. If you run into issues, comment line 67 (the contents of 'resolve') and
//        // uncomment 65. This will build our war with all dependencies instead.
//        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
////                .importRuntimeAndTestDependencies()
//                .resolve(
//                        "io.swagger:swagger-jaxrs:1.5.16"
//        ).withTransitivity().asFile();
//
//        return ShrinkWrap
//                .create(WebArchive.class, "test.war")
//                .addPackages(true, "org.jboss.quickstarts.wfk")
//                .addAsLibraries(libs)
//                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
//                .addAsWebInfResource("arquillian-ds.xml")
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//    }
//
//    @Inject
//    HotelRestService hotelRestService;
//
//    @Inject
//    HotelService hotelService;
//
//    @Inject
//    CustomerRestService customerRestService;
//
//    @Inject
//    CustomerService customerService;
//
//    @Inject
//    BookingRestService bookingRestService;
//
//    @Inject
//    @Named("logger")
//    Logger log;
//
//
//    /**
//     * <p>
//     *     BookingTest tests booking persistence with customer and taxi objects which are stored in database.
//     * </p>
//     */
//    @Test
//    @InSequence(1)
//    public void testRegister(){
//        Customer customer0 = createCustomerInstance("Jane Doe", "jane@mailinator.com", "07744754955");
//        Customer customer = storeCustomer(customer0);
//        Hotel taxi0 = createHotelInstance("JK66AKB",6);
//        Hotel taxi = storeHotel(taxi0);
//        //create a booking with a future date.
//        Booking booking = createBookingInstance(customer, taxi, futureDate1);
//        Response response = bookingRestService.createBooking(booking);
//
//        assertEquals("Unexpected response status", 201, response.getStatus());
//        log.info(" New booking was persisted and returned status " + response.getStatus());
//    }
//
////    /**
////     * <p>
////     *     BookingTest tests invalidly create a booking record.
////     * </p>
////     */
////    @Test
////    @InSequence(2)
////    public void testInvalidRegister() {
////        Hotel taxi = createHotelInstanceWithId("JK66AKB",6);
////        Customer customer = createCustomerInstanceWithId("Jane Doe", "jane@mailinator.com", "07744754955");
////        Booking booking = createBookingInstance(customer, taxi, futureDate2);
////
////        try {
////            bookingRestService.createBooking(booking);
////            fail("Expected a RestServiceException to be thrown");
////        } catch(RestServiceException e) {
////            assertEquals("Unexpected response status", Response.Status.BAD_REQUEST, e.getStatus());
////            assertEquals("Unexpected response body", 1, e.getReasons().size());
////            log.info("Invalid booking register attempt failed with return code " + e.getStatus());
////        }
////    }
////
////
//
//
//
//
//    /**
//     * <p> BookingTest </p>
//     * @param customer, taxi, bookingDate]
//     * @return org.jboss.quickstarts.wfk.booking.Booking
//     */
//    private Booking createBookingInstance(Customer customer, Hotel hotel, Date bookingDate ){
//        Booking booking = new Booking();
//        booking.setCustomer(customer);
//        booking.setHotel(hotel);
//        booking.setFutureDate(bookingDate);
//        return booking;
//    }
//
//    /**
//     * <p> BookingTest createCustomerInstance method to create Customer instance to be used to test booking </p>
//     * @param name customer's name
//     * @param email customer's email
//     * @param phoneNumber customer's phone number
//     * @return org.jboss.quickstarts.wfk.taxi.Hotel
//     */
//    private Customer createCustomerInstance(String name, String email, String phoneNumber) {
//        Customer customer = new Customer();
//        customer.setName(name);
//        customer.setEmail(email);
//        customer.setPhoneNumber(phoneNumber);
//        return customer;
//    }
//
//    /**
//     * <p> BookingTest createCustomerInstance method to create Customer instance with id to be used to test booking </p>
//     * @param name customer's name
//     * @param email customer's email
//     * @param phoneNumber customer's phone number
//     * @return org.jboss.quickstarts.wfk.taxi.Hotel
//     */
//    private Customer createCustomerInstanceWithId(String name, String email, String phoneNumber) {
//        Customer customer = new Customer();
//        customer.setId(1000L);
//        customer.setName(name);
//        customer.setEmail(email);
//        customer.setPhoneNumber(phoneNumber);
//        return customer;
//    }
//
//    /**
//     * <p> BookingTest persists a new customer object </p>
//     * @param customer customer object
//     * @return org.jboss.quickstarts.wfk.customer.Customer
//     */
//    private Customer storeCustomer(Customer customer){
//        customerRestService.createCustomer(customer);
//        return customer;
//    }
//
//    /**
//     * <p> BookingTest createHotelInstance method to create Hotel instance to be used to test booking </p>
//     * @param registration Hotel's registration
//     * @param seatsNumber Hotel's seat number
//     * @return org.jboss.quickstarts.wfk.taxi.Hotel
//     */
//    private Hotel createHotelInstance(String registration, Integer seatsNumber) {
//        Hotel taxi = new Hotel();
//        taxi.setRegistration(registration);
//        taxi.setSeatsNumber(seatsNumber);
//        return taxi;
//    }
//
//    /**
//     * <p> BookingTest createHotelInstance method to create Hotel instance with id to be used to test booking </p>
//     * @param registration Hotel's registration
//     * @param seatsNumber Hotel's seat number
//     * @return org.jboss.quickstarts.wfk.taxi.Hotel
//     */
//    private Hotel createHotelInstanceWithId(String registration, Integer seatsNumber) {
//        Hotel taxi = new Hotel();
//        taxi.setId(1000L);
//        taxi.setRegistration(registration);
//        taxi.setSeatsNumber(seatsNumber);
//        return taxi;
//    }
//
//    /**
//     * <p> BookingTest persists a taxi object </p>
//     * @param taxi taxi object
//     * @return org.jboss.quickstarts.wfk.taxi.Hotel
//     */
//    private Hotel storeHotel(Hotel taxi){
//        taxiRestService.createHotel(taxi);
//        return taxi;
//    }
//
//
//}
