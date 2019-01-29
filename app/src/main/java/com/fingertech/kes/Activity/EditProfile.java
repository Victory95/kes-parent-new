package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
    String nama_profile,no_hp,agama_profile,gender_profile;



    int status;
    String code,last_update;
    String authorization;
    Button btn_update;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        til_nama_lengkap    = (TextInputLayout)findViewById(R.id.til_nama_profile);
        til_email_profile   = (TextInputLayout)findViewById(R.id.til_email_profile);
        til_nomor_handphone = (TextInputLayout)findViewById(R.id.til_mobile_phone);
        et_nama_lengkap     = (EditText)findViewById(R.id.et_nama_profile);
        et_email            = (EditText)findViewById(R.id.et_email_profile);
        et_nomor_hp         = (EditText)findViewById(R.id.et_mobile_phone);
        rb_pria             = (RadioButton)findViewById(R.id.rb_pria);
        rb_wanita           = (RadioButton)findViewById(R.id.rb_wanita);
        sp_religion         = (Spinner)findViewById(R.id.sp_religion);
        btn_update          = (Button)findViewById(R.id.btn_update);
        mApiInterface       = ApiClient.getClient().create(Auth.class);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarprofil);
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

        final List<String> penghasil = new ArrayList<>(Arrays.asList(religion));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
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
        int position    = ArrayAdapter.getPosition(agama_profile);
        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_religion.setAdapter(ArrayAdapter);
        sp_religion.setSelection(position);
        sp_religion.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                if (position > 0) {
                    agama = penghasil.get(position);
                }
            }
        });

        agama = sp_religion.getSelectedItem().toString();

        rb_pria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getResources().getString(R.string.rb_laki);
            }
        });
        rb_wanita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = getResources().getString(R.string.rb_wanita);
            }
        });
        DateFormat df = new SimpleDateFormat("EEEEEE, dd MMM yyyy, HH:mm");
        last_update = df.format(Calendar.getInstance().getTime());

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                update_profile();
            }
        });

    }

    public void update_profile(){
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_update_put(authorization.toString(),parent_id.toString(),et_nama_lengkap.getText().toString(),et_email.getText().toString(),et_nomor_hp.getText().toString(),gender.toString(),agama.toString(),last_update);

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                Log.i("KES", response.code() + "");

                JSONResponse resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("UP_SCS_0001")) {
                    Intent intent = new Intent(EditProfile.this, ProfileParent.class);
                    startActivity(intent);
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

}
