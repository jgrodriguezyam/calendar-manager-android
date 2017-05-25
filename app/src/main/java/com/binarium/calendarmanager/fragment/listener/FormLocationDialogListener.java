package com.binarium.calendarmanager.fragment.listener;

import com.binarium.calendarmanager.viewmodels.location.Location;

/**
 * Created by jrodriguez on 23/05/2017.
 */

public interface FormLocationDialogListener {
    void createLocation(Location location);
    void updateLocation(Location location);
}
