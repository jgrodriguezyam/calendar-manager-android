package com.binarium.calendarmanager.infrastructure;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jrodriguez on 11/04/2017.
 */

public class DateExtensions {
    private String dateFormat;
    private String inputFormat;
    private SimpleDateFormat inputParser;

    public DateExtensions() {
        dateFormat = "dd/MM/yyyy";
        inputFormat = "HH:mm";
        inputParser = new SimpleDateFormat(inputFormat, Locale.getDefault());
    }

    public Date parseDate(String date) {
        try {
            return inputParser.parse(date);
        } catch (ParseException e) {
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

    public Calendar convertToCalendar(String date) {
        try {
            DateFormat currentDateFormat = new SimpleDateFormat(dateFormat);
            Date startDate = currentDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            return calendar;
        }
        catch (ParseException e){
            return null;
        }
    }

    public String convertToStringDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        DateFormat currentDateFormat = new SimpleDateFormat(dateFormat);
        return currentDateFormat.format(calendar.getTime());
    }
}
