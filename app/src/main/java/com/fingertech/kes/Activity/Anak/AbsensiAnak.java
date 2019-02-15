package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.fingertech.kes.Activity.Adapter.AbsenAdapter;
import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsensiAnak extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private int mScrollState;
    private AbsenAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;
    String authorization,student_id,classroom_id,school_code;
    Auth mApiInterface;
    String hari,bulan,tahun,Month;
    DateFormat bulanFormat  = new SimpleDateFormat("MM", Locale.getDefault());
    DateFormat tahunFormat  = new SimpleDateFormat("yyyy",Locale.getDefault());
    DateFormat dateFormat   = new SimpleDateFormat("dd",Locale.getDefault());
    DateFormat montFormat   = new SimpleDateFormat("MMMM",Locale.getDefault());
    int status;
    String code;
    String day_id,day_type,day_status;
    List<AbsenModel> absenModels = new ArrayList<AbsenModel>();
    List<AbsenModel.DataAbsensi> dataAbsensis = new ArrayList<AbsenModel.DataAbsensi>();
    AbsenModel absenModel;
    AbsenModel.DataAbsensi dataAbsensi;
    String timez_start,timez_finish,total_absen,leave_sick;
    List days;
    RecyclerTabLayout recyclerTabLayout;

    int daye;
    TextView month;
    Toolbar toolbar;
    String namakelas,walikelas;
    TextView nama_kelas,wali_kelas;
    ProgressDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absensi_anak);

        mApiInterface   = ApiClient.getClient().create(Auth.class);
        mViewPager      = findViewById(R.id.view_pager);
        month           = findViewById(R.id.bulan);
        recyclerTabLayout = findViewById(R.id.recycler_tab_layout);
        toolbar         = findViewById(R.id.toolbar_absen);
        nama_kelas      = findViewById(R.id.nama_kelas_absen);
        wali_kelas      = findViewById(R.id.wali_kelas_absen);
        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        student_id      = getIntent().getStringExtra("student_id");
        classroom_id    = getIntent().getStringExtra("classroom_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        hari    = dateFormat.format(Calendar.getInstance().getTime());
        if (hari.substring(0,1).equals("0")){
            hari    = hari.substring(1);
        }
        bulan   = bulanFormat.format(Calendar.getInstance().getTime());
        if(bulan.substring(0,1).equals("0"))
        {
            bulan = bulan.substring(1);
        }
        Month   = montFormat.format(Calendar.getInstance().getTime());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth yearMonth = YearMonth.of(Calendar.YEAR,Calendar.MONTH);
            daye = yearMonth.lengthOfMonth();
        }
        tahun   = tahunFormat.format(Calendar.getInstance().getTime());
        mItems = new ArrayList<>();
        for (int i = 1; i <= daye; i++) {
            mItems.add(String.valueOf(i));
        }
        month.setText(Month);
        mViewPager.addOnPageChangeListener(this);
        dapat_absen();
        Classroom_detail();

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        boolean nearLeftEdge = (position <= mItems.size());
        boolean nearRightEdge = (position >= mAdapter.getCount() - mItems.size());
        if (nearLeftEdge || nearRightEdge) {
            mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
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
                    for (int i = 0; i < response.body().getData().size();i++){
                        timez_start     = response.body().getData().get(i).getTimez_start();
                        timez_finish    = response.body().getData().get(i).getTimez_finish();
                        days              = response.body().getData().get(i).getDays();
                        absenModel  = new AbsenModel();
                        absenModel.setTimez_start(timez_start);
                        absenModel.setTimes_finish(timez_finish);
                        absenModel.setDays(response.body().getData().get(i).getDays());
                        absenModels.add(absenModel);
                    }
                    mAdapter = new AbsenAdapter(absenModels);
                    mAdapter.addAll(mItems);
                    mViewPager.setAdapter(mAdapter);
                    mViewPager.setCurrentItem(mAdapter.getCenterPosition(Integer.parseInt(hari) - 1));
                    recyclerTabLayout.setUpWithViewPager(mViewPager);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.AbsenSiswa> call, Throwable t) {
            hideDialog();
            }
        });
    }
    private void Classroom_detail(){

        Call<JSONResponse.ClassroomDetail> call = mApiInterface.kes_classroom_detail_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ClassroomDetail>() {

            @Override
            public void onResponse(Call<JSONResponse.ClassroomDetail> call, final Response<JSONResponse.ClassroomDetail> response) {
                Log.i("KES", response.code() + "");


                JSONResponse.ClassroomDetail resource = response.body();

                status = resource.status;
                code    = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    walikelas    = response.body().getData().getHomeroom_teacher();
                    namakelas    = response.body().getData().getClassroom_name();
                    wali_kelas.setText("Wali kelas: "+walikelas);
                    nama_kelas.setText("Kelas: "+namakelas);
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.ClassroomDetail> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(AbsensiAnak.this,t.toString(),Toast.LENGTH_LONG).show();

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
        dialog = new ProgressDialog(AbsensiAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
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

}
