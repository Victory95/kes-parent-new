package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.AbsensiAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.Activity.Model.DataHari;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.net.IDN;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.fingertech.kes.Service.App.getContext;

public class AbsenAnak extends AppCompatActivity {
    Toolbar toolbar;
    Auth mApiInterface;
    String bulan,tahun,tanggal,day,hari;
    CompactCalendarView compactCalendarView;

    private SimpleDateFormat dateFormat     = new SimpleDateFormat("MMMM - yyyy",new Locale("in","ID"));
    private SimpleDateFormat bulanFormat    = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat monthFormat    = new SimpleDateFormat("MMMM",new Locale("in","ID"));
    private SimpleDateFormat tahunFormat    = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("dd",Locale.getDefault());
    private SimpleDateFormat hariFormat     = new SimpleDateFormat("EEEE",new Locale("in","ID"));
    private SimpleDateFormat formattanggal  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    TextView month_calender;
    ImageView left_month,right_month;
    int status;
    String code;
    String authorization,school_code,student_id,classroom_id,calendar_year;
    RecyclerView recyclerView;
    String tanggals;
    private List<AbsensiModel>absensiModels;
    List <AbsenModel> absenModelList = new ArrayList<>();

    List<JSONResponse.ScheduleClassItem> scheduleClassItemList = new ArrayList<>();
    List<JSONResponse.JadwalData> jadwalDataList = new ArrayList<>();
    AbsensiModel absensiModel;
    AbsenModel absenModel;
    List<JSONResponse.DataJam> dataJamList = new ArrayList<>();

    AbsensiAdapter absensiAdapter;
    TextView tv_absen;
    LinearLayout hint;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;
    String days_name;
    String  daysid, day_type,month, day_status;
    SlidingUpPanelLayout slidingUpPanelLayout;
    Button btn_pilih;
    LinearLayout drag;
    ImageView arrow;
    TextView no_absen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absen_anak);
        compactCalendarView = findViewById(R.id.compactcalendar_view);
        month_calender      = findViewById(R.id.month_calender);
        left_month          = findViewById(R.id.left_calender);
        right_month         = findViewById(R.id.right_calender);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        recyclerView        = findViewById(R.id.rv_absen);
        toolbar             = findViewById(R.id.toolbar_absen);
        tv_absen            = findViewById(R.id.hint_absen);
        hint                = findViewById(R.id.hint);
        no_absen            = findViewById(R.id.no_absen);
//        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
//        btn_pilih           = findViewById(R.id.btn_pilih);
//        drag                = findViewById(R.id.dragView);
//        arrow               = findViewById(R.id.arrow);
//        name                = findViewById(R.id.name);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        compactCalendarView.setUseThreeLetterAbbreviation(true);
        month_calender.setText(dateFormat.format(compactCalendarView.getFirstDayOfCurrentMonth()));


        day = tanggalFormat.format(Calendar.getInstance().getTime());
        if(day.substring(0,1).equals("0"))
        {
            day = day.substring(1);
        }
        tanggals = formattanggal.format(Calendar.getInstance().getTime());
        bulan = bulanFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        if(bulan.substring(0,1).equals("0"))
        {
            bulan = bulan.substring(1);
        }
//        name.setText("Rekap presensi bulan "+monthFormat.format(compactCalendarView.getFirstDayOfCurrentMonth()));
        hari    = hariFormat.format(Calendar.getInstance().getTime());
        tahun   = tahunFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        absensiModels = new ArrayList<>();

        absensiAdapter = new AbsensiAdapter(absensiModels,absenModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(absensiAdapter);
        dapat_absen();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                hari    = hariFormat.format(dateClicked);
                day     = tanggalFormat.format(dateClicked);
                if(day.substring(0,1).equals("0"))
                {
                    day = day.substring(1);
                }
                tanggals = formattanggal.format(dateClicked);

                compactCalendarView.setCurrentDayBackgroundColor(Color.parseColor("#0Dffffff"));

                if (hari.equals("Sabtu") || hari.equals("Minggu")){
                    tv_absen.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                    hint.setVisibility(GONE);
                }else {
                    tv_absen.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                    hint.setVisibility(VISIBLE);
                    if (absensiModels != null) {
                        absensiModels.clear();
                        for (JSONResponse.ScheduleClassItem dataJam : scheduleClassItemList) {
                            absensiModel = new AbsensiModel();
                            absensiModel.setTanggal(tanggals);
                            absensiModel.setTimez_star(dataJam.getTimezOk());
                            absensiModel.setTimez_finish(dataJam.getTimezFinish());
                            absensiModel.setMapel(dataJam.getCourcesName());
                            absensiModel.setGuru(dataJam.getTeacherName());
                            absensiModels.add(absensiModel);
                        }

                        absensiAdapter.notifyDataSetChanged();
                    }
                    if (absenModelList != null) {
                        absenModelList.clear();
                        for (JSONResponse.DataJam dataJam : dataJamList) {
                            absenModel = new AbsenModel();
                            absenModel.setTanggal(tanggals);
                            absenModel.setTimez_star(dataJam.getTimez_ok());
                            absenModel.setTimez_finish(dataJam.getTimez_finish());
                            absenModel.setDay_id(dataJam.getDays().get(Integer.parseInt(day)-1).getAbsen_status());
                            absenModelList.add(absenModel);
                        }
                        absensiAdapter.notifyDataSetChanged();
                    }
                }
                dapat_mapel();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                day = tanggalFormat.format(firstDayOfNewMonth);
                if(day.substring(0,1).equals("0"))
                {
                    day = day.substring(1);
                }
                bulan = bulanFormat.format(firstDayOfNewMonth);
                if(bulan.substring(0,1).equals("0"))
                {
                    bulan = bulan.substring(1);
                }
                month_calender.setText(dateFormat.format(firstDayOfNewMonth));
                tahun   = tahunFormat.format(firstDayOfNewMonth);
                hari = hariFormat.format(firstDayOfNewMonth);
//                name.setText("Rekap presensi bulan "+monthFormat.format(firstDayOfNewMonth));
                dapat_absen();
            }
        });

        left_month.setOnClickListener(v -> compactCalendarView.scrollLeft());
        right_month.setOnClickListener(v -> compactCalendarView.scrollRight());

