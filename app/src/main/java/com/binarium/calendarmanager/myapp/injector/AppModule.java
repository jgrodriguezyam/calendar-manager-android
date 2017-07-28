package com.binarium.calendarmanager.myapp.injector;

import com.binarium.calendarmanager.interactors.AccountInteractorImpl;
import com.binarium.calendarmanager.interactors.LocationInteractorImpl;
import com.binarium.calendarmanager.interactors.LoginInteractorImpl;
import com.binarium.calendarmanager.interactors.GeoMapInteractorImpl;
import com.binarium.calendarmanager.interactors.ProfileInteractorImpl;
import com.binarium.calendarmanager.interactors.SplashInteractorImpl;
import com.binarium.calendarmanager.interfaces.account.AccountInteractor;
import com.binarium.calendarmanager.interfaces.location.LocationInteractor;
import com.binarium.calendarmanager.interfaces.login.LoginInteractor;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapInteractor;
import com.binarium.calendarmanager.interfaces.profile.ProfileInteractor;
import com.binarium.calendarmanager.interfaces.splash.SplashInteractor;
import com.binarium.calendarmanager.service.application.ApplicationApiService;
import com.binarium.calendarmanager.service.application.ApplicationApiServiceImpl;
import com.binarium.calendarmanager.service.checkin.CheckInApiService;
import com.binarium.calendarmanager.service.checkin.CheckInApiServiceImpl;
import com.binarium.calendarmanager.service.location.LocationApiService;
import com.binarium.calendarmanager.service.location.LocationApiServiceImpl;
import com.binarium.calendarmanager.service.sharedlocation.SharedLocationApiService;
import com.binarium.calendarmanager.service.sharedlocation.SharedLocationApiServiceImpl;
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
    public CheckInApiService provideCheckInApiService() {
        return new CheckInApiServiceImpl();
    }

    @Provides
    public LocationApiService provideLocationApiService() {
        return new LocationApiServiceImpl();
    }

    @Provides
    public ApplicationApiService provideApplicationApiService() {
        return new ApplicationApiServiceImpl();
    }

    @Provides
    public SharedLocationApiService provideSharedLocationApiService() {
        return new SharedLocationApiServiceImpl();
    }

    @Provides
    public SplashInteractor provideSplashInteractor(UserApiService userApiService) {
        return new SplashInteractorImpl(userApiService);
    }

    @Provides
    public LoginInteractor provideLoginInteractor(UserApiService userApiService, ApplicationApiService applicationApiService) {
        return new LoginInteractorImpl(userApiService, applicationApiService);
    }

    @Provides
    public AccountInteractor provideAccountInteractor(UserApiService userApiService) {
        return new AccountInteractorImpl(userApiService);
    }

    @Provides
    public GeoMapInteractor provideGeoMapInteractor(CheckInApiService checkInApiService, LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService, ApplicationApiService applicationApiService) {
        return new GeoMapInteractorImpl(checkInApiService, locationApiService, sharedLocationApiService, applicationApiService);
    }

    @Provides
    public LocationInteractor provideLocationInteractor(LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService, CheckInApiService checkInApiService) {
        return new LocationInteractorImpl(locationApiService, sharedLocationApiService, checkInApiService);
    }

    @Provides
    public ProfileInteractor provideProfileInteractor(UserApiService userApiService) {
        return new ProfileInteractorImpl(userApiService);
    }
}
