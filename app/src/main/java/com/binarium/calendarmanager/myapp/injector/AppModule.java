package com.binarium.calendarmanager.myapp.injector;

import com.binarium.calendarmanager.interactors.AccountInteractorImpl;
import com.binarium.calendarmanager.interactors.LocationInteractorImpl;
import com.binarium.calendarmanager.interactors.LoginInteractorImpl;
import com.binarium.calendarmanager.interactors.GeoMapInteractorImpl;
import com.binarium.calendarmanager.interactors.SplashInteractorImpl;
import com.binarium.calendarmanager.interfaces.account.AccountInteractor;
import com.binarium.calendarmanager.interfaces.location.LocationInteractor;
import com.binarium.calendarmanager.interfaces.login.LoginInteractor;
import com.binarium.calendarmanager.interfaces.geomap.GeoMapInteractor;
import com.binarium.calendarmanager.interfaces.splash.SplashInteractor;
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
    public SharedLocationApiService provideSharedLocationApiService() {
        return new SharedLocationApiServiceImpl();
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

    @Provides
    public GeoMapInteractor provideGeoMapInteractor(CheckInApiService checkInApiService, LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService) {
        return new GeoMapInteractorImpl(checkInApiService, locationApiService, sharedLocationApiService);
    }

    @Provides
    public LocationInteractor provideLocationInteractor(LocationApiService locationApiService, SharedLocationApiService sharedLocationApiService) {
        return new LocationInteractorImpl(locationApiService, sharedLocationApiService);
    }
}
