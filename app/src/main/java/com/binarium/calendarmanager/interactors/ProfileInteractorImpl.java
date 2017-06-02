package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;

import com.binarium.calendarmanager.dto.user.GetUserRequest;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.profile.ProfileInteractor;
import com.binarium.calendarmanager.interfaces.profile.ProfileListener;
import com.binarium.calendarmanager.mappers.UserMapper;
import com.binarium.calendarmanager.service.user.UserApiService;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 02/06/2017.
 */

public class ProfileInteractorImpl implements ProfileInteractor {
    private UserApiService userApiService;

    @Inject
    public ProfileInteractorImpl(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Override
    public void getUser(int userId, ProfileListener profileListener) {
        GetUserRequest getUserRequest = new GetUserRequest(userId);
        getUserAsync(getUserRequest, profileListener);

    }

    private void getUserAsync(GetUserRequest getUserRequest, final ProfileListener profileListener) {
        new AsyncTask<GetUserRequest, Void, UserResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected UserResponse doInBackground(GetUserRequest... params) {
                UserResponse userResponse = userApiService.get(params[0], profileListener);
                return userResponse;
            }

            @Override
            protected void onPostExecute(UserResponse userResponse) {
                super.onPostExecute(userResponse);
                if (ObjectValidations.IsNotNull(userResponse)) {
                    User user = UserMapper.toUser(userResponse);
                    profileListener.getUserSuccess(user);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getUserRequest);
    }
}