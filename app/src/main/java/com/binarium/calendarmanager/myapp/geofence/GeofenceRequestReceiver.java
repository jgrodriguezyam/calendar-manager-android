package com.binarium.calendarmanager.myapp.geofence;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.binarium.calendarmanager.fragment.GeoMapFragment;
import com.binarium.calendarmanager.infrastructure.Constants;

/**
 * Created by jrodriguez on 06/04/2017.
 */

public class GeofenceRequestReceiver extends BroadcastReceiver {

    public static final String PROCESS_RESPONSE = "com.binarium.calendarmanager.intent.action.PROCESS_RESPONSE";

    @Override
    public void onReceive(Context context, Intent intent) {
        int locationId = intent.getIntExtra(Constants.SEND_LOCATION_PARAMETER, 0);
        Boolean isVisible = intent.getBooleanExtra(Constants.IS_VISIBLE_PARAMETER, false);

        if (isVisible){
            GeoMapFragment.getInstace().setLocationToCheckIn(locationId);
            GeoMapFragment.getInstace().setButtonVisibility(View.VISIBLE);
        }else{
            GeoMapFragment.getInstace().setLocationToCheckIn(locationId);
            GeoMapFragment.getInstace().setButtonVisibility(View.GONE);
        }
    }
}
