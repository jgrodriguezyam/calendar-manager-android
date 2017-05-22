package com.binarium.calendarmanager.infrastructure;

import android.app.Activity;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.TextView;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.CheckInActivity;
import com.binarium.calendarmanager.activity.GeoMapActivity;
import com.binarium.calendarmanager.activity.LocationActivity;
import com.binarium.calendarmanager.activity.ProfileActivity;

/**
 * Created by link_jorge on 24/12/2016.
 */

public class NavigationExtensions {

    public static void setWorkerName(NavigationView navigationView) {
        View headerLayout = navigationView.getHeaderView(0);
        TextView userFullName = (TextView) headerLayout.findViewById(R.id.drawerName);
        userFullName.setText(Preferences.getUserFullName());
    }

    public static void sendTo(Activity activity, int valueToCompare) {
        switch (valueToCompare) {
            case R.id.btn_map:
                Util.sendAndFinish(activity, GeoMapActivity.class);
                break;
            case R.id.btn_location:
                Util.sendAndFinish(activity, LocationActivity.class);
                break;
            case R.id.btn_profile:
                Util.sendAndFinish(activity, ProfileActivity.class);
                break;
            default:
                break;
        }
    }
}
