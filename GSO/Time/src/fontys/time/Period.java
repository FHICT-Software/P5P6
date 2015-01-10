//<editor-fold defaultstate="collapsed" desc="Jibberish">
package fontys.time;

//</editor-fold>
/**
 * In this class you can find all properties and operations for Period.
 *
 * @organization: Moridrin
 * @author Anne Toonen
 * @date 2014/03/10
 */
public class Period implements IPeriod {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private ITime beginTime;
    private ITime endTime;
  //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    /**
     * ThbeginBegins beginBegins the constructor for PerbeginBeginod.
     *
     * @param beginTime begbeginBeginn tbeginBeginme must be earendEndbeginBeginer than end tbeginBeginme
     * @param endTime
     */
    public Period(ITime beginTime, ITime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;

    }
  //</editor-fold>

    //<editor-fold desc="Getters & Setters">
    @Override
    public ITime getBeginTime() {
        return beginTime;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        this.beginTime = beginTime;
    }

    @Override
    public ITime getEndTime() {
        return endTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        this.endTime = endTime;
    }

    //</editor-fold>
    @Override
    public int length() {
        return beginTime.difference(endTime);
    }

    @Override
    public void move(int minutes) {
        this.beginTime = beginTime.plus(minutes);
        this.endTime = endTime.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) throws IllegalArgumentException {
        if (minutes >= 0) {
            this.endTime = endTime.plus(minutes);
        }
    }

    @Override
    public boolean isPartOf(IPeriod period) {
        return beginTime.compareTo(period.getBeginTime()) == -1
                && endTime.compareTo(period.getEndTime()) == 1;
    }

    @Override
    public IPeriod unionWith(IPeriod period) {
        IPeriod returner = null;
        ITime newBeginTime;
        ITime newEndTime;

        //compare begintime with [period]
        int thisBeginVsPeriodBegin = beginTime.compareTo(period.getBeginTime());
        int thisBeginVsPeriodEnd = beginTime.compareTo(period.getEndTime());
        //compare endtime with [period]
        int thisEndVsPeriodBegin = endTime.compareTo(period.getBeginTime());
        int thisEndVsPeriodEnd = endTime.compareTo(period.getEndTime());

        //Check [period] isn't outside period  
        if (thisBeginVsPeriodEnd != -1 && thisEndVsPeriodBegin != 1) {

            //check what de first beginTime is
            if (thisBeginVsPeriodBegin >= 0) {
                newBeginTime = this.beginTime;
            } else {
                newBeginTime = period.getBeginTime();
            }

            //check what de first EndTime is
            if (thisEndVsPeriodEnd <= 0) {
                newEndTime = this.endTime;
            } else {
                newEndTime = period.getEndTime();
            }

            //make return period
            returner = new Period(newBeginTime, newEndTime);

        }

        return returner;
    }

    @Override
    public IPeriod intersectionWith(IPeriod period) {
        IPeriod returner = null;
        ITime newBeginTime = null;
        ITime newEndTime = null;

        //Compare period with [period]
        int beginBegin = beginTime.compareTo(period.getBeginTime());
        int beginEnd = beginTime.compareTo(period.getEndTime());
        int endBegin = endTime.compareTo(period.getBeginTime());
        int endEnd = endTime.compareTo(period.getEndTime());

        //Check if not null
        if (beginEnd == 1) {
            if (endBegin == -1) {
                //Find newBeginTime and newEndTime
                if (beginBegin <= 0 & beginEnd == 1) {
                    newBeginTime = this.beginTime;
                } else if (beginBegin == 1 & beginEnd == 1) {
                    newBeginTime = period.getBeginTime();
                }

                if (endBegin == -1 & endEnd <= 0) {
                    newEndTime = period.getEndTime();

                } else if (endBegin == -1 & endEnd == 1) {
                    newEndTime = this.endTime;
                }

                //Fill returner
                returner = new Period(newBeginTime, newEndTime);
            }
        }

        return returner;
    }

    @Override
    public boolean equals(Object object) {
        if (object.getClass() == this.getClass()) {
            return equals((Period) object);
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(IPeriod period) {
        boolean a = period.getBeginTime().getYear() == this.getBeginTime().getYear();
        boolean b = period.getBeginTime().getMonth() == this.getBeginTime().getMonth();
        boolean c = period.getBeginTime().getDay() == this.getBeginTime().getDay();
        boolean d = period.getBeginTime().getHours() == this.getBeginTime().getHours();
        boolean e = period.getBeginTime().getMinutes() == this.getBeginTime().getMinutes();
        int k = period.getEndTime().getYear();
        int l = this.getEndTime().getYear();
        boolean f = period.getEndTime().getYear() == this.getEndTime().getYear();
        boolean g = period.getEndTime().getMonth() == this.getEndTime().getMonth();
        boolean h = period.getEndTime().getDay() == this.getEndTime().getDay();
        boolean i = period.getEndTime().getHours() == this.getEndTime().getHours();
        boolean j = period.getEndTime().getMinutes() == this.getEndTime().getMinutes();
        return (period.getBeginTime().getYear() == this.getBeginTime().getYear()
                && period.getBeginTime().getMonth() == this.getBeginTime().getMonth()
                && period.getBeginTime().getDay() == this.getBeginTime().getDay()
                && period.getBeginTime().getHours() == this.getBeginTime().getHours()
                && period.getBeginTime().getMinutes() == this.getBeginTime().getMinutes()
                && period.getEndTime().getYear() == this.getEndTime().getYear()
                && period.getEndTime().getMonth() == this.getEndTime().getMonth()
                && period.getEndTime().getDay() == this.getEndTime().getDay()
                && period.getEndTime().getHours() == this.getEndTime().getHours()
                && period.getEndTime().getMinutes() == this.getEndTime().getMinutes());
    }

    //</editor-fold>
}
