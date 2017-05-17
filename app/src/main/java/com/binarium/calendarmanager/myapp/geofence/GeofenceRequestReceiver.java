package com.binarium.calendarmanager.myapp.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.binarium.calendarmanager.fragment.CheckInFragment;
import com.binarium.calendarmanager.infrastructure.Constants;

/**
 * Created by jrodriguez on 06/04/2017.
 */

public class GeofenceRequestReceiver extends BroadcastReceiver {

    public static final String PROCESS_RESPONSE = "com.binarium.calendarmanager.intent.action.PROCESS_RESPONSE";

    @Override
    public void onReceive(Context context, Intent intent) {
        Boolean isVisible = intent.getBooleanExtra(Constants.IS_VISIBLE_PARAMETER, false);

        if (isVisible){
            //CheckInFragment.getInstace().setButtonVisibility(View.VISIBLE);
        }else{
            //CheckInFragment.getInstace().setButtonVisibility(View.GONE);
        }
    }
}