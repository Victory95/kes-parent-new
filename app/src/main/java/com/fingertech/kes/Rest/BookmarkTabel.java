package com.fingertech.kes.Rest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class BookmarkTabel {
    private Data data;
    boolean check;

    public BookmarkTabel(){
        data = new Data();
    }

    public static String createTable(){
        return "CREATE TABLE " + Data.TABLE + " (" +
                Data.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                Data.KEY_Name + " STRING NOT NULL, " +
                Data.KEY_ALAMAT + " STRING NOT NULL, " +
                Data.KEY_LATITUDE + " DOUBLE NOT NULL, " +
                Data.KEY_LONGITUDE + " DOUBLE NOT NULL, " +
                Data.KEY_SCHOOLDETAIL + " STRING NOT NULL, " +
                Data.KEY_JENJANG + " STRING NOT NULL" +
                " )";
    }
    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + Data.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Data.KEY_CourseId, cursor.getString(0));
                map.put(Data.KEY_Name, cursor.getString(1));
                map.put(Data.KEY_ALAMAT, cursor.getString(2));
                map.put(Data.KEY_LATITUDE,cursor.getString(3));
                map.put(Data.KEY_LONGITUDE,cursor.getString(4));
                map.put(Data.KEY_SCHOOLDETAIL,cursor.getString(5));
                map.put(Data.KEY_JENJANG,cursor.getString(6));
                wordList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + wordList);

        return wordList;
    }

    public void insert(Data data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
            values.put(Data.KEY_CourseId, data.getId());
            values.put(Data.KEY_Name, data.getName());
            values.put(Data.KEY_ALAMAT, data.getAddress());
            values.put(Data.KEY_LATITUDE, data.getLat());
            values.put(Data.KEY_LONGITUDE, data.getLng());
            values.put(Data.KEY_SCHOOLDETAIL, data.getSchooldetailid());
            values.put(Data.KEY_JENJANG, data.getJenjang());
        // Inserting Row
        db.insert(Data.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void delete(String nama) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String updateQuery = "DELETE FROM " + Data.TABLE + " WHERE " + Data.KEY_Name + " = " + "'" + nama + "'";
        Log.e("update sqlite ", updateQuery);
        db.execSQL(updateQuery);
        DatabaseManager.getInstance().closeDatabase();
    }


}
