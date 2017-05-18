package com.binarium.calendarmanager.dto.sharedlocation;

import com.binarium.calendarmanager.dto.base.FindBaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindSharedLocationsResponse extends FindBaseResponse {
    @SerializedName("SharedLocations")
    private List<SharedLocationResponse> sharedLocations;

    public List<SharedLocationResponse> getSharedLocations() {
        return sharedLocations;
    }
}
