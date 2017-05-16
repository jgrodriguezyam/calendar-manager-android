package com.binarium.calendarmanager.infrastructure;

/**
 * Created by link_jorge on 20/11/2016.
 */

public class StringValidations {

    public static boolean IsNullOrEmpty(String value) {
        return (value == null || value.length() == 0);
    }

    public static boolean IsNotNullOrEmpty(String value) {
        return !IsNullOrEmpty(value);
    }
}
