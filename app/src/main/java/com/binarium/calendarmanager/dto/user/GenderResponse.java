package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class GenderResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;

    public GenderResponse(int id, String name) {
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
