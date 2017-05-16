package com.binarium.calendarmanager.service.user;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.base.SuccessResponse;
import com.binarium.calendarmanager.dto.user.ChangeUserPasswordRequest;
import com.binarium.calendarmanager.dto.user.DeleteUserRequest;
import com.binarium.calendarmanager.dto.user.GetUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserResponse;
import com.binarium.calendarmanager.dto.user.LogoutUserRequest;
import com.binarium.calendarmanager.dto.user.UserRequest;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.interfaces.base.BaseListener;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface UserApiService {
    CreateResponse create(UserRequest request, BaseListener baseListener);
    SuccessResponse update(UserRequest request, BaseListener baseListener);
    UserResponse get(GetUserRequest request, BaseListener baseListener);
    SuccessResponse delete(DeleteUserRequest request, BaseListener baseListener);
    LoginUserResponse login(LoginUserRequest request, BaseListener baseListener);
    SuccessResponse logout(LogoutUserRequest request, BaseListener baseListener);
    SuccessResponse changePassword(ChangeUserPasswordRequest request, BaseListener baseListener);
}
