package com.validator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Service for date check.
 *
 * @author Leonid Dubravsky
 */

public class CheckDateService {

    public static boolean checkBeforeDate(String firstDate, String secondDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(firstDate);
            Date date2 = sdf.parse(secondDate);

            if (date1.before(date2)) {
                return true;
            } else {
                return false;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean isDateFallInWeekend(String firstDate) {

        boolean isWeekend = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(firstDate);

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                isWeekend = true;
            }
            return isWeekend;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return isWeekend;
    }
}

