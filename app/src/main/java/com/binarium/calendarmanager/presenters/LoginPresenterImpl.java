package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.interfaces.login.LoginInteractor;
import com.binarium.calendarmanager.interfaces.login.LoginListener;
import com.binarium.calendarmanager.interfaces.login.LoginPresenter;
import com.binarium.calendarmanager.interfaces.login.LoginView;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginListener {
    private LoginView loginView;
    private LoginInteractor loginInteractor;

    @Inject
    public LoginPresenterImpl(LoginInteractor loginInteractor) {
        this.loginInteractor = loginInteractor;
    }

    //region LoginPresenter

    @Override
    public void setLoginView(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void userLogin(String userName, String password) {
        loginView.showProgress(ResourcesExtensions.toString(R.string.init_user_login));
        loginInteractor.userLogin(userName, password, this);
    }

    //endregion

    //region LoginListener

    @Override
    public void onSuccess(String message) {
        loginView.hideProgress();
        loginView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        loginView.hideProgress();
        loginView.showErrorMessage(message);
    }

    @Override
    public void userLoginSuccess(User user) {
        Preferences.setUserId(user.getId());
        Preferences.setUserFullName(user.getFirstName() + " " + user.getLastName());
        loginView.hideProgress();
        loginView.navigateToMap();
    }

    //endregion
}
