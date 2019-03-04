package com.fingertech.kes.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.fingertech.kes.Activity.MainActivity;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Setting.SettingsActivity;
import com.fingertech.kes.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

import static com.joooonho.SelectableRoundedImageView.TAG;



public class FirebaseMessaging extends FirebaseMessagingService {


    private Ringtone ringtone;

    NotificationManager notificationManager;
    public static final String ANDROID_CHANNEL_ID = "com.fingertech.kes";
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";



    @Override
    public void onMessageReceived(RemoteMessage remotemsg) {

        Log.d("Token", "From -> " + remotemsg.getFrom());
        String title       = remotemsg.getData().get("title");
        String body        = remotemsg.getData().get("body");
        Log.d("Title",remotemsg.getData()+"");
        nada();
//        sendnotification(title,body);
    }

    private void nada() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String path = preferences.getString("Ringtone", "");
        if (!path.isEmpty()) {
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(path));
            ringtone.play();
        }else if(path.isEmpty())
        {
            ringtone = RingtoneManager.getRingtone(this, Uri.parse(path));
            ringtone.stop();
        }

    }
    private void sendnotification(String title,String messageBody){

        Intent intent = new Intent(this, MenuUtama.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,ANDROID_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo))
                .setContentIntent(pendingIntent);

        create();
        notificationManager.notify(0, notificationBuilder.build());

    }



    public void create(){
        // create android channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel androidChannel = new NotificationChannel(ANDROID_CHANNEL_ID,
                    ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            // Sets whether notifications posted to this channel should display notification lights
            androidChannel.enableLights(true);
            // Sets whether notification posted to this channel should vibrate.
            androidChannel.enableVibration(true);
            // Sets the notification light color for notifications posted to this channel
            androidChannel.setLightColor(Color.GREEN);
            // Sets whether notifications posted to this channel appear on the lockscreen or not
            androidChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(androidChannel);
        }
    }
    private NotificationManager getManager() {
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }
    @Override
    public void onNewToken(String token) {
        Log.d("Token", "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
