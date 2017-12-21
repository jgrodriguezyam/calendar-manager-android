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
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public class FriendshipApiServiceImpl implements FriendshipApiService {
    @Override
    public FindFriendshipsResponse find(FindFriendshipsRequest request, BaseListener baseListener) {
        try {
            FriendshipApiServiceRetrofit friendshipApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(FriendshipApiServiceRetrofit.class);
            Call<FindFriendshipsResponse> call = friendshipApiServiceRetrofit.find(request.getUserId(), request.getFriendId(), request.isOnlyConfirmed(), request.isOnlyUnconfirmed(), request.getUserIdOrFriendId());
            Response<FindFriendshipsResponse> response = call.execute();

            if (response.isSuccessful()) {
                FindFriendshipsResponse findFriendshipsResponse = response.body();
                return findFriendshipsResponse;
            } else {
                String errorMessage = RetrofitBuilder.getErrorMessage(response.errorBody());
                baseListener.onError(errorMessage);
                return null;
            }
        } catch (Exception e) {
            baseListener.onError(e.getMessage());
            return null;
        }
    }

    @Override
    public CreateResponse create(FriendshipRequest request, BaseListener baseListener) {
        return null;
    }

    @Override
    public FriendshipResponse get(GetFriendshipRequest request, BaseListener baseListener) {
        return null;
    }

    @Override
    public SuccessResponse delete(DeleteFriendshipRequest request, BaseListener baseListener) {
        return null;
    }

    @Override
    public SuccessResponse confirm(ConfirmRequest request, BaseListener baseListener) {
        return null;
    }
}
