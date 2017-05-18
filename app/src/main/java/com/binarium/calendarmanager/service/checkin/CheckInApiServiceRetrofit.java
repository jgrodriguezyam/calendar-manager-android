package com.binarium.calendarmanager.service.checkin;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInRequest;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface CheckInApiServiceRetrofit {
    @GET("/checkins")
    Call<FindCheckInsResponse> find(@Query("Type") int type, @Query("UserId") int userId, @Query("LocationId") int locationId, @Query("CreatedOnlyToday") boolean createdOnlyToday, @Query("CreatedDate") String createdDate);
    @POST("/checkins")
    Call<CreateResponse> create(@Body CheckInRequest request);
    @GET("/checkins/{Id}")
    Call<CheckInResponse> get(@Path("Id") int id);
    @DELETE("/checkins/{Id}")
    Call<SuccessResponse> delete(@Path("Id") int id);
}
