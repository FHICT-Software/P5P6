//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

import static fontys.time.DayInWeek.FRI;
import static fontys.time.DayInWeek.MON;
import static fontys.time.DayInWeek.SAT;
import static fontys.time.DayInWeek.SUN;
import static fontys.time.DayInWeek.THU;
import static fontys.time.DayInWeek.TUE;
import static fontys.time.DayInWeek.WED;
import java.util.GregorianCalendar;
//</editor-fold>

/**
 * In this class you can find all properties and operations for Time. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/03/10
 */
public class Time implements ITime {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    GregorianCalendar gregorianCalendar;
  //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * This is the constructor for Time.
     *
     * @param year
     * @param month  1≤m≤12
     * @param day    1≤d≤31
     * @param hour   0≤h≤23
     * @param minute 0≤m≤59
     */
    public Time(int year, int month, int day, int hour, int minute) {
        if (month > 12 || month < 1) {
            throw new IllegalArgumentException("Month needs to be between 1 and 12");
        } else if (day < 1 || day > 31) {
            throw new IllegalArgumentException("Day needs to be between 1 and 31");
        } else if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour needs to be between 0 and 23");
        } else if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute needs to be between 0 and 59");
        } else {
            gregorianCalendar = new GregorianCalendar(year, month - 1, day, hour, minute);
        }
    }
  //</editor-fold>

    //</editor-fold>
    @Override
    public int getYear() {
        return gregorianCalendar.get(GregorianCalendar.YEAR);
    }

    @Override
    public int getMonth() {
        return gregorianCalendar.get(GregorianCalendar.MONTH) + 1;
    }

    @Override
    public int getDay() {
        return gregorianCalendar.get(GregorianCalendar.DATE);
    }

    @Override
    public int getHours() {
        return gregorianCalendar.get(GregorianCalendar.HOUR_OF_DAY);
    }

    @Override
    public int getMinutes() {
        return gregorianCalendar.get(GregorianCalendar.MINUTE);
    }

    @Override
    public DayInWeek getDayInWeek() {
        /*
         BaseCalendar.Date date = CalendarSystem.getGregorianCalendar().newCalendarDate();
         date.setYear(getYear());
         date.setMonth(getMonth());
         date.setDayOfMonth(getDay());
         int day = date.getDayOfWeek();
         */
        int day = gregorianCalendar.get(GregorianCalendar.DAY_OF_WEEK);
        switch (day) {
            case 1:
                return SUN;
            case 2:
                return MON;
            case 3:
                return TUE;
            case 4:
                return WED;
            case 5:
                return THU;
            case 6:
                return FRI;
            case 7:
                return SAT;
        }
        return null;
    }

    @Override
    public ITime plus(int minutes) {
        int year = getYear();
        int month = getMonth();
        int day = getDay();
        int hours = getHours();
        int minutes2 = getMinutes();
        Time returner = new Time(getYear(), getMonth(), getDay(), getHours(), getMinutes());
        returner.gregorianCalendar.add(GregorianCalendar.MINUTE, minutes);
        return returner;
    }

    @Override
    public int difference(ITime time) {
        Time input = (Time) time;
        long returner = 0;
        returner = input.gregorianCalendar.getTimeInMillis() - this.gregorianCalendar.getTimeInMillis();
        returner /= 1000; //ToSeconds
        returner /= 60; //ToMinutes
        return (int) returner;
    }

    @Override
    public int compareTo(ITime o) {
        if (this.difference(o) > 0) {
            return 1;
        }
        if (this.difference(o) < 0) {
            return -1;
        } else {
            return 0;
        }
    }

}
