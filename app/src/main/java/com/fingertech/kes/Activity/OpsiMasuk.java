package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fingertech.kes.R;

public class OpsiMasuk extends AppCompatActivity {
    private Button btn_masuk,btn_masuk_tamu;
    private TextView tv_daftar;

    Boolean session = false;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID     = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    String email, memberid, fullname, member_type;

    SharedPreferences sharedpreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opsi_masuk);

        btn_masuk=(Button)findViewById(R.id.btn_Masuk);
        btn_masuk_tamu=(Button)findViewById(R.id.btn_Masuk_tamu);
        tv_daftar=(TextView)findViewById(R.id.tv_Daftar);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        memberid = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type = sharedpreferences.getString(TAG_MEMBER_TYPE, null);

        if (session) {
            Intent intent = new Intent(OpsiMasuk.this, MainActivity.class);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_MEMBER_ID, memberid);
            intent.putExtra(TAG_FULLNAME, fullname);
            intent.putExtra(TAG_MEMBER_TYPE, member_type);
            finish();
            startActivity(intent);
        }
        checkLocationPermission();
        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Masuk.class);
                startActivity(intent);
            }
        });
        tv_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpsiDaftar.class);
                startActivity(intent);
            }
        });
        btn_masuk_tamu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuGuest.class);
                startActivity(intent);
            }
        });
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


}
