package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.user.GetUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserRequest;
import com.binarium.calendarmanager.dto.user.LoginUserResponse;
import com.binarium.calendarmanager.dto.user.UserResponse;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.interfaces.login.LoginInteractor;
import com.binarium.calendarmanager.interfaces.login.LoginListener;
import com.binarium.calendarmanager.service.user.UserApiService;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {
    private UserApiService userApiService;

    @Inject
    public LoginInteractorImpl(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Override
    public void userLogin(String userName, String password, LoginListener loginListener) {
        LoginUserRequest loginUserRequest = new LoginUserRequest(userName, password);
        userLoginAsync(loginUserRequest, loginListener);
    }

    @UiThread
    private void userLoginAsync(LoginUserRequest loginUserRequest, final LoginListener loginListener) {
        new AsyncTask<LoginUserRequest, Void, UserResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected UserResponse doInBackground(LoginUserRequest... params) {
                LoginUserResponse loginUserResponse = userApiService.login(params[0], loginListener);
                if(ObjectValidations.IsNotNull(loginUserResponse)) {
                    GetUserRequest getUserRequest = new GetUserRequest();
                    getUserRequest.setId(loginUserResponse.getUserId());
                    UserResponse userResponse = userApiService.get(getUserRequest, loginListener);
                    Preferences.setUserName(params[0].getUserName());
                    Preferences.setPassword(params[0].getPassword());
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
                    loginListener.userLoginSuccess(user);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, loginUserRequest);
    }
}
