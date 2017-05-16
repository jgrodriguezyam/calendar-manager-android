package com.binarium.calendarmanager.infrastructure;

import com.binarium.calendarmanager.myapp.CalendarManagerApplication;

/**
 * Created by jrodriguez on 15/12/2016.
 */

public class ResourcesExtensions {

    public static String toString(int id) {
        return CalendarManagerApplication.getInstance().getResources().getString(id);
    }

    public static int toInt(int id) {
        return CalendarManagerApplication.getInstance().getResources().getInteger(id);
    }
}
