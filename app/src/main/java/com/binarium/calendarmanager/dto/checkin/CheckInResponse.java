package com.binarium.calendarmanager.dto.checkin;

import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class CheckInResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("Type")
    private int type;
    @SerializedName("User")
    private UserResponse user;
    @SerializedName("Location")
    private LocationResponse location;

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public UserResponse getUser() {
        return user;
    }

    public LocationResponse getLocation() {
        return location;
    }
}
