package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.TugasAdapter;
import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.TugasModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TugasAnak extends AppCompatActivity {


    TextView wali_kelas,nama_kelas;
    CardView btn_download;
    RecyclerView recyclerView;
    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String authorization,school_code,classroom_id,student_id;

    TugasAdapter tugasAdapter;
    TextView no_tugas;
    private List<TugasModel> listTugas;
    String mapel,tanggals,deskripsi,tipe,guru,nilai,namakelas,walikelas;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas_anak);

        toolbar         = findViewById(R.id.toolbar_tugas);
        wali_kelas      = findViewById(R.id.wali_kelas_tugas);
        nama_kelas      = findViewById(R.id.nama_kelas_tugas);
        btn_download    = findViewById(R.id.btn_download_tugas);
        recyclerView    = findViewById(R.id.recycle_tugas);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        no_tugas        = findViewById(R.id.no_tugas);
        swipeRefreshLayout  = findViewById(R.id.pullToRefresh);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        student_id      = getIntent().getStringExtra("student_id");

        Tugas_anak();
        Classroom_detail();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1;
            @Override
            public void onRefresh() {
                Tugas_anak();
                Refreshcounter = Refreshcounter + 1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void Tugas_anak(){
        progressBar();
        showDialog();
        Call<JSONResponse.TugasAnak> call = mApiInterface.kes_cources_score_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.TugasAnak>() {

            @Override
            public void onResponse(Call<JSONResponse.TugasAnak> call, final Response<JSONResponse.TugasAnak> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.TugasAnak resource = response.body();

                status = resource.status;
                code    = resource.code;

                TugasModel tugasModel= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    listTugas = new ArrayList<TugasModel>();
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            tanggals = response.body().getData().get(i).getExamDate();
                            mapel = response.body().getData().get(i).getCources_name();
                            tipe = response.body().getData().get(i).getExamTypeName();
                            deskripsi = response.body().getData().get(i).getExamDesc();
                            guru = response.body().getData().get(i).getTeacher_name();
                            nilai = response.body().getData().get(i).getScoreValue();

                            tugasModel = new TugasModel();
                            tugasModel.setTanggal(tanggals);
                            tugasModel.setMapel(mapel);
                            tugasModel.setTipe(tipe);
                            tugasModel.setDeskripsi(deskripsi);
                            tugasModel.setGuru(guru);
                            tugasModel.setNilai(nilai);
                            listTugas.add(tugasModel);
                        }
                        tugasAdapter = new TugasAdapter(listTugas);
                        no_tugas.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TugasAnak.this, LinearLayoutManager.VERTICAL, false);
                        linearLayoutManager.setStackFromEnd(true);
                        linearLayoutManager.setReverseLayout(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(tugasAdapter);
                    }else {
                        no_tugas.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.TugasAnak> call, Throwable t) {
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
        dialog = new ProgressDialog(TugasAnak.this);
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
                Toast.makeText(TugasAnak.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
}
