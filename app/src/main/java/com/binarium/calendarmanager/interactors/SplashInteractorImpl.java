package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;

import androidx.annotation.UiThread;

import com.binarium.calendarmanager.dto.user.GetUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserResponse;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.splash.SplashInteractor;
import com.binarium.calendarmanager.interfaces.splash.SplashListener;
import com.binarium.calendarmanager.service.user.UserApiService;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class SplashInteractorImpl implements SplashInteractor {
    private UserApiService userApiService;

    @Inject
    public SplashInteractorImpl(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Override
    public void userLogin(String userName, String password, SplashListener splashListener) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(userName, password);
        userLoginAsync(loginUserRequest, splashListener);
    }

    @UiThread
    private void userLoginAsync(LoginUserRequest loginUserRequest, final SplashListener splashListener) {
        new AsyncTask<LoginUserRequest, Void, UserResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected UserResponse doInBackground(LoginUserRequest... params) {
                LoginUserResponse loginUserResponse = userApiService.login(params[0], splashListener);
                if(ObjectValidations.IsNotNull(loginUserResponse)) {
                    GetUserRequest getUserRequest = new GetUserRequest();
                    getUserRequest.setId(loginUserResponse.getUserId());
                    UserResponse userResponse = userApiService.get(getUserRequest, splashListener);
                    return userResponse;
                }
                return null;
            }

            @Override
            protected void onPostExecute(UserResponse userResponse) {
                super.onPostExecute(userResponse);
                if (ObjectValidations.IsNotNull(userResponse)) {
                    User user = new User(
                            userResponse.getId(), userResponse.getFirstName(), userResponse.getLastName(),
                            userResponse.getGenderType(), userResponse.getEmail(), userResponse.getCellNumber(),
                            userResponse.getUserName(), userResponse.getPassword(), userResponse.getPublicKey(),
                            userResponse.getBadge(), userResponse.getDeviceId());
                    splashListener.userLoginSuccess(user);
                } else {
                    splashListener.userLoginError();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginUserRequest);
    }
}
