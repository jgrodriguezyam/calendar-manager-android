package com.binarium.calendarmanager.interfaces.location;

import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public interface LocationPresenter {
    void setLocationView(LocationView locationView);
    void getAllLocations(int userId);
    void updateLocation(Location location);
}
