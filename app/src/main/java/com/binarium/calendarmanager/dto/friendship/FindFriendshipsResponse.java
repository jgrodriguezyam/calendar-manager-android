package com.binarium.calendarmanager.dto.friendship;

import com.binarium.calendarmanager.dto.base.FindBaseResponse;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FindFriendshipsResponse extends FindBaseResponse {
    @SerializedName("Friendships")
    private List<FriendshipResponse> friendships;

    public List<FriendshipResponse> getFriendships() {
        return friendships;
    }
}