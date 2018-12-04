package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.fingertech.kes.Activity.DaftarPublic;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataFragment extends Fragment {

    public DataFragment() {
        // Required empty public constructor
    }
private TextInputLayout til_namadepan,til_namabelakang,til_Nik,til_Hubungan,til_tempat_lahir,til_tanggal_lahir,til_negaraasal;
    private EditText et_namadepan,et_namabelakang,et_Nik,et_Hubungan,et_tempat_lahir,et_tanggal_lahir,et_negaraasal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        et_namadepan = (EditText)view.findViewById(R.id.et_nama_depan);
        et_namabelakang = (EditText)view.findViewById(R.id.et_nama_belakang);
        et_Nik = (EditText)view.findViewById(R.id.et_NIK);
        et_Hubungan = (EditText)view.findViewById(R.id.et_hubungan);
        et_tempat_lahir = (EditText)view.findViewById(R.id.et_tempatlahir);
        et_tanggal_lahir = (EditText)view.findViewById(R.id.et_tanggallahir);
        et_negaraasal = (EditText)view.findViewById(R.id.et_negara);
        til_namadepan = (TextInputLayout)view.findViewById(R.id.til_nama_depan);
        til_namabelakang = (TextInputLayout)view.findViewById(R.id.til_nama_belakang);
        til_Nik = (TextInputLayout)view.findViewById(R.id.til_NIK);
        til_Hubungan = (TextInputLayout)view.findViewById(R.id.til_hubungan);
        til_tempat_lahir = (TextInputLayout)view.findViewById(R.id.til_tempatlahir);
        til_tanggal_lahir = (TextInputLayout)view.findViewById(R.id.til_tanggallahir);
        til_negaraasal = (TextInputLayout)view.findViewById(R.id.til_negara);
       // submitForm();
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
            else{
           }
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
    private boolean validateNegaraAsal() {
        if (et_negaraasal.getText().toString().trim().isEmpty()) {
            til_negaraasal.setError(getResources().getString(R.string.validate_negara));
            requestFocus(et_negaraasal);
            return false;
        } else {
            til_negaraasal.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
