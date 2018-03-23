package com.teamnotfoundexception.impetus.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import com.teamnotfoundexception.impetus.Databases.EventItem;
import com.teamnotfoundexception.impetus.R;

import java.util.Random;


public class Notifier extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("recevie", "received");
        EventItem eventItem = (EventItem) intent.getSerializableExtra("dope");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.whatshotone);

        mBuilder.setSmallIcon(R.drawable.ic_whatshot_white_24dp)
                .setLargeIcon(icon)
                .setContentTitle("Event start alert!")
                .setContentText((eventItem != null ? eventItem.getName() : "Event") +" is about to start !");
        NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification.notify(eventItem.getId()+(new Random()).nextInt(2000),mBuilder.build());

        Toast.makeText(context,"hey your event will begin soon" , Toast.LENGTH_SHORT).show();
        Log.i("dope", "onReceive: lololoolo");
    }
}
