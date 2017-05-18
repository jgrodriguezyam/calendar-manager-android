package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindBaseResponse {
    @SerializedName("TotalRecords")
    private int totalRecords;

    public int getTotalRecords() {
        return totalRecords;
    }
}
