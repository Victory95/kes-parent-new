package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fingertech.kes.App.AppController;
import com.fingertech.kes.R;
import com.fingertech.kes.Util.Server;
import com.fingertech.kes.Helper.JSONToMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Masuk extends AppCompatActivity {
    private Button btn_masuk, btn_google, btn_facebook;
    private TextView tvb_lupa_pass, tvb_daftar;
    private EditText et_email, et_kata_sandi;
    private TextInputLayout til_email, til_kata_sandi;

    private ProgressDialog dialog;

    int status;
    String code;
    private String authtoken;
    private String deviceid;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ConnectivityManager conMgr;

    private String url = Server.URL + "login";

    private static final String TAG = Masuk.class.getSimpleName();

    private static final String TAG_STATUS = "status";
    private static final String TAG_CODE = "code";
    private static final String TAG_DATA = "data";

    public final static String TAG_MEMBERID  = "memberid";
    public final static String TAG_EMAIL     = "email";
    public final static String TAG_DEVICE_ID = "device_id";
    public final static String TAG_TOKEN = "token";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String memberid, email, device_id;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk);
        getSupportActionBar().setElevation(0);

        ///// check no internet
        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        btn_masuk = (Button) findViewById(R.id.btn_Masuk);
        btn_google = (Button) findViewById(R.id.btn_Google);
        btn_facebook = (Button) findViewById(R.id.btn_Facebook);
        tvb_lupa_pass = (TextView) findViewById(R.id.tvb_lupa_pass);
        tvb_daftar = (TextView) findViewById(R.id.tvb_daftar);
        et_email = (EditText) findViewById(R.id.et_email);
        et_kata_sandi = (EditText) findViewById(R.id.et_kata_sandi);
        til_email = (TextInputLayout) findViewById(R.id.til_email);
        til_kata_sandi = (TextInputLayout) findViewById(R.id.til_kata_sandi);

        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        memberid = sharedpreferences.getString(TAG_MEMBERID, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        device_id = sharedpreferences.getString(TAG_DEVICE_ID, null);
        authtoken = sharedpreferences.getString(TAG_TOKEN, null);

        ////// check and get deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(Masuk.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Masuk.this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(Masuk.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_REQUEST_CODE);
            }
        }
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        deviceid = tm.getDeviceId();

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        tvb_lupa_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //////////// list button opsi masuk
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvb_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpsiDaftar.class);
                startActivity(intent);
                finish();
            }
        });
    }

    ///// check editext
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }
        String email = et_email.getText().toString();
        String password = et_kata_sandi.getText().toString();
        String device_id = deviceid;
        checkLogin(email, password, device_id);
    }
    private boolean validateEmail() {
        String email = et_email.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            til_email.setError("Enter valid email address");
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword() {
        if (et_kata_sandi.getText().toString().trim().isEmpty()) {
            til_kata_sandi.setError("Enter the password");
            requestFocus(et_kata_sandi);
            return false;
        } else {
            til_kata_sandi.setErrorEnabled(false);
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

    private void checkLogin(final String email, final String password, final String device_id) {
        progressBar();
        showDialog();
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    status = jObj.getInt(TAG_STATUS);
                    code = jObj.getString(TAG_CODE);

                    String LP_SCS_0001 = getResources().getString(R.string.LP_SCS_0001);
                    String LP_ERR_0001 = getResources().getString(R.string.LP_ERR_0001);
                    String LP_ERR_0002 = getResources().getString(R.string.LP_ERR_0002);
                    String LP_ERR_0003 = getResources().getString(R.string.LP_ERR_0003);
                    String LP_ERR_0004 = getResources().getString(R.string.LP_ERR_0004);
                    String LP_ERR_0005 = getResources().getString(R.string.LP_ERR_0005);
                    String LP_ERR_0006 = getResources().getString(R.string.LP_ERR_0006);
                    String LP_ERR_0007 = getResources().getString(R.string.LP_ERR_0007);

                    if (status == 1 && code.equals("LP_SCS_0001")){
                        Map<String, Object> token_map = JSONToMap.jsonToMap(jObj.getJSONObject(TAG_DATA));
                        authtoken = (String)token_map.get("token");
                        /// save session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_MEMBERID, memberid);
                        editor.putString(TAG_EMAIL, email);
                        editor.putString(TAG_DEVICE_ID, device_id);
                        editor.putString(TAG_TOKEN, authtoken);
                        editor.commit();
                        /// call session
                        Toast.makeText(getApplicationContext(), LP_SCS_0001, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Masuk.this, MainActivity.class);
                        intent.putExtra(TAG_MEMBERID, memberid);
                        intent.putExtra(TAG_EMAIL, email);
                        intent.putExtra(TAG_DEVICE_ID, device_id);
                        intent.putExtra(TAG_TOKEN, authtoken);
                        finish();
                        startActivity(intent);
                    } else {
                        if(status == 0 && code.equals("LP_ERR_0001")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0001, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0002")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0002, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0003")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0003, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0004")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0004, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0005")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0005, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0006")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0006, Toast.LENGTH_LONG).show();
                        }if(status == 0 && code.equals("LP_ERR_0007")){
                            Toast.makeText(getApplicationContext(), LP_ERR_0007, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "JSON ERROR COY", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Time out : koneksi bermasalah!", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                params.put("device_id", device_id);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
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
        dialog = new ProgressDialog(Masuk.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }


}

