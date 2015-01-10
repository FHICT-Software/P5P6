//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//</editor-fold>

/**
 *
 * @author J.B.A.J. Berkvens
 */
public class PeriodTest {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private ITime beginTime;
    private ITime endTime;
    private Period defaultInstance;
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor + Set Up + Tear Down">
    public PeriodTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        beginTime = new Time(1991, 10, 16, 2, 43);
        endTime = new Time(2014, 3, 10, 18, 0);
        defaultInstance = new Period(beginTime, endTime);
    }

    @After
    public void tearDown() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Begin Time">
    //<editor-fold defaultstate="collapsed" desc="Get">
    /**
     * Test of getBeginTime method, of class Period.
     */
    @Test
    public void testGetBeginTime() {
        Period instance = defaultInstance;
        ITime expResult = beginTime;
        ITime result = instance.getBeginTime();
        assertEquals(expResult, result);
    }
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Set">
    /**
     * Test of setBeginTime method, of class Period.
     */
    @Test
    public void testSetBeginTime() {
        Period instance = defaultInstance;
        beginTime = new Time(1992, 11, 17, 3, 44);
        instance.setBeginTime(beginTime);
        ITime expResult = beginTime;
        ITime result = instance.getBeginTime();
        assertEquals(expResult, result);
    }
  //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="End Time">
    //<editor-fold defaultstate="collapsed" desc="Get">
    /**
     * Test of getEndTime method, of class Period.
     */
    @Test
    public void testGetEndTime() {
        Period instance = defaultInstance;
        ITime expResult = endTime;
        ITime result = instance.getEndTime();
        assertEquals(expResult, result);
    }
  //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Set">
    /**
     * Test of setEndTime method, of class Period.
     */
    @Test
    public void testSetEndTime() {
        Period instance = defaultInstance;
        endTime = new Time(2015, 4, 11, 19, 1);
        instance.setEndTime(endTime);
        ITime expResult = endTime;
        ITime result = instance.getEndTime();
        assertEquals(expResult, result);
    }
  //</editor-fold>
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Lengh">
    /**
     * Test of length method, of class Period.
     */
    @Test
    public void testLength() {
        beginTime = new Time(2014, 3, 10, 16, 43);
        endTime = new Time(2014, 3, 10, 18, 0);
        Period instance = new Period(beginTime, endTime);
        int expResult = 77;
        int result = instance.length();
        assertEquals(expResult, result);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Move">
    /**
     * Test of move method, of class Period.
     */
    @Test
    public void testMove() {
        int minutes = 5;
        Period instance = defaultInstance;
        instance.move(minutes);
        ITime expResult = new Time(1991, 10, 16, 2, 48);
        ITime result = instance.getBeginTime();
        assert (expResult.compareTo(result) == 0);
        expResult = new Time(2014, 3, 10, 18, 5);
        result = instance.getEndTime();
        assert (expResult.compareTo(result) == 0);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Change Length With">
    /**
     * Test of changeLengthWith method, of class Period.
     */
    @Test
    public void testChangeLengthWith() {
        int minutes = 77;
        Period instance = defaultInstance;
        instance.changeLengthWith(minutes);
        ITime expResult = new Time(2014, 3, 10, 19, 17);
        ITime result = instance.getEndTime();
        assert (expResult.compareTo(result) == 0);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Is Part Of">
    /**
     * Test of isPartOf method, of class Period.
     */
    @Test
    public void testIsPartOf() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Period instance;
        ITime periodBeginTime;
        ITime periodEndTime;
        IPeriod period;
        boolean expResult;
        boolean result;
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Full Part of (true)">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 11, 17, 3, 44);
        periodEndTime = new Time(2015, 2, 9, 16, 59);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = true;
        result = instance.isPartOf(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Partial Part of (false) - begin">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 9, 15, 1, 42);
        periodEndTime = new Time(2014, 3, 10, 18, 0);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = false;
        result = instance.isPartOf(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Partial Part of (false) - end">
        instance = defaultInstance;
        periodBeginTime = new Time(1992, 11, 17, 3, 44);
        periodEndTime = new Time(2015, 4, 11, 19, 1);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = false;
        result = instance.isPartOf(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="No Part of (false)">
        instance = defaultInstance;
        periodBeginTime = new Time(2015, 4, 11, 19, 1);
        periodEndTime = new Time(2044, 3, 8, 6, 22);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = false;
        result = instance.isPartOf(period);
        assertEquals(expResult, result);
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Union With">
    /**
     * Test of unionWith method, of class Period.
     */
    @Test
    public void testUnionWith() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Period instance;
        ITime periodBeginTime;
        ITime periodEndTime;
        IPeriod period;
        IPeriod expResult;
        IPeriod result;
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: A">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(1991, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(period.getBeginTime(), instance.getEndTime());
        result = instance.unionWith(period);
        assertTrue(expResult.equals(result));
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: B">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(1992, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(period.getBeginTime(), defaultInstance.getEndTime());
        result = instance.unionWith(period);
        assert expResult.equals(result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: C">
        instance = defaultInstance;
        periodBeginTime = new Time(1992, 10, 16, 2, 43);
        periodEndTime = new Time(1993, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(defaultInstance.getBeginTime(), defaultInstance.getEndTime());
        result = instance.unionWith(period);
        assert expResult.equals(result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: D">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(2177, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(period.getBeginTime(), period.getEndTime());
        result = instance.unionWith(period);
        assert expResult.equals(result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: E">
        instance = defaultInstance;
        periodBeginTime = new Time(2014, 3, 10, 18, 00);
        periodEndTime = new Time(2470, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(defaultInstance.getBeginTime(), period.getEndTime());
        result = instance.unionWith(period);
        assert expResult.equals(result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: F">
        instance = defaultInstance;
        periodBeginTime = new Time(2015, 3, 10, 18, 00);
        periodEndTime = new Time(2470, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        result = instance.unionWith(period);
        assertNull(result);
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Intersection With">
    /**
     * Test of intersectionWith method, of class Period.
     */
    @Test
    public void testIntersectionWith() {
        //<editor-fold defaultstate="collapsed" desc="Declarations">
        Period instance;
        ITime periodBeginTime;
        ITime periodEndTime;
        IPeriod period;
        IPeriod expResult;
        IPeriod result;
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: A">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(1991, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = null;
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: B">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(1992, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(defaultInstance.getBeginTime(), period.getEndTime());
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: C">
        instance = defaultInstance;
        periodBeginTime = new Time(1992, 10, 16, 2, 43);
        periodEndTime = new Time(1993, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(period.getBeginTime(), period.getEndTime());
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: D">
        instance = defaultInstance;
        periodBeginTime = new Time(1990, 10, 16, 2, 43);
        periodEndTime = new Time(2177, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = new Period(defaultInstance.getBeginTime(), defaultInstance.getEndTime());
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: E">
        instance = defaultInstance;
        periodBeginTime = new Time(2014, 3, 10, 18, 00);
        periodEndTime = new Time(2470, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = null;
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
    //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Documentation p3: F">
        instance = defaultInstance;
        periodBeginTime = new Time(2015, 3, 10, 18, 00);
        periodEndTime = new Time(2470, 10, 16, 2, 43);
        period = new Period(periodBeginTime, periodEndTime);
        expResult = null;
        result = instance.intersectionWith(period);
        assertEquals(expResult, result);
        //</editor-fold>
    }
    //</editor-fold>

    /**
     * Test of equals method, of class Period.
     */
    @Test
    public void testEquals() {
        Period instance;
        boolean expResult;
        boolean result;
        
        instance = defaultInstance;
        expResult = true;
        result = instance.equals(new Period(beginTime, endTime));
        assertEquals(expResult, result);
    }
}
