package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by link_jorge on 10/04/2017.
 */

public class SuccessResponse {
    @SerializedName("IsSuccess")
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }
}
