package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.interfaces.location.LocationInteractor;
import com.binarium.calendarmanager.interfaces.location.LocationListener;
import com.binarium.calendarmanager.interfaces.location.LocationPresenter;
import com.binarium.calendarmanager.interfaces.location.LocationView;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public class LocationPresenterImpl implements LocationPresenter, LocationListener {
    private LocationView locationView;
    private LocationInteractor locationInteractor;

    @Inject
    public LocationPresenterImpl(LocationInteractor locationInteractor) {
        this.locationInteractor = locationInteractor;
    }

    //region LocationPresenter

    @Override
    public void setLocationView(LocationView locationView) {
        this.locationView = locationView;
    }

    @Override
    public void getAllLocations(int userId, String date) {
        locationView.showProgress(ResourcesExtensions.toString(R.string.location_init_get_all_locations));
        locationInteractor.getAllLocations(userId, date, this);
    }

    @Override
    public void deleteLocation(Location location) {
        locationView.showProgress(ResourcesExtensions.toString(R.string.location_init_delete_location));
        locationInteractor.deleteLocation(location, this);
    }

    //endregion

    //region LocationListener

    @Override
    public void onSuccess(String message) {
        locationView.hideProgress();
        locationView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        locationView.hideProgress();
        locationView.showErrorMessage(message);
    }

    @Override
    public void getAllLocationsSuccess(List<Location> locations) {
        locationView.hideProgress();
        locationView.getAllLocationsSuccess(locations);
    }

    @Override
    public void deleteLocationSuccess(Location location) {
        locationView.hideProgress();
        locationView.showSuccessMessage(ResourcesExtensions.toString(R.string.location_delete_location_success));
        locationView.deleteLocationSuccess(location);
    }

    //endregion
}
