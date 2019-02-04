package com.fingertech.kes.Rest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Service.DatabaseManager;

import java.util.ArrayList;
import java.util.HashMap;

public class StudentTable {

    private Data.Student student;
    boolean check;

    public StudentTable(){

        student = new Data.Student();
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
    public static String create_table(){
        return "CREATE TABLE " + Data.Student.TABLE + " (" +
                Data.Student.KEY_CourseId + " INTEGER PRIMARY KEY autoincrement, " +
                Data.Student.KEY_StudentId + " STRING NOT NULL, " +
                Data.Student.KEY_SchoolCode + " STRING NOT NULL, " +
                " )";
    }
    public ArrayList<HashMap<String, String>> getAllData() {
        ArrayList<HashMap<String, String>> studentList;
        studentList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " + Data.Student.TABLE;
        SQLiteDatabase database = DatabaseManager.getInstance().openDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(Data.Student.KEY_CourseId, cursor.getString(0));
                map.put(Data.Student.KEY_StudentId, cursor.getString(1));
                map.put(Data.Student.KEY_SchoolCode, cursor.getString(2));
                studentList.add(map);
            } while (cursor.moveToNext());
        }

        Log.e("select sqlite ", "" + studentList);

        database.close();
        return studentList;
    }

    public void insert(Data.Student data) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put(Data.Student.KEY_CourseId, data.getId());
        values.put(Data.Student.KEY_StudentId, data.getStudent_id());
        values.put(Data.Student.KEY_SchoolCode, data.getSchool_code());
        // Inserting Row
        db.insert(Data.Student.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
    }
    public void delete(String nama) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        String updateQuery = "DELETE FROM " + Data.Student.TABLE + " WHERE " + Data.Student.KEY_SchoolCode + " = " + "'" + nama + "'";
        Log.e("update sqlite ", updateQuery);
        db.execSQL(updateQuery);
        DatabaseManager.getInstance().closeDatabase();
    }


}
