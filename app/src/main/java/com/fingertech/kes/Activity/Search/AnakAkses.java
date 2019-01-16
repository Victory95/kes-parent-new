package com.fingertech.kes.Activity.Search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
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

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.fingertech.kes.Activity.Adapter.Adapter;
import com.fingertech.kes.Activity.Adapter.InfoWindow;
import com.fingertech.kes.Activity.Adapter.SearchAdapter;
import com.fingertech.kes.Activity.AksesAnak;
import com.fingertech.kes.Activity.CameraScanning;
import com.fingertech.kes.Activity.KodeAksesAnak;
import com.fingertech.kes.Activity.MainActivity;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.RecommendSchool;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnakAkses extends AppCompatActivity {

    ProgressDialog dialog;
    private List<JSONResponse.SData> arraylist;
    private SearchAdapter adapter;
    Auth mApiInterface;
    String code;
    int status;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    LinearLayout linearLayout;
    Button Kodeakses;
    EditText search,et_nik;
    String authorization;
    ImageView iv_camera;
    Integer status_nik = 0;
    String email, member_id, fullname, member_type, student_id, student_nik, school_id,school_name,school_code,sekolah_kode;

    TextView tvnamajoin,tvkodejoin,tvinfo,tvnamaanak;
    TextInputLayout tl_input_noira;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    SharedPreferences sharedpreferences;
    public static final String TAG_EMAIL        = "email";
    public static final String TAG_MEMBER_ID    = "member_id";
    public static final String TAG_FULLNAME     = "fullname";

    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anak_akses);
        search          = (EditText)findViewById(R.id.et_search);
        recyclerView    = findViewById(R.id.recycler_search);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        linearLayout    = (LinearLayout)findViewById(R.id.infof);
        Kodeakses       = (Button)findViewById(R.id.btn_kode_akses);
        tvnamajoin      = (TextView)findViewById(R.id.tv_val_nama_kodes);
        tvkodejoin      = (TextView)findViewById(R.id.tv_kode_join);
        iv_camera       = (ImageView)findViewById(R.id.iv_camera);
        et_nik          = (EditText)findViewById(R.id.et_nik_niora_siswa);
        tl_input_noira  = (TextInputLayout)findViewById(R.id.til_nik_niora_siswa);
        tvinfo          = (TextView)findViewById(R.id.tv_info_nama_anak);
        tvnamaanak      = (TextView)findViewById(R.id.tv_nama_anak);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getval_InfoAksesAnak();
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        member_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        member_type   = sharedpreferences.getString(TAG_MEMBER_TYPE,"member_type");
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");

        Kodeakses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                search_school_post(String.valueOf(s));
                recyclerView.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //////// Camera scan nik
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnakAkses.this, CameraScanning.class);
                startActivityForResult(i, 1);
            }
        });

        InputFilter[] editFilters = et_nik.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        et_nik.setFilters(newFilters);
        et_nik.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!validateNikNiora()==false) {
                        hideKeyboard(AnakAkses.this);
                        et_nik.clearFocus();
                        check_student_nik_post();
                        return true;
                    }
                }
                return false;
            } });

    }

    private void submitForm() {
        if (!validatenamasekolah()) {
            return;
        }
        if (!validateNikNiora()) {
            return;
        }
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(session_status, true);
            editor.putString(TAG_STUDENT_ID, (String) student_id);
            editor.putString(TAG_STUDENT_NIK, (String) student_nik);
            editor.putString(TAG_SCHOOL_ID, (String) school_id);
            editor.putString(TAG_NAMA_ANAK, tvinfo.getText().toString());
            editor.putString(TAG_NAMA_SEKOLAH, (String) school_name);
            editor.putString(TAG_SCHOOL_CODE, (String)sekolah_kode);
            editor.commit();

            status_nik=0;
            recyclerView.setVisibility(View.GONE);
            search.setText("");
            tvinfo.setText("_____");
            et_nik.setText("");
            request_code_acsess_post();

    }

    private boolean validatenamasekolah() {
        if (search.getText().toString().trim().isEmpty()) {
        //    tvnamajoin.setVisibility(View.VISIBLE);
            tl_input_noira.setError(getResources().getString(R.string.validate_nik_niora_ortu));
            requestFocus(search);
            return false;
        }else {
            tl_input_noira.setErrorEnabled(false);
//            tvnamajoin.setVisibility(View.GONE);
        }
        return true;
    }

    private boolean validateNikNiora() {
        if (et_nik.getText().toString().trim().isEmpty()) {
            tl_input_noira.setError(getResources().getString(R.string.validate_nik_niora_anak));
            requestFocus(et_nik);
            return false;
        } else if(status_nik==0){
            tl_input_noira.setError(getResources().getString(R.string.validate_nik_niora));
            requestFocus(et_nik);
        }else {
            tl_input_noira.setErrorEnabled(false);
        }
        return true;
    }

    public void search_school_post(final String key){

        Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key);
        postCall.enqueue(new Callback<JSONResponse.School>() {
            @Override
            public void onResponse(Call<JSONResponse.School> call, final Response<JSONResponse.School> response) {

                Log.d("TAG",response.code()+"");

                JSONResponse.School resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("SS_SCS_0001")) {
                    arraylist = response.body().getData();
                    adapter = new SearchAdapter(arraylist, AnakAkses.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    adapter.getFilter().filter(key);
//                    adapter.setFilter(arraylist,key);
                    adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            school_name         = response.body().getData().get(position).getSchool_name();
                            sekolah_kode        = response.body().getData().get(position).getSchool_code();
                            school_id           = response.body().getData().get(position).getSchool_id();
                            search.setText(school_name);
                            recyclerView.setVisibility(View.GONE);
                            hideKeyboard(AnakAkses.this);
                            check_school_kes_post();
                            search.clearFocus();
                        }
                    });

                } else {
                    if(status == 0 && code.equals("SS_ERR_0001")){
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.School> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
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
        dialog = new ProgressDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void check_school_kes_post(){
        progressBar();
        showDialog();

        Call<JSONResponse> postCall = mApiInterface.check_school_kes_post(authorization.toString(),sekolah_kode);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String CSK_SCS_0001 = getResources().getString(R.string.CSK_SCS_0001);
                String CSK_ERR_0001 = getResources().getString(R.string.CSK_ERR_0001);
                String CSK_ERR_0002 = getResources().getString(R.string.CSK_ERR_0002);

                if (status == 1 && code.equals("CSK_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), CSK_SCS_0001, Toast.LENGTH_LONG).show();
                    tvkodejoin.setVisibility(View.GONE);
                } else {
                    if(status == 0 && code.equals("CSK_ERR_0001")){
                        tvkodejoin.setVisibility(View.VISIBLE);
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
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getval_Recommend(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            SpannableString ss = new SpannableString("The school hasn't joined KES. Recommend the school to join KES");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AnakAkses.this, RecommendSchool.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 32, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvkodejoin.setText(ss);
            tvkodejoin.setMovementMethod(LinkMovementMethod.getInstance());
            tvkodejoin.setHighlightColor(Color.TRANSPARENT);
        }
        else if (language.equals("in")) {
            SpannableString ss = new SpannableString("Sekolah belum bergabung di KES. Rekomendasikan sekolah untuk bergabung dengan KES");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AnakAkses.this, RecommendSchool.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 32, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvkodejoin.setText(ss);
            tvkodejoin.setMovementMethod(LinkMovementMethod.getInstance());
            tvkodejoin.setHighlightColor(Color.TRANSPARENT);
        }
    }

    public void getval_InfoAksesAnak(){
        String language = Locale.getDefault().getLanguage();
        if (language.equals("en")) {
            SpannableString ss = new SpannableString("The access code will be sent via email registered with the school, if the email you use is different from the email registered with the school please Contact us");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AnakAkses.this, MainActivity.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 150, 160, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvinfo.setText(ss);
            tvinfo.setMovementMethod(LinkMovementMethod.getInstance());
            tvinfo.setHighlightColor(Color.TRANSPARENT);
        }
        else if (language.equals("in")) {
            SpannableString ss = new SpannableString("Kode akses akan dikirimkan melalui email yang terdaftar pada sekolah, bila email yang anda gunakan berbeda dengan email yang terdaftar pada sekolah mohon Hubungi Kami");
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View textView) {
                    startActivity(new Intent(AnakAkses.this, MainActivity.class));
                }
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(true);
                }
            };
            ss.setSpan(clickableSpan, 154, 166, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tvinfo.setText(ss);
            tvinfo.setMovementMethod(LinkMovementMethod.getInstance());
            tvinfo.setHighlightColor(Color.TRANSPARENT);
        }
    }

    public void check_student_nik_post(){
        progressBar();
        showDialog();
        Call<JSONResponse.Check_Student_NIK> postCall = mApiInterface.check_student_nik_post(authorization.toString(),member_id.toString(), et_nik.getText().toString(), sekolah_kode);
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
                            tvnamaanak.setText(item);
                            status_nik =1;
                            tl_input_noira.setErrorEnabled(false);
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
                        tl_input_noira.setError(getResources().getString(R.string.validate_nik_niora));
                        requestFocus(et_nik);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("key_nik_akses_anak");
                et_nik.setText(strEditText);
                et_nik.clearFocus();
            }
        }
    }


}