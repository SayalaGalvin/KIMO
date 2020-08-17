package com.example.checkfrige;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    String title = "KIMO";
    String message = "Hi, Today you have to go the shop.";
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationCompat = notificationHelper.getChannelNotification(title,message);
        notificationHelper.getNotificationManager().notify(1, notificationCompat.build());
    }
}


