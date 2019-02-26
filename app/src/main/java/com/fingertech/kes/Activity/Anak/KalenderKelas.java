package com.fingertech.kes.Activity.Anak;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.fingertech.kes.Activity.Adapter.CalendarAdapter;
import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.ParseException;
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

    private Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    TextView month_calender;
    ImageView left_month,right_month;
    Auth mApiInterface;
    int status;
    String code;
    String authorization,school_code,student_id,classroom_id,calendar_month,calendar_year;
    RecyclerView recyclerView;
    CalendarAdapter calendarAdapter;
    List<Event> eventList,events;

    CalendarModel calendarModel;
    List<CalendarModel> calendarModelList;
    Date date;
    String calendar_id,calendar_type,calendar_colour,calendar_time,calendar_date,calendar_title;
    Toolbar toolbar;
    TextView kalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender_kelas);

        compactCalendarView = findViewById(R.id.compactcalendar_view);
        month_calender      = findViewById(R.id.month_calender);
        left_month          = findViewById(R.id.left_calender);
        right_month         = findViewById(R.id.right_calender);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        recyclerView        = findViewById(R.id.recylceview_calendar);
        toolbar             = findViewById(R.id.toolbar_kalendar);
        kalendar            = findViewById(R.id.no_kalendar);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        classroom_id        = getIntent().getStringExtra("classroom_id");
        student_id          = getIntent().getStringExtra("student_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

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
                compactCalendarView.setCurrentDayBackgroundColor(R.color.transparent);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                if (eventList != null){
                    compactCalendarView.removeAllEvents();
                    compactCalendarView.removeEvents(eventList);
                }
                if (events != null){
                    compactCalendarView.removeAllEvents();
                    compactCalendarView.removeEvents(events);
                }
                month_calender.setText(dateFormat.format(firstDayOfNewMonth));
                calendar_month = bulanFormat.format(firstDayOfNewMonth);
                if(calendar_month.substring(0,1).equals("0"))
                {
                    calendar_month = calendar_month.substring(1);
                }
                calendar_year   = tahunFormat.format(firstDayOfNewMonth);
                dapat_calendar();
                final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                        .adapter(calendarAdapter)
                        .shimmer(true)
                        .angle(20)
                        .frozen(false)
                        .duration(1200)
                        .count(10)
                        .load(R.layout.item_calendar)
                        .show(); //default count is 10
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        skeletonScreen.hide();
                    }
                }, 3000);
                return;
            }
        });
        left_month.setOnClickListener(v -> compactCalendarView.scrollLeft());
        right_month.setOnClickListener(v -> compactCalendarView.scrollRight());
        calendarModelList = new ArrayList<CalendarModel>();
        dapat_calendar();

        calendarAdapter = new CalendarAdapter(calendarModelList);
        calendarAdapter.setOnItemClickListener((view, position) -> {
            if (calendarModelList.get(position).getCalendar_type().equals("-1")) {
                Toast.makeText(getApplicationContext(),calendarModelList.get(position).getCalendar_title(),Toast.LENGTH_LONG).show();
            }else {
                calendar_date = calendarModelList.get(position).getCalendar_date();
                calendar_time = calendarModelList.get(position).getCalendar_time();
                calendar_id = calendarModelList.get(position).getCalendar_id();
                Intent intent = new Intent(KalenderKelas.this, KalendarDetail.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("calendar_id", calendar_id);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(KalenderKelas.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final SkeletonScreen skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(calendarAdapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_calendar)
                .show(); //default count is 10
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 3000);
        return;
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
                if (status == 1 & code.equals("DTS_SCS_0001")) {
                    recyclerView.setVisibility(View.VISIBLE);
                    kalendar.setVisibility(View.GONE);
                    if (response.body().getData() != null) {
                        List<JSONResponse.DataCalendar> calendarList = response.body().getData();
                        if (calendarModelList != null) {
                            calendarModelList.clear();
                            for (JSONResponse.DataCalendar calendar : calendarList) {
                                Calendar cal = Calendar.getInstance();
                                calendar_date = calendar.getCalendar_date();
                                calendar_time = calendar.getCalendar_time();
                                calendar_type = calendar.getCalendar_type();
                                calendar_colour = calendar.getCalendar_colour();
                                if (calendar_type.equals("-2")) {
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.getDefault());
                                    try {
                                        date = format.parse(calendar_date + " " + calendar_time);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    cal.setTime(date);
                                    setToMidnight(cal);
                                    Long times = cal.getTimeInMillis();
                                    eventList = getevent(calendar_colour,times);
                                    compactCalendarView.addEvents(eventList);
                                    calendarModel = new CalendarModel();
                                    calendarModel.setCalendar_id(String.valueOf(calendar.getCalendar_id()));
                                    calendarModel.setCalendar_time(calendar.getCalendar_time());
                                    calendarModel.setCalendar_date(converDate(calendar.getCalendar_date()));
                                    calendarModel.setCalendar_title(calendar.getCalendar_title());
                                    calendarModel.setCalendar_desc(calendar.getCalendar_desc());
                                    calendarModel.setCalendar_type(calendar_type);
                                    calendarModelList.add(calendarModel);

                                } else if (calendar_type.equals("-1")) {
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                    try {
                                        date = format.parse(calendar_date);

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    cal.setTime(date);
                                    setToMidnight(cal);
                                    Long times = cal.getTimeInMillis();
                                    events = getEventList(calendar_colour,times);
                                    compactCalendarView.addEvents(events);
                                    String remove="Hari Libur - ";
                                    calendarModel = new CalendarModel();
                                    calendarModel.setCalendar_id(String.valueOf(calendar.getCalendar_id()));
                                    calendarModel.setCalendar_time("Seharian");
                                    calendarModel.setCalendar_date(converDate(calendar.getCalendar_date()));
                                    String sentence = calendar.getCalendar_title();
                                    String replaced = sentence.replace("Hari Libur - ", "");
                                    calendarModel.setCalendar_title(replaced);
                                    calendarModel.setCalendar_desc(calendar.getCalendar_desc());
                                    calendarModel.setCalendar_type(calendar_type);
                                    calendarModelList.add(calendarModel);
                                }

                            }
                            calendarAdapter.notifyDataSetChanged();
                            kalendar.setVisibility(View.GONE);
                        }
                    }
                }
                else if (status == 0 &&code.equals("DTS_ERR_0001")){
//                    calendarModelList.clear();
                    kalendar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
//                    calendarAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ClassCalendar> call, Throwable t) {
                Log.d("onFailure",t.toString());
            }
        });
    }
    public static String removeWords(String word ,String remove) {
        return word.replace(remove,"");
    }
    private List<Event> getevent(String color,long timeinMilis){
        return Arrays.asList(new Event(Color.parseColor(color),timeinMilis));
    }
    private List<Event>getEventList(String color,long timeinMilis){
        return Arrays.asList(new Event(Color.parseColor(color),timeinMilis));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
