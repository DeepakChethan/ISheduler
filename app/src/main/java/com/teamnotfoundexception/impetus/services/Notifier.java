package com.teamnotfoundexception.impetus.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
        //NotificationCompat.Builder mBuilder = new NotificationCompat.Builder()
        Notification.Builder mBuilder  = new Notification.Builder(context);
        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.whatshotone);

        mBuilder.setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(icon)
                .setContentTitle("Event start alert!")
                .setContentText((eventItem != null ? eventItem.getName() : "Event") +" is about to start !");
        NotificationManager mNotification = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification.notify(eventItem.getId()+(new Random()).nextInt(2000),mBuilder.build());

        Toast.makeText(context,"hey your event will begin soon" , Toast.LENGTH_SHORT).show();
        Log.i("dope", "onReceive: lololoolo");
    }
}
