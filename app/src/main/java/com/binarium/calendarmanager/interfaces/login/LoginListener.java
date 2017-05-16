package com.binarium.calendarmanager.interfaces.login;

import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public interface LoginListener extends BaseListener {
    void userLoginSuccess(User user);
}
