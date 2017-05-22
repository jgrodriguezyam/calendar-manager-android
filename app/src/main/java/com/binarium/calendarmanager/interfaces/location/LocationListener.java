package com.binarium.calendarmanager.interfaces.location;

import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public interface LocationListener extends BaseListener {
    void getAllLocationsSuccess(List<Location> locations);
    void updateLocationSuccess();
}
