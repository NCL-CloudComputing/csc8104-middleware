package org.jboss.quickstarts.wfk.hotel;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.quickstarts.wfk.util.RestServiceException;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;


import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * <p>
 * A suite of tests, run with {@link org.jboss.arquillian Arquillian} to test
 * the JAX-RS endpoint for Hotel creation functionality
 * (see {@link HotelRestService#createHotel(Hotel)} ).
 * </p>
 *
 * @author JayXu
 *
 * @see HotelRestService
 */
@RunWith(Arquillian.class)
public class HotelRegistrationTest {

    /**
     * <p>
     * Compiles an Archive using Shrinkwrap, containing those external dependencies
     * necessary to run the tests.
     * </p>
     *
     * <p>
     * Note: This code will be needed at the start of each Arquillian test, but
     * should not need to be edited, except to pass *.class values to
     * .addClasses(...) which are appropriate to the functionality you are trying to
     * test.
     * </p>
     *
     * @return Micro test war to be deployed and executed.
     */
    @Deployment
    public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .resolve("io.swagger:swagger-jaxrs:1.5.15")
                .withTransitivity()
                .asFile();

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackages(true, "uk.ac.ncl.enterprisemiddleware")
                .addPackages(true, "org.jboss.quickstarts.wfk").addAsLibraries(libs)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("arquillian-ds.xml").addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    HotelRestService hotelRestService;

    @Inject
    @Named("logger")
    Logger log;


    /**
     * <p> HotelTest persists a hotel object correctly </p>
     * @param
     * @return void
     */
    @Test
    @InSequence(1)
    public void testRegister(){
        Hotel hotel = createHotelInstance("ibs","02364787973","123456");
        Response response = hotelRestService.createHotel(hotel);

        assertEquals("Unexpected response status", 201, response.getStatus());
        log.info(" New hotel was persisted and returned status " + response.getStatus());
    }

//    /**
//     * <p> HotelTest invalidly persist a hotel object </p>
//     * @param
//     * @return void
//     */
//    @Test
//    @InSequence(3)
//    public void testInvalidRegister() {
//        Hotel hotel = createHotelInstance("", "","");
//
//        try {
//            hotelRestService.createHotel(hotel);
//            fail("Expected a RestServiceException to be thrown");
//        } catch(RestServiceException e) {
//            assertEquals("Unexpected response status",
//                    Response.Status.BAD_REQUEST, e.getStatus());
//            assertEquals("Unexpected response body",
//                    3, e.getReasons().size());
//            log.info("Invalid hotel register attempt failed with return code " + e.getStatus());
////        }
//
//    }


    /**
     * <p>A utility method to construct a {@link Hotel Hotel} object for use in
     * testing. This object is not persisted.</p>
     *
     * @param phoneNumber The phoneNumber of the Hotel being created
     * @param postCode  The postCode of the Hotel being created
     * @return The Hotel object create
     */
    private Hotel createHotelInstance(String name, String phoneNumber ,String postCode) {
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setPhoneNumber(phoneNumber);
        hotel.setPostCode(postCode);
        return hotel;
    }

}
