package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.fingertech.kes.R;
import com.fingertech.kes.Service.Common;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;
    TextView tv_memberid, tv_email, tv_device_id,tv_token,tv_token_decode;
    String email, member_id, fullname, member_type;
    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID     = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ///////////////////// Message Firebase
//        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d("Token", Common.currentToken);

        tv_memberid = (TextView) findViewById(R.id.tv_memberid);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_device_id = (TextView) findViewById(R.id.tv_device_id);
        tv_token = (TextView) findViewById(R.id.tv_token);
        tv_token_decode = (TextView) findViewById(R.id.tv_token_decode);
        btn_logout = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);

//        email = sharedpreferences.getString(TAG_EMAIL,"");
//        member_id = sharedpreferences.getString(TAG_MEMBER_ID,"");
//        fullname = sharedpreferences.getString(TAG_FULLNAME,"");
//        member_type = sharedpreferences.getString(TAG_MEMBER_TYPE,"");

        email = getIntent().getStringExtra(TAG_EMAIL);
        member_id = getIntent().getStringExtra(TAG_MEMBER_ID);
        fullname = getIntent().getStringExtra(TAG_FULLNAME);
        member_type = getIntent().getStringExtra(TAG_MEMBER_TYPE);

        tv_email.setText("EMAIL : " + email);
        tv_memberid.setText("MEMBERID : " + member_id);
        tv_device_id.setText("FULLNAME : " + fullname);
        tv_token.setText("MEMBER_TYPE : " + member_type);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Masuk.session_status, false);
                editor.putString(TAG_EMAIL, null);
                editor.putString(TAG_MEMBER_ID, null);
                editor.putString(TAG_FULLNAME, null);
                editor.putString(TAG_MEMBER_TYPE, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, OpsiMasuk.class);
                finish();
                startActivity(intent);
            }
        });
    }
}
