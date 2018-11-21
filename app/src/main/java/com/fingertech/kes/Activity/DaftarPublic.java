package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.fingertech.kes.App.AppController;
import com.fingertech.kes.R;
import com.fingertech.kes.Util.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DaftarPublic extends AppCompatActivity {

    private Button btn_buat_akun;
    private EditText et_fullname,et_email,et_mobile_phone,et_password,et_ulangi_password;
    private CheckBox cb_ketentuan;
    private TextInputLayout til_fullname, til_email, til_mobile_phone, til_password, til_ulangi_password;

    private String deviceid;
    String code;
    ProgressDialog pDialog;
    Dialog progressDialog;
    Intent intent;

    int status;
    ConnectivityManager conMgr;
    private static final int PERMISSION_REQUEST_CODE = 1;

    private String url = Server.URL + "register";

    private static final String TAG = DaftarPublic.class.getSimpleName();
    private static final String TAG_STATUS   = "status";
    private static final String TAG_CODE     = "code";

    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_public);
        getSupportActionBar().setElevation(0);

        ///// check internet connection
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_conn),
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_buat_akun        =(Button)findViewById(R.id.btn_buat_akun);
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

        ////// check and get deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(DaftarPublic.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(DaftarPublic.this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(DaftarPublic.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE);
            }
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(DaftarPublic.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        deviceid = tm.getDeviceId();

        //// CheckBox persetujuan
        cb_ketentuan.setText(Html.fromHtml("Saya setuju dengan " +
                "<a href='http://www.google.com'>Syarat Dan Ketentuan</a>" + " yang di berikan"));
        cb_ketentuan.setClickable(true);
        cb_ketentuan.setMovementMethod(LinkMovementMethod.getInstance());

        btn_buat_akun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaftarPublic alert = new DaftarPublic();
                //alert.showDialog(DaftarPublic.this);
                if(cb_ketentuan.isChecked()) {
                    submitForm();
                }else{
                    Toast.makeText(DaftarPublic.this, getResources().getString(R.string.tcb_ketentuan), Toast.LENGTH_SHORT).show();
                }
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
        String fullname= et_fullname.getText().toString();
        String email = et_email.getText().toString();
        String mobile_phone = et_mobile_phone.getText().toString();
        String password = et_password.getText().toString();
        String device_id = deviceid;
        checkRegister(fullname,email,mobile_phone,password,device_id);
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

    private void checkRegister(final String fullname, final String email, final String mobile_phone, final String password, final String device_id) {
//        pDialog = new ProgressDialog(this);
//        pDialog.setCancelable(false);
//        pDialog.setMessage("Daftar ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Register Response: " + response.toString());
                //hideDialog();

                String RP_SCS_0001 = getResources().getString(R.string.RP_SCS_0001);
                String RP_ERR_0001 = getResources().getString(R.string.RP_ERR_0001);
                String RP_ERR_0002 = getResources().getString(R.string.RP_ERR_0002);
                String RP_ERR_0003 = getResources().getString(R.string.RP_ERR_0003);
                String RP_ERR_0004 = getResources().getString(R.string.RP_ERR_0004);
                String RP_ERR_0005 = getResources().getString(R.string.RP_ERR_0005);
                String RP_ERR_0006 = getResources().getString(R.string.RP_ERR_0006);
                String RP_ERR_0007 = getResources().getString(R.string.RP_ERR_0007);
                String RP_ERR_0008 = getResources().getString(R.string.RP_ERR_0008);

                try {
                    JSONObject jObj = new JSONObject(response);
                    status = jObj.getInt(TAG_STATUS);
                    code = jObj.getString(TAG_CODE);

                    if (status == 1 && code.equals("RP_SCS_0001")) {
                        Toast.makeText(getApplicationContext(), RP_SCS_0001, Toast.LENGTH_LONG).show();
                        et_fullname.setText("");
                        et_email.setText("");
                        et_mobile_phone.setText("");
                        et_password.setText("");
                        et_ulangi_password.setText("");
                    } else {
                        if(status == 0 && code.equals("RP_ERR_0001")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0001, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0002")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0002, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0003")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0003, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0004")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0004, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0005")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0005, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0006")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0006, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0007")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0007, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("RP_ERR_0008")){
                            Toast.makeText(getApplicationContext(), RP_ERR_0008, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Time out : koneksi bermasalah!", Toast.LENGTH_LONG).show();
                //hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fullname", fullname);
                params.put("email", email);
                params.put("mobile_phone", mobile_phone);
                params.put("password", password);
                params.put("device_id", device_id);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

//    public void showDialog(Activity activity){
//        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
//        ImageView iv_kes_loader = (ImageView)
//        dialog.setContentView(R.layout.progress_dialog);
//        Glide.with(DaftarPublic.this)
//                .load(R.drawable.kes_loader)
//                .
//
//        dialog.show();
//    }

    @Override
    public void onBackPressed() {
        intent = new Intent(DaftarPublic.this, Masuk.class);
        finish();
        startActivity(intent);
    }
}
