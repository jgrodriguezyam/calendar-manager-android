package com.binarium.calendarmanager.dto.friendship;

import com.binarium.calendarmanager.dto.base.FindBaseRequest;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FindFriendshipsRequest extends FindBaseRequest {
    @SerializedName("UserId")
    private int userId;
    @SerializedName("FriendId")
    private int friendId;
    @SerializedName("OnlyConfirmed")
    private boolean onlyConfirmed;
    @SerializedName("OnlyUnconfirmed")
    private boolean onlyUnconfirmed;
    @SerializedName("UserIdOrFriendId")
    private int userIdOrFriendId;

    public FindFriendshipsRequest() {
    }

    public FindFriendshipsRequest(String sort, String sortBy, int itemsToShow, int page, int userId, int friendId, boolean onlyConfirmed, boolean onlyUnconfirmed, int userIdOrFriendId) {
        super(sort, sortBy, itemsToShow, page);
        this.userId = userId;
        this.friendId = friendId;
        this.onlyConfirmed = onlyConfirmed;
        this.onlyUnconfirmed = onlyUnconfirmed;
        this.userIdOrFriendId = userIdOrFriendId;
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

    public boolean isOnlyConfirmed() {
        return onlyConfirmed;
    }

    public void setOnlyConfirmed(boolean onlyConfirmed) {
        this.onlyConfirmed = onlyConfirmed;
    }

    public boolean isOnlyUnconfirmed() {
        return onlyUnconfirmed;
    }

    public void setOnlyUnconfirmed(boolean onlyUnconfirmed) {
        this.onlyUnconfirmed = onlyUnconfirmed;
    }

    public int getUserIdOrFriendId() {
        return userIdOrFriendId;
    }

    public void setUserIdOrFriendId(int userIdOrFriendId) {
        this.userIdOrFriendId = userIdOrFriendId;
    }
}
