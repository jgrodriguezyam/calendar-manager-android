package com.binarium.calendarmanager.interfaces.location;

import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public interface LocationInteractor {
    void getAllLocations(int userId, String date, LocationListener locationListener);
    void createLocation(Location location, LocationListener locationListener);
    void updateLocation(Location location, LocationListener locationListener);
    void deleteLocation(Location location, LocationListener locationListener);
}
