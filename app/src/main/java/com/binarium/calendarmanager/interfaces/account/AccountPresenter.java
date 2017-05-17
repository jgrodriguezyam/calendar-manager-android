package com.binarium.calendarmanager.interfaces.account;

import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public interface AccountPresenter {
    void setAccountView(AccountView accountView);
    void createUser(User user);
}
