package com.binarium.calendarmanager.dto.user;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 06/06/2017.
 */

public class AddImageUserResponse {
    @SerializedName("ImagePath")
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }
}
