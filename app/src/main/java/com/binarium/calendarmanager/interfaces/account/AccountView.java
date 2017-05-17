package com.binarium.calendarmanager.interfaces.account;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public interface AccountView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void createUser();
    void navigateToLogin();
}
