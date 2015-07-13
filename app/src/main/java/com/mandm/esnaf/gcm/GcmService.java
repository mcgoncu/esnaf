package com.mandm.esnaf.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;
import com.mandm.esnaf.MainActivity;
import com.mandm.esnaf.data.PushMessage;

import java.util.Date;

public class GcmService extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {

        PushMessage pushMessage = GcmService.getPushMessage(data);

        // TODO : uygulama açıkken ne yapacak

        if (pushMessage != null) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(getApplicationInfo().icon)
                            //.setLargeIcon(BitmapFactory.decodeResource(getResources(), getApplicationInfo().icon))
                            // TODO: It can make the large icon set by the main app
                    .setContentTitle(pushMessage.Title)
                    .setContentText(pushMessage.Message)
                    .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(pushMessage.Message))
                    .setAutoCancel(true)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLights(Color.YELLOW, 500, 1000)
                    .setVibrate(new long[]{500, 500})
                    .setWhen(new Date(System.currentTimeMillis()).getTime());

            Intent pushIntent = new Intent(this, MainActivity.class);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                pushIntent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
            }
            pushIntent.putExtra(pushMessage.Key, pushMessage.Value);

            int notificationId = (pushMessage.Message + pushMessage.Title).hashCode();

            PendingIntent contentIntent = PendingIntent.getBroadcast(this, notificationId, pushIntent, 0);
            builder.setContentIntent(contentIntent);
            NotificationManager mgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            mgr.notify(notificationId, builder.build());
        }
    }

    @Override
    public void onDeletedMessages() {
        // TODO
    }

    private static PushMessage getPushMessage(Bundle data){
        PushMessage pushMessage = new PushMessage();
        // TODO : set push message
        return pushMessage;
    }
}