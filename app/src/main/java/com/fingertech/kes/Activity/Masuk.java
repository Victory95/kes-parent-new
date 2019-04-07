package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
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
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fingertech.kes.Activity.Search.AnakAkses;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Util.JWTUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import com.facebook.ProfileTracker;
import com.facebook.AccessTokenTracker;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shashank.sony.fancytoastlib.FancyToast;

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
    String count_student;
    String code;
    Date currentTime;
    String id, email, member_id, fullname, member_type, token, deviceid,parent_nik,lastlogin,image_google;
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
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_PARENT_NIK   = "parent_nik";
    public static final String TAG_LASTLOGIN    = "last_login";
    public static final String TAG_COUNT        = "count_children";
    public static final String TAG_PHOTO        = "foto_profile";



    Auth mApiInterface;
    String password,last_login;
    int min,second,year,month,date,jam;
    CallbackManager callbackManager = CallbackManager.Factory.create();

    private static final String TAG = "MainActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton sign_in_button;
    String firebase_token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masuk);
        getSupportActionBar().setElevation(0);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        ///// checking internet connection
        checkInternetCon();


        btn_masuk      = findViewById(R.id.btn_Masuk);
        btn_google     = findViewById(R.id.btn_Google);
        btn_facebook   = findViewById(R.id.btn_Facebook);
        tvb_lupa_pass  = findViewById(R.id.tvb_lupa_pass);
        tvb_daftar     = findViewById(R.id.tvb_daftar);
        et_email       = findViewById(R.id.et_email);
        et_kata_sandi  = findViewById(R.id.et_kata_sandi);
        til_email      = findViewById(R.id.til_email);
        til_kata_sandi = findViewById(R.id.til_kata_sandi);
        loginButton    = findViewById(R.id.login_button);
        sign_in_button = findViewById(R.id.sign_in_button);

        ////// sharedpreferences
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session      = sharedpreferences.getBoolean(session_status, false);
        email        = sharedpreferences.getString(TAG_EMAIL, null);
        member_id    = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname     = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type  = sharedpreferences.getString(TAG_MEMBER_TYPE, null);
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,null);
        token        = sharedpreferences.getString(TAG_TOKEN, null);
        lastlogin    = sharedpreferences.getString(TAG_LASTLOGIN,null);
        count_student= sharedpreferences.getString(TAG_COUNT,null);

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Masuk.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(Masuk.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        String webClientId = getString(R.string.Deafult_web_id);

        ////// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(webClientId)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();

        btn_masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceID();
                submitForm();
            }
        });

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.fingertech.kes",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("nama not found : ", ""+e.fillInStackTrace());
        } catch (NoSuchAlgorithmException e) {
            Log.d("gala not found : ", ""+e.fillInStackTrace());
        }
        tvb_lupa_pass.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ForgotPassword.class);
            startActivity(intent);
        });

        //////////// list button opsi masuk
        btn_google.setOnClickListener(v -> signIn());

        btn_facebook.setOnClickListener(v -> {

            LoginManager.getInstance().logInWithReadPermissions(Masuk.
                    this,
                    Arrays.asList("email", "public_profile"));

            loginFacebook();
        });

        tvb_daftar.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), OpsiDaftar.class);
            startActivity(intent);
            finish();
        });

        DateFormat df = new SimpleDateFormat("EEEEEE, dd MMM yyyy, HH:mm");
        last_login = df.format(Calendar.getInstance().getTime());

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("coba", "getInstanceId failed", task.getException());
                        return;
                    }

                    // Get new Instance ID token
                    firebase_token = task.getResult().getToken();

                    // Log and toast
                    String msg = getString(R.string.msg_token_fmt, token);
                    Log.d("Token", msg);
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
            Toast.makeText(getApplicationContext(),"Email Tidak sesuai",Toast.LENGTH_LONG).show();
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validatePassword() {
        if (et_kata_sandi.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Masukan kata sandi anda",Toast.LENGTH_LONG).show();
            requestFocus(et_kata_sandi);
            return false;
        }else if(et_kata_sandi.length()<6) {
            Toast.makeText(getApplicationContext(),"Minimal 6 karakter",Toast.LENGTH_LONG).show();
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
        String device_id = "android_"+deviceid;
        Call<JSONResponse> call = mApiInterface.login_post(et_email.getText().toString(), et_kata_sandi.getText().toString(), device_id,firebase_token);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
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

                    if (status == 1 && code.equals("LP_SCS_0001")) {
                        JSONResponse.Login_Data data = resource.login_data;
                        JSONObject jsonObject = null;
                        try {
                            token = data.token;
                            parent_nik = data.parent_nik;
                            count_student = data.count_children;
                            jsonObject = new JSONObject(JWTUtils.decoded(token));
                            /// save session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_EMAIL, (String) jsonObject.get("email"));
                            editor.putString(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            editor.putString(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            editor.putString(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                            editor.putString(TAG_PARENT_NIK, parent_nik);
                            editor.putString(TAG_COUNT, count_student);
                            editor.putString(TAG_TOKEN, token);
                            editor.putString(TAG_LASTLOGIN, last_login);
                            editor.commit();
                            /// call session
                            if (jsonObject.get("member_type").toString().equals("6")) {
                                Toast.makeText(getApplicationContext(), LP_SCS_0001, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Masuk.this, MenuUtama.class);
                                intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                                intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                                intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                                intent.putExtra(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                                intent.putExtra(TAG_TOKEN, token);
                                intent.putExtra(TAG_LASTLOGIN, last_login);
                                finish();
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), LP_SCS_0001, Toast.LENGTH_LONG).show();
                                if (count_student.equals("0")) {
                                    Intent intent = new Intent(Masuk.this, AnakAkses.class);
                                    intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                                    intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                                    intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                                    intent.putExtra(TAG_PARENT_NIK, parent_nik);
                                    intent.putExtra(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                                    intent.putExtra(TAG_LASTLOGIN, last_login);
                                    intent.putExtra(TAG_COUNT, count_student);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                } else {
                                    Intent intent = new Intent(Masuk.this, MenuUtama.class);
                                    intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                                    intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                                    intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                                    intent.putExtra(TAG_PARENT_NIK, parent_nik);
                                    intent.putExtra(TAG_MEMBER_TYPE, (String) jsonObject.get("member_type"));
                                    intent.putExtra(TAG_LASTLOGIN, last_login);
                                    intent.putExtra(TAG_COUNT, count_student);
                                    intent.putExtra(TAG_TOKEN, token);
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (status == 0 && code.equals("LP_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0005")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0005, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0006")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0006, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LP_ERR_0007")) {
                            Toast.makeText(getApplicationContext(), LP_ERR_0007, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
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
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_internet_con), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void loginFacebook(){
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };
        ProfileTracker profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

            }
        };
        progressBar();
        showDialog();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                hideDialog();
                AccessToken accessToken = loginResult.getAccessToken();
                Profile profile = Profile.getCurrentProfile();
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.v("LoginActivity", response.toString());
                            //if (Profile.getCurrentProfile()!=null) { Log.v("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200)); }
                            // Application code
                            Log.i(TAG, "LoginButton FacebookCallback onSuccess");
                            AccessToken accessToken1 = AccessToken.getCurrentAccessToken();
                            boolean isLoggedIn = accessToken1 != null && !accessToken1.isExpired();
                            if(isLoggedIn ){

                            try {

                                Log.d(id, "id");
                                id = object.getString("id");
                                email = object.getString("email");
                                fullname = object.getString("name");
                                getDeviceID();
                                register_sosmed_post();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }});
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                hideDialog();
                Toast.makeText(Masuk.this, getResources().getString(R.string.toast_cancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                hideDialog();
                Log.d("FB Response :", "Error" + exception);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        //////// Google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
//                Log.w(TAG,e.toString());
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }



    public void getDeviceID(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(Masuk.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { return; }
        deviceid = tm.getDeviceId();
        Log.d("device_id",deviceid);
    }

    public void register_sosmed_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> postCall = mApiInterface.register_sosmed_post(email, fullname, id, device_id,firebase_token);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();

                    status = resource.status;
                    code = resource.code;
                    token = resource.token;

                    String RS_SCS_0001 = getResources().getString(R.string.RS_SCS_0001);
                    String RS_ERR_0001 = getResources().getString(R.string.RS_ERR_0001);
                    String RS_ERR_0002 = getResources().getString(R.string.RS_ERR_0002);
                    String RS_ERR_0007 = getResources().getString(R.string.RS_ERR_0003);
                    String RS_ERR_0003 = getResources().getString(R.string.RS_ERR_0007);
                    String RS_ERR_0004 = getResources().getString(R.string.RS_ERR_0004);
                    String RS_ERR_0005 = getResources().getString(R.string.RS_ERR_0005);
                    String RS_ERR_0006 = getResources().getString(R.string.RS_ERR_0006);

                    if (status == 1 && code.equals("RS_SCS_0001")) {
                        Toast.makeText(getApplicationContext(), RS_SCS_0001, Toast.LENGTH_LONG).show();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(JWTUtils.decoded(token));
                            //// save session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_EMAIL, (String) jsonObject.get("email"));
                            editor.putString(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            editor.putString(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            editor.putString(TAG_MEMBER_TYPE, "6");
                            editor.putString(TAG_PHOTO, image_google);
                            editor.putString(TAG_TOKEN, token);
                            editor.commit();
                            /// call session
                            Toast.makeText(getApplicationContext(), RS_SCS_0001, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MenuUtama.class);
                            intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                            intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            intent.putExtra(TAG_MEMBER_TYPE, "6");
                            intent.putExtra(TAG_TOKEN, token);
                            intent.putExtra(TAG_PHOTO, image_google);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (status == 0 && code.equals("RS_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0007")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0007, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0003")) {
                            login_sosmed_post();
//                        Toast.makeText(getApplicationContext(), RS_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0005")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0005, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("RS_ERR_0006")) {
                            Toast.makeText(getApplicationContext(), RS_ERR_0006, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
                Log.e("gagal",t.toString());
            }
        });
    }
    public void login_sosmed_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> postCall = mApiInterface.login_sosmed_post(id, device_id,firebase_token);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;
//                token = resource.token;
                    String LS_SCS_0001 = getResources().getString(R.string.LS_SCS_0001);
                    String LS_ERR_0001 = getResources().getString(R.string.LS_ERR_0001);
                    String LS_ERR_0002 = getResources().getString(R.string.LS_ERR_0002);

                    if (status == 1 && code.equals("LS_SCS_0001")) {
                        JSONObject jsonObject = null;
                        JSONResponse.Login_Data login_data = response.body().login_data;
                        token = login_data.token;
                        Toast.makeText(getApplicationContext(), LS_SCS_0001, Toast.LENGTH_LONG).show();
                        try {

                            jsonObject = new JSONObject(JWTUtils.decoded(token));
                            //// save session
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putBoolean(session_status, true);
                            editor.putString(TAG_EMAIL, (String) jsonObject.get("email"));
                            editor.putString(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            editor.putString(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            editor.putString(TAG_MEMBER_TYPE, "6");
//                        editor.putString(TAG_PHOTO,image_google);
                            editor.putString(TAG_TOKEN, token);
                            editor.commit();
                            /// call session
                            Toast.makeText(getApplicationContext(), LS_SCS_0001, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MenuUtama.class);
                            intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                            intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            intent.putExtra(TAG_MEMBER_TYPE, "6");
//                        intent.putExtra(TAG_PHOTO,image_google);
                            intent.putExtra(TAG_TOKEN, token);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (status == 0 && code.equals("LS_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), LS_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("LS_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), LS_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
                Log.e("gagal",t.toString());
            }
        });
    }

    /////// Login with Google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        progressBar();
        showDialog();
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        hideDialog();
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        hideDialog();
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Login Failed: ", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            email = user.getEmail();
            fullname = user.getDisplayName();
            id = user.getUid();
            getDeviceID();
            image_google = String.valueOf(user.getPhotoUrl());
            register_sosmed_post();
            // Loading profile image
//            Uri profilePicUrl = user.getPhotoUrl();
//            if (profilePicUrl != null) { Glide.with(this).load(profilePicUrl).into(profileImage); }
        } else {
        }
    }

}

