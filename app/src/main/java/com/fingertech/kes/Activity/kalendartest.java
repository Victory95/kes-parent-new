package com.fingertech.kes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fingertech.kes.R;

import java.util.Calendar;
import java.util.List;

import ru.cleverpumpkin.calendar.CalendarDate;
import ru.cleverpumpkin.calendar.CalendarView;

public class kalendartest extends AppCompatActivity {

    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendartest);

        calendarView = findViewById(R.id.calendar_view);

    }
}
