package com.binarium.calendarmanager.dto.checkin;

import com.binarium.calendarmanager.dto.base.FindBaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class FindCheckInsResponse extends FindBaseResponse {
    @SerializedName("CheckIns")
    private List<CheckInResponse> checkIns;

    public List<CheckInResponse> getCheckIns() {
        return checkIns;
    }
}
