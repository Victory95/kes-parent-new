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
import com.fingertech.kes.Util.JWTUtils;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;
    TextView tv_memberid, tv_email, tv_device_id,tv_token,tv_token_decode;
    String memberid, email, device_id, authtoken;
    SharedPreferences sharedpreferences;

    public static final String TAG_MEMBERID   = "memberid";
    public static final String TAG_EMAIL      = "email";
    public static final String TAG_DEVICE_ID  = "device_id";
    private static final String TAG_TOKEN     = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        tv_memberid  = (TextView) findViewById(R.id.tv_memberid);
        tv_email     = (TextView) findViewById(R.id.tv_email);
        tv_device_id = (TextView) findViewById(R.id.tv_device_id);
        tv_token     = (TextView) findViewById(R.id.tv_token);
        tv_token_decode    = (TextView) findViewById(R.id.tv_token_decode);
        btn_logout   = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);

        memberid = getIntent().getStringExtra(TAG_MEMBERID);
        email = getIntent().getStringExtra(TAG_EMAIL);
        device_id = getIntent().getStringExtra(TAG_DEVICE_ID);
        authtoken = getIntent().getStringExtra(TAG_TOKEN);

        tv_memberid.setText("MEMBERID : " + memberid);
        tv_email.setText("EMAIL : " + email);
        tv_device_id.setText("DEVICEID : " + device_id);
        tv_token.setText("TOKEN : " + authtoken);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // update login session ke FALSE dan mengosongkan nilai id dan username
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Masuk.session_status, false);
                editor.putString(TAG_MEMBERID, null);
                editor.putString(TAG_EMAIL, null);
                editor.putString(TAG_DEVICE_ID, null);
                editor.putString(TAG_TOKEN, null);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, OpsiMasuk.class);
                finish();
                startActivity(intent);
            }
        });

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JWTUtils.decoded(authtoken));
            tv_token_decode.setText("member_id : " +jsonObject.get("member_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getDecodeToken() {
        try {
            JWTUtils.decoded(authtoken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
