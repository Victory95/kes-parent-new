package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.fingertech.kes.R;

public class OpsiMasuk extends AppCompatActivity {
    private Button btn_masuk,btn_masuk_tamu;
    private TextView tv_daftar;

    Boolean session = false;
    public final static String TAG_EMAIL     = "email";
    public final static String TAG_MEMBERID  = "memberid";
    public final static String TAG_DEVICE_ID = "device_id";
    public final static String TAG_TOKEN = "token";
    String memberid, email, device_id;
    private String authtoken;

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
        memberid = sharedpreferences.getString(TAG_MEMBERID, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        device_id = sharedpreferences.getString(TAG_DEVICE_ID, null);
        authtoken = sharedpreferences.getString(TAG_TOKEN, null);

        if (session) {
            Intent intent = new Intent(OpsiMasuk.this, MainActivity.class);
            intent.putExtra(TAG_MEMBERID, memberid);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_DEVICE_ID, device_id);
            intent.putExtra(TAG_TOKEN, authtoken);
            finish();
            startActivity(intent);
        }

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
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
