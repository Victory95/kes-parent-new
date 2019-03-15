package com.fingertech.kes.Service;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class  App extends Application {
    private static Context context;
    private static DBHelper dbHelper;
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper(this);
        DatabaseManager.initializeInstance(dbHelper);

        createNotificationChannels();

    }

    public static Context getContext(){
        return context;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(DBHelper.onAttach(base,"en"));
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel2);
        }
    }

}
