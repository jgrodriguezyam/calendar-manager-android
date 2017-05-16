package com.binarium.calendarmanager.infrastructure;

/**
 * Created by link_jorge on 20/11/2016.
 */

public class ObjectValidations {

    public static boolean IsNull(Object value) {
        return value == null;
    }

    public static boolean IsNotNull(Object value) {
        return !IsNull(value);
    }
}
