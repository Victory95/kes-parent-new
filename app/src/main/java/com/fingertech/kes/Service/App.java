package com.fingertech.kes.Service;

import android.app.Application;
import android.content.Context;

public class  App extends Application {
    private static Context context;
    private static DBHelper dbHelper;

    @Override
    public void onCreate()
    {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHelper(this);
        DatabaseManager.initializeInstance(dbHelper);

    }

    public static Context getContext(){
        return context;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(DBHelper.onAttach(base,"en"));
    }

}
