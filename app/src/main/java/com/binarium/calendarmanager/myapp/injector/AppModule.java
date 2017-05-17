package com.binarium.calendarmanager.myapp.injector;

import com.binarium.calendarmanager.interactors.AccountInteractorImpl;
import com.binarium.calendarmanager.interactors.LoginInteractorImpl;
import com.binarium.calendarmanager.interactors.SplashInteractorImpl;
import com.binarium.calendarmanager.interfaces.account.AccountInteractor;
import com.binarium.calendarmanager.interfaces.login.LoginInteractor;
import com.binarium.calendarmanager.interfaces.splash.SplashInteractor;
import com.binarium.calendarmanager.service.user.UserApiService;
import com.binarium.calendarmanager.service.user.UserApiServiceImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jrodriguez on 27/02/2017.
 */

@Module
public class AppModule {

    @Provides
    public UserApiService provideUserApiService() {
        return new UserApiServiceImpl();
    }

    @Provides
    public SplashInteractor provideSplashInteractor(UserApiService userApiService) {
        return new SplashInteractorImpl(userApiService);
    }

    @Provides
    public LoginInteractor provideLoginInteractor(UserApiService userApiService) {
        return new LoginInteractorImpl(userApiService);
    }

    @Provides
    public AccountInteractor provideAccountInteractor(UserApiService userApiService) {
        return new AccountInteractorImpl(userApiService);
    }
}
