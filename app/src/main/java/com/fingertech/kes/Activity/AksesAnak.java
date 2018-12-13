package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AksesAnak extends AppCompatActivity {
    private EditText et_nik_niora_siswa;
    private TextInputLayout til_nik_niora_siswa;
    private Button btn_minta_kode_akses;
    private ImageView iv_camera;
    private FloatingSearchView floating_search_view;
    private LinearLayout lay_akses_anak;
    private TextView tv_val_nama_kodes;
    String email, member_id, fullname, member_type, parent_id, student_id, school_id;
    int status;
    String code;
    String authorization;

    private ProgressDialog dialog;
    ConnectivityManager conMgr;
    Auth mApiInterface;

    SharedPreferences sharedpreferences;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";

    Integer kosong = 0;

    //Dummy data for search suggestions
    private static final List<String> SOME_HARDCODED_DATA;
    static {
        SOME_HARDCODED_DATA = new ArrayList<>();
        SOME_HARDCODED_DATA.add("abc");
        SOME_HARDCODED_DATA.add("def");
        SOME_HARDCODED_DATA.add("ghi");
        SOME_HARDCODED_DATA.add("jkl");
        SOME_HARDCODED_DATA.add("mno");
    }

    //This just for illustration how to populate suggestions
    private static class SimpleSuggestions implements SearchSuggestion {
        private final String mData;
        public SimpleSuggestions(String string) {
            mData = string;
        }
        @Override
        public String getBody() {
            return mData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(mData);
        }

        public static final Parcelable.Creator<SimpleSuggestions> CREATOR = new Parcelable.Creator<SimpleSuggestions>() {
            public SimpleSuggestions createFromParcel(Parcel in) {
                return new SimpleSuggestions(in);
            }

            public SimpleSuggestions[] newArray(int size) {
                return new SimpleSuggestions[size];
            }
        };
        private SimpleSuggestions(Parcel in) {
            mData = in.readString();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.akses_anak);
        getSupportActionBar().setElevation(0);
        checkInternetCon();

        et_nik_niora_siswa    = (EditText)findViewById(R.id.et_nik_niora_siswa);
        til_nik_niora_siswa   = (TextInputLayout)findViewById(R.id.til_nik_niora_siswa);
        btn_minta_kode_akses  = (Button)findViewById(R.id.btn_minta_kode_akses);
        iv_camera             = (ImageView) findViewById(R.id.iv_camera);
        floating_search_view  = (FloatingSearchView) findViewById(R.id.floating_search_view);
        lay_akses_anak        = (LinearLayout) findViewById(R.id.lay_akses_anak);
        tv_val_nama_kodes     = (TextView) findViewById(R.id.tv_val_nama_kodes);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        member_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        member_type   = sharedpreferences.getString(TAG_MEMBER_TYPE,"member_type");
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");

        btn_minta_kode_akses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AksesAnak.this, CameraScanning.class);
                startActivityForResult(i, 1);
            }
        });

        //////// Editext disable focus touch screen
        lay_akses_anak.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (et_nik_niora_siswa.isFocused()) {
                        Rect outRect = new Rect();
                        et_nik_niora_siswa.getGlobalVisibleRect(outRect);
                        if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                            et_nik_niora_siswa.clearFocus();
                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                return false;
            }
        });

        floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                //emulating search on dummy data
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    floating_search_view.clearSuggestions();
                    kosong = 0;
                } else {
                    for (String item : SOME_HARDCODED_DATA) {
                        if (item.contains(newQuery)) {
                            list.add(new SimpleSuggestions(item));
                        }
                    }
                    floating_search_view.swapSuggestions(list);
                    kosong = 1;
                }
            }
        });

    }

    private void submitForm() {
        if (!validateNamaKodeSekolah()) {
            return;
        }
        if (!validateNikNiora()) {
            return;
        }
        tv_val_nama_kodes.setVisibility(View.GONE);
        kosong = 0;
//        request_code_acsess_post();
    }
    private boolean validateNamaKodeSekolah() {
        if (kosong == 0) {
            tv_val_nama_kodes.setVisibility(View.VISIBLE);
            return false;
        } else {
            tv_val_nama_kodes.setVisibility(View.GONE);
        }
        return true;
    }
    private boolean validateNikNiora() {
        if (et_nik_niora_siswa.getText().toString().trim().isEmpty()) {
            til_nik_niora_siswa.setError(getResources().getString(R.string.validate_nik_niora_anak));
            requestFocus(et_nik_niora_siswa);
            return false;
        } else {
            til_nik_niora_siswa.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
        dialog = new ProgressDialog(AksesAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("key_nik_akses_anak");
                et_nik_niora_siswa.setText(strEditText);
            }
        }
    }

    ///// Retrofit JSON
    public void request_code_acsess_post(){
        parent_id  = member_id;
        student_id = "12";
        school_id  = "5";
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.request_code_acsess_post(authorization.toString(), email.toString(), fullname.toString(), parent_id.toString(), student_id.toString(), school_id.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String RCA_SCS_0001 = getResources().getString(R.string.RCA_SCS_0001);
                String RCA_ERR_0001 = getResources().getString(R.string.RCA_ERR_0001);
                String RCA_ERR_0002 = getResources().getString(R.string.RCA_ERR_0002);
                String RCA_ERR_0003 = getResources().getString(R.string.RCA_ERR_0003);
                String RCA_ERR_0004 = getResources().getString(R.string.RCA_ERR_0004);
                String RCA_ERR_0005 = getResources().getString(R.string.RCA_ERR_0005);
                String RCA_ERR_0006 = getResources().getString(R.string.RCA_ERR_0006);
                String RCA_ERR_0007 = getResources().getString(R.string.RCA_ERR_0007);
                String RCA_ERR_0008 = getResources().getString(R.string.RCA_ERR_0008);

                if (status == 1 && code.equals("RCA_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), RCA_SCS_0001, Toast.LENGTH_LONG).show();
                    email.equals("");
                    fullname.equals("");
                    parent_id.equals("");
                    student_id.equals("");
                    school_id.equals("");
                } else {
                    if(status == 0 && code.equals("RCA_ERR_0001")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0001, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0002")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0002, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0003")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0003, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0004")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0004, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0005")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0005, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0006")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0006, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0007")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0007, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RCA_ERR_0008")){
                        Toast.makeText(getApplicationContext(), RCA_ERR_0008, Toast.LENGTH_LONG).show();
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
