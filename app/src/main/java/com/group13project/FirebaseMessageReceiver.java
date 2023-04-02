package com.group13project;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessageReceiver
        extends FirebaseMessagingService {

    private static final String TAG = "Project/";

    // Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token)
    {
        Log.d(TAG, "Refreshed token: " + token);
    }


    // Override onMessageReceived() method to extract the
    // title and
    // body from the message passed in FCM
    @Override
    public void
    onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getNotification() != null) {
            // Since the notification is received directly
            // from FCM, the title and the body can be
            // fetched directly as below.
            showNotification(
                    remoteMessage.getNotification().getTitle(),
                    remoteMessage.getNotification().getBody());
        }
    }


    // Method to display the notifications
    public void showNotification(String title,
                                 String message)
    {
        // Pass the intent to switch to the MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        // Assign channel ID
        String channel_id = "notification_channel";
        // Here FLAG_ACTIVITY_CLEAR_TOP flag is set to clear
        // the activities present in the activity stack,
        // on the top of the Activity that is to be launched
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Pass the intent to PendingIntent to start the
        // next Activity
        PendingIntent pendingIntent
                = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        // Create a Builder object using NotificationCompat
        // class. This will allow control over all the flags
        NotificationCompat.Builder builder
                = new NotificationCompat
                .Builder(getApplicationContext(),
                channel_id)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000,
                        1000, 1000 })
                .setOnlyAlertOnce(true)
                .setContentIntent(pendingIntent);

            builder = builder.setContentTitle(title)
                    .setContentText(message);

        // Create an object of NotificationManager class to
        // notify the
        // user of events that happen in the background.
        NotificationManager notificationManager
                = (NotificationManager)getSystemService(
                Context.NOTIFICATION_SERVICE);
        // Check if the Android Version is greater than Oreo

        notificationManager.notify(0, builder.build());
    }
}

