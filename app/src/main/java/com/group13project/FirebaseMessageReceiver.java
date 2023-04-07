package com.group13project;

import android.app.Notification;
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
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class FirebaseMessageReceiver
        extends FirebaseMessagingService {

    private static final String TAG = "Project/";

    private static final String title = "New Job Posting";
    private static final String message = "A new job posting has been released, check it out!";
    // Override onNewToken to get new token
    @Override
    public void onNewToken(@NonNull String token)
    {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage)
    {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() == null) {
            return;
        }

        final String title = remoteMessage.getNotification().getTitle();
        final String body = remoteMessage.getNotification().getBody();

        Intent intent = new Intent (this, EmployerNewPostActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("body",body);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),10,intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this, "JOB_POSTINGS")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(com.google.firebase.messaging.R.drawable.notification_icon_background);

        notiBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        int id = (int) System.currentTimeMillis();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("JOB_POSTINGS", "JOB_POSTINGS", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(id, notiBuilder.build());
    }
}

