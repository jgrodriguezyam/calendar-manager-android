package com.binarium.calendarmanager.myapp.geofence;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.binarium.calendarmanager.R;
import com.binarium.calendarmanager.activity.GeoMapActivity;
import com.binarium.calendarmanager.infrastructure.Constants;
import com.binarium.calendarmanager.infrastructure.ResourcesExtensions;
import com.binarium.calendarmanager.infrastructure.StringValidations;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

/**
 * Created by jrodriguez on 25/03/2017.
 */

public class GeofenceTransitionsIntentService extends IntentService {

    public static final String TRANSITION_INTENT_SERVICE = "GeffenTransitionsIntentService";

    public GeofenceTransitionsIntentService() {
        super(TRANSITION_INTENT_SERVICE);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        int transitionType = geofencingEvent.getGeofenceTransition();

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(GeofenceRequestReceiver.PROCESS_RESPONSE);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        String message = Constants.EMPTY_STRING;
        int requestId = Constants.ZERO_INT;

        if (geofencingEvent.hasError()) {
            String errorMessage = geofencingEvent.getErrorCode()+"";
            Log.e("error", errorMessage);
            return;
        }

        if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
            message = ResourcesExtensions.toString(R.string.notification_geofence_transition_enter);
            requestId = Integer.valueOf(geofencingEvent.getTriggeringGeofences().get(0).getRequestId());
            broadcastIntent.putExtra(Constants.SEND_LOCATION_PARAMETER, requestId);
            broadcastIntent.putExtra(Constants.IS_VISIBLE_PARAMETER, true);
        }

        if (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT) {
            broadcastIntent.putExtra(Constants.SEND_LOCATION_PARAMETER, requestId);
            broadcastIntent.putExtra(Constants.IS_VISIBLE_PARAMETER, false);
        }

        if (transitionType == Geofence.GEOFENCE_TRANSITION_DWELL) {
            requestId = Integer.valueOf(geofencingEvent.getTriggeringGeofences().get(0).getRequestId());
            broadcastIntent.putExtra(Constants.SEND_LOCATION_PARAMETER, requestId);
            broadcastIntent.putExtra(Constants.IS_VISIBLE_PARAMETER, true);
        }

        if(StringValidations.IsNotNullOrEmpty(message)) {
            generateNotification(Constants.GEOFENCE_LOCATION_BINARIUM, message);
        }

        sendBroadcast(broadcastIntent);
    }

    private void generateNotification(String locationId, String address) {
        long when = System.currentTimeMillis();
        Intent notifyIntent = new Intent(this, GeoMapActivity.class);
        notifyIntent.putExtra("id", locationId);
        notifyIntent.putExtra("address", address);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_plenumsoft)
                        .setContentTitle(locationId)
                        .setContentText(address)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setWhen(when);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) when, builder.build());
    }
}
