package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;
import com.binarium.calendarmanager.dto.location.DeleteLocationRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsRequest;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsResponse;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;
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
    private CheckInApiService checkInApiService;

    @Inject
    public LocationInteractorImpl(LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService, CheckInApiService checkInApiService) {
        this.locationApiService = locationApiService;
        this.sharedLocationApiService = sharedLocationApiService;
        this.checkInApiService = checkInApiService;
    }

    @Override
    public void getAllLocations(int userId, String date, LocationListener locationListener) {
        FindLocationsRequest findLocationsRequest = new FindLocationsRequest();
        findLocationsRequest.setUserId(userId);
        findLocationsRequest.setDate(date);
        getAllLocationsAsync(findLocationsRequest, locationListener);
    }

    @UiThread
    private void getAllLocationsAsync(FindLocationsRequest findLocationsRequest, final LocationListener locationListener) {
        new AsyncTask<FindLocationsRequest, Void, List<Location>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Location> doInBackground(FindLocationsRequest... params) {
                List<Location> userLocations = new ArrayList<>();

                List<Location> locations = findLoctations(params[0].getUserId(), params[0].getDate(), locationListener);
                if (CollectionValidations.IsNotEmpty(locations))
                    userLocations.addAll(locations);

                List<Location> sharedLocations = findSharedLoctations(params[0].getUserId(), params[0].getDate(), locationListener);
                if (CollectionValidations.IsNotEmpty(sharedLocations))
                    userLocations.addAll(sharedLocations);

                List<CheckInResponse> checkInsResponse = findCheckInsResponse(params[0].getUserId(), params[0].getDate(), locationListener);
                if (CollectionValidations.IsNotEmpty(checkInsResponse))
                    compareLocationsWithCheckIns(userLocations, checkInsResponse);

                return userLocations;
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                super.onPostExecute(locations);
                locationListener.getAllLocationsSuccess(locations);
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, findLocationsRequest);
    }

    private List<Location> findLoctations(int userId, String date, LocationListener locationListener) {
        FindLocationsRequest findLocationsRequest = new FindLocationsRequest();
        findLocationsRequest.setUserId(userId);
        findLocationsRequest.setDate(date);
        FindLocationsResponse findLocationsResponse = locationApiService.find(findLocationsRequest, locationListener);
        List<Location> locations = new ArrayList<>();
        for (LocationResponse locationResponse : findLocationsResponse.getLocations()) {
            Location location = LocationMapper.toLocation(locationResponse);
            location.setOwner(true);
            locations.add(location);
        }
        return locations;
    }

    private List<Location> findSharedLoctations(int userId, String date, LocationListener locationListener) {
        FindSharedLocationsRequest findSharedLocationsRequest = new FindSharedLocationsRequest();
        findSharedLocationsRequest.setUserId(userId);
        findSharedLocationsRequest.setLocationDate(date);
        FindSharedLocationsResponse findSharedLocationsResponse = sharedLocationApiService.find(findSharedLocationsRequest, locationListener);
        List<Location> locations = new ArrayList<>();
        for (SharedLocationResponse sharedLocationResponse : findSharedLocationsResponse.getSharedLocations()) {
            Location location = LocationMapper.toLocation(sharedLocationResponse.getLocation());
            locations.add(location);
        }
        return locations;
    }

    private List<CheckInResponse> findCheckInsResponse(int userId, String date, LocationListener locationListener) {
        FindCheckInsRequest findCheckInsRequest = new FindCheckInsRequest();
        findCheckInsRequest.setUserId(userId);
        findCheckInsRequest.setCreatedDate(date);
        FindCheckInsResponse findCheckInsResponse = checkInApiService.find(findCheckInsRequest, locationListener);
        return findCheckInsResponse.getCheckIns();
    }

    private void compareLocationsWithCheckIns(List<Location> locations, List<CheckInResponse> checkInsResponse) {
        for (final CheckInResponse checkInResponse : checkInsResponse) {
            List<Location> currentLocations = FluentIterable.from(locations).filter(new Predicate<Location>() {
                @Override
                public boolean apply(Location location) {
                    return location.getId() == checkInResponse.getLocation().getId();
                }
            }).toList();
            Location location = Iterables.getFirst(currentLocations, null);

            if (ObjectValidations.IsNotNull(location)) {
                location.setChecked(true);
                location.setCheckInDate(checkInResponse.getCreatedOn());
            } else {
                Location checkInlocation = LocationMapper.toLocation(checkInResponse.getLocation());
                checkInlocation.setChecked(true);
                checkInlocation.setCheckInDate(checkInResponse.getCreatedOn());
                locations.add(checkInlocation);
            }
        }
    }

    @Override
    public void deleteLocation(Location location, LocationListener locationListener) {
        LocationRequest locationRequest = LocationMapper.ToLocationRequest(location);
        deleteLocationAsync(locationRequest, locationListener);
    }

    @UiThread
    private void deleteLocationAsync(final LocationRequest locationRequest, final LocationListener locationListener) {
        new AsyncTask<LocationRequest, Void, SuccessResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected SuccessResponse doInBackground(LocationRequest... params) {
                DeleteLocationRequest deleteLocationRequest = new DeleteLocationRequest(params[0].getId());
                SuccessResponse successResponse = locationApiService.delete(deleteLocationRequest, locationListener);
                return successResponse;
            }

            @Override
            protected void onPostExecute(SuccessResponse successResponse) {
                super.onPostExecute(successResponse);
                if (ObjectValidations.IsNotNull(successResponse)) {
                    Location location = LocationMapper.ToLocation(locationRequest);
                    locationListener.deleteLocationSuccess(location);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, locationRequest);
    }
}
