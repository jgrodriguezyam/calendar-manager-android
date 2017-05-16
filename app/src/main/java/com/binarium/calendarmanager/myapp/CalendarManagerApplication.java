package com.binarium.calendarmanager.myapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.infrastructure.ObjectValidations;
import com.binarium.calendarmanager.infrastructure.Preferences;
import com.binarium.calendarmanager.myapp.injector.AppComponent;
import com.binarium.calendarmanager.myapp.injector.AppModule;
import com.binarium.calendarmanager.myapp.injector.DaggerAppComponent;
import com.binarium.calendarmanager.myapp.injector.InjectorBuilder;
import com.binarium.calendarmanager.service.retrofitconfig.RetrofitBuilder;

/**
 * Created by jrodriguez on 15/05/2017.
 */

public class CalendarManagerApplication extends Application implements InjectorBuilder {
    private static CalendarManagerApplication calendarManagerApplication = null;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        final String BASE_URL = getApplicationContext().getResources().getString(R.string.url_base);
        RetrofitBuilder.Build(BASE_URL);
        Preferences.setContext(getApplicationContext());
        calendarManagerApplication = this;
    }

    public static CalendarManagerApplication getInstance() {
        return calendarManagerApplication;
    }

    @Override
    public AppComponent getAppComponent() {
        if (ObjectValidations.IsNull(appComponent)) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule())
                    .build();
        }
        return appComponent;
    }

    @Override
    public void reInject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule())
                .build();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
