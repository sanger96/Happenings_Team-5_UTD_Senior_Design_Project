package com.example.happeningsapp;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationHelper notificationHelper = new NotificationHelper(context);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        assert geofencingEvent != null;
        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }
        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence : geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
        int transitionType = geofencingEvent.getGeofenceTransition();
        switch (transitionType) {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                for(Geofence geofence : geofenceList) {
                    String ID = geofence.getRequestId();
                    Toast.makeText(context,ID +" : GEOFENCE_TRANSITION_ENTER", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_ENTER", "", MapsActivity.class);
                }
                break;
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                for(Geofence geofence : geofenceList) {
                    String ID = geofence.getRequestId();
                    Toast.makeText(context, ID +" : GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL", "", MapsActivity.class);
                }
                break;
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                for(Geofence geofence : geofenceList) {
                    String ID = geofence.getRequestId();
                    Toast.makeText(context, ID +" : GEOFENCE_TRANSITION_EXIT", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_EXIT", "", MapsActivity.class);
                }
                break;
        }

    }
}