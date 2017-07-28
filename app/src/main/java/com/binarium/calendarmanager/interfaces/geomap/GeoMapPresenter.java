package com.binarium.calendarmanager.interfaces.geomap;

import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface GeoMapPresenter {
    void setGeoMapView(GeoMapView geoMapView);
    void createCheckIn(int userId, int locationId);
    void getAllLocations(int userId, String date);
    void createLocation(Location location);
    void updateLocation(Location location);
    void deleteLocation(Location location);
}
