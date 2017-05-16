package com.binarium.calendarmanager.interfaces.login;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public interface LoginInteractor {
    void userLogin(String userName, String password, LoginListener loginListener);
}
