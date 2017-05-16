package com.binarium.calendarmanager.infrastructure.enums;

/**
 * Created by jrodriguez on 04/05/2017.
 */

public enum Gender {
    MALE(1, "Masculino"),
    FEMALE(2, "Femenino");

    private int key;
    private String value;

    private Gender(int enumKey, String enumValue) {
        key = enumKey;
        value = enumValue;
    }

    public int getIdentifier() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