//        btn_pilih.setOnClickListener(v -> {
//            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//            arrow.setImageResource(R.drawable.ic_up_arrow);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("authorization",authorization);
//            editor.putString("school_code",school_code);
//            editor.putString("classroom_id",classroom_id);
//            editor.putString("student_id",student_id);
//            editor.putString("bulan",bulan);
//            editor.putString("tahun",tahun);
//            editor.commit();
//            Intent intent = new Intent(AbsenAnak.this,RekapAbsensi.class);
//            intent.putExtra("authorization",authorization);
//            intent.putExtra("school_code",school_code);
//            intent.putExtra("classroom_id",classroom_id);
//            intent.putExtra("student_id",student_id);
//            intent.putExtra("bulan",bulan);
//            intent.putExtra("tahun",tahun);
//            startActivity(intent);
//        });

//        slidingUpPanelLayout.setFadeOnClickListener(view -> {
//            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//            arrow.setImageResource(R.drawable.ic_up_arrow);
//        });
//
//        drag.setOnClickListener(v -> {
//            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
//                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//                arrow.setImageResource(R.drawable.ic_up_arrow);
//            }else if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
//                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
//                arrow.setImageResource(R.drawable.ic_arrow_down);
//            }
//        });
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

    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
        dialog.setContentView(R.layout.progressbar);
    }
    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }
    public void progressBar(){
        dialog = new ProgressDialog(AbsenAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void dapat_absen(){
        progressBar();
        showDialog();
        Call<JSONResponse.AbsenSiswa> call = mApiInterface.kes_class_attendance_get(authorization.toString(),school_code.toLowerCase(),student_id.toString(),classroom_id.toString(),bulan.toString(),tahun.toString());
        call.enqueue(new Callback<JSONResponse.AbsenSiswa>() {
            @Override
            public void onResponse(Call<JSONResponse.AbsenSiswa> call, Response<JSONResponse.AbsenSiswa> response) {
                Log.i("KES",response.code() + "");
                hideDialog();
                JSONResponse.AbsenSiswa resource = response.body();
                status = resource.status;
                code   = resource.code;

                if (status == 1 & code.equals("DTS_SCS_0001")){
                    dataJamList     = response.body().getData();
                    dapat_mapel();

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.AbsenSiswa> call, Throwable t) {
                Log.d("Gagal",t.toString());
                hideDialog();
            }
        });
    }
    public void dapat_mapel(){
        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization,school_code.toLowerCase(),student_id,classroom_id);
        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalPelajaran> call, Response<JSONResponse.JadwalPelajaran> response) {
                Log.d("Success",response.code()+"");
                JSONResponse.JadwalPelajaran resource = response.body();
                status = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("CSCH_SCS_0001")) {
                    jadwalDataList = response.body().getData();

                    for (int i = 0; i < response.body().getData().size(); i++) {
                        scheduleClassItemList = response.body().getData().get(i).getScheduleClass();
                        days_name = response.body().getData().get(i).getDayName();
                        day_status = response.body().getData().get(i).getDayStatus();
                        daysid = response.body().getData().get(i).getDayid();
                        day_type = response.body().getData().get(i).getDayType();
                        if (days_name.equals(hari)){
                            if (hari.equals("Sabtu") || hari.equals("Minggu")){
                                tv_absen.setVisibility(VISIBLE);
                                recyclerView.setVisibility(GONE);
                                hint.setVisibility(GONE);
                            }else {
                                if (scheduleClassItemList.size() == 0){
                                    no_absen.setVisibility(VISIBLE);
                                    recyclerView.setVisibility(GONE);
                                    hint.setVisibility(GONE);
                                }else {
                                    tv_absen.setVisibility(GONE);
                                    recyclerView.setVisibility(VISIBLE);
                                    hint.setVisibility(VISIBLE);
                                    no_absen.setVisibility(GONE);
                                    if (absensiModels != null) {
                                        absensiModels.clear();
                                        for (JSONResponse.ScheduleClassItem dataJam : scheduleClassItemList) {
                                            absensiModel = new AbsensiModel();
                                            absensiModel.setTanggal(tanggals);
                                            absensiModel.setTimez_star(dataJam.getTimezOk());
                                            absensiModel.setTimez_finish(dataJam.getTimezFinish());
                                            absensiModel.setMapel(dataJam.getCourcesName());
                                            absensiModel.setGuru(dataJam.getTeacherName());
                                            absensiModels.add(absensiModel);
                                        }
                                        absensiAdapter.notifyDataSetChanged();
                                    }
                                    if (absenModelList != null) {
                                        absenModelList.clear();
                                        for (JSONResponse.DataJam dataJam : dataJamList) {
                                            absenModel = new AbsenModel();
                                            absenModel.setTanggal(tanggals);
                                            absenModel.setTimez_star(dataJam.getTimez_ok());
                                            absenModel.setTimez_finish(dataJam.getTimez_finish());
                                            absenModel.setDay_id(dataJam.getDays().get(Integer.parseInt(day) - 1).getAbsen_status());
                                            absenModelList.add(absenModel);
                                        }
                                        absensiAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalPelajaran> call, Throwable t) {
                Log.d("onfailure",t.toString());
            }
        });
    }
}
