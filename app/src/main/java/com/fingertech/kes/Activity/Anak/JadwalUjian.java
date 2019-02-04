package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalUjian extends AppCompatActivity {

    TextView wali_kelas,no_ujian,nama_kelas;
    CardView btn_download;
    RecyclerView recyclerView;
    private List<ItemUjian> itemlist;
    private UjianAdapter ujianAdapter = null;
    int status;
    String walikelas,namakelas;
    Auth mApiInterface;
    String code;
    String authorization,school_code,jam,tanggal,type_id,mapel,classroom_id,student_id;
    Toolbar toolbar;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_ujian);

        wali_kelas      = (TextView)findViewById(R.id.wali_kelas_ujian);
        btn_download    = (CardView)findViewById(R.id.btn_download_jadwal);
        toolbar         = (Toolbar)findViewById(R.id.toolbar_ujian);
        no_ujian        = findViewById(R.id.no_ujian);
        nama_kelas      = findViewById(R.id.nama_kelas_ujian);

        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        student_id      = getIntent().getStringExtra("student_id");


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        Classroom_detail();
        Jadwal_ujian();

    }
    private void Classroom_detail(){

        Call<JSONResponse.ClassroomDetail> call = mApiInterface.kes_classroom_detail_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ClassroomDetail>() {

            @Override
            public void onResponse(Call<JSONResponse.ClassroomDetail> call, final Response<JSONResponse.ClassroomDetail> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.ClassroomDetail resource = response.body();

                status = resource.status;
                code    = resource.code;

                ItemUjian itemUjian= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                   walikelas    = response.body().getData().getHomeroom_teacher();
                   namakelas    = response.body().getData().getClassroom_name();
                   wali_kelas.setText(walikelas);
                   nama_kelas.setText(namakelas);
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.ClassroomDetail> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(JadwalUjian.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
    private void Jadwal_ujian(){
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {

            @Override
            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.JadwalUjian resource = response.body();

                status = resource.status;
                code    = resource.code;

                ItemUjian itemUjian= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    itemlist = new ArrayList<ItemUjian>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            jam = response.body().getData().get(i).getExam_time();
                            tanggal = response.body().getData().get(i).getExam_date();
                            mapel = response.body().getData().get(i).getCources_name();
                            type_id = response.body().getData().get(i).getType_name();

                            itemUjian = new ItemUjian();
                            itemUjian.setJam(jam);
                            itemUjian.setTanggal(tanggal);
                            itemUjian.setMapel(mapel);
                            itemUjian.setType_id(type_id);
                            itemlist.add(itemUjian);

                        }
                        no_ujian.setVisibility(View.GONE);
                        recyclerView = (RecyclerView) findViewById(R.id.recycle_ujian);
                        ujianAdapter = new UjianAdapter(itemlist);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalUjian.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(ujianAdapter);
                    }else {
                        no_ujian.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
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
        dialog = new ProgressDialog(JadwalUjian.this);
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

