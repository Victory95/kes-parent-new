package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.TugasAdapter;
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

public class RaportAnak extends AppCompatActivity {

    TextView wali_kelas;
    CardView btn_download;
    RecyclerView recyclerView;
    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String authorization,school_code,classroom_id,student_id,semester_id;

    TugasAdapter tugasAdapter;
    private List<TugasModel> listTugas;
    String mapel,tanggals,deskripsi,tipe,guru,nilai;
    String type_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapor_anak);
        toolbar         = findViewById(R.id.toolbar_rapor);
        wali_kelas      = findViewById(R.id.wali_kelas);
        btn_download    = findViewById(R.id.btn_download_raport);
        recyclerView    = findViewById(R.id.recycle_raport);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        student_id      = getIntent().getStringExtra("student_id");

        semester_id = "1";
        Tugas_anak();
    }

    private void Tugas_anak(){
        progressBar();
        showDialog();
        Call<JSONResponse.Raport> call = mApiInterface.kes_rapor_score_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString(),semester_id.toString());

        call.enqueue(new Callback<JSONResponse.Raport>() {

            @Override
            public void onResponse(Call<JSONResponse.Raport> call, final Response<JSONResponse.Raport> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.Raport resource = response.body();

                status = resource.status;
                code    = resource.code;

                TugasModel tugasModel= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    listTugas = new ArrayList<TugasModel>();
                    for (int i = 0; i < response.body().getData().getDetailScore().size(); i++) {
                        tanggals  = response.body().getData().getDetailScore().get(i).getCourcesCode();
                        mapel     = response.body().getData().getDetailScore().get(i).getCourcesid();
                        tipe      = response.body().getData().getDetailScore().get(i).getCourcesName();
                        deskripsi = response.body().getData().getDetailScore().get(i).getCourcesSpec();

                        nilai     = response.body().getData().getDetailScore().get(i).getTypeExam().getJsonMember1().getTypeName();
                        guru      = response.body().getData().getDetailScore().get(i).getTypeExam().getJsonMember2().getTypeName();
                        Toast.makeText(RaportAnak.this,nilai + guru,Toast.LENGTH_LONG).show();


//                        tugasModel   = new TugasModel();
//                        tugasModel.setTanggal(tanggals);
//                        tugasModel.setMapel(mapel);
//                        tugasModel.setTipe(tipe);
//                        tugasModel.setDeskripsi(deskripsi);
//                        tugasModel.setGuru(guru);
//                        tugasModel.setNilai(nilai);
//                        listTugas.add(tugasModel);
                    }
//                    tugasAdapter    = new TugasAdapter(listTugas);
//
//                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RaportAnak.this);
//                    recyclerView.setLayoutManager(layoutManager);
//                    recyclerView.setAdapter(tugasAdapter);
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Raport> call, Throwable t) {
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
        dialog = new ProgressDialog(RaportAnak.this);
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
