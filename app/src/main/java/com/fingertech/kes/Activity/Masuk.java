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

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Util.JWTUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Masuk extends AppCompatActivity {
    private Button btn_masuk, btn_google, btn_facebook;
    private TextView tvb_lupa_pass, tvb_daftar;
    private EditText et_email, et_kata_sandi;
    private TextInputLayout til_email, til_kata_sandi;
    private LoginButton loginButton;
    private ProgressDialog dialog;

    int status;
    String code;
    String deviceid;
    String email, member_id, fullname, member_type, token;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ConnectivityManager conMgr;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";

    Auth mApiInterface;
    CallbackManager callbackManager = CallbackManager.Factory.create();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk);
        getSupportActionBar().setElevation(0);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        ///// checking internet connection
        checkInternetCon();

        btn_masuk      = (Button) findViewById(R.id.btn_Masuk);
        btn_google     = (Button) findViewById(R.id.btn_Google);
        btn_facebook   = (Button) findViewById(R.id.btn_Facebook);
        tvb_lupa_pass  = (TextView) findViewById(R.id.tvb_lupa_pass);
        tvb_daftar     = (TextView) findViewById(R.id.tvb_daftar);
        et_email       = (EditText) findViewById(R.id.et_email);
        et_kata_sandi  = (EditText) findViewById(R.id.et_kata_sandi);
        til_email      = (TextInputLayout) findViewById(R.id.til_email);
        til_kata_sandi = (TextInputLayout) findViewById(R.id.til_kata_sandi);
        loginButton    = (LoginButton) findViewById(R.id.login_button);

        ////// sharedpreferences
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session      = sharedpreferences.getBoolean(session_status, false);
        email        = sharedpreferences.getString(TAG_EMAIL, null);
        member_id    = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname     = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type  = sharedpreferences.getString(TAG_MEMBER_TYPE, null);

        /////// Get Token
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            token = accessToken.getToken();
//            Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        }

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Masuk.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(Masuk.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////// get Deviceid
                getDeviceID();
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
//                Toast.makeText(Masuk.this, fullname, Toast.LENGTH_SHORT).show();
//                Toast.makeText(Masuk.this, email, Toast.LENGTH_SHORT).show();
//                Toast.makeText(Masuk.this, token, Toast.LENGTH_SHORT).show();
//                Toast.makeText(Masuk.this, deviceid, Toast.LENGTH_SHORT).show();
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(Masuk.this, Arrays.asList("public_profile"));
                loginFacebook();
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
        login_post();
    }
    //////// validate Editext
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
    //////// Touchscreen disable focus keyobard on Editext
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

    //////// Progressbar Dolog - Loading Animation
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

    //////// Permission APP
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

    public void login_post(){
        progressBar();
        showDialog();
        Call<JSONResponse> call = mApiInterface.login_post(et_email.getText().toString(), et_kata_sandi.getText().toString(), deviceid.toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
//                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                Log.d("TAG",response.code()+"");
                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String LP_SCS_0001 = getResources().getString(R.string.LP_SCS_0001);
                String LP_ERR_0001 = getResources().getString(R.string.LP_ERR_0001);
                String LP_ERR_0002 = getResources().getString(R.string.LP_ERR_0002);
                String LP_ERR_0003 = getResources().getString(R.string.LP_ERR_0003);
                String LP_ERR_0004 = getResources().getString(R.string.LP_ERR_0004);
                String LP_ERR_0005 = getResources().getString(R.string.LP_ERR_0005);
                String LP_ERR_0006 = getResources().getString(R.string.LP_ERR_0006);
                String LP_ERR_0007 = getResources().getString(R.string.LP_ERR_0007);

                if (status == 1 && code.equals("LP_SCS_0001")){
                    JSONResponse.Login_Data data = resource.login_data;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(JWTUtils.decoded(data.token));
                        /// save session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_EMAIL, (String) jsonObject.get("email"));
                        editor.putString(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                        editor.putString(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                        editor.putString(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                        editor.commit();
                        /// call session
                        Toast.makeText(getApplicationContext(), LP_SCS_0001, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Masuk.this, MainActivity.class);
                        intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                        intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                        intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                        intent.putExtra(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                        finish();
                        startActivity(intent);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
    public void loginFacebook(){
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
//                                if (Profile.getCurrentProfile()!=null) { Log.v("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200)); }
                                // Application code
                                try {
                                    email = object.getString("email");
                                    fullname = object.getString("name");
                                    getDeviceID();
                                    register_sosmed_post();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(Masuk.this, "Batalkan euyyyyy", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("FB Response :", "Error" + exception);
                Toast.makeText(Masuk.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void register_sosmed_post(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.register_sosmed_post(fullname.toString(), email.toString(), token.toString(), deviceid.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                JSONResponse.Token_Data tokendata = resource.token_data;


//                JSONObject jsonObject = null;
//                try {
//                    jsonObject = new JSONObject(JWTUtils.decoded(token));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


//                JSONResponse.Token_Data tokendata = resource.token;
                Log.v("JSONObject", tokendata.toString());
//                Toast.makeText(Masuk.this, (CharSequence) jsonObject, Toast.LENGTH_SHORT).show();

                String RS_SCS_0001 = getResources().getString(R.string.RS_SCS_0001);
                String RS_ERR_0001 = getResources().getString(R.string.RS_ERR_0001);
                String RS_ERR_0002 = getResources().getString(R.string.RS_ERR_0002);
                String RS_ERR_0003 = getResources().getString(R.string.RS_ERR_0003);
                String RS_ERR_0004 = getResources().getString(R.string.RS_ERR_0004);
                String RS_ERR_0005 = getResources().getString(R.string.RS_ERR_0005);
                String RS_ERR_0006 = getResources().getString(R.string.RS_ERR_0006);
                String RS_ERR_0007 = getResources().getString(R.string.RS_ERR_0007);

//                if (status == 1 && code.equals("RO_SCS_0001")) {
//                    JSONResponse.Token_Data tokendata = resource.token_data;
//                    token = tokendata;
//                    JSONObject jsonObject = null;
//                    try {
//                        jsonObject = new JSONObject(JWTUtils.decoded(tokendata));
//                        /// save session
//                        Toast.makeText(Masuk.this, (CharSequence) jsonObject, Toast.LENGTH_SHORT).show();
////                        SharedPreferences.Editor editor = sharedpreferences.edit();
////                        editor.putBoolean(session_status, true);
////                        editor.putString(TAG_EMAIL, (String) jsonObject.get("email"));
////                        editor.putString(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
////                        editor.putString(TAG_FULLNAME, (String) jsonObject.get("fullname"));
////                        editor.putString(TAG_MEMBER_TYPE, "6");
////                        editor.commit();
////                        /// call session
////                        Toast.makeText(getApplicationContext(), RS_SCS_0001, Toast.LENGTH_LONG).show();
////                        Intent intent = new Intent(Masuk.this, MainActivity.class);
////                        intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
////                        intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
////                        intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
////                        intent.putExtra(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
////                        finish();
////                        startActivity(intent);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
////                    fullname.equals("");
////                    email.equals("");
////                    token.equals("");
//
//                } else {
//                    if(status == 0 && code.equals("RS_ERR_0001")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0001, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0002")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0002, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0003")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0003, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0004")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0004, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0005")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0005, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0006")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0006, Toast.LENGTH_LONG).show();
//                    }if(status == 0 && code.equals("RS_ERR_0007")){
//                        Toast.makeText(getApplicationContext(), RS_ERR_0007, Toast.LENGTH_LONG).show();
//                    }
//                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public  void getDeviceID(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { return; }
        deviceid = tm.getDeviceId();
    }
}

