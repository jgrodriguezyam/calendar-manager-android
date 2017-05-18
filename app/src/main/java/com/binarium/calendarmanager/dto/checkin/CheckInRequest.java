package com.binarium.calendarmanager.dto.checkin;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class CheckInRequest {
    @SerializedName("Id")
    private int id;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("LocationId")
    private int locationId;

    public CheckInRequest() {
    }

    public CheckInRequest(int id, int userId, int locationId) {
        this.id = id;
        this.userId = userId;
        this.locationId = locationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}
