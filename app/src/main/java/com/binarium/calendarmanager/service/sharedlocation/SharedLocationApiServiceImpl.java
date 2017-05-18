package com.binarium.calendarmanager.service.sharedlocation;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.sharedlocation.DeleteSharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsRequest;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsResponse;
import com.binarium.calendarmanager.dto.sharedlocation.GetSharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class SharedLocationApiServiceImpl implements SharedLocationApiService {
    @Override
    public FindSharedLocationsResponse find(FindSharedLocationsRequest request, BaseListener baseListener) {
        try {
            SharedLocationApiServiceRetrofit sharedLocationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(SharedLocationApiServiceRetrofit.class);
            Call<FindSharedLocationsResponse> call = sharedLocationApiServiceRetrofit.find(request.getUserId(), request.getLocationId(), request.isLocationOnlyToday(), request.getLocationDate());
            Response<FindSharedLocationsResponse> response = call.execute();

            if (response.isSuccessful()) {
                FindSharedLocationsResponse findSharedLocationsResponse = response.body();
                return findSharedLocationsResponse;
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
    public CreateResponse create(SharedLocationRequest request, BaseListener baseListener) {
        try {
            SharedLocationApiServiceRetrofit sharedLocationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(SharedLocationApiServiceRetrofit.class);
            Call<CreateResponse> call = sharedLocationApiServiceRetrofit.create(request);
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
    public SharedLocationResponse get(GetSharedLocationRequest request, BaseListener baseListener) {
        try {
            SharedLocationApiServiceRetrofit sharedLocationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(SharedLocationApiServiceRetrofit.class);
            Call<SharedLocationResponse> call = sharedLocationApiServiceRetrofit.get(request.getId());
            Response<SharedLocationResponse> response = call.execute();

            if (response.isSuccessful()) {
                SharedLocationResponse sharedLocationResponse = response.body();
                return sharedLocationResponse;
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
    public SuccessResponse delete(DeleteSharedLocationRequest request, BaseListener baseListener) {
        try {
            SharedLocationApiServiceRetrofit sharedLocationApiServiceRetrofit = RetrofitBuilder.getRetrofit().create(SharedLocationApiServiceRetrofit.class);
            Call<SuccessResponse> call = sharedLocationApiServiceRetrofit.delete(request.getId());
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
