package com.binarium.calendarmanager.dto.location;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 24/05/2017.
 */

public class RadiusResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;

    public RadiusResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return getName();
    }
}
