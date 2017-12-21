package com.binarium.calendarmanager.service.friendship;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.friendship.ConfirmRequest;
import com.binarium.calendarmanager.dto.friendship.DeleteFriendshipRequest;
import com.binarium.calendarmanager.dto.friendship.FindFriendshipsRequest;
import com.binarium.calendarmanager.dto.friendship.FindFriendshipsResponse;
import com.binarium.calendarmanager.dto.friendship.FriendshipRequest;
import com.binarium.calendarmanager.dto.friendship.FriendshipResponse;
import com.binarium.calendarmanager.dto.friendship.GetFriendshipRequest;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public interface FriendshipApiService {
    FindFriendshipsResponse find(FindFriendshipsRequest request, BaseListener baseListener);
    CreateResponse create(FriendshipRequest request, BaseListener baseListener);
    FriendshipResponse get(GetFriendshipRequest request, BaseListener baseListener);
    SuccessResponse delete(DeleteFriendshipRequest request, BaseListener baseListener);
    SuccessResponse confirm(ConfirmRequest request, BaseListener baseListener);
}