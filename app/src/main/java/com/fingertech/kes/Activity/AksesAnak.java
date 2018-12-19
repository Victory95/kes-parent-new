package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import butterknife.ButterKnife;
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
    Integer kosong = 0;
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

    List<String> SOME_HARDCODED_DATA;
    List<String> LISTD_CHECK_NIK;

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

        et_disableFocus();

        btn_minta_kode_akses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                submitForm();
            }
        });

        //////// Camera scan nik
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AksesAnak.this, CameraScanning.class);
                startActivityForResult(i, 1);
            }
        });

        floating_search_view.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {
                if (!oldQuery.equals("") && newQuery.equals("")) {
                    floating_search_view.clearSuggestions();
                    kosong = 0;
                } else {
                    floating_search_view.showProgress();
                    /////// Json search school
                    String key = floating_search_view.getQuery();
                    Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key.toString());
                    postCall.enqueue(new Callback<JSONResponse.School>() {
                        @Override
                        public void onResponse(Call<JSONResponse.School> call, Response<JSONResponse.School> response) {
                            Log.d("TAG",response.code()+"");

                            JSONResponse.School resource = response.body();
                            status = resource.status;
                            code = resource.code;

                            String SS_SCS_0001 = getResources().getString(R.string.SS_SCS_0001);
                            String SS_ERR_0001 = getResources().getString(R.string.SS_ERR_0001);

                            if (status == 1 && code.equals("SS_SCS_0001")) {
                                List<JSONResponse.SData> arrayList = response.body().getData();
                                if (arrayList != null) {
                                    SOME_HARDCODED_DATA = new ArrayList<String>();
                                    for (int i = 0; i < arrayList.size(); i++){
                                        SOME_HARDCODED_DATA.add(arrayList.get(i).getSchool_name());
                                        SOME_HARDCODED_DATA.add(arrayList.get(i).getSchool_code());
                                    }
                                }

                                List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                                for (String item : SOME_HARDCODED_DATA) {
                                    if (item.contains(newQuery.toUpperCase())) {
                                        list.add(new SimpleSuggestions(item));
                                        floating_search_view.hideProgress();
                                    }
                                }
                                floating_search_view.swapSuggestions(list);
                                kosong = 1;

                            } else {
                                if(status == 0 && code.equals("SS_ERR_0001")){
                                    Toast.makeText(getApplicationContext(), SS_ERR_0001, Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        InputFilter[] editFilters = et_nik_niora_siswa.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        et_nik_niora_siswa.setFilters(newFilters);

        et_nik_niora_siswa.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!validateNikNiora()==false) {
                        hideKeyboard(AksesAnak.this);
                        et_nik_niora_siswa.clearFocus();
                        check_student_nik_post();
                        return true;
                    }
                }
                return false;
            } });

    }

    private void submitForm() {
        if (!validateNamaKodeSekolah()) {
            return;
        }
        if (!validateNikNiora()) {
            return;
        }
        tv_val_nama_kodes.setVisibility(View.GONE);
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
    @SuppressLint("ClickableViewAccessibility")
    private void et_disableFocus(){
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
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
    public void check_student_nik_post(){
        String parent_id = "6891";
        String children_nik = "5555666677778888";
        String school_code = "BPK01";
        progressBar();
        showDialog();
        Call<JSONResponse.Check_Student_NIK> postCall = mApiInterface.check_student_nik_post(authorization.toString(),parent_id.toString(), et_nik_niora_siswa.getText().toString(), school_code.toString());
        postCall.enqueue(new Callback<JSONResponse.Check_Student_NIK>() {
            @Override
            public void onResponse(Call<JSONResponse.Check_Student_NIK> call, Response<JSONResponse.Check_Student_NIK> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse.Check_Student_NIK resource = response.body();
                status = resource.status;
                code = resource.code;

                String CSN_SCS_0001 = getResources().getString(R.string.CSN_SCS_0001);
                String CSN_ERR_0001 = getResources().getString(R.string.CSN_ERR_0001);
                String CSN_ERR_0002 = getResources().getString(R.string.CSN_ERR_0002);

                if (status == 1 && code.equals("CSN_SCS_0001")) {
                    List<JSONResponse.CSNIK_Data> arrayList = response.body().getData();
                    if (arrayList != null) {
                        LISTD_CHECK_NIK = new ArrayList<String>();
                        for (int i = 0; i < arrayList.size(); i++){
                            LISTD_CHECK_NIK.add(arrayList.get(i).getFullname());
                        }
                    }
                    Toast.makeText(getApplicationContext(), String.valueOf(LISTD_CHECK_NIK), Toast.LENGTH_LONG).show();
                } else {
                    if(status == 0 && code.equals("CSN_ERR_0001")){
                        Toast.makeText(getApplicationContext(), CSN_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                    if(status == 0 && code.equals("CSN_ERR_0002")){
                        Toast.makeText(getApplicationContext(), CSN_ERR_0002, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.Check_Student_NIK> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

}
