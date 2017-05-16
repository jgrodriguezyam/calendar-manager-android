package com.binarium.calendarmanager.infrastructure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.binarium.calendarmanager.activity.CheckInActivity;

/**
 * Created by link_jorge on 06/11/2016.
 */

public class Util {

    public static void sendAndFinish(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
        activity.finish();
    }

    public static void sendToCheckIn(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        Bundle bundle = new Bundle();
        //bundle.putBoolean(Constants.IS_WORKER_CHECK_INS, true);
        mainIntent.putExtras(bundle);
        activity.startActivity(mainIntent);
        //activity.finish();
    }

    public static void sendToUserNew(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        Bundle bundle = new Bundle();
        //bundle.putBoolean(Constants.IS_WORKER_NEW, true);
        mainIntent.putExtras(bundle);
        activity.startActivity(mainIntent);
        activity.finish();
    }

    public static void sendToCheckIn(Activity activity){
        Intent mainIntent = new Intent().setClass(activity, CheckInActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(mainIntent);
        activity.finish();
    }

    public static void sendTo(Activity activity, Class clase){
        Intent mainIntent = new Intent().setClass(activity, clase);
        activity.startActivity(mainIntent);
    }

    public static ProgressDialog createModalProgressDialog(Activity activity){
        return createModalProgressDialog(activity, null);
    }

    public static ProgressDialog createModalProgressDialog(Activity activity, String dialogMessage){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(activity);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);

        if(dialogMessage != null){
            progressDialog.setMessage(dialogMessage);
        }
        return progressDialog;
    }
}
