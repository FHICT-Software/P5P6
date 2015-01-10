package fontys.time;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Anne Toonen
 */
public class TimeTest {

    public TimeTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    
    @Test
    public void testTime() {
    /**
     * creation of a Time-object with year y, month m, day d, hours h and minutes m;
     * 
     * if the combination of y-m-d refers to a non-existing date a correct value of this Time-object will be not guaranteed
     *
     * @param y
     */
    //public Time(int y, int m, int d, int h, int min)
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testM(){
    // @param m 1≤m≤12
        Time time = new Time(2014, 14, 3, 4, 25);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testD(){
    // @param d 1≤d≤31
        Time time = new Time(2014, 12, 35, 4, 25);        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testH(){
    // @param h 0≤h≤23
        Time time = new Time(2014, 12, 3, -3, 25);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testMin(){
    // @param m 0≤m≤59
        Time time = new Time(2014, 12, 3, 4, 132);
    }
    
    @Test
    public void testPlus(){
    /**
     * 
     * @param minutes (a negative value is allowed)
     * @return  this time plus minutes
     */
    //ITime plus(int minutes);
    }
    
    @Test
    public void testDifference(){
    /**
     * 
     * @param time
     * @return the difference between this time and [time] expressed in minutes
     */
    //int difference(ITime time);
    }
}
