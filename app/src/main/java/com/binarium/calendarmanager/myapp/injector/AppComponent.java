package com.binarium.calendarmanager.myapp.injector;

import com.binarium.calendarmanager.fragment.LoginFragment;
import com.binarium.calendarmanager.fragment.SplashFragment;

import dagger.Component;

/**
 * Created by jrodriguez on 27/02/2017.
 */

@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(SplashFragment splashFragment);
    void inject(LoginFragment loginFragment);
}
