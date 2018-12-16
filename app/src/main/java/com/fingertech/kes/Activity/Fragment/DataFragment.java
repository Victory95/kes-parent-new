package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.ParseException;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
    private TextView t_tanggal_lahir;

    private ProgressDialog dialog;
    int status;
    String code;
    String deviceid;
    String id, nama_depan,nama_belakang,nik,hubungan,tempat_lahir,tanggal_lahir, token;
    private static final int PERMISSION_REQUEST_CODE = 1;

    ConnectivityManager conMgr;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_NAMA_DEPAN        = "fullname";
    public static final String TAG_NAMA_BELAKANG     = "nama_belakang";
    public static final String TAG_NIK               = "parent_nik";
    public static final String TAG_HUBUNGAN          = "hubungan";
    public static final String TAG_TEMPAT_LAHIR      = "parent_birth_place";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_TOKEN             = "token";

    Auth mApiInterface;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        et_namadepan = (EditText)view.findViewById(R.id.et_nama_depan);
        et_namabelakang = (EditText)view.findViewById(R.id.et_nama_belakang);
        et_Nik = (EditText)view.findViewById(R.id.et_NIK);
        et_tempat_lahir = (EditText)view.findViewById(R.id.et_tempatlahir);
        et_tanggal_lahir = (EditText)view.findViewById(R.id.et_tanggallahir);
        et_negaraasal = (Spinner)view.findViewById(R.id.sp_negara);
        til_namadepan = (TextInputLayout)view.findViewById(R.id.til_nama_depan);
        til_namabelakang = (TextInputLayout)view.findViewById(R.id.til_nama_belakang);
        til_Nik = (TextInputLayout)view.findViewById(R.id.til_NIK);
        til_tempat_lahir = (TextInputLayout)view.findViewById(R.id.til_tempatlahir);
        til_tanggal_lahir = (TextInputLayout)view.findViewById(R.id.til_tanggallahir);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        //Mengambil calendar bawaan dari android
        Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//i adalah tahun, i1 adalah bulan dan i2 adalah hari
                //Respon dari dialog, di convert ke format tanggal yang diinginkan lalu setelah itu ditampilkan
                et_tanggal_lahir.setText(convertDate(i, i1, i2));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //calendar.get(Calendar.YEAR) memberikan nilai tahun awal pada dialog sesuai tahun yang didapat dari calendar

        et_tanggal_lahir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
            }
        });

        et_tanggal_lahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
                }
            }
        });

        et_negaraasal.setOnItemSelectedListener(this);

        // submitForm();
        //login_post();
        // Loading spinner data from database
        loadSpinnerData();

        // Spinner click listener
        Spinner et_hubungan = (Spinner) view.findViewById(R.id.sp_hubungan);
        String[] years = {"Hubungan","Ayah","Ibu","Wali"};

        final List<String> plantsList = new ArrayList<>(Arrays.asList(years));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_text,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_hubungan.setAdapter(spinnerArrayAdapter);

        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        nama_depan       = sharedpreferences.getString(TAG_NAMA_DEPAN,"fullname");
        nik   = sharedpreferences.getString(TAG_NIK,"parent_nik");
        tempat_lahir    = sharedpreferences.getString(TAG_TEMPAT_LAHIR,"parent_birth_place");

        token       = sharedpreferences.getString(TAG_TOKEN,"token");

        et_namadepan.setText(nama_depan);
        et_Nik.setText(nik);
        et_tempat_lahir.setText(tempat_lahir);

        mApiInterface = ApiClient.getClient().create(Auth.class);

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
        Call<JSONResponse> call = mApiInterface.update_orangtua_get(et_namadepan.getText().toString(), et_Nik.getText().toString(),et_Hubungan.getText().toString(), et_tempat_lahir.getText().toString(),et_tanggal_lahir.getText().toString());
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
//                Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                Log.d("TAG",response.code()+"");
                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

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
    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
                } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

}
