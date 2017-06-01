package com.binarium.calendarmanager.service.application;

import com.binarium.calendarmanager.dto.base.DateResponse;
import com.binarium.calendarmanager.dto.base.IsAliveResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public class ApplicationApiServiceImpl implements ApplicationApiService {
    @Override
    public IsAliveResponse isAlive(BaseListener baseListener) {
        try {
            ApplicationApiServiceRetrofit applicationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(ApplicationApiServiceRetrofit.class);
            Call<IsAliveResponse> call = applicationApiServiceRetrofit.isAlive();
            Response<IsAliveResponse> response = call.execute();

            if (response.isSuccessful()) {
                IsAliveResponse isAliveResponse = response.body();
                return isAliveResponse;
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
    public DateResponse getDate(BaseListener baseListener) {
        try {
            ApplicationApiServiceRetrofit applicationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(ApplicationApiServiceRetrofit.class);
            Call<DateResponse> call = applicationApiServiceRetrofit.getDate();
            Response<DateResponse> response = call.execute();

            if (response.isSuccessful()) {
                DateResponse dateResponse = response.body();
                return dateResponse;
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
}
