package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 13/03/2017.
 */

public class ErrorMessage {

    @SerializedName("Message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
