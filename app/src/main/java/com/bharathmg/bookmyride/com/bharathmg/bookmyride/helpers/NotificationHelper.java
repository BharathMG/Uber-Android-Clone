package com.bharathmg.bookmyride.com.bharathmg.bookmyride.helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by bharathmg on 29/11/14.
 */

public class NotificationHelper {
    NotificationManager mNotificationManager;
    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void showNotification(String ticker_text) {
        Intent intent = new Intent();
        PendingIntent contentIntent = PendingIntent.getActivity(context, 10000, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(ticker_text).setTicker(ticker_text)
                .setDefaults(Notification.DEFAULT_LIGHTS);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(10000, mBuilder.build());
    }

    public void cancelNotification() {
        mNotificationManager.cancel(10000);
    }
}
