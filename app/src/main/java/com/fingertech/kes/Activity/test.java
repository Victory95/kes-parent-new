package com.fingertech.kes.Activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.fingertech.kes.R;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class test extends AppCompatActivity {


    private SimpleDateFormat hariFormat     = new SimpleDateFormat("EEEE",new Locale("in","ID"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        CollapsibleCalendar collapsibleCalendar = findViewById(R.id.calendarView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            collapsibleCalendar.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            @Override
            public void onDaySelect() {
                Day day = collapsibleCalendar.getSelectedDay();

                Toast.makeText(getApplicationContext(),day.getDay()+"",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {
                Toast.makeText(getApplicationContext(),collapsibleCalendar.getMonth()+"",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onWeekChange(int position) {

            }
        });
    }
}
