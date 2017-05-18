package com.binarium.calendarmanager.service.location;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.location.DeleteLocationRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.GetLocationRequest;
import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class LocationApiServiceImpl implements LocationApiService {
    @Override
    public FindLocationsResponse find(FindLocationsRequest request, BaseListener baseListener) {
        try {
            LocationApiServiceRetrofit locationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(LocationApiServiceRetrofit.class);
            Call<FindLocationsResponse> call = locationApiServiceRetrofit.find(request.getName(), request.getType(), request.getUserId(), request.getDate());
            Response<FindLocationsResponse> response = call.execute();

            if (response.isSuccessful()) {
                FindLocationsResponse findLocationsResponse = response.body();
                return findLocationsResponse;
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
    public CreateResponse create(LocationRequest request, BaseListener baseListener) {
        try {
            LocationApiServiceRetrofit locationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(LocationApiServiceRetrofit.class);
            Call<CreateResponse> call = locationApiServiceRetrofit.create(request);
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
    public SuccessResponse update(LocationRequest request, BaseListener baseListener) {
        try {
            LocationApiServiceRetrofit locationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(LocationApiServiceRetrofit.class);
            Call<SuccessResponse> call = locationApiServiceRetrofit.update(request);
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

    @Override
    public LocationResponse get(GetLocationRequest request, BaseListener baseListener) {
        try {
            LocationApiServiceRetrofit locationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(LocationApiServiceRetrofit.class);
            Call<LocationResponse> call = locationApiServiceRetrofit.get(request.getId());
            Response<LocationResponse> response = call.execute();

            if (response.isSuccessful()) {
                LocationResponse locationResponse = response.body();
                return locationResponse;
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
    public SuccessResponse delete(DeleteLocationRequest request, BaseListener baseListener) {
        try {
            LocationApiServiceRetrofit locationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(LocationApiServiceRetrofit.class);
            Call<SuccessResponse> call = locationApiServiceRetrofit.delete(request.getId());
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