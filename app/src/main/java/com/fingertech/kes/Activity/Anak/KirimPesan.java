package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KirimPesan extends AppCompatActivity {

    Toolbar toolbar;
    Auth mApiInterface;
    TextView tv_sekolah,tv_kelas;
    Spinner sp_guru,sp_mapel;
    EditText et_pesan;
    CardView btn_kirim;
    List<JSONResponse.DataMapel> dataMapelList;
    List<JSONResponse.DataGuru> dataGuruList;
    ProgressDialog dialog;
    CoordinatorLayout coordinatorLayout;
    int status;
    String code,authorization,school_code,classroom_id,member_id,walikelas,namakelas,school_name,student_id,teacher_id,cources_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kirim_pesan);
        toolbar         = findViewById(R.id.toolbar_pesan);
        tv_sekolah      = findViewById(R.id.sekolah_pesan);
        tv_kelas        = findViewById(R.id.kelas_pesan);
        sp_guru         = findViewById(R.id.sp_guru);
        sp_mapel        = findViewById(R.id.sp_mapel);
        et_pesan        = findViewById(R.id.et_pesan);
        btn_kirim       = findViewById(R.id.btn_kirim);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        coordinatorLayout   = findViewById(R.id.coordinator);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        member_id       = getIntent().getStringExtra("member_id");
        classroom_id    = getIntent().getStringExtra("classroom_id");
        school_name     = getIntent().getStringExtra("school_name");
        student_id      = getIntent().getStringExtra("student_id");

        dapat_mapel();
        dapat_guru();
        Classroom_detail();
        tv_sekolah.setText(school_name);
        btn_kirim.setOnClickListener(v -> {
            kirim_pesan();
        });
    }

    public void dapat_mapel(){
        Call<JSONResponse.ListMapel> call = mApiInterface.kes_list_cources_get(authorization.toString(),school_code,classroom_id.toString());
        call.enqueue(new Callback<JSONResponse.ListMapel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapel> call, Response<JSONResponse.ListMapel> response) {
                Log.d("onResponse",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListMapel resource = response.body();

                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("KLC_SCS_0001")) {
                        dataMapelList = response.body().getData();
                        List<String> listMapel = new ArrayList<>();
                        listMapel.add("Pilih mata pelajaran");
                        for (int m = 0; m < dataMapelList.size(); m++) {
                            listMapel.add(dataMapelList.get(m).getCources_name());
                        }

                        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(
                                KirimPesan.this, R.layout.spinner_full, listMapel) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_mapel.setAdapter(adapterMapel);
                        sp_mapel.setOnItemSelectedListener((parent, view, position, id) -> {
                            cources_id = dataMapelList.get(position - 1).getCourcesid();
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapel> call, Throwable t) {
            Log.d("onfailure",t.toString());
            }
        });
    }
    private void dapat_guru(){
        Call<JSONResponse.ListTeacher> call = mApiInterface.kes_list_teacher_get(authorization,school_code.toLowerCase(),classroom_id);
        call.enqueue(new Callback<JSONResponse.ListTeacher>() {
            @Override
            public void onResponse(Call<JSONResponse.ListTeacher> call, Response<JSONResponse.ListTeacher> response) {
                Log.d("onResponse",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListTeacher resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataGuruList = response.body().getData();
                        List<String> listGuru = new ArrayList<>();
                        listGuru.add("Pilih guru");
                        for (int m = 0; m < dataGuruList.size(); m++) {
                            listGuru.add(dataGuruList.get(m).getFullname());
                        }

                        final ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(
                                KirimPesan.this, R.layout.spinner_full, listGuru) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_guru.setAdapter(adapterMapel);
                        sp_guru.setOnItemSelectedListener((parent, view, position, id) -> {
                            teacher_id = dataGuruList.get(position - 1).getTeacher_id();
                        });

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListTeacher> call, Throwable t) {
            Log.d("onFailure",t.toString());
            }
        });
    }

    private void Classroom_detail(){

        Call<JSONResponse.ClassroomDetail> call = mApiInterface.kes_classroom_detail_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ClassroomDetail>() {

            @Override
            public void onResponse(Call<JSONResponse.ClassroomDetail> call, final Response<JSONResponse.ClassroomDetail> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {

                    JSONResponse.ClassroomDetail resource = response.body();

                    status = resource.status;
                    code = resource.code;


                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        walikelas = response.body().getData().getHomeroom_teacher();
                        namakelas = response.body().getData().getClassroom_name();
                        tv_kelas.setText(namakelas);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ClassroomDetail> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(KirimPesan.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
    private void kirim_pesan(){
        if (sp_mapel.getSelectedItem().equals("Pilih mata pelajaran")|| sp_guru.getSelectedItem().equals("Pilih guru")){
            Snackbar.make(coordinatorLayout,"Silah pilih mapel dan guru dulu",Snackbar.LENGTH_LONG).show();
        }else if (et_pesan.getText().toString().trim().isEmpty()) {
            Snackbar.make(coordinatorLayout,"Harap diisi pesan terlebih dahulu",Snackbar.LENGTH_LONG).show();
        }else {
            progressBar();
            showDialog();
            Call<JSONResponse.KirimPesan> call = mApiInterface.kes_send_message_post(authorization,school_code.toLowerCase(),member_id,student_id,classroom_id,teacher_id,cources_id,et_pesan.getText().toString());
            call.enqueue(new Callback<JSONResponse.KirimPesan>() {
                @Override
                public void onResponse(Call<JSONResponse.KirimPesan> call, Response<JSONResponse.KirimPesan> response) {
                    Log.d("onResponse",response.code()+"");
                    hideDialog();
                    if (response.isSuccessful()) {
                        JSONResponse.KirimPesan resource = response.body();
                        status = resource.status;
                        code = resource.code;
                        if (status == 1 && code.equals("DTS_SCS_0001")) {
                            finish();
                        }
                    }
                }
                @Override
                public void onFailure(Call<JSONResponse.KirimPesan> call, Throwable t) {
                    hideDialog();
                    Log.d("onFailure",t.toString());
                }
            });
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
        dialog = new ProgressDialog(KirimPesan.this);
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
