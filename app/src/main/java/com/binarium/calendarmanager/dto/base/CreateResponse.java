package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 07/04/2017.
 */

public class CreateResponse {
    @SerializedName("Id")
    private int id;

    public int getId() {
        return id;
    }
}
