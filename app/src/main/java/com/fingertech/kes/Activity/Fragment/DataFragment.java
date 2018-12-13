package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fingertech.kes.Activity.DaftarPublic;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.ParentMain;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Service.DBHelper;
import com.fingertech.kes.Util.JWTUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public DataFragment() {
        // Required empty public constructor
    }

    private TextInputLayout til_namadepan,til_namabelakang,til_Nik,til_Hubungan,til_tempat_lahir,til_tanggal_lahir;
    private EditText et_namadepan,et_namabelakang,et_Nik,et_Hubungan,et_tempat_lahir,et_tanggal_lahir;
    private Spinner et_negaraasal;

    private ProgressDialog dialog;
    int status;
    String code;
    String deviceid;
    String id, nama_depan,nama_belakang,nik,hubungan,tempat_lahir,tanggal_lahir, token;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ConnectivityManager conMgr;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_NAMA_DEPAN        = "fullname";
    public static final String TAG_NAMA_BELAKANG     = "parent_name";
    public static final String TAG_NIK               = "parent_nik";
    public static final String TAG_HUBUNGAN          = "parent_type";
    public static final String TAG_TEMPAT_LAHIR      = "parent_birth_place";
    public static final String TAG_TANGGAL_LAHIR     = "parent_birth_date";
    public static final String TAG_TOKEN             = "token";

    Auth mApiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        et_namadepan = (EditText)view.findViewById(R.id.et_nama_depan);
        et_namabelakang = (EditText)view.findViewById(R.id.et_nama_belakang);
        et_Nik = (EditText)view.findViewById(R.id.et_NIK);
        et_Hubungan = (EditText)view.findViewById(R.id.et_hubungan);
        et_tempat_lahir = (EditText)view.findViewById(R.id.et_tempatlahir);
        et_tanggal_lahir = (EditText)view.findViewById(R.id.et_tanggallahir);
        et_negaraasal = (Spinner)view.findViewById(R.id.et_negara);
        til_namadepan = (TextInputLayout)view.findViewById(R.id.til_nama_depan);
        til_namabelakang = (TextInputLayout)view.findViewById(R.id.til_nama_belakang);
        til_Nik = (TextInputLayout)view.findViewById(R.id.til_NIK);
        til_Hubungan = (TextInputLayout)view.findViewById(R.id.til_hubungan);
        til_tempat_lahir = (TextInputLayout)view.findViewById(R.id.til_tempatlahir);
        til_tanggal_lahir = (TextInputLayout)view.findViewById(R.id.til_tanggallahir);
       //submitForm();
           // login_post();
        // Spinner click listener
        et_negaraasal.setOnItemSelectedListener(this);
        mApiInterface = ApiClient.getClient().create(Auth.class);
        // Loading spinner data from database
        loadSpinnerData();
    return view;
    }

    private void submitForm() {
        if (!validateNamaDepan()) {
            return;
        }
        if (!validateNamaBelakang()) {
            return;
        }
        if (!validateNIK()) {
            return;
        }
        if (!validateHubungan()) {
            return;
        }
        if (!validateTempatLahir()) {
            return;
        }
        if (!validateTanggalLahir()) {
            return;

        }
        login_post();
    }

    private boolean validateNamaDepan() {
        if (et_namadepan.getText().toString().trim().isEmpty()) {
            til_namadepan.setError(getResources().getString(R.string.validate_name_depan));
            requestFocus(et_namadepan);
            return false;
        } else {
            til_namadepan.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNamaBelakang() {
        if (et_namabelakang.getText().toString().trim().isEmpty()) {
            til_namabelakang.setError(getResources().getString(R.string.validate_name_belakang));
            requestFocus(et_namabelakang);
            return false;
        } else {
            til_namabelakang.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNIK() {
        if (et_Nik.getText().toString().trim().isEmpty()) {
            til_Nik.setError(getResources().getString(R.string.validate_nik));
            requestFocus(et_Nik);
            return false;
        } else {
            til_Nik.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateHubungan() {
        if (et_Hubungan.getText().toString().trim().isEmpty()) {
            til_Hubungan.setError(getResources().getString(R.string.validate_hubungan));
            requestFocus(et_Hubungan);
            return false;
        } else {
            til_Hubungan.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTempatLahir() {
        if (et_tempat_lahir.getText().toString().trim().isEmpty()) {
            til_tempat_lahir.setError(getResources().getString(R.string.validate_tempat_lahir));
            requestFocus(et_tempat_lahir);
            return false;
        } else {
            til_tempat_lahir.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateTanggalLahir() {
        if (et_tanggal_lahir.getText().toString().trim().isEmpty()) {
            til_tanggal_lahir.setError(getResources().getString(R.string.validate_tanggal_lahir));
            requestFocus(et_tanggal_lahir);
            return false;
        } else {
            til_tanggal_lahir.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(getApplicationContext());

        final Cursor myData = db.SelectAllData();

        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.costum_spinner_item, myData
                ,new String[] {"negara"}
                ,new int[] { R.id.negarA});


        et_negaraasal.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void login_post(){
        Call<JSONResponse> call = mApiInterface.update_orangtua_get(et_namadepan.getText().toString(),et_namabelakang.getText().toString(), et_Nik.getText().toString(),et_Hubungan.getText().toString(), et_tempat_lahir.getText().toString(),et_tanggal_lahir.getText().toString(), deviceid.toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
//                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                Log.d("TAG",response.code()+"");
                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                String RO_SCS_0001 = getResources().getString(R.string.RO_SCS_0001);
                String RO_ERR_0001 = getResources().getString(R.string.RO_ERR_0001);
                String RO_ERR_0002 = getResources().getString(R.string.RO_ERR_0002);
                String RO_ERR_0003 = getResources().getString(R.string.RO_ERR_0003);
                String RO_ERR_0004 = getResources().getString(R.string.RO_ERR_0004);
                String RO_ERR_0005 = getResources().getString(R.string.RO_ERR_0005);
                String RO_ERR_0006 = getResources().getString(R.string.RO_ERR_0006);
                String RO_ERR_0007 = getResources().getString(R.string.RO_ERR_0007);
                String RO_ERR_0008 = getResources().getString(R.string.RO_ERR_0008);
                String RO_ERR_0009 = getResources().getString(R.string.RO_ERR_0009);
                String RO_ERR_0010 = getResources().getString(R.string.RO_ERR_0010);

                if (status == 1 && code.equals("RO_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), RO_SCS_0001, Toast.LENGTH_LONG).show();
                    et_namadepan.setText("");
                    et_namabelakang.setText("");
                    et_Nik.setText("");
                    et_Hubungan.setText("");
                    et_tempat_lahir.setText("");
                    et_tanggal_lahir.setText("");
                } else {
                    if(status == 0 && code.equals("RO_ERR_0001")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0001, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0002")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0002, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0003")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0003, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0004")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0004, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0005")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0005, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0006")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0006, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0007")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0007, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0008")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0008, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0009")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0009, Toast.LENGTH_LONG).show();
                    }if(status == 0 && code.equals("RO_ERR_0010")){
                        Toast.makeText(getApplicationContext(), RO_ERR_0010, Toast.LENGTH_LONG).show();
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

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }

}
