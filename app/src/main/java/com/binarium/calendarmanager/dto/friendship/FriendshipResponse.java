package com.binarium.calendarmanager.dto.friendship;

import com.binarium.calendarmanager.dto.user.UserResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FriendshipResponse {
    @SerializedName("Id")
    private int id;
    @SerializedName("User")
    private UserResponse user;
    @SerializedName("Friend")
    private UserResponse friend;
    @SerializedName("IsConfirmed")
    private boolean isConfirmed;

    public int getId() {
        return id;
    }

    public UserResponse getUser() {
        return user;
    }

    public UserResponse getFriend() {
        return friend;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
