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
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Util.JWTUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OpsiDaftar extends AppCompatActivity {
    private TextView tv_masuk;
    private Button btn_google,btn_facebook,btn_email,btn_orang_tua;
    private LoginButton loginButton;
    private ProgressDialog dialog;

    int status;
    String code;
    String id, email, member_id, fullname, member_type, token, deviceid;
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

    Auth mApiInterface;
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
        setContentView(R.layout.opsi_daftar);
        getSupportActionBar().setElevation(0);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        ///// checking internet connection
        checkInternetCon();

        tv_masuk       =(TextView)findViewById(R.id.tv_Masuk);
        btn_google     =(Button)findViewById(R.id.btn_Google);
        btn_facebook   =(Button)findViewById(R.id.btn_Facebook);
        btn_email      =(Button)findViewById(R.id.btn_Email);
        btn_orang_tua  =(Button)findViewById(R.id.btn_Orangtua);
        loginButton    =(LoginButton)findViewById(R.id.login_button);
        sign_in_button =(SignInButton)findViewById(R.id.sign_in_button);

        ////// sharedpreferences
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session      = sharedpreferences.getBoolean(session_status, false);
        email        = sharedpreferences.getString(TAG_EMAIL, null);
        member_id    = sharedpreferences.getString(TAG_MEMBER_ID, null);
        fullname     = sharedpreferences.getString(TAG_FULLNAME, null);
        member_type  = sharedpreferences.getString(TAG_MEMBER_TYPE, null);
        token        = sharedpreferences.getString(TAG_TOKEN, null);

        ////// check permission READ_PHONE_STATE for deviceid[imei] smartphone
        if (ContextCompat.checkSelfPermission(OpsiDaftar.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(OpsiDaftar.this, Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(OpsiDaftar.this, new String[]{Manifest.permission.READ_PHONE_STATE}, PERMISSION_REQUEST_CODE);
            }
        }

        ////// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.Deafult_web_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(OpsiDaftar.this, Arrays.asList("public_profile"));
                loginFacebook();
            }
        });

        btn_orang_tua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DaftarParent.class);
                startActivity(intent);
            }
        });

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
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
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
        progressBar();
        showDialog();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                hideDialog();
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        (object, response) -> {
                            Log.v("LoginActivity", response.toString());
                            //if (Profile.getCurrentProfile()!=null) { Log.v("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200)); }
                            // Application code
                            try {
                                id = object.getString("id");
                                email = object.getString("email");
                                fullname = object.getString("name");
                                getDeviceID();
                                register_sosmed_post();
                            } catch (JSONException e) {
                                e.printStackTrace();
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
                Toast.makeText(OpsiDaftar.this, getResources().getString(R.string.toast_cancel), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                hideDialog();
                Log.d("FB Response :", "Error" + exception);
                Toast.makeText(OpsiDaftar.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
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
        dialog = new ProgressDialog(OpsiDaftar.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void getDeviceID(){
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(OpsiDaftar.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) { return; }
        deviceid = tm.getDeviceId();
    }

    public void register_sosmed_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> postCall = mApiInterface.register_sosmed_post(email.toString(), fullname.toString(), id.toString(), device_id.toString(),firebase_token);
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
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void login_sosmed_post(){
        progressBar();
        showDialog();
        String device_id = "android_"+deviceid;
        Call<JSONResponse> postCall = mApiInterface.login_sosmed_post(id.toString(), device_id.toString(),firebase_token);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    token = resource.token;

                    String LS_SCS_0001 = getResources().getString(R.string.LS_SCS_0001);
                    String LS_ERR_0001 = getResources().getString(R.string.LS_ERR_0001);
                    String LS_ERR_0002 = getResources().getString(R.string.LS_ERR_0002);

                    if (status == 1 && code.equals("LS_SCS_0001")) {
                        JSONObject jsonObject = null;
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
                            editor.putString(TAG_TOKEN, token);
                            editor.commit();
                            /// call session
                            Toast.makeText(getApplicationContext(), LS_SCS_0001, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MenuUtama.class);
                            intent.putExtra(TAG_EMAIL, (String) jsonObject.get("email"));
                            intent.putExtra(TAG_MEMBER_ID, (String) jsonObject.get("member_id"));
                            intent.putExtra(TAG_FULLNAME, (String) jsonObject.get("fullname"));
                            intent.putExtra(TAG_MEMBER_TYPE, "6");
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
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Log.d("onFailure",t.toString());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
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
            register_sosmed_post();
        } else {
        }
    }

}
