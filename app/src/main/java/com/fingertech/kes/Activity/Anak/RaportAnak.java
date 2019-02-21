package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.Color;
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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.RaportAdapter;
import com.fingertech.kes.Activity.Adapter.TugasAdapter;
import com.fingertech.kes.Activity.Model.RaporModel;
import com.fingertech.kes.Activity.Model.TugasModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaportAnak extends AppCompatActivity {

    TextView wali_kelas,no_rapor,nama_kelas;
    CardView btn_download,btn_go;
    RecyclerView recyclerView;
    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String statusrapor,peringkat,kritik;
    String authorization,school_code,classroom_id,student_id,semester_id;

    RaportAdapter raportAdapter;
    private List<RaporModel> raporModels;
    String teori,ulangan_harian,praktikum,eskul,ujian_sekolah,ujian_negara,mapel,nilai_akhir,rata_rata;

    String semester_nama;
    private List<JSONResponse.DataSemester> dataSemesters;

    Spinner sp_semester;
    TextView status_rapor,tv_peringkat,tv_kritik,tv_start,tv_end;
    TableLayout tableLayout;

    String date,namakelas,walikelas;
    String guru,tanggal,type,nilai,deskripsi,start_date,end_date,semester,start_year,start_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rapor_anak);
        toolbar         = findViewById(R.id.toolbar_rapor);
        wali_kelas      = findViewById(R.id.tv_walikelas);
        nama_kelas      = findViewById(R.id.tv_kelas);
        recyclerView    = findViewById(R.id.rv_rapor);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        sp_semester     = findViewById(R.id.sp_semester);
        no_rapor        = findViewById(R.id.tv_no_rapor);
        status_rapor    = findViewById(R.id.status_rapor);
        tv_peringkat    = findViewById(R.id.peringkat);
        tv_kritik       = findViewById(R.id.kritik_saran);
        tableLayout     = findViewById(R.id.table_layout);
        btn_go          = findViewById(R.id.btn_go);
        tv_end          = findViewById(R.id.tv_end);
        tv_start        = findViewById(R.id.tv_start);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        student_id      = getIntent().getStringExtra("student_id");

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());

        Check_Semester();
        Classroom_detail();


        btn_go.setOnClickListener(v -> {
            RaporAnak();
            dapat_semester();
        });


    }
    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {

                Log.i("KES", response.code() + "");

                JSONResponse.CheckSemester resource = response.body();

                status = resource.status;
                code    = resource.code;
                semester_id = response.body().getData();
                dapat_semester();
                RaporAnak();
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.ListSemester resource = response.body();

                status = resource.status;
                code = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    dataSemesters = response.body().getData();
                    List<String> listSpinner = new ArrayList<String>();
                    listSpinner.add("Pilih Semester...");
                    for (int i = 0; i < dataSemesters.size(); i++){
                        listSpinner.add(dataSemesters.get(i).getSemester_name());
                        if (dataSemesters.get(i).getSemester_id().equals(semester_id)){
                            semester_nama    = response.body().getData().get(i).getSemester_name();
                            start_date  = response.body().getData().get(i).getStart_date();
                            end_date    = response.body().getData().get(i).getEnd_date();
                            start_year  = response.body().getData().get(0).getStart_date();
                            start_end   = response.body().getData().get(1).getEnd_date();
                            tv_start.setText(converDate(start_date));
                            tv_end.setText(converDate(end_date));

                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                    RaportAnak.this,R.layout.spinner_full,listSpinner){
                                @Override
                                public boolean isEnabled(int position){
                                    if(position == 0)
                                    {
                                        // Disable the first item from Spinner
                                        // First item will be use for hint
                                        return false;
                                    }
                                    else
                                    {
                                        return true;
                                    }
                                }

                                @Override
                                public View getDropDownView(int position, View convertView,
                                                            ViewGroup parent) {
                                    View view = super.getDropDownView(position, convertView, parent);
                                    TextView tv = (TextView) view;
                                    if(position == 0){
                                        // Set the hint text color gray
                                        tv.setTextColor(Color.GRAY);
                                    }
                                    else {
                                        tv.setTextColor(Color.BLACK);
                                    }
                                    return view;
                                }
                            };
                            int spinnerPosition = adapterRaport.getPosition(semester_nama);
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_semester.setAdapter(adapterRaport);
                            sp_semester.setOnItemSelectedListener((parent, view, position, id) ->
                                    semester_id = dataSemesters.get(position-1).getSemester_id());
                            sp_semester.setSelection(spinnerPosition);
                        }
                    }
                    final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                            RaportAnak.this,R.layout.spinner_full,listSpinner){
                        @Override
                        public boolean isEnabled(int position){
                            if(position == 0)
                            {
                                // Disable the first item from Spinner
                                // First item will be use for hint
                                return false;
                            }
                            else
                            {
                                return true;
                            }
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,
                                                    ViewGroup parent) {
                            View view = super.getDropDownView(position, convertView, parent);
                            TextView tv = (TextView) view;
                            if(position == 0){
                                // Set the hint text color gray
                                tv.setTextColor(Color.GRAY);
                            }
                            else {
                                tv.setTextColor(Color.BLACK);
                            }
                            return view;
                        }
                    };
                    int spinnerPosition = adapterRaport.getPosition(semester_nama);
                    adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                    sp_semester.setAdapter(adapterRaport);
                    sp_semester.setOnItemSelectedListener((parent, view, position, id) ->
                            semester_id = dataSemesters.get(position-1).getSemester_id());
                    sp_semester.setSelection(spinnerPosition);

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
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

    private void RaporAnak(){
        progressBar();
        showDialog();
        Call<JSONResponse.Raport> call = mApiInterface.kes_rapor_score_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString(),semester_id.toString());
        call.enqueue(new Callback<JSONResponse.Raport>() {
            @Override
            public void onResponse(Call<JSONResponse.Raport> call, final Response<JSONResponse.Raport> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.Raport resource = response.body();

                status = resource.status;
                code    = resource.code;
                RaporModel raporModel= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    raporModels = new ArrayList<RaporModel>();
                    if(response.body().getData().getDetailScore() != null) {
                        statusrapor = response.body().getData().getClassroom().getPromoteText();
                        peringkat   = response.body().getData().getClassroom().getPromoteRanking();
                        kritik      = response.body().getData().getClassroom().getDescriptionText();
                        status_rapor.setText(statusrapor);
                        tv_peringkat.setText(peringkat);
                        tv_kritik.setText(kritik);
                        for (int i = 0; i < response.body().getData().getDetailScore().size(); i++) {
                            mapel = response.body().getData().getDetailScore().get(i).getCourcesName();
                            teori = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getLatihanTeori().getScoreExam());
                            ulangan_harian = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getUlanganHarian().getScoreExam());
                            praktikum = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getPraktikum().getScoreExam());
                            eskul = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getEkstrakulikuler().getScoreExam());
                            ujian_sekolah = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getUjianSekolah().getScoreExam());
                            ujian_negara = String.valueOf(response.body().getData().getDetailScore().get(i).getTypeExam().getUjianNegara().getScoreExam());
                            nilai_akhir = String.valueOf(response.body().getData().getDetailScore().get(i).getFinalScore());
                            rata_rata = String.valueOf(response.body().getData().getDetailScore().get(i).getClassAverageScore());

                            raporModel = new RaporModel();
                            raporModel.setMapel(mapel);
                            raporModel.setTeori(teori);
                            raporModel.setUlangan_harian(ulangan_harian);
                            raporModel.setPraktikum(praktikum);
                            raporModel.setEskul(eskul);
                            raporModel.setUjian_sekolah(ujian_sekolah);
                            raporModel.setUjian_negara(ujian_negara);
                            raporModel.setNilai_akhir(nilai_akhir);
                            raporModel.setRata_rata(rata_rata);
                            raporModels.add(raporModel);

                        }

                        no_rapor.setVisibility(View.GONE);
                        tableLayout.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                        raportAdapter = new RaportAdapter(raporModels);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RaportAnak.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(raportAdapter);
                    }
                    else {
                        tableLayout.setVisibility(View.GONE);
                        no_rapor.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Raport> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
                no_rapor.setText(t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

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
                Toast.makeText(RaportAnak.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
}
