package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class IdentifierBaseRequest {
    @SerializedName("Id")
    private int id;

    public IdentifierBaseRequest() {
    }

    public IdentifierBaseRequest(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
