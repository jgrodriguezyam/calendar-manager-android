package com.binarium.calendarmanager.dto.location;

import com.binarium.calendarmanager.dto.base.IdentifierBaseRequest;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class DeleteLocationRequest extends IdentifierBaseRequest {
    public DeleteLocationRequest() {
    }

    public DeleteLocationRequest(int id) {
        super(id);
    }
}
