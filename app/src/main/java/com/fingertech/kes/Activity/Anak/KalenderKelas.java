package com.fingertech.kes.Activity.Anak;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.CalendarAdapter;
import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalenderKelas extends AppCompatActivity {


    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormat     = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private SimpleDateFormat bulanFormat    = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat tahunFormat    = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat dayformat      = new SimpleDateFormat("EEEE",Locale.getDefault());

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
    TextView month_calender;
    ImageView left_month,right_month;
    Auth mApiInterface;
    int status;
    String code;
    String authorization,school_code,student_id,classroom_id,calendar_month,calendar_year;
    RecyclerView recyclerView;
    CalendarAdapter calendarAdapter;

    CalendarModel calendarModel;
    List<CalendarModel> calendarModelList;
    String calendar_id,calendar_type,calendar_desc,calendar_time,calendar_date,calendar_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender_kelas);

        compactCalendarView = (CompactCalendarView)findViewById(R.id.compactcalendar_view);
        month_calender      = findViewById(R.id.month_calender);
        left_month          = findViewById(R.id.left_calender);
        right_month         = findViewById(R.id.right_calender);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        recyclerView        = findViewById(R.id.recylceview_calendar);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        classroom_id        = getIntent().getStringExtra("classroom_id");
        student_id          = getIntent().getStringExtra("student_id");

        compactCalendarView.setUseThreeLetterAbbreviation(true);

        month_calender.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        calendar_month = bulanFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        if(calendar_month.substring(0,1).equals("0"))
        {
            calendar_month = calendar_month.substring(1);
        }
        calendar_year   = tahunFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Context context = getApplicationContext();
                Toast.makeText(context,dayformat.format(dateClicked),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                month_calender.setText(dateFormat.format(firstDayOfNewMonth));
                calendar_month = bulanFormat.format(firstDayOfNewMonth);
                if(calendar_month.substring(0,1).equals("0"))
                {
                    calendar_month = calendar_month.substring(1);
                }
                calendar_year   = tahunFormat.format(firstDayOfNewMonth);
                dapat_calendar();
            }
        });
        left_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollLeft();
            }
        });
        right_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compactCalendarView.scrollRight();
            }
        });
        calendarModelList = new ArrayList<CalendarModel>();
        loadEvents();
        dapat_calendar();

        calendarAdapter = new CalendarAdapter(calendarModelList);
        calendarAdapter.setOnItemClickListener(new CalendarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                calendar_date = calendarModelList.get(position).getCalendar_date();
                Toast.makeText(KalenderKelas.this,calendar_date,Toast.LENGTH_LONG).show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KalenderKelas.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);

    }
    private void loadEvents() {
        addEvents(-1, -1);
        addEvents(Calendar.DECEMBER, -1);
        addEvents(Calendar.AUGUST, -1);
        addEvents(Calendar.FEBRUARY, -1);
        addEvents(Calendar.SEPTEMBER, -1);
    }

    private void addEvents(int month, int year) {
        currentCalender.setTime(new Date());
        currentCalender.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = currentCalender.getTime();
        for (int i = 0; i < 10; i++) {
            currentCalender.setTime(firstDayOfMonth);
            if (month > -1) {
                currentCalender.set(Calendar.MONTH, month);
            }
            if (year > -1) {
                currentCalender.set(Calendar.ERA, GregorianCalendar.AD);
                currentCalender.set(Calendar.YEAR, year);
            }
            currentCalender.add(Calendar.DATE, i);
            setToMidnight(currentCalender);
            long timeInMillis = currentCalender.getTimeInMillis();

            List<Event> events = getEvents(timeInMillis, i);

            compactCalendarView.addEvents(events);
        }
    }

    private List<Event> getEvents(long timeInMillis, int day) {
        if (day < 2) {
            return Arrays.asList(new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)));
        } else if ( day > 2 && day <= 4) {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)));
        } else {
            return Arrays.asList(
                    new Event(Color.argb(255, 169, 68, 65), timeInMillis, "Event at " + new Date(timeInMillis) ),
                    new Event(Color.argb(255, 100, 68, 65), timeInMillis, "Event 2 at " + new Date(timeInMillis)),
                    new Event(Color.argb(255, 70, 68, 65), timeInMillis, "Event 3 at " + new Date(timeInMillis)));
        }
    }
    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
    public void dapat_calendar(){
        Call<JSONResponse.ClassCalendar> call = mApiInterface.kes_class_calendar_get(authorization.toString(),school_code.toLowerCase(),student_id.toString(),classroom_id.toString(),calendar_month.toString(),calendar_year.toString());
        call.enqueue(new Callback<JSONResponse.ClassCalendar>() {
            @Override
            public void onResponse(Call<JSONResponse.ClassCalendar> call, Response<JSONResponse.ClassCalendar> response) {
                Log.i("KES",response.code() + "");

                JSONResponse.ClassCalendar resource = response.body();
                status = resource.status;
                code   = resource.code;
                if (status == 1 & code.equals("DTS_SCS_0001")){

                    List<JSONResponse.DataCalendar> calendarList = response.body().getData();

                        if (calendarModelList != null) {
                            calendarModelList.clear();
                            for (JSONResponse.DataCalendar calendar : calendarList) {
                                calendarModel = new CalendarModel();
                                calendarModel.setCalendar_time(calendar.getCalendar_time());
                                calendarModel.setCalendar_date(calendar.getCalendar_date());
                                calendarModel.setCalendar_title(calendar.getCalendar_title());
                                calendarModel.setCalendar_desc(calendar.getCalendar_desc());
                                calendarModelList.add(calendarModel);
                            }
                            calendarAdapter.notifyDataSetChanged();
                        }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ClassCalendar> call, Throwable t) {

            }
        });
    }
}
