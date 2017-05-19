package com.binarium.calendarmanager.interfaces.geomap;

import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public interface GeoMapListener extends BaseListener {
    void createCheckInSuccess(int userId, int locationId);
    void getAllLocationsSuccess(List<Location> locations);
}
