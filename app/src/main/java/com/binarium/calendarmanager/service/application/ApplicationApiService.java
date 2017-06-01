package com.binarium.calendarmanager.service.application;

import com.binarium.calendarmanager.dto.base.DateResponse;
import com.binarium.calendarmanager.dto.base.IsAliveResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 01/06/2017.
 */

public interface ApplicationApiService {
    IsAliveResponse isAlive(BaseListener baseListener);
    DateResponse getDate(BaseListener baseListener);
}
