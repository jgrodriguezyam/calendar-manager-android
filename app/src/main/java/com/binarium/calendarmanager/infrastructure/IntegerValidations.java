package com.binarium.calendarmanager.infrastructure;

/**
 * Created by link_jorge on 20/11/2016.
 */

public class IntegerValidations {

    public static boolean IsEqualTo(int intValue, int valueToCompare) {
        return intValue == valueToCompare;
    }

    public static boolean IsNotEqualTo(int intValue, int valueToCompare) {
        return !IsEqualTo(intValue, valueToCompare);
    }

    public static boolean IsGreaterThan(int intValue, int valueToCompare) {
        return intValue > valueToCompare;
    }

    public static boolean IsLessThan(int intValue, int valueToCompare) {
        return intValue < valueToCompare;
    }

    public static boolean IsZero(int value) {
        return value == 0;
    }

    public static boolean IsNotZero(int value) {
        return !IsZero(value);
    }
}
