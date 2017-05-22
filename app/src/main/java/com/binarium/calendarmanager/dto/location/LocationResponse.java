package com.binarium.calendarmanager.dto.location;

import com.binarium.calendarmanager.dto.user.UserResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class LocationResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Radius")
    private double radius;
    @SerializedName("Type")
    private int type;
    @SerializedName("StartDate")
    private String startDate;
    @SerializedName("EndDate")
    private String endDate;
    @SerializedName("Comment")
    private String comment;
    @SerializedName("User")
    private UserResponse user;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getRadius() {
        return radius;
    }

    public int getType() {
        return type;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getComment() {
        return comment;
    }

    public UserResponse getUser() {
        return user;
    }
}