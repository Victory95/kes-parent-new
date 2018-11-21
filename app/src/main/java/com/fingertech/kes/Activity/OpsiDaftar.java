package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fingertech.kes.R;

public class OpsiDaftar extends AppCompatActivity {
    private TextView tv_masuk;
    private Button btn_google,btn_facebook,btn_email,btn_orang_tua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opsi_daftar);
        getSupportActionBar().setElevation(0);

        tv_masuk      =(TextView)findViewById(R.id.tv_Masuk);
        btn_google    =(Button)findViewById(R.id.btn_Google);
        btn_facebook  =(Button)findViewById(R.id.btn_Facebook);
        btn_email     =(Button)findViewById(R.id.btn_Email);
        btn_orang_tua =(Button)findViewById(R.id.btn_Orangtua);

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DaftarPublic.class);
                startActivity(intent);
            }
        });

        tv_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Masuk.class);
                finish();
                startActivity(intent);
            }
        });
    }

}
