package com.binarium.calendarmanager.dto.user;

import com.binarium.calendarmanager.dto.base.IdentifierBaseRequest;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class DeleteUserRequest extends IdentifierBaseRequest {
    public DeleteUserRequest() {
    }

    public DeleteUserRequest(int id) {
        super(id);
    }
}
