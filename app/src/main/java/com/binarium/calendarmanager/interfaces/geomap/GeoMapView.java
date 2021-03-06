package com.binarium.calendarmanager.interfaces.geomap;

import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface GeoMapView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void createCheckInSuccess(int userId, int locationId);
    void getAllLocationsSuccess(List<Location> locations);
    void createLocationSuccess(Location location);
    void updateLocationSuccess(Location location);
    void deleteLocationSuccess(Location location);
}
