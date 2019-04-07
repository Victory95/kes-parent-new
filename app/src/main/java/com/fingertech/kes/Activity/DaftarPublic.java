package com.fingertech.kes.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarPublic extends AppCompatActivity {

    private Button btn_buat_akun;
    private TextInputLayout til_fullname, til_email, til_mobile_phone, til_password, til_ulangi_password;
    private EditText et_fullname,et_email,et_mobile_phone,et_password,et_ulangi_password;
    private CheckBox cb_ketentuan;
    private ProgressDialog dialog;
    private String deviceid;
    int status;
    String code;
    private static final int PERMISSION_REQUEST_CODE = 1;
    Auth mApiInterface;
    ConnectivityManager conMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_public);
        getSupportActionBar().setElevation(0);

        btn_buat_akun        = findViewById(R.id.btn_buat_akun);
        cb_ketentuan         =(CheckBox)findViewById(R.id.cb_ketentuan);
        et_fullname          =(EditText)findViewById(R.id.et_nama_lengkap);
        et_email             =(EditText)findViewById(R.id.et_email);
        et_mobile_phone      =(EditText)findViewById(R.id.et_number_phone);
        et_password          =(EditText)findViewById(R.id.et_kata_sandi);
        et_ulangi_password   =(EditText)findViewById(R.id.et_ulangi_kata_sandi);
        til_fullname         =(TextInputLayout)findViewById(R.id.til_nama_lengkap);
        til_email            =(TextInputLayout)findViewById(R.id.til_email);
        til_mobile_phone     =(TextInputLayout)findViewById(R.id.til_number_phone);
        til_password         =(TextInputLayout)findViewById(R.id.til_kata_sandi);
        til_ulangi_password  =(TextInputLayout)findViewById(R.id.til_ulangi_kata_sandi);;
        //// Caps Text in First Alfabet
        et_fullname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        checkInternetCon();

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(DaftarPublic.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DaftarPublic.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(DaftarPublic.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        //// CheckBox persetujuan
        getCb_ketentuan();

        mApiInterface = ApiClient.getClient().create(Auth.class);
        btn_buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }

    private void submitForm() {
        if (!validateNamaLengkap()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }
        if (!validateNumberPhone()) {
            return;
        }
        if (!validateKataSandi()) {
            return;
        }
        if (!validateUlangiKataSandi()) {
            return;
        }
        if(cb_ketentuan.isChecked()) {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(DaftarPublic.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            deviceid = tm.getDeviceId();
            register_post();
        }else{
            Toast.makeText(DaftarPublic.this, getResources().getString(R.string.tcb_ketentuan), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean validateNamaLengkap() {
        if (et_fullname.getText().toString().trim().isEmpty()) {
            til_fullname.setError(getResources().getString(R.string.validate_fullname));
            requestFocus(et_fullname);
            return false;
        } else {
            til_fullname.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            til_email.setError(getResources().getString(R.string.validate_email));
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateNumberPhone() {
        if (et_mobile_phone.getText().toString().trim().isEmpty()) {
            til_mobile_phone.setError(getResources().getString(R.string.validate_mobile_phone));
            requestFocus(et_mobile_phone);
            return false;
        }else if(et_mobile_phone.length()<10) {
            til_mobile_phone.setError(getResources().getString(R.string.validate_number_lengh));
            requestFocus(et_mobile_phone);
            return false;
        } else {
            til_mobile_phone.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateKataSandi() {
        if (et_password.getText().toString().trim().isEmpty()) {
            til_password.setError(getResources().getString(R.string.validate_pass));
            requestFocus(et_password);
            return false;
        }else if(et_password.length()<6) {
            til_password.setError(getResources().getString(R.string.validate_pass_lengh));
            requestFocus(et_password);
            return false;
        } else {
            til_password.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateUlangiKataSandi() {
        String pass = et_password.getText().toString();
        String cpass = et_ulangi_password.getText().toString();
        if (!pass.equals(cpass)) {
            til_ulangi_password.setError(getResources().getString(R.string.validate_cpass));
            requestFocus(et_ulangi_password);
            return false;
        } else {
            til_ulangi_password.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    @Override
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
        dialog = new ProgressDialog(DaftarPublic.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DaftarPublic.this, OpsiDaftar.class);
        finish();
        startActivity(intent);
    }
    public void register_post(){
        progressBar();
        showDialog();
        String device_id = "android_" + deviceid;
        Call<JSONResponse> postCall = mApiInterface.register_post(et_fullname.getText().toString(), et_email.getText().toString(), et_mobile_phone.getText().toString(), et_password.getText().toString(), device_id.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String RP_SCS_0001 = getResources().getString(R.string.RP_SCS_0001);
                    String RP_ERR_0001 = getResources().getString(R.string.RP_ERR_0001);
                    String RP_ERR_0002 = getResources().getString(R.string.RP_ERR_0002);
                    String RP_ERR_0003 = getResources().getString(R.string.RP_ERR_0003);
                    String RP_ERR_0004 = getResources().getString(R.string.RP_ERR_0004);
                    String RP_ERR_0005 = getResources().getString(R.string.RP_ERR_0005);
                    String RP_ERR_0006 = getResources().getString(R.string.RP_ERR_0006);
                    String RP_ERR_0007 = getResources().getString(R.string.RP_ERR_0007);
                    String RP_ERR_0008 = getResources().getString(R.string.RP_ERR_0008);

                    if (status == 1 && code.equals("RP_SCS_0001")) {
                        Toast.makeText(getApplicationContext(), RP_SCS_0001, Toast.LENGTH_LONG).show();
                        et_fullname.setText("");
                        et_email.setText("");
                        et_mobile_phone.setText("");
                        et_password.setText("");
                        et_ulangi_password.setText("");
                        if (cb_ketentuan.isChecked()) {
                            cb_ketentuan.toggle();
                        }
                        Intent intent = new Intent(DaftarPublic.this, OpsiMasuk.class);
                        startActivity(intent);
                    } else {
                        if (status == 0 && code.equals("RP_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0005")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0005, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0006")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0006, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0007")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0007, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RP_ERR_0008")) {
                            Toast.makeText(getApplicationContext(), RP_ERR_0008, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void checkInternetCon(){
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_internet_con),
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    @SuppressLint("ResourceAsColor")
    public void getCb_ketentuan(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            cb_ketentuan.setText(Html.fromHtml("I agree to the " +
                    "<a href='http://www.google.com'>Terms and Conditions</a>" + " given"));
            cb_ketentuan.setClickable(true);
            cb_ketentuan.setHighlightColor(R.color.colorPrimary);
            cb_ketentuan.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (language.equals("in")) {
            cb_ketentuan.setText(Html.fromHtml("Setuju dengan " +
                    "<a href='http://www.google.com'>Ketentuan Penggunaan</a>" + " dari KES untuk membuat akun baru"));
            cb_ketentuan.setClickable(true);
            cb_ketentuan.setHighlightColor(R.color.colorPrimary);
            cb_ketentuan.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
}
