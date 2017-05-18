package com.binarium.calendarmanager.interfaces.geomap;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface GeoMapView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void createCheckInSuccess(int userId, int locationId);
}
