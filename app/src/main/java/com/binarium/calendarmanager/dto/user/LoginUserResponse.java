package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class LoginUserResponse {
    @SerializedName("UserId")
    private int userId;

    public int getUserId() {
        return userId;
    }
}