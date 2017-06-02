package com.binarium.calendarmanager.interfaces.profile;

import com.binarium.calendarmanager.interfaces.base.BaseListener;
import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public interface ProfileListener extends BaseListener {
    void getUserSuccess(User user);
}
