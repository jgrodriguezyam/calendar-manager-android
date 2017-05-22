package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;
import com.binarium.calendarmanager.dto.location.FindLocationsRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsRequest;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsResponse;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;
import com.binarium.calendarmanager.dto.user.UserRequest;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapListener;
import com.binarium.calendarmanager.interfaces.location.LocationInteractor;
import com.binarium.calendarmanager.interfaces.location.LocationListener;
import com.binarium.calendarmanager.mappers.LocationMapper;
import com.binarium.calendarmanager.service.checkin.CheckInApiService;
import com.binarium.calendarmanager.service.location.LocationApiService;
import com.binarium.calendarmanager.service.sharedlocation.SharedLocationApiService;
import com.binarium.calendarmanager.viewmodels.location.Location;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationInteractorImpl implements LocationInteractor {
    private LocationApiService locationApiService;
    private SharedLocationApiService sharedLocationApiService;

    @Inject
    public LocationInteractorImpl(LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService) {
        this.locationApiService = locationApiService;
        this.sharedLocationApiService = sharedLocationApiService;
    }

    @Override
    public void getAllLocations(int userId, LocationListener locationListener) {
        getAllLocationsAsync(userId, locationListener);
    }

    @UiThread
    private void getAllLocationsAsync(int userId, final LocationListener locationListener) {
        new AsyncTask<Integer, Void, List<Location>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Location> doInBackground(Integer... params) {
                List<Location> userLocations = new ArrayList<>();

                List<Location> locations = findLoctations(params[0], locationListener);
                if (CollectionValidations.IsNotEmpty(locations))
                    userLocations.addAll(locations);

                List<Location> sharedLocations = findSharedLoctations(params[0], locationListener);
                if (CollectionValidations.IsNotEmpty(sharedLocations))
                    userLocations.addAll(sharedLocations);

                return userLocations;
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                super.onPostExecute(locations);
                if (CollectionValidations.IsNotEmpty(locations)) {
                    locationListener.getAllLocationsSuccess(locations);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userId);
    }

    private List<Location> findLoctations(int userId, LocationListener locationListener) {
        FindLocationsRequest findLocationsRequest = new FindLocationsRequest();
        findLocationsRequest.setUserId(userId);
        findLocationsRequest.setOnlyToday(true);
        FindLocationsResponse findLocationsResponse = locationApiService.find(findLocationsRequest, locationListener);
        List<Location> locations = new ArrayList<>();
        for (LocationResponse locationResponse : findLocationsResponse.getLocations()) {
            Location location = LocationMapper.toLocation(locationResponse);
            location.setOwner(true);
            locations.add(location);
        }
        return locations;
    }

    private List<Location> findSharedLoctations(int userId, LocationListener locationListener) {
        FindSharedLocationsRequest findSharedLocationsRequest = new FindSharedLocationsRequest();
        findSharedLocationsRequest.setUserId(userId);
        findSharedLocationsRequest.setLocationOnlyToday(true);
        FindSharedLocationsResponse findSharedLocationsResponse = sharedLocationApiService.find(findSharedLocationsRequest, locationListener);
        List<Location> locations = new ArrayList<>();
        for (SharedLocationResponse sharedLocationResponse : findSharedLocationsResponse.getSharedLocations()) {
            Location location = LocationMapper.toLocation(sharedLocationResponse.getLocation());
            locations.add(location);
        }
        return locations;
    }

    @Override
    public void updateLocation(Location location, LocationListener locationListener) {
        LocationRequest locationRequest = LocationMapper.ToLocationRequest(location);
        updateLocationAsync(locationRequest, locationListener);
    }

    @UiThread
    private void updateLocationAsync(LocationRequest locationRequest, final LocationListener locationListener) {
        new AsyncTask<LocationRequest, Void, SuccessResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected SuccessResponse doInBackground(LocationRequest... params) {
                SuccessResponse successResponse = locationApiService.update(params[0], locationListener);
                return successResponse;
            }

            @Override
            protected void onPostExecute(SuccessResponse successResponse) {
                super.onPostExecute(successResponse);
                if (ObjectValidations.IsNotNull(successResponse)) {
                    locationListener.updateLocationSuccess();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, locationRequest);
    }
}
