package com.binarium.calendarmanager.service.location;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface LocationApiServiceRetrofit {
    @GET("/locations")
    Call<FindLocationsResponse> find(@Query("Name") String name, @Query("Type") int type, @Query("UserId") int userId, @Query("OnlyToday") boolean onlyToday, @Query("Date") String date);
    @POST("/locations")
    Call<CreateResponse> create(@Body LocationRequest request);
    @PUT("/locations")
    Call<SuccessResponse> update(@Body LocationRequest request);
    @GET("/locations/{Id}")
    Call<LocationResponse> get(@Path("Id") int id);
    @DELETE("/locations/{Id}")
    Call<SuccessResponse> delete(@Path("Id") int id);
}
