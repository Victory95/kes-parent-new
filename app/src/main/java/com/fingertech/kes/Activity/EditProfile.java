package com.fingertech.kes.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    TextInputLayout til_nama_lengkap,til_email_profile,til_nomor_handphone;
    EditText et_nama_lengkap,et_email,et_nomor_hp;
    RadioButton rb_pria,rb_wanita;
    Spinner sp_religion;
    String gender,agama;
    private String[] religion = {
            "Pilih Agama...",
            "Islam",
            "Kristen",
            "Katolik",
            "Budhaa",
            "Hindu"
    };

    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;

    Auth mApiInterface;
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

    public static final String my_shared_update = "my_shared_update";
    public static final String TAG_NAMA_PROFILE = "nama_profile";
    public static final String TAG_NOMOR_HP     = "nomor_hp";
    public static final String TAG_AGAMA        = "agama";
    public static final String TAG_GENDER       = "gender";
    public static final String TAG_TANGGAL      = "tanggal_lahir";
    String nama_profile,no_hp,agama_profile,gender_profile,tanggal_lahir;

    int status;
    String code,last_update;
    String authorization;
    Button btn_update;
    EditText et_tanggal;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        til_nama_lengkap    = findViewById(R.id.til_nama_profile);
        til_email_profile   = findViewById(R.id.til_email_profile);
        til_nomor_handphone = findViewById(R.id.til_mobile_phone);
        et_nama_lengkap     = findViewById(R.id.et_nama_profile);
        et_email            = findViewById(R.id.et_email_profile);
        et_nomor_hp         = findViewById(R.id.et_mobile_phone);
        rb_pria             = findViewById(R.id.rb_pria);
        rb_wanita           = findViewById(R.id.rb_wanita);
        sp_religion         = findViewById(R.id.sp_religion);
        btn_update          = findViewById(R.id.btn_update);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        et_tanggal          = findViewById(R.id.et_tanggal);


        final Toolbar toolbar = findViewById(R.id.toolbarprofil);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);


        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        email         = sharedpreferences.getString(TAG_EMAIL,"email");


        sharedupdate    = getSharedPreferences(my_shared_update,Context.MODE_PRIVATE);
        nama_profile    = sharedupdate.getString(TAG_NAMA_PROFILE,"");
        email           = sharedupdate.getString(TAG_EMAIL,"");
        agama_profile   = sharedupdate.getString(TAG_AGAMA,"");
        gender_profile  = sharedupdate.getString(TAG_GENDER,"");
        no_hp           = sharedupdate.getString(TAG_NOMOR_HP,"");
        tanggal_lahir   = sharedupdate.getString(TAG_TANGGAL,"");

        et_nama_lengkap.setText(nama_profile);
        et_email.setText(email);
        et_nomor_hp.setText(no_hp);
        if(gender_profile.equals("Pria")){
            rb_pria.setChecked(true);
            rb_wanita.setChecked(false);
        }else if (gender_profile.equals("Wanita")){
            rb_wanita.setChecked(true);
            rb_pria.setChecked(false);
        }
        et_tanggal.setText(tanggal_lahir);

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


        et_tanggal.setOnClickListener(view -> {
//                datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
            mDatePicker.show();
        });

        et_tanggal.setOnFocusChangeListener((view, b) -> {
            if (b) {
//                    datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
                mDatePicker.show();
            }
        });


        final List<String> penghasil = new ArrayList<>(Arrays.asList(religion));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_full,penghasil){
            @Override
            public boolean isEnabled(int position){
                return position != 0;
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
        int position    = ArrayAdapter.getPosition(agama_profile);
        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_religion.setAdapter(ArrayAdapter);
        sp_religion.setSelection(position);
        sp_religion.setOnItemSelectedListener((parent, view, position1, id) -> {
            if (position1 > 0) {
                agama = penghasil.get(position1);
            }
        });

        agama = sp_religion.getSelectedItem().toString();

        rb_pria.setOnClickListener(v -> gender_profile = getResources().getString(R.string.rb_laki));
        rb_wanita.setOnClickListener(v -> gender_profile = getResources().getString(R.string.rb_wanita));
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        last_update = df.format(Calendar.getInstance().getTime());

        btn_update.setOnClickListener(v -> update_profile());

    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    public void update_profile(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_update_put(authorization, parent_id,et_email.getText().toString(),et_nama_lengkap.getText().toString(),et_nomor_hp.getText().toString(), last_update, gender_profile, agama,et_tanggal.getText().toString());
        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                Log.i("KES", response.code() + "");
                hideDialog();
                JSONResponse resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("UP_SCS_0001")) {
                    Intent intent = new Intent(EditProfile.this, ProfileParent.class);
                    startActivity(intent);
                    finish();
                    FancyToast.makeText(getApplicationContext(),"Berhasil Update",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                } else{
                    if (status == 0 && code.equals("UP_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }else if (status == 0 & code.equals("UP_ERR_0002")){
                        Toast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }else if (status == 0 & code.equals("UP_ERR_0003")){
                        Toast.makeText(getApplicationContext(), "Nomor HP tidak boleh kosong", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse> call, Throwable t) {
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
        dialog = new ProgressDialog(EditProfile.this);
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
