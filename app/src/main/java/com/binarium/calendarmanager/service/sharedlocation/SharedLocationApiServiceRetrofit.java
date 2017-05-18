package com.binarium.calendarmanager.service.sharedlocation;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsResponse;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface SharedLocationApiServiceRetrofit {
    @GET("/shared-locations")
    Call<FindSharedLocationsResponse> find(@Query("UserId") int userId, @Query("LocationId") int locationId, @Query("LocationOnlyToday") boolean locationOnlyToday, @Query("LocationDate") String locationDate);
    @POST("/shared-locations")
    Call<CreateResponse> create(@Body SharedLocationRequest request);
    @GET("/shared-locations/{Id}")
    Call<SharedLocationResponse> get(@Path("Id") int id);
    @DELETE("/shared-locations/{Id}")
    Call<SuccessResponse> delete(@Path("Id") int id);
}
