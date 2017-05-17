package com.binarium.calendarmanager.interactors;

import android.os.AsyncTask;
import android.support.annotation.UiThread;

import com.binarium.calendarmanager.dto.base.CreateResponse;
import com.binarium.calendarmanager.dto.user.UserRequest;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.interfaces.account.AccountInteractor;
import com.binarium.calendarmanager.interfaces.account.AccountListener;
import com.binarium.calendarmanager.service.user.UserApiService;
import com.binarium.calendarmanager.viewmodels.user.User;

import javax.inject.Inject;

/**
 * Created by jrodriguez on 16/05/2017.
 */

public class AccountInteractorImpl implements AccountInteractor {
    private UserApiService userApiService;

    @Inject
    public AccountInteractorImpl(UserApiService userApiService) {
        this.userApiService = userApiService;
    }

    @Override
    public void createUser(User user, AccountListener accountListener) {
        UserRequest userRequest = new UserRequest();
        userRequest.setFirstName(user.getFirstName());
        userRequest.setLastName(user.getLastName());
        userRequest.setGenderType(user.getGenderType());
        userRequest.setEmail(user.getEmail());
        userRequest.setCellNumber(user.getCellNumber());
        userRequest.setUserName(user.getUserName());
        userRequest.setPassword(user.getPassword());
        createUserAsync(userRequest, accountListener);
    }

    @UiThread
    private void createUserAsync(UserRequest UserRequest, final AccountListener accountListener) {
        new AsyncTask<UserRequest, Void, CreateResponse>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected CreateResponse doInBackground(UserRequest... params) {
                CreateResponse createResponse = userApiService.create(params[0], accountListener);
                return createResponse;
            }

            @Override
            protected void onPostExecute(CreateResponse createResponse) {
                super.onPostExecute(createResponse);
                if (ObjectValidations.IsNotNull(createResponse)) {
                    accountListener.createUserSuccess();
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, UserRequest);
    }
}