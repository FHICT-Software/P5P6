package fontys.time;

/**
 * A stretch of time with a begin time and end time. The end time is always later then the begin time; the length of the period is
 * always positive
 *
 *
 * WARNING: length of period should be smaller than Integer.MAXINT; this restriction will never be checked
 *
 * @author frankpeeters
 */
public interface IPeriod {

  /**
   *
   * @return the begin time of this period
   */
  ITime getBeginTime();

  /**
   *
   * @return the end time of this period
   */
  ITime getEndTime();

  /**
   *
   * @return the length of this period expressed in minutes (always posmoitive)
   */
  int length();

  /**
   * beginTime will be the new begin time of this period
   *
   * @param beginTime must be earlier than the current end time of this period
   */
  void setBeginTime(ITime beginTime);

  /**
   * endTime will be the new end time of this period
   *
   * @param endTime must be later than the current end time of this period
   */
  void setEndTime(ITime endTime);

  /**
   * the begin and end time of this period will be moved up both with [minutes] minutes
   *
   * @param minutes (a negative value is allowed)
   */
  void move(int minutes);

  /**
   * the end time of this period will be moved up with [minutes] minutes
   *
   * @param minutes minutes + length of this period must be positive
   */
  void changeLengthWith(int minutes);

  /**
   *
   * @param period
   *
   * @return true if all moments within this period are included within [period], otherwise false
   */
  boolean isPartOf(IPeriod period);

  /**
   *
   * @param period
   *
   * @return if this period and [period] are consecutive or possess a common intersection, then the smallest period p will be
   *         returned, for which this period and [period] are part of p, otherwise null will be returned
   */
  IPeriod unionWith(IPeriod period);

  /**
   *
   * @param period
   *
   * @return the largest period which is part of this period and [period] will be returned; if the intersection is empty null will
   *         be returned
   */
  IPeriod intersectionWith(IPeriod period);
  
  @Override
  boolean equals(Object object);
  
  boolean equals(IPeriod period);
}
