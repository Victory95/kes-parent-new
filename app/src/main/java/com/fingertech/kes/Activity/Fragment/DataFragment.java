package com.fingertech.kes.Activity.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
//import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.DaftarParent;
import com.fingertech.kes.Activity.Maps.FullMap;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.ParentMain;
import com.rey.material.widget.Spinner;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Service.DBHelper;
import com.shashank.sony.fancytoastlib.FancyToast;

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
//implements AdapterView.OnItemSelectedListener
public class DataFragment extends Fragment  {
    private TextInputLayout til_namadepan,til_namabelakang,til_Nik,til_Hubungan,til_tempat_lahir,til_tanggal_lahir,til_Email;
    private EditText et_namadepan,et_namabelakang,et_Nik,et_Hubungan,et_tempat_lahir,et_tanggal_lahir,et_Email;
    private Spinner et_negaraasal;
    private Spinner et_hubungan;
    private ProgressDialog dialog;
    private RadioButton rb_wni,rb_wna;
    int status;
    String code;
    String nama_depan,email,nik,hubungan,tempat_lahir,tanggal_lahir, token,student_id,school_code,authorization,type_warga;
    String parent_name,parent_email,parent_nik,parent_type,parent_birth_place,parent_birth_date,kewarganegaraan,parent_address,parent_phone;

    String namaparent,Email,nik_parent,jeniskelamin,tempatlahir,tanggallahir,negaraasal;

    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ParentMain.FragmentAdapter fragmentAdapter;

    ConnectivityManager conMgr;
    SharedPreferences sharedpreferences,sharedviewpager;
    Boolean session = false;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String my_shared_viewpager   = "my_shared_viewpager";
    public static final String session_status = "session_status";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";

    public static final String TAG_PARENT_NAME       = "nama_parent";
    public static final String TAG_NIK_PARENT        = "nik_parent";
    public static final String TAG_EMAIL_PARENT      = "email_parent";
    public static final String TAG_TEMPAT_LAHIR      = "tempat_lahir";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_HUBUNGAN          = "hubungan";
    public static final String TAG_KEWARGANEGARAAN   = "kewarganegaraan";

    String verification_code,parent_id,student_nik,school_id,childrenname,school_name,fullname,member_id;

    Button next,back;
    ParentMain parentMain;
    private ViewPager ParentPager;


    Auth mApiInterface;
    private String[] listSpinner = {
            "Hubungan",
            "Ayah",
            "Ibu",
            "Wali"
    };

    TextView tv_kewarganegaraan_validate,tv_validate_hubungan;

