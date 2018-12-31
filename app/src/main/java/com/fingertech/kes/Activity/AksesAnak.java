package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import java.util.Locale;

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
    private TextView tv_val_nama_kodes,tv_val_nama_kodes_join,tv_info_nama_anak,tv_nama_anak;
    private ProgressDialog dialog;
    String email, member_id, fullname, member_type, student_id, student_nik, school_id,school_name,school_code;
    int status;
    String code;
    Integer kosong = 0,status_nik =0;
    String authorization;

    ConnectivityManager conMgr;
    Auth mApiInterface;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    SharedPreferences sharedpreferences;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";

    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_STUDNET_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";

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

        et_nik_niora_siswa     = (EditText)findViewById(R.id.et_nik_niora_siswa);
        til_nik_niora_siswa    = (TextInputLayout)findViewById(R.id.til_nik_niora_siswa);
        btn_minta_kode_akses   = (Button)findViewById(R.id.btn_minta_kode_akses);
        iv_camera              = (ImageView) findViewById(R.id.iv_camera);
        floating_search_view   = (FloatingSearchView) findViewById(R.id.floating_search_view);
        lay_akses_anak         = (LinearLayout) findViewById(R.id.lay_akses_anak);
        tv_val_nama_kodes      = (TextView) findViewById(R.id.tv_val_nama_kodes);
        tv_val_nama_kodes_join = (TextView) findViewById(R.id.tv_val_nama_kodes_join);
        tv_info_nama_anak      = (TextView) findViewById(R.id.tv_info_nama_anak);
        tv_nama_anak           = (TextView) findViewById(R.id.tv_nama_anak);

        mApiInterface = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        member_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        member_type   = sharedpreferences.getString(TAG_MEMBER_TYPE,"member_type");
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");

        et_disableFocus();
        getval_InfoAksesAnak();

        btn_minta_kode_akses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
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
            public void onSearchTextChanged(final String oldQuery, final String newQuery) {
                floating_search_view.showProgress();
                if(newQuery.equals("")){
                    tv_val_nama_kodes_join.setVisibility(View.GONE);
                    floating_search_view.hideProgress();
                    kosong = 0;
                    tv_val_nama_kodes.setText(getResources().getString(R.string.validate_nama_kode_sekolah));
                }else{
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

                            List<String> SS_GETNAME_AND_SCHOOLCODE = null;

                            String SS_SCS_0001 = getResources().getString(R.string.SS_SCS_0001);
                            String SS_ERR_0001 = getResources().getString(R.string.SS_ERR_0001);

                            if (status == 1 && code.equals("SS_SCS_0001")) {
                                List<JSONResponse.SData> arrayList = response.body().getData();
                                if (arrayList != null) {
                                    SS_GETNAME_AND_SCHOOLCODE = new ArrayList<String>();
                                    for (int i = 0; i < arrayList.size(); i++){
                                        SS_GETNAME_AND_SCHOOLCODE.add(arrayList.get(i).getSchool_name());
                                        SS_GETNAME_AND_SCHOOLCODE.add(arrayList.get(i).getSchool_code());
                                    }
                                }

                                List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                                for (String item : SS_GETNAME_AND_SCHOOLCODE) {
                                    if (item.contains(newQuery.toUpperCase())) {
                                        list.add(new SimpleSuggestions(item));
                                        floating_search_view.swapSuggestions(list);
                                        if(floating_search_view.getQuery().toString().equals(item)){
                                            floating_search_view.clearSuggestions();
                                            tv_val_nama_kodes.setVisibility(View.GONE);
                                            tv_val_nama_kodes_join.setVisibility(View.GONE);
                                            floating_search_view.hideProgress();
                                            floating_search_view.clearSearchFocus();
                                            kosong = 1;
                                            search_school_post();
                                        }else{
                                            floating_search_view.hideProgress();
                                            kosong = 0;
                                        }
                                    }
                                }
                            } else {
                                if(status == 0 && code.equals("SS_ERR_0001")){
                                    Toast.makeText(getApplicationContext(), SS_ERR_0001, Toast.LENGTH_LONG).show();
                                    floating_search_view.hideProgress();
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                            floating_search_view.hideProgress();
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

        floating_search_view.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                check_school_kes_post();
                tv_val_nama_kodes.setVisibility(View.GONE);
            }
            @Override
            public void onSearchAction(String currentQuery) {
                if(kosong==1){
                    check_school_kes_post();
                }else if(floating_search_view.getQuery().toString().isEmpty()){
                    tv_val_nama_kodes.setText(getResources().getString(R.string.validate_nama_kode_sekolah));
                }else {
                    tv_val_nama_kodes.setVisibility(View.VISIBLE);
                    tv_val_nama_kodes.setText(getResources().getString(R.string.validate_sekolah_code));
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
        if(kosong==0){
            /// save session
            Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show();
        }else{
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(session_status, true);
            editor.putString(TAG_STUDNET_ID, (String) student_id);
            editor.putString(TAG_STUDENT_NIK, (String) student_nik);
            editor.putString(TAG_SCHOOL_ID, (String) school_id);
            editor.putString(TAG_NAMA_ANAK, tv_nama_anak.getText().toString());
            editor.putString(TAG_NAMA_SEKOLAH, (String) school_name);
            editor.commit();
            kosong=0;
            status_nik=0;
            tv_val_nama_kodes.setVisibility(View.GONE);
            tv_val_nama_kodes_join.setVisibility(View.GONE);
            floating_search_view.clearQuery();
            tv_nama_anak.setText("_____");
            et_nik_niora_siswa.setText("");
            request_code_acsess_post();
        }
    }
    private boolean validateNamaKodeSekolah() {
        if (kosong == 0) {
            tv_val_nama_kodes.setVisibility(View.VISIBLE);
            til_nik_niora_siswa.setError(getResources().getString(R.string.validate_nik_niora_ortu));
            return false;
        } else {
            til_nik_niora_siswa.setErrorEnabled(false);
            tv_val_nama_kodes.setVisibility(View.GONE);
        }
        return true;
    }
    private boolean validateNikNiora() {
        if (et_nik_niora_siswa.getText().toString().trim().isEmpty()) {
            til_nik_niora_siswa.setError(getResources().getString(R.string.validate_nik_niora_anak));
            requestFocus(et_nik_niora_siswa);
            return false;
        } else if(status_nik==0){
            til_nik_niora_siswa.setError(getResources().getString(R.string.validate_nik_niora));
            requestFocus(et_nik_niora_siswa);
        }else {
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
                    floating_search_view.clearSuggestions();
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
    public void getval_Recommend(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            SpannableString ss = new SpannableString("The school hasn't joined KES. Recommend the school to join KES");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AksesAnak.this, RecommendSchool.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 32, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_val_nama_kodes_join.setText(ss);
            tv_val_nama_kodes_join.setMovementMethod(LinkMovementMethod.getInstance());
            tv_val_nama_kodes_join.setHighlightColor(Color.TRANSPARENT);
        }
        else if (language.equals("in")) {
            SpannableString ss = new SpannableString("Sekolah belum bergabung di KES. Rekomendasikan sekolah untuk bergabung dengan KES");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AksesAnak.this, RecommendSchool.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 32, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_val_nama_kodes_join.setText(ss);
            tv_val_nama_kodes_join.setMovementMethod(LinkMovementMethod.getInstance());
            tv_val_nama_kodes_join.setHighlightColor(Color.TRANSPARENT);
        }
    }
    public void getval_InfoAksesAnak(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            SpannableString ss = new SpannableString("The access code will be sent via email registered with the school, if the email you use is different from the email registered with the school please Contact us");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AksesAnak.this, RecommendSchool.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 150, 160, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_info_nama_anak.setText(ss);
            tv_info_nama_anak.setMovementMethod(LinkMovementMethod.getInstance());
            tv_info_nama_anak.setHighlightColor(Color.TRANSPARENT);
        }
        else if (language.equals("in")) {
            SpannableString ss = new SpannableString("Kode akses akan dikirimkan melalui email yang terdaftar pada sekolah, bila email yang anda gunakan berbeda dengan email yang terdaftar pada sekolah mohon Hubungi Kami");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AksesAnak.this, MainActivity.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 154, 166, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tv_info_nama_anak.setText(ss);
            tv_info_nama_anak.setMovementMethod(LinkMovementMethod.getInstance());
            tv_info_nama_anak.setHighlightColor(Color.TRANSPARENT);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        hideKeyboard(AksesAnak.this);
    }

    ///// Retrofit JSON
    public void request_code_acsess_post(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.request_code_acsess_post(authorization.toString(), email.toString(), fullname.toString(), member_id.toString(), student_id.toString(), school_id.toString());
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
                    Intent intent = new Intent(getApplicationContext(), KodeAksesAnak.class);
                    startActivity(intent);
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
        progressBar();
        showDialog();
        Call<JSONResponse.Check_Student_NIK> postCall = mApiInterface.check_student_nik_post(authorization.toString(),member_id.toString(), et_nik_niora_siswa.getText().toString(), floating_search_view.getQuery().toString());
        postCall.enqueue(new Callback<JSONResponse.Check_Student_NIK>() {
            @Override
            public void onResponse(Call<JSONResponse.Check_Student_NIK> call, Response<JSONResponse.Check_Student_NIK> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse.Check_Student_NIK resource = response.body();
                status = resource.status;
                code = resource.code;

                List<String> AL_CHECK_NIK_GETFULLNAME = null;
                List<String> AL_CHECK_NIK_GETMEMBERID = null;
                List<String> AL_CHECK_NIK_GETNIK = null;

                String CSN_SCS_0001 = getResources().getString(R.string.CSN_SCS_0001);
                String CSN_ERR_0001 = getResources().getString(R.string.CSN_ERR_0001);
                String CSN_ERR_0002 = getResources().getString(R.string.CSN_ERR_0002);

                if (status == 1 && code.equals("CSN_SCS_0001")) {
                    List<JSONResponse.CSNIK_Data> arrayList = response.body().getData();
                    if (arrayList != null) {
                        AL_CHECK_NIK_GETFULLNAME = new ArrayList<String>();
                        AL_CHECK_NIK_GETMEMBERID = new ArrayList<String>();
                        AL_CHECK_NIK_GETNIK = new ArrayList<String>();
                        for (int i = 0; i < arrayList.size(); i++){
                            AL_CHECK_NIK_GETFULLNAME.add(arrayList.get(i).getFullname());
                            AL_CHECK_NIK_GETMEMBERID.add(arrayList.get(i).getMemberid());
                            AL_CHECK_NIK_GETNIK.add(arrayList.get(i).getNik());
                        }
                    }

                    List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                    for (final String item : AL_CHECK_NIK_GETFULLNAME) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            tv_nama_anak.setText(item);
                            status_nik =1;
                            til_nik_niora_siswa.setErrorEnabled(false);
                            Toast.makeText(getApplicationContext(), CSN_SCS_0001, Toast.LENGTH_SHORT).show();
                        }
                    }
                    for (final String item : AL_CHECK_NIK_GETMEMBERID) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            student_id = item;
                        }
                    }
                    for (final String item : AL_CHECK_NIK_GETNIK) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            student_nik = item;
                        }
                    }
                } else {
                    status_nik =0;
                    if(status == 0 && code.equals("CSN_ERR_0001")){
                        til_nik_niora_siswa.setError(getResources().getString(R.string.validate_nik_niora));
                        requestFocus(et_nik_niora_siswa);
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
    public void check_school_kes_post(){
        String key = floating_search_view.getQuery();
        Call<JSONResponse> postCall = mApiInterface.check_school_kes_post(authorization.toString(),key.toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String CSK_SCS_0001 = getResources().getString(R.string.CSK_SCS_0001);
                String CSK_ERR_0001 = getResources().getString(R.string.CSK_ERR_0001);
                String CSK_ERR_0002 = getResources().getString(R.string.CSK_ERR_0002);

                if (status == 1 && code.equals("CSK_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), CSK_SCS_0001, Toast.LENGTH_LONG).show();
                    tv_val_nama_kodes_join.setVisibility(View.GONE);
                } else {
                    if(status == 0 && code.equals("CSK_ERR_0001")){
                        tv_val_nama_kodes_join.setVisibility(View.VISIBLE);
                        getval_Recommend();
                        Toast.makeText(getApplicationContext(), CSK_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                    if(status == 0 && code.equals("CSK_ERR_0002")){
//                        Toast.makeText(getApplicationContext(), CSK_ERR_0002, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void search_school_post(){
        String key = floating_search_view.getQuery();
        Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key.toString());
        postCall.enqueue(new Callback<JSONResponse.School>() {
            @Override
            public void onResponse(Call<JSONResponse.School> call, Response<JSONResponse.School> response) {
                Log.d("TAG",response.code()+"");

                JSONResponse.School resource = response.body();
                status = resource.status;
                code = resource.code;

                List<String> SS_GETSCHOOLNAME = null;
                List<String> SS_GETSCHOOLID = null;
                List<String> SS_GETSCHOOL_CODE = null;

                if (status == 1 && code.equals("SS_SCS_0001")) {
                    List<JSONResponse.SData> arrayList = response.body().getData();
                    SS_GETSCHOOLNAME = new ArrayList<String>();
                    SS_GETSCHOOLID = new ArrayList<String>();
                    SS_GETSCHOOL_CODE = new ArrayList<String>();
                    if (arrayList != null) {
                        for (int i = 0; i < arrayList.size(); i++){
                            SS_GETSCHOOLNAME.add(arrayList.get(i).getSchool_name());
                            SS_GETSCHOOLID.add(arrayList.get(i).getSchoolid());
                            SS_GETSCHOOL_CODE.add(arrayList.get(i).getSchool_code());
                        }
                    }
                    List<SearchSuggestion> list = new ArrayList<SearchSuggestion>();
                    for (final String item : SS_GETSCHOOLID) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            school_id =item;
                        }
                    }
                    for (final String item : SS_GETSCHOOLNAME) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            school_name =item;
                        }
                    }
                    for (final String item : SS_GETSCHOOL_CODE) {
                        if (item.contains(item)) {
                            list.add(new SimpleSuggestions(item));
                            school_code =item;
                        }
                    }
                } else {
                    if(status == 0 && code.equals("SS_ERR_0001")){
                        floating_search_view.hideProgress();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                floating_search_view.hideProgress();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }


}
