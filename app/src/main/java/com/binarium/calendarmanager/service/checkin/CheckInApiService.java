package com.binarium.calendarmanager.service.checkin;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.checkin.CheckInRequest;
import com.binarium.calendarmanager.dto.checkin.CheckInResponse;
import com.binarium.calendarmanager.dto.checkin.DeleteCheckInRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsRequest;
import com.binarium.calendarmanager.dto.checkin.FindCheckInsResponse;
import com.binarium.calendarmanager.dto.checkin.GetCheckInRequest;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 18/05/2017.
 */

public interface CheckInApiService {
    FindCheckInsResponse find(FindCheckInsRequest request, BaseListener baseListener);
    CreateResponse create(CheckInRequest request, BaseListener baseListener);
    CheckInResponse get(GetCheckInRequest request, BaseListener baseListener);
    SuccessResponse delete(DeleteCheckInRequest request, BaseListener baseListener);
}
