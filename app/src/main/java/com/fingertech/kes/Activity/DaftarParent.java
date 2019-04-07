package com.fingertech.kes.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Controller.Auth;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarParent extends AppCompatActivity {
    private Button btn_buat_akun, btn_ayah, btn_ibu, btn_wali;
    private TextInputLayout til_fullname, til_nik, til_email, til_mobile_phone, til_password, til_ulangi_password;
    private EditText et_fullname, et_nik, et_email, et_mobile_phone, et_password, et_ulangi_password;
    private CheckBox cb_ketentuan;
    private RadioButton rb_laki_laki, rb_perempuan;
    private RadioGroup rg_hubungan;
    private ImageView iv_camera;
    private TextView tv_line_boundaryLeft, tv_line_boundaryRight, tv_hubungan_validate;
    private ProgressDialog dialog;
    private String deviceid;
    int status;
    String code, hubungan = "", jenis_kelamin = "";
    private static final int PERMISSION_REQUEST_CODE = 1;
    Auth mApiInterface;
    ConnectivityManager conMgr;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_parent);
        getSupportActionBar().setElevation(0);

        btn_buat_akun          = (Button) findViewById(R.id.btn_buat_akun);
        btn_ayah               = (Button) findViewById(R.id.btn_ayah);
        btn_ibu                = (Button) findViewById(R.id.btn_ibu);
        btn_wali               = (Button) findViewById(R.id.btn_wali);
        rg_hubungan            = (RadioGroup) findViewById(R.id.rg_hubungan);
        rb_laki_laki           = (RadioButton) findViewById(R.id.rb_laki_laki);
        rb_perempuan           = (RadioButton) findViewById(R.id.rb_perempuan);
        cb_ketentuan           = (CheckBox) findViewById(R.id.cb_ketentuan);
        et_fullname            = (EditText) findViewById(R.id.et_nama_lengkap);
        et_nik                 = (EditText) findViewById(R.id.et_nik);
        et_email               = (EditText) findViewById(R.id.et_email);
        et_mobile_phone        = (EditText) findViewById(R.id.et_number_phone);
        et_password            = (EditText) findViewById(R.id.et_kata_sandi);
        et_ulangi_password     = (EditText) findViewById(R.id.et_ulangi_kata_sandi);
        til_fullname           = (TextInputLayout) findViewById(R.id.til_nama_lengkap);
        til_nik                = (TextInputLayout) findViewById(R.id.til_nik);
        til_email              = (TextInputLayout) findViewById(R.id.til_email);
        til_mobile_phone       = (TextInputLayout) findViewById(R.id.til_number_phone);
        til_password           = (TextInputLayout) findViewById(R.id.til_kata_sandi);
        til_ulangi_password    = (TextInputLayout) findViewById(R.id.til_ulangi_kata_sandi);
        tv_line_boundaryLeft   = (TextView) findViewById(R.id.tv_line_boundaryLeft);
        tv_line_boundaryRight  = (TextView) findViewById(R.id.tv_line_boundaryRight);
        tv_hubungan_validate   = (TextView) findViewById(R.id.tv_hubungan_validate);
        iv_camera = (ImageView) findViewById(R.id.iv_camera);
        //// Caps Text in First Alfabet
        et_fullname.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        checkInternetCon();
        mApiInterface = ApiClient.getClient().create(Auth.class);

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(DaftarParent.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DaftarParent.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(DaftarParent.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        //// CheckBox persetujuan
        getCb_ketentuan();

        //// nik allcaps
        InputFilter[] editFilters = et_nik.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        et_nik.setFilters(newFilters);

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DaftarParent.this, CameraScanning.class);
                startActivityForResult(i, 1);
            }
        });

        btn_ayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                btn_ayah.setBackground(ContextCompat.getDrawable(DaftarParent.this, R.drawable.rectangle_line));
                btn_ayah.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

                ////// deactive
                btn_ibu.setBackgroundColor(Color.TRANSPARENT);
                btn_ibu.setTextColor(getResources().getColor(R.color.textColor_Grey));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.textColor_Grey));

                btn_wali.setBackgroundColor(Color.TRANSPARENT);
                btn_wali.setTextColor(getResources().getColor(R.color.textColor_Grey));
                rg_hubungan.setVisibility(View.GONE);
                hubungan = getResources().getString(R.string.hub_ayah);
                tv_hubungan_validate.setVisibility(View.GONE);
            }
        });

        btn_ibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                btn_ibu.setBackground(ContextCompat.getDrawable(DaftarParent.this, R.drawable.rectangle_line));
                btn_ibu.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

                ////// deactive
                btn_ayah.setBackgroundColor(Color.TRANSPARENT);
                btn_ayah.setTextColor(getResources().getColor(R.color.textColor_Grey));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.default_background));

                btn_wali.setBackgroundColor(Color.TRANSPARENT);
                btn_wali.setTextColor(getResources().getColor(R.color.textColor_Grey));
                rg_hubungan.setVisibility(View.GONE);
                hubungan = getResources().getString(R.string.hub_ibu);
                tv_hubungan_validate.setVisibility(View.GONE);
            }
        });

        btn_wali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                btn_wali.setBackground(ContextCompat.getDrawable(DaftarParent.this, R.drawable.rectangle_line));
                btn_wali.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.textColor_Grey));

                ////// deactive
                btn_ayah.setBackgroundColor(Color.TRANSPARENT);
                btn_ayah.setTextColor(getResources().getColor(R.color.textColor_Grey));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.default_background));

                btn_ibu.setBackgroundColor(Color.TRANSPARENT);
                btn_ibu.setTextColor(getResources().getColor(R.color.textColor_Grey));
                rg_hubungan.setVisibility(View.VISIBLE);
                hubungan = getResources().getString(R.string.hub_wali);
                tv_hubungan_validate.setVisibility(View.GONE);
            }
        });

        rb_laki_laki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_kelamin = getResources().getString(R.string.rb_laki_laki);
            }
        });

        rb_perempuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_kelamin = getResources().getString(R.string.rb_perempuan);
            }
        });

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
        if (!validateNik()) {
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
        if(hubungan.isEmpty()){
            tv_hubungan_validate.setVisibility(View.VISIBLE);
        }else {
            tv_hubungan_validate.setVisibility(View.GONE);
            if(cb_ketentuan.isChecked()) {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(DaftarParent.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                deviceid = tm.getDeviceId();
                register_orangtua_post();
            }else{
                Toast.makeText(DaftarParent.this, getResources().getString(R.string.tcb_ketentuan), Toast.LENGTH_SHORT).show();
            }
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
    private boolean validateNik() {
        if (et_nik.getText().toString().trim().isEmpty()) {
            til_nik.setError(getResources().getString(R.string.validate_nik_niora_anak));
            requestFocus(et_nik);
            return false;
        } else {
            til_nik.setErrorEnabled(false);
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
        dialog = new ProgressDialog(DaftarParent.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DaftarParent.this, OpsiDaftar.class);
        finish();
        startActivity(intent);
    }

    public void register_orangtua_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> postCall = mApiInterface.register_orangtua_post(et_fullname.getText().toString(), et_nik.getText().toString(), et_email.getText().toString(), et_mobile_phone.getText().toString(), et_password.getText().toString(), hubungan.toString(), jenis_kelamin.toString(), device_id.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String RO_SCS_0001 = getResources().getString(R.string.RO_SCS_0001);
                    String RO_ERR_0001 = getResources().getString(R.string.RO_ERR_0001);
                    String RO_ERR_0002 = getResources().getString(R.string.RO_ERR_0002);
                    String RO_ERR_0003 = getResources().getString(R.string.RO_ERR_0003);
                    String RO_ERR_0004 = getResources().getString(R.string.RO_ERR_0004);
                    String RO_ERR_0005 = getResources().getString(R.string.RO_ERR_0005);
                    String RO_ERR_0006 = getResources().getString(R.string.RO_ERR_0006);
                    String RO_ERR_0007 = getResources().getString(R.string.RO_ERR_0007);
                    String RO_ERR_0008 = getResources().getString(R.string.RO_ERR_0008);
                    String RO_ERR_0009 = getResources().getString(R.string.RO_ERR_0009);
                    String RO_ERR_0010 = getResources().getString(R.string.RO_ERR_0010);
                    String RO_ERR_0011 = getResources().getString(R.string.RO_ERR_0011);

                    if (status == 1 && code.equals("RO_SCS_0001")) {
                        Toast.makeText(getApplicationContext(), RO_SCS_0001, Toast.LENGTH_LONG).show();
                        et_fullname.setText("");
                        et_nik.setText("");
                        et_email.setText("");
                        et_mobile_phone.setText("");
                        et_password.setText("");
                        et_ulangi_password.setText("");
                        getRefreshHub();
                        jenis_kelamin = "";
                        if (cb_ketentuan.isChecked()) {
                            cb_ketentuan.toggle();
                        }
                        Intent intent = new Intent(DaftarParent.this, OpsiMasuk.class);
                        startActivity(intent);
                    } else {
                        if (status == 0 && code.equals("RO_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0005")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0005, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0006")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0006, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0007")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0007, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0008")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0008, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0009")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0009, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0010")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0010, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RO_ERR_0011")) {
                            Toast.makeText(getApplicationContext(), RO_ERR_0011, Toast.LENGTH_LONG).show();
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
    public void getCb_ketentuan(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            cb_ketentuan.setText(Html.fromHtml("I agree to the " +
                    "<a href='http://www.kes.co.id'>Terms and Conditions</a>" + " given"));
            cb_ketentuan.setClickable(true);
            cb_ketentuan.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (language.equals("in")) {
            cb_ketentuan.setText(Html.fromHtml("Setuju dengan " +
                    "<a href='http://www.kes.co.id'>Ketentuan Penggunaan</a>" + " dari KES untuk membuat akun baru"));
            cb_ketentuan.setClickable(true);
            cb_ketentuan.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }
    public void getRefreshHub(){
        ////// deactive
        btn_ayah.setBackgroundColor(Color.TRANSPARENT);
        btn_ayah.setTextColor(getResources().getColor(R.color.textColor_Grey));
        tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.textColor_Grey));

        btn_ibu.setBackgroundColor(Color.TRANSPARENT);
        btn_ibu.setTextColor(getResources().getColor(R.color.textColor_Grey));

        tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.textColor_Grey));
        btn_wali.setBackgroundColor(Color.TRANSPARENT);
        btn_wali.setTextColor(getResources().getColor(R.color.textColor_Grey));
        rg_hubungan.setVisibility(View.GONE);
        hubungan = "";
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("key_nik");
                et_nik.setText(strEditText);
            }
        }
    }
}

