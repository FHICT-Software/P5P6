/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

import java.util.ArrayList;
import java.util.Iterator;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author phinux
 */
public class ContactTest {

    public ContactTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of constructor, of class Contact.
     */
    @Test
    public void testContact() {
        System.out.println("getName");
        Contact instance = new Contact("Anne Toonen");
        String expResult = "Anne Toonen";
        String result = instance.getName();
        assertEquals(expResult, result);
    }

    /**
     * Test of addAppointment method, of class Contact.
     */
    @Test
    public void testAddAppointment() {
        System.out.println("addAppointment");
        Contact instance = new Contact("Jeroen Berkvens");
        Appointment a;
        boolean expResult;
        boolean result;

        a = null;
        expResult = false;
        result = instance.addAppointment(a);
        assertEquals(expResult, result);

        a = new Appointment("Anne jarig", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0)));
        expResult = true;
        result = instance.addAppointment(a);
        assertEquals(expResult, result);

        a = new Appointment("handboog schieten", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 4, 10, 22, 0)));
        expResult = false;
        result = instance.addAppointment(a);
        assertEquals(expResult, result);

    }

    /**
     * Test of removeAppointment method, of class Contact.
     */
    @Test
    public void testRemoveAppointment() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Contact instance;
        ArrayList<Appointment> expResultList;
        Iterator<Appointment> expResultIterator;
        Iterator<Appointment> resultIterator;
        Appointment expResult = null;
        Appointment result = null;
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Adding Contacts">
        instance = new Contact("jeroen");
        instance.addAppointment(new Appointment("Anne jarig", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0))));
        instance.addAppointment(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Removing Contacts">
        Appointment a = new Appointment("Anne jarig", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0)));
        instance.removeAppointment(a);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Expected Result">
        expResultList = new ArrayList<Appointment>();
        expResultList.add(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Check if removed">
        for (int i = 0; i < expResultList.size(); i++) {
            expResultIterator = expResultList.iterator();
            resultIterator = instance.allAppointments();
            for (int j = 0; j <= i; j++) {
                expResult = expResultIterator.next();
                result = resultIterator.next();
            }
            assertTrue(expResult.equals(result));
        }
        //</editor-fold>
    }

    /**
     * Test of allAppointments method, of class Contact.
     */
    @Test
    public void testAllAppointments() {
        System.out.println("allAppointments");

        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Contact instance;
        ArrayList<Appointment> expResultList;
        Iterator<Appointment> expResultIterator;
        Iterator<Appointment> resultIterator;
        Appointment expResult = null;
        Appointment result = null;
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="No Contacts">
        instance = new Contact("Anne Toonen");
        expResultIterator = null;
        resultIterator = instance.allAppointments();
        assertEquals(expResultIterator, resultIterator);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Adding Appointments">
        instance.addAppointment(new Appointment("Party Time", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0))));
        instance.addAppointment(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));

        expResultList = new ArrayList<Appointment>();
        expResultList.add(new Appointment("Party Time", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0))));
        expResultList.add(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Check Appointment">
        for (int i = 0; i < expResultList.size(); i++) {
            expResultIterator = expResultList.iterator();
            resultIterator = instance.allAppointments();
            for (int j = 0; j <= i; j++) {
                expResult = expResultIterator.next();
                result = resultIterator.next();
            }
            assertTrue(expResult.equals(result));
        }
        //</editor-fold>
    }

    /**
     * Test of equals method, of class Contact.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Contact instance = new Contact("Anne Toonen");
        instance.addAppointment(new Appointment("Party Time", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0))));
        instance.addAppointment(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));

        Contact expResult = new Contact("Anne Toonen");
        expResult.addAppointment(new Appointment("Party Time", new Period(new Time(2014, 4, 10, 20, 0), new Time(2014, 5, 10, 0, 0))));
        expResult.addAppointment(new Appointment("handboog schieten", new Period(new Time(2014, 3, 10, 20, 0), new Time(2014, 3, 10, 22, 0))));

        //assertEquals(expResult, instance);
        assertTrue("Contacten zijn niet hetzelfde", instance.equals(expResult));
    }

}
