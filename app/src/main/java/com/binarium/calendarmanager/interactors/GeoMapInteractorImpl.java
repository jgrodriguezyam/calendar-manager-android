package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInRequest;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;
import com.binarium.calendarmanager.dto.location.FindLocationsRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsRequest;
import com.binarium.calendarmanager.dto.sharedlocation.FindSharedLocationsResponse;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;
import com.binarium.calendarmanager.infrastructure.CollectionValidations;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapInteractor;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapListener;
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
 * Created by jrodriguez on 18/05/2017.
 */

public class GeoMapInteractorImpl implements GeoMapInteractor {
    private CheckInApiService checkInApiService;
    private LocationApiService locationApiService;
    private SharedLocationApiService sharedLocationApiService;

    @Inject
    public GeoMapInteractorImpl(CheckInApiService checkInApiService, LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService) {
        this.checkInApiService = checkInApiService;
        this.locationApiService = locationApiService;
        this.sharedLocationApiService = sharedLocationApiService;
    }

    @Override
    public void createCheckIn(int userId, int locationId, GeoMapListener geoMapListener) {
        CheckInRequest checkInRequest = new CheckInRequest();
        checkInRequest.setUserId(userId);
        checkInRequest.setLocationId(locationId);
        createCheckInAsync(checkInRequest, geoMapListener);
    }

    @Override
    public void getAllLocations(int userId, GeoMapListener geoMapListener) {
        getAllLocationsAsync(userId, geoMapListener);
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

    @UiThread
    private void getAllLocationsAsync(int userId, final GeoMapListener geoMapListener) {
        new AsyncTask<Integer, Void, List<Location>>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected List<Location> doInBackground(Integer... params) {
                List<Location> userLocations = new ArrayList<>();

                List<Location> locations = findLoctations(params[0], geoMapListener);
                if (CollectionValidations.IsNotEmpty(locations))
                    userLocations.addAll(locations);

                List<Location> sharedLocations = findSharedLoctations(params[0], geoMapListener);
                if (CollectionValidations.IsNotEmpty(sharedLocations))
                    userLocations.addAll(sharedLocations);

                List<CheckInResponse> checkInsResponse = findCheckInsResponse(params[0], geoMapListener);
                if (CollectionValidations.IsNotEmpty(checkInsResponse))
                    compareLocationsWithCheckIns(userLocations, checkInsResponse);

                return userLocations;
            }

            @Override
            protected void onPostExecute(List<Location> locations) {
                super.onPostExecute(locations);
                if (CollectionValidations.IsNotEmpty(locations)) {
                    geoMapListener.getAllLocationsSuccess(locations);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, userId);
    }

    private List<Location> findLoctations(int userId, GeoMapListener geoMapListener) {
        FindLocationsRequest findLocationsRequest = new FindLocationsRequest();
        findLocationsRequest.setUserId(userId);
        findLocationsRequest.setOnlyToday(true);
        FindLocationsResponse findLocationsResponse = locationApiService.find(findLocationsRequest, geoMapListener);
        List<Location> locations = new ArrayList<>();
        for (LocationResponse locationResponse : findLocationsResponse.getLocations()) {
            Location location = new Location();
            location.setId(locationResponse.getId());
            location.setName(locationResponse.getName());
            location.setLatitude(locationResponse.getLatitude());
            location.setLongitude(locationResponse.getLongitude());
            location.setRadius(locationResponse.getRadius());
            location.setType(locationResponse.getType());
            location.setStartDate(locationResponse.getStartDate());
            location.setEndDate(locationResponse.getEndDate());
            location.setOwner(true);
            locations.add(location);
        }
        return locations;
    }

    private List<Location> findSharedLoctations(int userId, GeoMapListener geoMapListener) {
        FindSharedLocationsRequest findSharedLocationsRequest = new FindSharedLocationsRequest();
        findSharedLocationsRequest.setUserId(userId);
        findSharedLocationsRequest.setLocationOnlyToday(true);
        FindSharedLocationsResponse findSharedLocationsResponse = sharedLocationApiService.find(findSharedLocationsRequest, geoMapListener);
        List<Location> locations = new ArrayList<>();
        for (SharedLocationResponse sharedLocationResponse : findSharedLocationsResponse.getSharedLocations()) {
            Location location = new Location();
            location.setId(sharedLocationResponse.getLocation().getId());
            location.setName(sharedLocationResponse.getLocation().getName());
            location.setLatitude(sharedLocationResponse.getLocation().getLatitude());
            location.setLongitude(sharedLocationResponse.getLocation().getLongitude());
            location.setRadius(sharedLocationResponse.getLocation().getRadius());
            location.setType(sharedLocationResponse.getLocation().getType());
            location.setStartDate(sharedLocationResponse.getLocation().getStartDate());
            location.setEndDate(sharedLocationResponse.getLocation().getEndDate());
            locations.add(location);
        }
        return locations;
    }

    private List<CheckInResponse> findCheckInsResponse(int userId, GeoMapListener geoMapListener) {
        FindCheckInsRequest findCheckInsRequest = new FindCheckInsRequest();
        findCheckInsRequest.setUserId(userId);
        findCheckInsRequest.setCreatedOnlyToday(true);
        FindCheckInsResponse findCheckInsResponse = checkInApiService.find(findCheckInsRequest, geoMapListener);
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
            } else {
                Location checkInlocation = new Location();
                checkInlocation.setId(checkInResponse.getLocation().getId());
                checkInlocation.setName(checkInResponse.getLocation().getName());
                checkInlocation.setLatitude(checkInResponse.getLocation().getLatitude());
                checkInlocation.setLongitude(checkInResponse.getLocation().getLongitude());
                checkInlocation.setRadius(checkInResponse.getLocation().getRadius());
                checkInlocation.setType(checkInResponse.getLocation().getType());
                checkInlocation.setStartDate(checkInResponse.getLocation().getStartDate());
                checkInlocation.setEndDate(checkInResponse.getLocation().getEndDate());
                checkInlocation.setChecked(true);
                locations.add(checkInlocation);
            }
        }
    }
}
