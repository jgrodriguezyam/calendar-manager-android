package com.binarium.calendarmanager.service.friendship;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.friendship.ConfirmRequest;
import com.binarium.calendarmanager.dto.friendship.FindFriendshipsResponse;
import com.binarium.calendarmanager.dto.friendship.FriendshipRequest;
import com.binarium.calendarmanager.dto.friendship.FriendshipResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jrodriguez on 03/08/2017.
 */

public interface FriendshipApiServiceRetrofit {
    @GET("/friendships")
    Call<FindFriendshipsResponse> find(@Query("UserId") int userId, @Query("FriendId") int friendId, @Query("OnlyConfirmed") boolean onlyConfirmed, @Query("OnlyUnconfirmed") boolean onlyUnconfirmed, @Query("UserIdOrFriendId") int userIdOrFriendId);
    @POST("/friendships")
    Call<CreateResponse> create(@Body FriendshipRequest request);
    @GET("/friendships/{Id}")
    Call<FriendshipResponse> get(@Path("Id") int id);
    @DELETE("/friendships/{Id}")
    Call<SuccessResponse> delete(@Path("Id") int id);
    @PUT("/friendships/confirm")
    Call<SuccessResponse> confirm(@Body ConfirmRequest request);
}