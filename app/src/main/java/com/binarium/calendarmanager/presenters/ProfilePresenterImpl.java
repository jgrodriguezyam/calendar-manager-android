package com.binarium.calendarmanager.presenters;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.interfaces.profile.ProfileInteractor;
import com.binarium.calendarmanager.interfaces.profile.ProfileListener;
import com.binarium.calendarmanager.interfaces.profile.ProfilePresenter;
import com.binarium.calendarmanager.interfaces.profile.ProfileView;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public class ProfilePresenterImpl implements ProfilePresenter, ProfileListener {
    private ProfileView profileView;
    private ProfileInteractor profileInteractor;

    @Inject
    public ProfilePresenterImpl(ProfileInteractor profileInteractor) {
        this.profileInteractor = profileInteractor;
    }

    //region ProfilePresenterImpl

    @Override
    public void setProfileView(ProfileView profileView) {
        this.profileView = profileView;
    }

    @Override
    public void getUser(int userId) {
        profileView.showProgress(ResourcesExtensions.toString(R.string.init_get_user));
        profileInteractor.getUser(userId, this);
    }

    //endregion

    //region ProfileListener

    @Override
    public void onSuccess(String message) {
        profileView.hideProgress();
        profileView.showSuccessMessage(message);
    }

    @Override
    public void onError(String message) {
        profileView.hideProgress();
        profileView.showErrorMessage(message);
    }

    @Override
    public void getUserSuccess(User user) {
        profileView.hideProgress();
        profileView.getUserSuccess(user);
    }

    //endregion
}