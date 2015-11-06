/*****************************************************************************
Copyright 2015 Hypotemoose, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*****************************************************************************/
package com.moose.cal.date;

import static com.moose.cal.util.Converter.*;
import static java.lang.Math.*;

/**
 * An almanac.
 * @author Chris Engelsma.
 * @version 2015.11.04
 */
public abstract class Almanac {

  /**
   * Gets the week length.
   * @return the week length.
   */
  public int getNumberOfDaysInWeek() { return 7; }

  /** 
   * Gets the year length in months.
   * @return the number of months in a year.
   */
  public int getNumberOfMonthsInYear() { return 12; }

  /**
   * Returns the weekday of a 7-day week.
   * The returned value will be an integer representing the day number of the
   * week starting at Sunday (0) and ending on Saturday (6).
   * @param jd an Almanac.
   * @return a weekday in the range [0,6].
   */
  public int getWeekDay() {
    int weekLength = getNumberOfDaysInWeek();
    double jd = (toJulianDay(this)).getValue();
    return (int)floor(jd+1.5)%weekLength;
  }

  /**
   * Determines if a list of almanacs are in chronologic order.
   * @param a a group of almanacs.
   * @return true, if are chronological; false, otherwise.
   */
  public static boolean datesAreChronological(Almanac... a) {
    JulianDay d0 = toJulianDay(a[0]);
    for (int i=1; i<a.length; ++i) {
      JulianDay d1 = toJulianDay(a[i]);
      if (d1.getValue() < d0.getValue()) return false;
      d0 = d1;
    }
    return true;
  }

  /**
   * Determines if a list of almanacs are in reverse chronologic order.
   * @param a a group of almanacs.
   * @return true, if are reverse chronological; false, otherwise.
   */
  public static boolean datesAreReverseChronological(Almanac... a) {
    JulianDay d0 = toJulianDay(a[0]);
    for (int i=1; i<a.length; ++i) {
      JulianDay d1 = toJulianDay(a[i]);
      if (d1.getValue() > d0.getValue()) return false;
      d0 = d1;
    }
    return true;
  }

  /** 
   * Determines if this date comes after a given date.
   * @param a an almanac.
   * @return true, if before; false, otherwise.
   */
  public boolean isBefore(Almanac a) {
    return datesAreChronological(this,a);
  }

  /**
   * Determines if this date comes after a given date.
   * @param a an almanac.
   * @return true, if after; false, otherwise.
   */
  public boolean isAfter(Almanac a) {
    return datesAreReverseChronological(this,a);
  }

  /**
   * Gets the year.
   * @return the year.
   */
  public int getYear() {
    return year;
  }

  /**
   * Gets the month.
   * @return the month.
   */
  public int getMonth() {
    return month;
  }

  /**
   * Gets the day.
   * @return the day.
   */
  public int getDay() {
    return day;
  }

  /**
   * Increments this date by one day.
   */
  public void nextDay() {
    if (day==getNumberOfDaysInMonth()) {
      if (month==getNumberOfMonthsInYear()) {
        month = 1;
        year++;
      } else month++;
      day = 1;
    } else day++;
  }

  /**
   * Subtracts this date by one day.
   */
  public void prevDay() {
    if (day==1) {
      if (month==1) {
        month = getNumberOfMonthsInYear();
        year--;
      } else month--;
      day = getNumberOfDaysInMonth();
    } else day--;
  }

//////////////////////////////////////////////////////////////////////////////
// abstract
  
  abstract String getDate();
  abstract String getName();
  abstract int getNumberOfDaysInMonth();

//////////////////////////////////////////////////////////////////////////////
// protected

  protected int year;
  protected int month;
  protected int day;
}
