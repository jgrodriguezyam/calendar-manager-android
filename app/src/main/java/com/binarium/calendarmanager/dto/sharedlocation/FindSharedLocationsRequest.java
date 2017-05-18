package com.binarium.calendarmanager.dto.sharedlocation;

import com.binarium.calendarmanager.dto.base.FindBaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindSharedLocationsRequest extends FindBaseRequest {
    @SerializedName("UserId")
    private int userId;
    @SerializedName("LocationId")
    private int locationId;
    @SerializedName("LocationOnlyToday")
    private boolean locationOnlyToday;
    @SerializedName("LocationDate")
    private String locationDate;

    public FindSharedLocationsRequest() {
    }

    public FindSharedLocationsRequest(String sort, String sortBy, int itemsToShow, int page, int userId, int locationId, boolean locationOnlyToday, String locationDate) {
        super(sort, sortBy, itemsToShow, page);
        this.userId = userId;
        this.locationId = locationId;
        this.locationOnlyToday = locationOnlyToday;
        this.locationDate = locationDate;
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

    public boolean isLocationOnlyToday() {
        return locationOnlyToday;
    }

    public void setLocationOnlyToday(boolean locationOnlyToday) {
        this.locationOnlyToday = locationOnlyToday;
    }

    public String getLocationDate() {
        return locationDate;
    }

    public void setLocationDate(String locationDate) {
        this.locationDate = locationDate;
    }
}
