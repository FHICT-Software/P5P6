//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class AppointmentTest {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private String subject;
    private IPeriod period;
    private Appointment defaultInstance;
    private Contact contact;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor + Set Up + Tear Down">
    public AppointmentTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        subject = "Saai Onderwerp.";
        period = new Period(new Time(1991, 10, 16, 2, 42), new Time(2014, 3, 13, 11, 37));
        defaultInstance = new Appointment(subject, period);
        contact = new Contact("Jeroen Berkvens");
    }

    @After
    public void tearDown() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Subject">
    /**
     * Test of getSubject method, of class Appointment.
     */
    @Test
    public void testGetSubject() {
        Appointment instance = defaultInstance;
        String expResult = "Saai Onderwerp.";
        String result = instance.getSubject();
        assertEquals(expResult, result);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get Period">
    /**
     * Test of getPeriod method, of class Appointment.
     */
    @Test
    public void testGetPeriod() {
        Appointment instance = defaultInstance;
        IPeriod expResult = period;
        IPeriod result = instance.getPeriod();
        assertEquals(expResult, result);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Invitees">
    /**
     * Test of invitees method, of class Appointment.
     */
    @Test
    public void testinvitees() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Appointment instance;
        ArrayList<Contact> expResultList;
        Iterator<Contact> expResultIterator;
        Iterator<Contact> resultIterator;
        Contact expResult = null;
        Contact result = null;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="No Contacts">
        instance = defaultInstance;
        expResultIterator = null;
        resultIterator = instance.invitees();
        assertEquals(expResultIterator, resultIterator);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Adding Contacts">
        instance.addContact(contact);
        expResultList = new ArrayList<Contact>();
        expResultList.add(contact);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Contacts">
        expResultIterator = expResultList.iterator();
        resultIterator = instance.invitees();
        try {
            for (int i = 0; i < expResultList.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    expResult = expResultIterator.next();
                    result = resultIterator.next();
                }
                assertEquals(expResult, result);
            }
        } catch (Exception exception) {
            String message = exception.getMessage();
            fail(exception.getMessage());
        }
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Add Contact">
    /**
     * Test of addContact method, of class Appointment.
     */
    @Test
    public void testAddContact() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Appointment instance;
        ArrayList<Contact> expResultList;
        Iterator<Contact> expResultIterator;
        Iterator<Contact> resultIterator;
        Contact expResult = null;
        Contact result = null;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Adding Contacts">
        instance = defaultInstance;
        instance.addContact(contact);
        expResultList = new ArrayList<Contact>();
        expResultList.add(contact);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Check if Added">
        try {
            for (int i = 0; i < expResultList.size(); i++) {
                expResultIterator = expResultList.iterator();
                resultIterator = instance.invitees();
                for (int j = 0; j <= i; j++) {
                    expResult = expResultIterator.next();
                    result = resultIterator.next();
                }
                assertEquals(expResult, result);
            }
        } catch (Exception exception) {
            String message = exception.toString();
            fail(exception.getMessage());
        }
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Remove Contact">
    /**
     * Test of removeContact method, of class Appointment.
     */
    @Test
    public void testRemoveContact() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Appointment instance;
        ArrayList<Contact> expResultList;
        Iterator<Contact> expResult;
        Iterator<Contact> result;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Adding Contacts">
        instance = defaultInstance;
        instance.addContact(contact);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Removing Contacts">
        instance.removeContact(contact);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Check if removed">
        expResult = null;
        result = instance.invitees();
        assertEquals(expResult, result);
        //</editor-fold>
    }
    //</editor-fold>
}
