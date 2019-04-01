package com.fingertech.kes.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.facebook.login.LoginManager;
import com.fingertech.kes.R;
import com.fingertech.kes.Service.Common;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;
    TextView tv_memberid, tv_email, tv_fullname,tv_member_type,tv_token;
    String email, member_id, fullname, member_type,token;
    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

        ///////////////////// Message Firebase
//        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
//        Log.d("Token", Common.currentToken);

        tv_email       = (TextView) findViewById(R.id.tv_email);
        tv_memberid    = (TextView) findViewById(R.id.tv_memberid);
        tv_fullname    = (TextView) findViewById(R.id.tv_fullname);
        tv_member_type = (TextView) findViewById(R.id.tv_member_type);
        tv_token       = (TextView) findViewById(R.id.tv_token);
        btn_logout     = (Button) findViewById(R.id.btn_logout);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        email       = sharedpreferences.getString(TAG_EMAIL,"email");
        member_id   = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        fullname    = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        member_type = sharedpreferences.getString(TAG_MEMBER_TYPE,"member_type");
        token       = sharedpreferences.getString(TAG_TOKEN,"token");

        tv_email.setText("EMAIL : " + email);
        tv_memberid.setText("MEMBERID : " + member_id);
        tv_fullname.setText("FULLNAME : " + fullname);
        tv_member_type.setText("MEMBER_TYPE : " + member_type);
        tv_token.setText("Token : " + token);

        ////// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putBoolean(Masuk.session_status, false);
                editor.putString(TAG_EMAIL, null);
                editor.putString(TAG_MEMBER_ID, null);
                editor.putString(TAG_FULLNAME, null);
                editor.putString(TAG_MEMBER_TYPE, null);
                editor.putString(TAG_TOKEN, null);
                editor.commit();

                /////// Logout Facebook
                LoginManager.getInstance().logOut();


                ///// Google Logout
                mAuth.signOut();
                mGoogleSignInClient.signOut().addOnCompleteListener(MainActivity.this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                FirebaseUser user = null;
                            }
                        });

                Intent intent = new Intent(MainActivity.this, OpsiMasuk.class);
                finish();
                startActivity(intent);
            }
        });
    }



}
