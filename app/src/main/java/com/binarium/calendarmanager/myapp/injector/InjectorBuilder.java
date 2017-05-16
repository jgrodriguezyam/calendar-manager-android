package com.binarium.calendarmanager.myapp.injector;

/**
 * Created by jrodriguez on 27/02/2017.
 */

public interface InjectorBuilder {
    AppComponent getAppComponent();
    void reInject();
}
