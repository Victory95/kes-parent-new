package com.fingertech.kes.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class JadiParent extends AppCompatActivity {


    private String[] hubungan = {
            "Pilih Hubungan Anda...",
            "Ayah",
            "Ibu",
            "Wali"
    };

    EditText et_nik,et_tanggal;
    Spinner sp_hubungan;
    RadioButton rb_laki,rb_wanita;
    String relation;
    TextView gender;
    RadioGroup rg_hubungan;
    Auth mApiInterface;

    ProgressDialog dialog;
    SharedPreferences sharedpreferences,sharedupdate;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";
    public static final String TAG_LASTLOGIN    = "last_login";

    String authorization,parent_id,jenis_kelamin,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;

    int status;
    String code;
    Button btn_jadi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadi_parent);

        et_nik      = (EditText)findViewById(R.id.et_nik_member);
        et_tanggal  = (EditText)findViewById(R.id.et_tanggal_lahir);
        rb_laki     = (RadioButton)findViewById(R.id.rb_pria);
        rb_wanita   = (RadioButton)findViewById(R.id.rb_wanita);
        gender      = (TextView)findViewById(R.id.gender);
        rg_hubungan = (RadioGroup)findViewById(R.id.rg_hubungan_member);
        sp_hubungan = (Spinner)findViewById(R.id.sp_relation);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        btn_jadi    = (Button)findViewById(R.id.btn_jadi);


        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_prof);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        final List<String> penghasil = new ArrayList<>(Arrays.asList(hubungan));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_full,penghasil){
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
        spinnerAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_hubungan.setAdapter(spinnerAdapter);
        sp_hubungan.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {

                if (position == 0){
                    gender.setVisibility(View.GONE);
                    rg_hubungan.setVisibility(View.GONE);
                }

                if (position > 0) {
                    relation = penghasil.get(position);

                    if (position == 3){
                        gender.setVisibility(View.VISIBLE);
                        rg_hubungan.setVisibility(View.VISIBLE);
                    }else {
                        gender.setVisibility(View.GONE);
                        rg_hubungan.setVisibility(View.GONE);
                    }
                    if (position == 1){
                        jenis_kelamin = getResources().getString(R.string.rb_laki);
                    }else if (position == 2){
                        jenis_kelamin = getResources().getString(R.string.rb_wanita);
                    }

                }
            }
        });

        relation = sp_hubungan.getSelectedItem().toString();


        rb_laki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_kelamin = getResources().getString(R.string.rb_laki);
            }
        });
        rb_wanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_kelamin = getResources().getString(R.string.rb_wanita);
            }
        });


        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                et_tanggal.setText(convertDate(selectedyear, selectedmonth, selectedday));
            }
        }, mYear, mMonth, mDay);


        et_tanggal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
//                datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
                mDatePicker.show();
            }
        });

        et_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
//                    datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
                    mDatePicker.show();
                }
            }
        });

        btn_jadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch_parent();
            }
        });
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


    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    public void switch_parent(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_switch_to_parent_put(authorization.toString(),parent_id.toString(),et_nik.getText().toString(),relation.toString(),jenis_kelamin.toString(),convertTanggal(et_tanggal.getText().toString()));

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                hideDialog();
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("SWP_SCS_0001")) {
                        FancyToast.makeText(JadiParent.this, "Berhasil menjadi Orangtua", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                        Intent intent = new Intent(JadiParent.this, MenuUtama.class);
                        startActivity(intent);
                        finish();
                    } else {
                        if (status == 0 && code.equals("SWP_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
                        } else if (status == 0 & code.equals("SWP_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
                        } else if (status == 0 & code.equals("SWP_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), "Nomor HP tidak boleh kosong", Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
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
        dialog = new ProgressDialog(JadiParent.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
