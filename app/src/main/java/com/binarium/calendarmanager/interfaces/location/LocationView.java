package com.binarium.calendarmanager.interfaces.location;

import com.binarium.calendarmanager.viewmodels.location.Location;

import java.util.List;

/**
 * Created by jrodriguez on 22/05/2017.
 */

public interface LocationView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void getAllLocationsSuccess(List<Location> locations);
    void updateLocationSuccess();
}
