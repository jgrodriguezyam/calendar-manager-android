package com.binarium.calendarmanager.dto.sharedlocation;

import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class SharedLocationResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("User")
    private UserResponse user;
    @SerializedName("Location")
    private LocationResponse location;

    public int getId() {
        return id;
    }

    public UserResponse getUser() {
        return user;
    }

    public LocationResponse getLocation() {
        return location;
    }
}