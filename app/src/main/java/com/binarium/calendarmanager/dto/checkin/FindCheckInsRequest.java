package com.binarium.calendarmanager.dto.checkin;

import com.binarium.calendarmanager.dto.base.FindBaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindCheckInsRequest extends FindBaseRequest {
    @SerializedName("Type")
    private int type;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("LocationId")
    private int locationId;
    @SerializedName("CreatedOnlyToday")
    private boolean createdOnlyToday;
    @SerializedName("CreatedDate")
    private String createdDate;

    public FindCheckInsRequest() {
    }

    public FindCheckInsRequest(String sort, String sortBy, int itemsToShow, int page, int type, int userId, int locationId, boolean createdOnlyToday, String createdDate) {
        super(sort, sortBy, itemsToShow, page);
        this.type = type;
        this.userId = userId;
        this.locationId = locationId;
        this.createdOnlyToday = createdOnlyToday;
        this.createdDate = createdDate;
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

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isCreatedOnlyToday() {
        return createdOnlyToday;
    }

    public void setCreatedOnlyToday(boolean createdOnlyToday) {
        this.createdOnlyToday = createdOnlyToday;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