    public DataFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view         = inflater.inflate(R.layout.fragment_data, container, false);
        et_namadepan      = view.findViewById(R.id.et_nama_depan);
        et_Email          = view.findViewById(R.id.et_Email);
        et_Nik            = view.findViewById(R.id.et_NIK);
        et_tempat_lahir   = view.findViewById(R.id.et_tempatlahir);
        et_tanggal_lahir  = view.findViewById(R.id.et_tanggallahir);
        et_negaraasal     = view.findViewById(R.id.sp_negara);
        til_namadepan     = view.findViewById(R.id.til_nama_depan);
        til_Email         = view.findViewById(R.id.til_Email);
        til_Nik           = view.findViewById(R.id.til_NIK);
        til_tempat_lahir  = view.findViewById(R.id.til_tempatlahir);
        til_tanggal_lahir = view.findViewById(R.id.til_tanggallahir);
        et_hubungan       = view.findViewById(R.id.sp_hubungan);
        rb_wni            = view.findViewById(R.id.rb_wni);
        rb_wna            = view.findViewById(R.id.rb_wna);
        dateFormatter     = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        parentMain        = (ParentMain)getActivity();
        indicator         = view.findViewById(R.id.indicators);
        back              = view.findViewById(R.id.btn_kembali);
        next              = view.findViewById(R.id.btn_berikut);
        fragmentAdapter   = new ParentMain.FragmentAdapter(getActivity().getSupportFragmentManager());
        ParentPager       = parentMain.findViewById(R.id.PagerParent);
        tv_kewarganegaraan_validate = view.findViewById(R.id.tv_kewarganegaraan_validate);
        tv_validate_hubungan        = view.findViewById(R.id.tv_hubungan);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    submitForm();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentPager.setCurrentItem(getItem(-1),true);
            }
        });
        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[1].setBackgroundResource(R.drawable.selected_item);


        mApiInterface = ApiClient.getClient().create(Auth.class);
        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        school_id     = sharedpreferences.getString(TAG_SCHOOL_ID,"school_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        childrenname  = sharedpreferences.getString(TAG_NAMA_ANAK,"childrenname");
        school_name   = sharedpreferences.getString(TAG_NAMA_SEKOLAH,"school_name");
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,"school_code");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");

        data_parent_student_get();

        //Mengambil calendar bawaan dari android
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        et_Email.setEnabled(false);
        et_Email.setFocusable(false);
        et_Nik.setFocusable(false);
        et_Nik.setEnabled(false);
        et_hubungan.setEnabled(false);
        rb_wna.setEnabled(false);
        rb_wni.setEnabled(false);

        final DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(getContext(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                et_tanggal_lahir.setText(convertDate(selectedyear, selectedmonth, selectedday));
            }
        }, mYear, mMonth, mDay);


        et_tanggal_lahir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mDatePicker.show();//Dialog ditampilkan ketika edittext diclick
            }
        });

        et_tanggal_lahir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    mDatePicker.show();//Dialog ditampilkan ketika edittext mendapat fokus
                }
            }
        });

        loadSpinnerData();

        sharedviewpager  = getActivity().getSharedPreferences(my_shared_viewpager, getActivity().MODE_PRIVATE);
        session          = sharedpreferences.getBoolean(session_status, false);
        parent_name      = sharedviewpager.getString(TAG_PARENT_NAME,null);
        parent_email     = sharedviewpager.getString(TAG_EMAIL_PARENT,null);
        nik              = sharedviewpager.getString(TAG_NIK_PARENT,null);
        tempat_lahir     = sharedviewpager.getString(TAG_TEMPAT_LAHIR,null);
        tanggal_lahir    = sharedviewpager.getString(TAG_TANGGAL_LAHIR,null);
        hubungan         = sharedviewpager.getString(TAG_HUBUNGAN,null);
        type_warga       = sharedviewpager.getString(TAG_KEWARGANEGARAAN,null);

        return view;
    }

    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
    }


    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(getApplicationContext());

        final List<String> myData = db.getAllLabels();

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text, myData);
        //int spinnerPosition = spinnerArrayAdapter.getPosition(myString);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_negaraasal.setAdapter(spinnerArrayAdapter);
        et_negaraasal.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                negaraasal = myData.get(position);
            }
        });



    }

    public void submitForm() {
        if (!validateNamaDepan()) {
            return;
        }
        if (!validateNIK()) {
            return;
        }
        if (!validateTempatLahir()) {
            return;
        }
        if (!validateTanggalLahir()) {
            return;
        }if (!validateHubungan()){
            return;
        }if (kewarganegaraan == null){
            tv_kewarganegaraan_validate.setVisibility(View.VISIBLE);
        }else {
            tv_kewarganegaraan_validate.setVisibility(View.GONE);
            SharedPreferences.Editor editor = sharedviewpager.edit();
            editor.putBoolean(session_status, true);
            editor.putString(TAG_PARENT_NAME, et_namadepan.getText().toString());
            editor.putString(TAG_EMAIL_PARENT, et_Email.getText().toString());
            editor.putString(TAG_NIK_PARENT, et_Nik.getText().toString());
            editor.putString(TAG_TEMPAT_LAHIR, et_tempat_lahir.getText().toString());
            editor.putString(TAG_TANGGAL_LAHIR, et_tanggal_lahir.getText().toString());
            editor.putString(TAG_HUBUNGAN, et_hubungan.getSelectedItem().toString());
            editor.putString(TAG_KEWARGANEGARAAN, kewarganegaraan);
            editor.commit();
            KontakFragment kontakFragment = new KontakFragment();
            Bundle Dataparent = new Bundle();
            Dataparent.putString(TAG_PARENT_NAME,et_namadepan.getText().toString());
            Dataparent.putString(TAG_EMAIL_PARENT,et_Email.getText().toString());
            Dataparent.putString(TAG_NIK_PARENT,et_Nik.getText().toString());
            Dataparent.putString(TAG_HUBUNGAN,et_hubungan.getSelectedItem().toString());
            Dataparent.putString(TAG_TEMPAT_LAHIR,et_tempat_lahir.getText().toString());
            Dataparent.putString(TAG_TANGGAL_LAHIR,et_tanggal_lahir.getText().toString());
            Dataparent.putString(TAG_KEWARGANEGARAAN,negaraasal);
            kontakFragment.setArguments(Dataparent);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragKontak,kontakFragment);
            fragmentTransaction.commit();
            ParentPager.setCurrentItem(getItem(+1), true);
           }
    }
    private boolean validateNamaDepan() {
        if (et_namadepan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(et_namadepan);
            return false;
        } else {
            til_namadepan.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNIK() {
        if (et_Nik.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(et_Nik);
            return false;
        } else {
            til_Nik.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateHubungan() {
        if (parent_type == null) {
            tv_validate_hubungan.setVisibility(View.VISIBLE);
            return false;
        } else {
            tv_validate_hubungan.setVisibility(View.GONE);
        }

        return true;
    }
    private boolean validateTempatLahir() {
        if (et_tempat_lahir.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(et_tempat_lahir);
            return false;
        } else {
            til_tempat_lahir.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateTanggalLahir() {
        if (et_tanggal_lahir.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(et_tanggal_lahir);
            return false;
        } else {
            til_tanggal_lahir.setErrorEnabled(false);
        }

        return true;
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
        dialog = new ProgressDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void data_parent_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.Data_parent_student> call = mApiInterface.data_parent_student_get(authorization, school_code.toLowerCase(), parent_nik, student_id);
        call.enqueue(new Callback<JSONResponse.Data_parent_student>() {
            @Override
            public void onResponse(Call<JSONResponse.Data_parent_student> call, Response<JSONResponse.Data_parent_student> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.Data_parent_student resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String DPG_SCS_0001 = getResources().getString(R.string.DPG_SCS_0001);
                    String DPG_ERR_0001 = getResources().getString(R.string.DPG_ERR_0001);
                    String DPG_ERR_0002 = getResources().getString(R.string.DPG_ERR_0002);
                    String DPG_ERR_0003 = getResources().getString(R.string.DPG_ERR_0003);

                    if (status == 1 && code.equals("DPG_SCS_0001")) {
                        namaparent = response.body().data.getParent_name();
                        Email = response.body().data.getParent_email();
                        nik_parent = response.body().data.getParent_nik();
                        hubungan = response.body().data.getParent_type();
                        tempatlahir = response.body().data.getParent_birth_place();
                        tanggallahir = response.body().data.getParent_birth_date();
                        kewarganegaraan = response.body().data.getType_warga();

                        et_namadepan.setText(namaparent);
                        et_Nik.setText(nik_parent);
                        et_Email.setText(Email);
                        et_tempat_lahir.setText(tempatlahir);
                        et_tanggal_lahir.setText(tanggallahir);

                        final List<String> penghasil = new ArrayList<>(Arrays.asList(listSpinner));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, penghasil) {
                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };

                        int spinnerPosition = ArrayAdapter.getPosition(hubungan);
                        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        et_hubungan.setAdapter(ArrayAdapter);
                        et_hubungan.setSelection(spinnerPosition);
                        et_hubungan.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                if (position > 0) {
                                    parent_type = penghasil.get(position);
//                            Toast.makeText(getApplicationContext(), parent_type, Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        parent_type = et_hubungan.getSelectedItem().toString();

                        if (kewarganegaraan.equals("WNI")) {
                            rb_wni.setChecked(true);
                            rb_wna.setChecked(false);
                        } else if (kewarganegaraan.equals("WNA")) {
                            rb_wna.setChecked(true);
                            rb_wni.setChecked(false);
                        }
                        rb_wni.setOnClickListener(v -> {
                            kewarganegaraan = getResources().getString(R.string.rb_wni);
                            et_negaraasal.setVisibility(View.GONE);
                        });

                        rb_wna.setOnClickListener(v -> {
                            et_negaraasal.setVisibility(View.VISIBLE);
                            kewarganegaraan = et_negaraasal.getSelectedItem().toString();
                        });


                    } else {
                        if (status == 0 && code.equals("DPG_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("DPG_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("DPG_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.Data_parent_student> call, Throwable t) {
                hideDialog();
                Log.i("onFailure",t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setUiPageViewController() {
        mDotCount = fragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            mDots[i] = new LinearLayout(getContext());
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            indicator.addView(mDots[i]);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }


}
