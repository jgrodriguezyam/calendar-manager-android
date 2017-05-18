package com.binarium.calendarmanager.dto.location;

import com.binarium.calendarmanager.dto.base.FindBaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindLocationsResponse extends FindBaseResponse {
    @SerializedName("Locations")
    private List<LocationResponse> locations;

    public List<LocationResponse> getLocations() {
        return locations;
    }
}
