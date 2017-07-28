package com.binarium.calendarmanager.interfaces.geomap;

import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public interface GeoMapInteractor {
    void createCheckIn(int userId, int locationId, GeoMapListener geoMapListener);
    void getAllLocations(int userId, String date, GeoMapListener geoMapListener);
    void createLocation(Location location, GeoMapListener geoMapListener);
    void updateLocation(Location location, GeoMapListener geoMapListener);
    void deleteLocation(Location location, GeoMapListener geoMapListener);
}
