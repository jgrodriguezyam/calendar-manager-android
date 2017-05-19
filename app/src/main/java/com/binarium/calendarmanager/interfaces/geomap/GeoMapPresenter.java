package com.binarium.calendarmanager.interfaces.geomap;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface GeoMapPresenter {
    void setGeoMapView(GeoMapView geoMapView);
    void createCheckIn(int userId, int locationId);
    void getAllLocations(int userId);
}
