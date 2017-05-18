package com.binarium.calendarmanager.service.checkin;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInRequest;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.DeleteCheckInRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;
import com.binarium.calendarmanager.dto.checkin.GetCheckInRequest;
import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public class CheckInApiServiceImpl implements CheckInApiService {
    @Override
    public FindCheckInsResponse find(FindCheckInsRequest request, BaseListener baseListener) {
        try {
            CheckInApiServiceRetrofit checkInApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(CheckInApiServiceRetrofit.class);
            Call<FindCheckInsResponse> call = checkInApiServiceRetrofit.find(request.getType(), request.getUserId(), request.getLocationId(), request.isCreatedOnlyToday(), request.getCreatedDate());
            Response<FindCheckInsResponse> response = call.execute();

            if (response.isSuccessful()) {
                FindCheckInsResponse findCheckInsResponse = response.body();
                return findCheckInsResponse;
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
    public CreateResponse create(CheckInRequest request, BaseListener baseListener) {
        try {
            CheckInApiServiceRetrofit checkInApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(CheckInApiServiceRetrofit.class);
            Call<CreateResponse> call = checkInApiServiceRetrofit.create(request);
            Response<CreateResponse> response = call.execute();

            if (response.isSuccessful()) {
                CreateResponse createResponse = response.body();
                return createResponse;
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
    public CheckInResponse get(GetCheckInRequest request, BaseListener baseListener) {
        try {
            CheckInApiServiceRetrofit checkInApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(CheckInApiServiceRetrofit.class);
            Call<CheckInResponse> call = checkInApiServiceRetrofit.get(request.getId());
            Response<CheckInResponse> response = call.execute();

            if (response.isSuccessful()) {
                CheckInResponse checkInResponse = response.body();
                return checkInResponse;
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
    public SuccessResponse delete(DeleteCheckInRequest request, BaseListener baseListener) {
        try {
            CheckInApiServiceRetrofit checkInApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(CheckInApiServiceRetrofit.class);
            Call<SuccessResponse> call = checkInApiServiceRetrofit.delete(request.getId());
            Response<SuccessResponse> response = call.execute();

            if (response.isSuccessful()) {
                SuccessResponse successResponse = response.body();
                return successResponse;
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
