package com.binarium.calendarmanager.interfaces.splash;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public interface SplashView {
    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void userLogin();
    void navigateToMap();
    void navigateToLogin();
}
