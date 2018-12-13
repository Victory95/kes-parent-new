package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    private Button btn_ubah_pass;
    private EditText et_email;
    private TextInputLayout til_email;

    int status;
    String code;
    Auth mApiInterface;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);
        getSupportActionBar().setElevation(0);

        et_email      = (EditText)findViewById(R.id.et_email);
        til_email     = (TextInputLayout)findViewById(R.id.til_email);
        btn_ubah_pass = (Button) findViewById(R.id.btn_ubah_pass);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        btn_ubah_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }
        forgot_password_post();
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
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
        dialog = new ProgressDialog(ForgotPassword.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
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

    public void forgot_password_post(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.forgot_password_post(et_email.getText().toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String FP_SCS_0001 = getResources().getString(R.string.FP_SCS_0001);
                String FP_ERR_0001 = getResources().getString(R.string.FP_ERR_0001);
                String FP_ERR_0002 = getResources().getString(R.string.FP_ERR_0002);
                String FP_ERR_0003 = getResources().getString(R.string.FP_ERR_0003);

                if (status == 1 && code.equals("FP_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), FP_SCS_0001, Toast.LENGTH_LONG).show();
                    et_email.setText("");
                } else {
                    if(status == 0 && code.equals("FP_ERR_0001")){
                        Toast.makeText(getApplicationContext(), FP_ERR_0001, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("FP_ERR_0002")){
                        Toast.makeText(getApplicationContext(), FP_ERR_0002, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("FP_ERR_0003")){
                        Toast.makeText(getApplicationContext(), FP_ERR_0003, Toast.LENGTH_LONG).show();
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
}
