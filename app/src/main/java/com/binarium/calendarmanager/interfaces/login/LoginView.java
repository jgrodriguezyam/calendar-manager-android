package com.binarium.calendarmanager.interfaces.login;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public interface LoginView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void userLogin();
    void navigateToMap();
    void navigateToAccount();
}
