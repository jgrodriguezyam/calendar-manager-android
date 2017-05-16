package com.binarium.calendarmanager.interfaces.splash;

import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface SplashListener extends BaseListener {
    void userLoginSuccess(User user);
}