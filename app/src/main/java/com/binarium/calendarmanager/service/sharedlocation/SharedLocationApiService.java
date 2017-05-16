package com.binarium.calendarmanager.service.sharedlocation;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.sharedlocation.DeleteSharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.GetSharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationRequest;
import com.binarium.calendarmanager.dto.sharedlocation.SharedLocationResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface SharedLocationApiService {
    CreateResponse create(SharedLocationRequest request, BaseListener baseListener);
    SharedLocationResponse get(GetSharedLocationRequest request, BaseListener baseListener);
    SuccessResponse delete(DeleteSharedLocationRequest request, BaseListener baseListener);
}
