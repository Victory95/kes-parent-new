package com.fingertech.kes.Rest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Activity.Model.DataMaps;
import com.fingertech.kes.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsTable {
    private DataMaps data;
    boolean check;

    public MapsTable(){
        data = new DataMaps();
    }

    public static String createTable(){
        return "CREATE TABLE " + DataMaps.TABLE + " (" +
                DataMaps.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                DataMaps.KEY_Name + " STRING NOT NULL, " +
                DataMaps.KEY_LATITUDE + " DOUBLE NOT NULL, " +
                DataMaps.KEY_LONGITUDE + " DOUBLE NOT NULL " +
                " )";
    }

    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + DataMaps.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(DataMaps.KEY_CourseId, cursor.getString(0));
                map.put(DataMaps.KEY_Name, cursor.getString(1));
                map.put(DataMaps.KEY_LATITUDE,cursor.getString(2));
                map.put(DataMaps.KEY_LONGITUDE,cursor.getString(3));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public void insert(DataMaps data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(DataMaps.KEY_CourseId, data.getId());
        values.put(DataMaps.KEY_Name, data.getName());
        values.put(DataMaps.KEY_LATITUDE, data.getLat());
        values.put(DataMaps.KEY_LONGITUDE, data.getLng());
        // Inserting Row
        db.insert(DataMaps.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void delete(String nama) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String updateQuery = "DELETE FROM " + DataMaps.TABLE + " WHERE " + DataMaps.KEY_Name + " = " + "'" + nama + "'";
        Log.e("update sqlite ", updateQuery);
        db.execSQL(updateQuery);
        DatabaseManager.getInstance().closeDatabase();
    }
}

