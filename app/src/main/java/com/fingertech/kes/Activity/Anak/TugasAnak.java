package com.fingertech.kes.Activity.Anak;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TugasAnak extends AppCompatActivity {

    List<TugasModel> itemTugasList = new ArrayList<>();
    TugasModel itemTugas;
    TugasAdapter tugasAdapter;
    Auth mApiInterface;
    EditText et_kata_kunci;
    TextView tv_semester,tv_start,tv_end;
    String authorization,memberid,school_code,classroom_id;
    RecyclerView rv_tugas;
    Toolbar toolbar;
    ProgressDialog dialog;
    int status;
    String code;
    String guru,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
    TextView no_ujian;
    String date,semester_nama;

    private List<JSONResponse.DataSemester> dataSemesters;
    private List<JSONResponse.DataMapel> dataMapelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tugas_anak);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        rv_tugas        = findViewById(R.id.rv_tugas);
        toolbar         = findViewById(R.id.toolbar_tugas);
        no_ujian        = findViewById(R.id.tv_no_ujian);
        tv_semester     = findViewById(R.id.tv_semester);
        tv_start        = findViewById(R.id.tv_start);
        tv_end          = findViewById(R.id.tv_end);
//        tv_filter       = findViewById(R.id.tv_filter);
        et_kata_kunci   = findViewById(R.id.et_kata_kunci);
//        ll_slide        = findViewById(R.id.slide_down);

        authorization   = getIntent().getStringExtra("authorization");
        memberid        = getIntent().getStringExtra("student_id");
        school_code     = getIntent().getStringExtra("school_code");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_launcher_background), PorterDuff.Mode.SRC_ATOP);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        Check_Semester();

        et_kata_kunci.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                tugasAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
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
                Tugas_anak();
                dapat_mapel();
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.ListSemester resource = response.body();

                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    for (int i = 0;i < response.body().getData().size();i++){

                        if (response.body().getData().get(i).getSemester_id().equals(semester_id)){
                            semester    = response.body().getData().get(i).getSemester_name();
                            start_date  = response.body().getData().get(i).getStart_date();
                            end_date    = response.body().getData().get(i).getEnd_date();
                            start_year  = response.body().getData().get(0).getStart_date();
                            start_end   = response.body().getData().get(1).getEnd_date();
                            tv_semester.setText("Semester "+semester+" ("+converTahun(start_year)+"/"+converTahun(start_end)+")");
                            tv_start.setText(converDate(start_date));
                            tv_end.setText(converDate(end_date));

                        }
                    }

                    dataSemesters = response.body().getData();

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void dapat_mapel(){
        Call<JSONResponse.ListMapel> call = mApiInterface.kes_list_cources_get(authorization.toString(),school_code.toLowerCase(),classroom_id);
        call.enqueue(new Callback<JSONResponse.ListMapel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapel> call, Response<JSONResponse.ListMapel> response) {
                Log.d("onResponse",response.code()+"");
                JSONResponse.ListMapel resource = response.body();

                status  = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("KLC_SCS_0001")){
                    dataMapelList = response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapel> call, Throwable t) {

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
    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void Tugas_anak(){
        progressBar();
        showDialog();
        Call<JSONResponse.TugasAnak> call = mApiInterface.kes_cources_score_get(authorization.toString(),school_code.toString().toLowerCase(),memberid.toString(),classroom_id.toString(),semester_id.toString());

        call.enqueue(new Callback<JSONResponse.TugasAnak>() {

            @Override
            public void onResponse(Call<JSONResponse.TugasAnak> call, final Response<JSONResponse.TugasAnak> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.TugasAnak resource = response.body();

                status = resource.status;
                code    = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    if (response.body().getData() != null) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            tanggal = response.body().getData().get(i).getExamDateOk();
                            mapel = response.body().getData().get(i).getCources_name();
                            type = response.body().getData().get(i).getExamTypeName();
                            deskripsi = response.body().getData().get(i).getExamDesc();
                            guru = response.body().getData().get(i).getTeacher_name();
                            nilai = response.body().getData().get(i).getScoreValue();

                            itemTugas = new TugasModel();
                            itemTugas.setTanggal(tanggal);
                            itemTugas.setMapel(mapel);
                            itemTugas.setType_id(type);
                            itemTugas.setDeskripsi(deskripsi);
                            itemTugas.setGuru(guru);
                            itemTugas.setNilai(nilai);
                            itemTugasList.add(itemTugas);
                        }
                        tugasAdapter = new TugasAdapter(itemTugasList,TugasAnak.this);
                        no_ujian.setVisibility(View.GONE);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TugasAnak.this, LinearLayoutManager.VERTICAL, false);
                        linearLayoutManager.setStackFromEnd(true);
                        linearLayoutManager.setReverseLayout(true);
                        rv_tugas.setLayoutManager(linearLayoutManager);
                        rv_tugas.setAdapter(tugasAdapter);
                    }else {
                        no_ujian.setVisibility(View.VISIBLE);
                        rv_tugas.setVisibility(View.GONE);
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

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

}
