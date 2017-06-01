package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public class DateResponse {
    @SerializedName("Date")
    private String date;

    public String getDate() {
        return date;
    }
}