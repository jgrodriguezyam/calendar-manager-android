package com.binarium.calendarmanager.interfaces.geomap;

import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public interface GeoMapListener extends BaseListener {
    void createCheckInSuccess(int userId, int locationId);
}
