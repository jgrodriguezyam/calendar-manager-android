package com.binarium.calendarmanager.myapp.injector;

import com.binarium.calendarmanager.interactors.SplashInteractorImpl;
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
//
//    @Provides
//    public CheckInApiService provideCheckInApiService() {
//        return new CheckInApiServiceImpl();
//    }
//
//    @Provides
//    public LocationApiService provideLocationApiService() {
//        return new LocationApiServiceImpl();
//    }
//
    @Provides
    public SplashInteractor provideSplashInteractor(UserApiService userApiService) {
        return new SplashInteractorImpl(userApiService);
    }
//
//    @Provides
//    public WorkerInteractor provideWorkerInteractor(CheckInApiService checkInApiService, LocationApiService locationApiService, WorkerApiService workerApiService) {
//        return new WorkerInteractorImpl(checkInApiService, locationApiService, workerApiService);
//    }
//
//    @Provides
//    public ProfileInteractor provideProfileInteractor(WorkerApiService workerApiService) {
//        return new ProfileInteractorImpl(workerApiService);
//    }
}
