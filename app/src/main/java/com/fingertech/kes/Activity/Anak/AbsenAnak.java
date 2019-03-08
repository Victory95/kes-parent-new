package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.AbsensiAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;

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

    private SimpleDateFormat dateFormat     = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    private SimpleDateFormat bulanFormat    = new SimpleDateFormat("MM", Locale.getDefault());
    private SimpleDateFormat tahunFormat    = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("dd",Locale.getDefault());
    private SimpleDateFormat hariFormat     = new SimpleDateFormat("EEEE",Locale.getDefault());
    private SimpleDateFormat formattanggal  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());


    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM - yyyy", Locale.getDefault());
    TextView month_calender;
    ImageView left_month,right_month;
    int status;
    String code;
    String authorization,school_code,student_id,classroom_id,calendar_year;
    RecyclerView recyclerView;
    String tanggals;

    private List<AbsensiModel>absensiModels;
    AbsensiModel absensiModel;


    List<JSONResponse.DataJam> dataJamList = new ArrayList<>();
    AbsensiAdapter absensiAdapter;
    TextView tv_absen;
    LinearLayout hint;
    ProgressDialog dialog;
    SharedPreferences sharedPreferences;

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


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        compactCalendarView.setUseThreeLetterAbbreviation(true);
        month_calender.setText(dateFormatForMonth.format(compactCalendarView.getFirstDayOfCurrentMonth()));

        tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        if(tanggal.substring(0,1).equals("0"))
        {
            tanggal = tanggal.substring(1);
        }
        tanggals = formattanggal.format(Calendar.getInstance().getTime());
        bulan = bulanFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        if(bulan.substring(0,1).equals("0"))
        {
            bulan = bulan.substring(1);
        }

        hari    = hariFormat.format(Calendar.getInstance().getTime());
        tahun   = tahunFormat.format(compactCalendarView.getFirstDayOfCurrentMonth());
        absensiModels = new ArrayList<>();
        absensiAdapter = new AbsensiAdapter(absensiModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(absensiAdapter);
        dapat_absen();

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                hari    = hariFormat.format(dateClicked);
                day = tanggalFormat.format(dateClicked);
                if(day.substring(0,1).equals("0"))
                {
                    day = day.substring(1);
                }
                tanggals = formattanggal.format(dateClicked);

                compactCalendarView.setCurrentDayBackgroundColor(Color.parseColor("#0Dffffff"));

                if (absensiModels != null) {
                    absensiModels.clear();
                        for (JSONResponse.DataJam dataJam : dataJamList) {
                            absensiModel = new AbsensiModel();
                            absensiModel.setTanggal(tanggals);
                            absensiModel.setTimez_star(dataJam.getTimez_ok());
                            absensiModel.setTimez_finish(dataJam.getTimez_finish());
                            absensiModel.setDay_id(dataJam.getDays().get(Integer.parseInt(day)-1).getAbsen_status());
                            absensiModels.add(absensiModel);
                        }
                        absensiAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                bulan = bulanFormat.format(firstDayOfNewMonth);
                if(bulan.substring(0,1).equals("0"))
                {
                    bulan = bulan.substring(1);
                }
                month_calender.setText(dateFormat.format(firstDayOfNewMonth));
                tahun   = tahunFormat.format(firstDayOfNewMonth);
                dapat_absen();
            }
        });

        left_month.setOnClickListener(v -> compactCalendarView.scrollLeft());
        right_month.setOnClickListener(v -> compactCalendarView.scrollRight());
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

    public void dapat_absen(){

        Call<JSONResponse.AbsenSiswa> call = mApiInterface.kes_class_attendance_get(authorization.toString(),school_code.toLowerCase(),student_id.toString(),classroom_id.toString(),bulan.toString(),tahun.toString());
        call.enqueue(new Callback<JSONResponse.AbsenSiswa>() {
            @Override
            public void onResponse(Call<JSONResponse.AbsenSiswa> call, Response<JSONResponse.AbsenSiswa> response) {
                Log.i("KES",response.code() + "");

                JSONResponse.AbsenSiswa resource = response.body();
                status = resource.status;
                code   = resource.code;
                if (status == 1 & code.equals("DTS_SCS_0001")){
                    dataJamList     = response.body().getData();
                    if (hari.equals("Sabtu") ||hari.equals("Minggu")){
                        tv_absen.setVisibility(VISIBLE);
                        recyclerView.setVisibility(GONE);
                        hint.setVisibility(GONE);
                    }else {
                        tv_absen.setVisibility(GONE);
                        recyclerView.setVisibility(VISIBLE);
                        hint.setVisibility(VISIBLE);
                        if (absensiModels != null) {
                            absensiModels.clear();
                            for (JSONResponse.DataJam dataJam : dataJamList) {
                                absensiModel = new AbsensiModel();
                                absensiModel.setTanggal(tanggals);
                                absensiModel.setTimez_star(dataJam.getTimez_ok());
                                absensiModel.setTimez_finish(dataJam.getTimez_finish());
                                absensiModel.setDay_id(dataJam.getDays().get(Integer.parseInt(tanggal)-1).getAbsen_status());
                                absensiModels.add(absensiModel);
                            }
                            absensiAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.AbsenSiswa> call, Throwable t) {
                Log.d("Gagal",t.toString());
            }
        });
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

}
