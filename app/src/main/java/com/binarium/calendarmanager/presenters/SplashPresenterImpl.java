package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.interfaces.splash.SplashInteractor;
import com.binarium.calendarmanager.interfaces.splash.SplashListener;
import com.binarium.calendarmanager.interfaces.splash.SplashPresenter;
import com.binarium.calendarmanager.interfaces.splash.SplashView;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class SplashPresenterImpl implements SplashPresenter, SplashListener {
    private SplashView splashView;
    private SplashInteractor splashInteractor;

    @Inject
    public SplashPresenterImpl(SplashInteractor splashInteractor) {
        this.splashInteractor = splashInteractor;
    }

    //region SplashPresenter

    @Override
    public void setSplashView(SplashView splashView) {
        this.splashView = splashView;
    }

    @Override
    public void userLogin(String userName, String password) {
        splashInteractor.userLogin(userName, password, this);
    }

    //endregion

    //region SplashListener

    @Override
    public void onSuccess(String message) {
        splashView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        splashView.showErrorMessage(message);
    }

    @Override
    public void userLoginSuccess(User user) {
        Preferences.setUserId(user.getId());
        Preferences.setUserFullName(user.getFirstName() + " " + user.getLastName());
        splashView.navigateToMap();
    }

    @Override
    public void userLoginError() {
        splashView.navigateToLogin();
    }

    //endregion
}
