package com.binarium.calendarmanager.dto.user;

import com.binarium.calendarmanager.dto.base.IdentifierBaseRequest;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class GetUserRequest extends IdentifierBaseRequest {
    public GetUserRequest() {
    }

    public GetUserRequest(int id) {
        super(id);
    }

}
