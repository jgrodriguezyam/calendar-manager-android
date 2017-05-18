package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInRequest;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapInteractor;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapListener;
import com.binarium.calendarmanager.service.checkin.CheckInApiService;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public class GeoMapInteractorImpl implements GeoMapInteractor {
    private CheckInApiService checkInApiService;

    @Inject
    public GeoMapInteractorImpl(CheckInApiService checkInApiService) {
        this.checkInApiService = checkInApiService;
    }

    @Override
    public void createCheckIn(int userId, int locationId, GeoMapListener geoMapListener) {
        CheckInRequest checkInRequest = new CheckInRequest();
        checkInRequest.setUserId(userId);
        checkInRequest.setLocationId(locationId);
        createCheckInAsync(checkInRequest, geoMapListener);
    }

    @UiThread
    private void createCheckInAsync(final CheckInRequest checkInRequest, final GeoMapListener geoMapListener) {
        new AsyncTask<CheckInRequest, Void, CreateResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected CreateResponse doInBackground(CheckInRequest... params) {
                CreateResponse createResponse = checkInApiService.create(params[0], geoMapListener);
                return createResponse;
            }

            @Override
            protected void onPostExecute(CreateResponse createResponse) {
                super.onPostExecute(createResponse);
                if (ObjectValidations.IsNotNull(createResponse)) {
                    geoMapListener.createCheckInSuccess(checkInRequest.getUserId(), checkInRequest.getLocationId());
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, checkInRequest);
    }
}
