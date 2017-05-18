package com.binarium.calendarmanager.service.location;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.location.DeleteLocationRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsRequest;
import com.binarium.calendarmanager.dto.location.FindLocationsResponse;
import com.binarium.calendarmanager.dto.location.GetLocationRequest;
import com.binarium.calendarmanager.dto.location.LocationRequest;
import com.binarium.calendarmanager.dto.location.LocationResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface LocationApiService {
    FindLocationsResponse find(FindLocationsRequest request, BaseListener baseListener);
    CreateResponse create(LocationRequest request, BaseListener baseListener);
    SuccessResponse update(LocationRequest request, BaseListener baseListener);
    LocationResponse get(GetLocationRequest request, BaseListener baseListener);
    SuccessResponse delete(DeleteLocationRequest request, BaseListener baseListener);
}
