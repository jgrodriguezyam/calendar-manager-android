package com.binarium.calendarmanager.service.application;

import com.binarium.calendarmanager.dto.base.DateResponse;
import com.binarium.calendarmanager.dto.base.IsAliveResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public interface ApplicationApiServiceRetrofit {
    @GET("/alive")
    Call<IsAliveResponse> isAlive();
    @GET("/date")
    Call<DateResponse> getDate();
}
