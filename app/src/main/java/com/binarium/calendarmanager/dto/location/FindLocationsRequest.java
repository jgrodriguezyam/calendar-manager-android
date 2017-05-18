package com.binarium.calendarmanager.dto.location;

import com.binarium.calendarmanager.dto.base.FindBaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindLocationsRequest extends FindBaseRequest {
    @SerializedName("Name")
    private String name;
    @SerializedName("Type")
    private int type;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("OnlyToday")
    private boolean onlyToday;
    @SerializedName("Date")
    private String date;

    public FindLocationsRequest() {
    }

    public FindLocationsRequest(String sort, String sortBy, int itemsToShow, int page, String name, int type, int userId, boolean onlyToday, String date) {
        super(sort, sortBy, itemsToShow, page);
        this.name = name;
        this.type = type;
        this.userId = userId;
        this.onlyToday = onlyToday;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isOnlyToday() {
        return onlyToday;
    }

    public void setOnlyToday(boolean onlyToday) {
        this.onlyToday = onlyToday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
