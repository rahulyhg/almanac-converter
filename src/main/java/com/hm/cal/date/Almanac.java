/*****************************************************************************
 * Copyright 2015 Hypotemoose, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *****************************************************************************/
package com.hm.cal.date;

import java.io.Serializable;

import static com.hm.cal.util.AlmanacConverter.toJulianDay;
import static java.lang.Math.floor;

/**
 * An almanac.
 *
 * @author Chris Engelsma.
 * @version 2015.11.04
 */
public abstract class Almanac implements Serializable {

  public static final String CALENDAR_NAME = "";
  private static final long serialVersionUID = 1L;
  protected int year;
  protected int month;
  protected int day;

  /**
   * Determines if a list of almanacs are in chronologic order.
   *
   * @param a a group of almanacs.
   * @return true, if are chronological; false, otherwise.
   */
  public static boolean datesAreChronological(Almanac... a) {
    JulianDay d0 = toJulianDay(a[0]);
    for (int i = 1; i < a.length; ++i) {
      JulianDay d1 = toJulianDay(a[i]);
      if (d1.getValue() < d0.getValue()) return false;
      d0 = d1;
    }
    return true;
  }

  /**
   * Determines if a list of almanacs are in reverse chronologic order.
   *
   * @param a a group of almanacs.
   * @return true, if are reverse chronological; false, otherwise.
   */
  public static boolean datesAreReverseChronological(Almanac... a) {
    JulianDay d0 = toJulianDay(a[0]);
    for (int i = 1; i < a.length; ++i) {
      JulianDay d1 = toJulianDay(a[i]);
      if (d1.getValue() > d0.getValue()) return false;
      d0 = d1;
    }
    return true;
  }

  /**
   * Returns the weekday of a 7-day week.
   * The returned value will be an integer representing the day number of the
   * week starting at Sunday (0) and ending on Saturday (6).
   *
   * @return a weekday in the range [0,6].
   */
  public int getWeekDayNumber() {
    int weekLength = getNumberOfDaysInWeek();
    double jd = (toJulianDay(this)).getValue();
    return (int) floor(jd + 1.5) % weekLength;
  }

  /**
   * Gets the day of the week.
   *
   * @return the week day.
   */
  public String getWeekDay() {
    return "";
  }

  /**
   * Determines if this date comes after a given date.
   *
   * @param a an almanac.
   * @return true, if before; false, otherwise.
   */
  public boolean isBefore(Almanac a) {
    return datesAreChronological(this, a);
  }

  /**
   * Determines if this date comes after a given date.
   *
   * @param a an almanac.
   * @return true, if after; false, otherwise.
   */
  public boolean isAfter(Almanac a) {
    return datesAreReverseChronological(this, a);
  }

  /**
   * Gets the year.
   *
   * @return the year.
   */
  public int getYear() {
    return year;
  }

  /**
   * Sets the year.
   *
   * @param year the year.
   */
  public void setYear(int year) {
    this.year = year;
  }

  /**
   * Gets the month.
   *
   * @return the month.
   */
  public int getMonth() {
    return month;
  }

  /**
   * Sets the month.
   *
   * @param month the month.
   */
  public void setMonth(int month) {
    this.month = month;
  }

  /**
   * Gets the day.
   *
   * @return the day.
   */
  public int getDay() {
    return day;
  }

  /**
   * Sets the day.
   *
   * @param day the day.
   */
  public void setDay(int day) {
    this.day = day;
  }

  /**
   * Gets the month names.
   *
   * @return the month names.
   */
  public String[] getMonths() {
    return new String[getNumberOfMonthsInYear()];
  }

  /**
   * Gets the weekday names.
   *
   * @return the week day names.
   */
  public String[] getWeekDays() {
    return new String[getNumberOfDaysInWeek()];
  }

  /**
   * Increments this date by one day.
   */
  public void nextDay() {
    if (day == getNumberOfDaysInMonth()) {
      if (month == getNumberOfMonthsInYear()) {
        month = 1;
        year++;
      } else month++;
      day = 1;
    } else day++;
  }

//////////////////////////////////////////////////////////////////////////////
// abstract

  /**
   * Subtracts this date by one day.
   */
  public void prevDay() {
    if (day == 1) {
      if (month == 1) {
        month = getNumberOfMonthsInYear();
        year--;
      } else month--;
      day = getNumberOfDaysInMonth();
    } else day--;
  }

  /**
   * Adds days to this date.
   *
   * @param n the number of days to add.
   */
  public void addDays(int n) {
    for (int i = 0; i < n; ++i) nextDay();
  }

  /**
   * Subtracts days from this date.
   *
   * @param n the number of days to subtract.
   */
  public void subtractDays(int n) {
    for (int i = 0; i < n; ++i) prevDay();
  }

  public abstract String getDate();

  public abstract String getName();

  public abstract int getNumberOfDaysInMonth();

//////////////////////////////////////////////////////////////////////////////
// protected

  public abstract int getNumberOfDaysInWeek();

  public abstract int getNumberOfMonthsInYear();

  public abstract void set(Almanac a);
}
