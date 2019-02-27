package com.fingertech.kes.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    Auth mApiInterface;
    String code;
    int status;
    EditText et_password_lama,et_password_baru;
    Button btn_ganti_password;
    TextView tv_lupa_password;
    ProgressDialog dialog;

    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id";
    SharedPreferences sharedpreferences;
    String authorization,memberid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        et_password_baru    = findViewById(R.id.et_kata_sandi_baru);
        et_password_lama    = findViewById(R.id.et_kata_sandi_lama);
        btn_ganti_password  = findViewById(R.id.btn_ganti_sandi);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        tv_lupa_password    = findViewById(R.id.tvb_lupa_pass);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_change);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization    = sharedpreferences.getString(TAG_TOKEN,"token");
        memberid         = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");

        btn_ganti_password.setOnClickListener(v -> change_password());
        tv_lupa_password.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(intent);
        });
    }

    public void change_password(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_change_password_post(authorization.toString(),memberid.toString(),et_password_baru.getText().toString(),et_password_lama.getText().toString());

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("CP_SCS_0001")) {
                    FancyToast.makeText(getApplicationContext(),"Kata sandi berhasil diubah",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,true).show();
                    Intent intent = new Intent(ChangePassword.this,ProfileParent.class);
                    startActivity(intent);

                } else{
                    if (status == 0 && code.equals("CP_ERR_0001")) {
                        FancyToast.makeText(getApplicationContext(),"Kata sandi lama anda salah",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                        Toast.makeText(getApplicationContext(), "Kata sandi lama anda salah", Toast.LENGTH_LONG).show();
                    }else if (status == 0 && code.equals("CP_ERR_0004")){
                        FancyToast.makeText(getApplicationContext(),"Kata sandi tidak boleh sama dengan yang lain",FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                        Toast.makeText(getApplicationContext(), "Kata sandi tidak boleh sama dengan yang lain", Toast.LENGTH_LONG).show();
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    //////// Progressbar - Loading Animation
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
        dialog = new ProgressDialog(ChangePassword.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
