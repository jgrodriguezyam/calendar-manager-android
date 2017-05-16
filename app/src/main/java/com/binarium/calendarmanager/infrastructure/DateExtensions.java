package com.binarium.calendarmanager.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jrodriguez on 11/04/2017.
 */

public class DateExtensions {

    private String inputFormat;
    private SimpleDateFormat inputParser;

    public DateExtensions() {
        inputFormat = "HH:mm";
        inputParser = new SimpleDateFormat(inputFormat, Locale.getDefault());
    }

    public Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (java.text.ParseException e) {
            return new Date(0);
        }
    }

    public Date getHourWithMinuteOfToday() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        return parseDate(hour + ":" + minute);
    }

    public boolean isSameHour(Date dateOne, Date dateTwo) {
        return dateOne.getTime() == dateTwo.getTime();
    }
}
