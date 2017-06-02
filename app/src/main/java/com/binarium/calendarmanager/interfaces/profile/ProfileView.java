package com.binarium.calendarmanager.interfaces.profile;

import com.binarium.calendarmanager.viewmodels.user.User;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public interface ProfileView {
    void showProgress(String message);
    void hideProgress();

    void showErrorMessage(String message);
    void showSuccessMessage(String message);

    void getUserSuccess(User user);
}