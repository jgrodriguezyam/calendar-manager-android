package com.binarium.calendarmanager.interfaces.geomap;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public interface GeoMapInteractor {
    void createCheckIn(int userId, int locationId, GeoMapListener geoMapListener);
    void getAllLocations(int userId, String date, GeoMapListener geoMapListener);
}
