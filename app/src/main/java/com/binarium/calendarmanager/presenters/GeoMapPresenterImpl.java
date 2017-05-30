package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapInteractor;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapListener;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapPresenter;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapView;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public class GeoMapPresenterImpl implements GeoMapPresenter, GeoMapListener {
    private GeoMapView geoMapView;
    private GeoMapInteractor geoMapInteractor;

    @Inject
    public GeoMapPresenterImpl(GeoMapInteractor geoMapInteractor) {
        this.geoMapInteractor = geoMapInteractor;
    }

    //region GeoMapPresenter

    @Override
    public void setGeoMapView(GeoMapView geoMapView) {
        this.geoMapView = geoMapView;
    }

    @Override
    public void createCheckIn(int userId, int locationId) {
        geoMapView.showProgress(ResourcesExtensions.toString(R.string.init_check_in));
        geoMapInteractor.createCheckIn(userId, locationId, this);
    }

    @Override
    public void getAllLocations(int userId, String date) {
        geoMapView.showProgress(ResourcesExtensions.toString(R.string.init_get_all_locations));
        geoMapInteractor.getAllLocations(userId, date, this);
    }

    //endregion

    //region GeoMapListener

    @Override
    public void onSuccess(String message) {
        geoMapView.hideProgress();
        geoMapView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        geoMapView.hideProgress();
        geoMapView.showErrorMessage(message);
    }

    @Override
    public void createCheckInSuccess(int userId, int locationId) {
        geoMapView.hideProgress();
        geoMapView.showSuccessMessage(ResourcesExtensions.toString(R.string.create_check_in_success));
        geoMapView.createCheckInSuccess(userId, locationId);
    }

    @Override
    public void getAllLocationsSuccess(List<Location> locations) {
        geoMapView.hideProgress();
        geoMapView.getAllLocationsSuccess(locations);
    }

    //endregion
}
