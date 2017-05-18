package com.binarium.calendarmanager.dto.checkin;

import com.binarium.calendarmanager.dto.base.IdentifierBaseRequest;

/**
 * Created by jrodriguez on 17/05/2017.
 */

public class DeleteCheckInRequest extends IdentifierBaseRequest {
    public DeleteCheckInRequest() {
    }

    public DeleteCheckInRequest(int id) {
        super(id);
    }
}
