package com.binarium.calendarmanager.dto.friendship;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FriendshipRequest {
    @SerializedName("Id")
    private int id;
    @SerializedName("UserId")
    private int userId;
    @SerializedName("FriendId")
    private int friendId;

    public FriendshipRequest() {
    }

    public FriendshipRequest(int id, int userId, int friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}