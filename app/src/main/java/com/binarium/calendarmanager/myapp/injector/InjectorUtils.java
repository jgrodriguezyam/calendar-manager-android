package com.binarium.calendarmanager.myapp.injector;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;

/**
 * Created by jrodriguez on 27/02/2017.
 */

public class InjectorUtils {

    public static AppComponent getInjector(Context context){
        Context appContext = context.getApplicationContext();
        if(appContext instanceof InjectorBuilder){
            return ((InjectorBuilder) appContext).getAppComponent();
        }
        throw new IllegalArgumentException("Expected an Instance of: " + AppComponent.class);
    }

    public static AppComponent getInjector(Fragment fragment){
        return getInjector(fragment.getContext());
    }

    public static AppComponent getInjector(Activity activity){
        return getInjector(activity.getApplicationContext());
    }
}
