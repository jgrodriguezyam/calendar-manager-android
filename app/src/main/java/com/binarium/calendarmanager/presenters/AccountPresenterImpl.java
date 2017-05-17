package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.interfaces.account.AccountInteractor;
import com.binarium.calendarmanager.interfaces.account.AccountListener;
import com.binarium.calendarmanager.interfaces.account.AccountPresenter;
import com.binarium.calendarmanager.interfaces.account.AccountView;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class AccountPresenterImpl implements AccountPresenter, AccountListener {
    private AccountView accountView;
    private AccountInteractor accountInteractor;

    @Inject
    public AccountPresenterImpl(AccountInteractor accountInteractor) {
        this.accountInteractor = accountInteractor;
    }

    //region AccountPresenter

    @Override
    public void setAccountView(AccountView accountView) {
        this.accountView = accountView;
    }

    @Override
    public void createUser(User user) {
        accountView.showProgress(ResourcesExtensions.toString(R.string.init_user_login));
        accountInteractor.createUser(user, this);
    }

    //endregion

    //region AccountListener

    @Override
    public void onSuccess(String message) {
        accountView.hideProgress();
        accountView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        accountView.hideProgress();
        accountView.showErrorMessage(message);
    }

    @Override
    public void createUserSuccess() {
        accountView.hideProgress();
        accountView.navigateToLogin();
    }

    //endregion
}
