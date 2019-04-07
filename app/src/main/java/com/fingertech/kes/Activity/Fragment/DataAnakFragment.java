package com.fingertech.kes.Activity.Fragment;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;

import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.ParentMain;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Service.DBHelper;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

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
public class DataAnakFragment extends Fragment {


    private EditText et_tanggal;
    private Spinner et_negara_asal;
    public DataAnakFragment() {
        // Required empty public constructor
    }

    ViewPager ParentPager;
    AnakMain anakMain;
    Button buttonBerikutnya,buttonKembali;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private String[] listSekolah = {
            "Tingkatan Kelas",
            "SD 1",
            "SD 2",
            "SD 3",
            "SD 4",
            "SD 5",
            "SD 6",
            "SMP 1",
            "SMP 2",
            "SMP 3",
            "SMA/SMK 1",
            "SMA/SMK 2",
            "SMA/SMK 3"
    };
    private String[] listAgama = {
            "Agama",
            "Buddha",
            "Hindu",
            "Islam",
            "Katolik",
            "Kristen"
    };
    String negaraasal,kelas,levelkelas;
    private AnakMain.FragmentAdapter fragmentAdapter;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String my_shared_anak   = "my_shared_anak";
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

    public static final String TAG_NAMA_LENGKAP      = "nama_lengkap";
    public static final String TAG_NIS               = "nis";
    public static final String TAG_NISN              = "nisn";
    public static final String TAG_NIK               = "nik";
    public static final String TAG_JENIS_KELAMIN     = "jenis_kelamin";
    public static final String TAG_TEMPAT_LAHIR      = "tempat_lahir";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_ROMBEL            = "rombel";
    public static final String TAG_KEBUTUHAN_KHUSUS  = "kebutuhan_khusus";
    public static final String TAG_TINGKATAN         = "tingkatan";
    public static final String TAG_KEWARGANEGARAAN   = "kewarganegaraan";
    public static final String TAG_AGAMA             = "agama";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    SharedPreferences sharedpreferences,sharedanak;
    int status;
    String code;
    ProgressDialog dialog;
    Auth mApiInterface;
    Spinner sp_tingkatan,sp_agama;
    RadioButton rb_laki,rb_wanita,rb_wni,rb_wna;
    String Nama_lengkap,Nis,Nisn,Nik,Rombel,Tingkatan,Agama,Negara,Kebutuhankhusus,Tempat_lahir,Tanggal_lahir,Jenis_kelamin;
    EditText et_nama_lengkap,et_nis,et_nisn,et_nik,et_tempat_lahir,et_rombel,et_kebutuhan_khusus;
    TextInputLayout til_nama_lengkap,til_nis,til_nisn,til_nik,til_rombel,til_tempat_lahir,til_tanggal_lahir,til_kebutuhan_khusus;
    String email,parent_id,student_nik,school_id,childrenname,school_name,fullname,student_id,member_id,parent_nik,authorization,school_code;
    String tingkatan_kelas,nama_lengkap,nis,nisn,nik,rombel,jenis_kelamin,tanggal_lahir,tempat_lahir,religion,kebutuhan_khusus,kewarganegaraan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view           = inflater.inflate(R.layout.fragment_data_anak, container, false);
        et_negara_asal      = view.findViewById(R.id.sp_negara_asal);
        et_tanggal          = view.findViewById(R.id.et_tanggallahiR);
        et_nama_lengkap     = view.findViewById(R.id.et_nama_lengkap_anak);
        et_nis              = view.findViewById(R.id.et_nama_nis);
        et_nisn             = view.findViewById(R.id.et_nama_nisn);
        et_rombel           = view.findViewById(R.id.et_rombel);
        et_tempat_lahir     = view.findViewById(R.id.et_tempatlahiR);
        et_nik              = view.findViewById(R.id.et_Nik);
        et_kebutuhan_khusus = view.findViewById(R.id.et_kebutuhan_khusus);
        anakMain            = (AnakMain)getActivity();
        ParentPager         = anakMain.findViewById(R.id.PagerAnak);
        indicator           = view.findViewById(R.id.indicators);
        buttonKembali       = view.findViewById(R.id.btn_kembali);
        buttonBerikutnya    = view.findViewById(R.id.btn_berikut);
        fragmentAdapter     = new AnakMain.FragmentAdapter(getActivity().getSupportFragmentManager());
        sp_tingkatan        = view.findViewById(R.id.sp_tingkatan);
        sp_agama            = view.findViewById(R.id.sp_agama);
        rb_laki             = view.findViewById(R.id.rb_laki_lakI);
        rb_wanita           = view.findViewById(R.id.rb_perempuaN);
        rb_wni              = view.findViewById(R.id.rb_wnI);
        rb_wna              = view.findViewById(R.id.rb_wnA);
        til_nama_lengkap    = view.findViewById(R.id.til_nama_lengkap_anak);
        til_nis             = view.findViewById(R.id.til_nis);
        til_nisn            = view.findViewById(R.id.til_nisn);
        til_nik             = view.findViewById(R.id.til_Nik);
        til_rombel          = view.findViewById(R.id.til_rombel);
        til_tempat_lahir    = view.findViewById(R.id.til_tempatlahiR);
        til_tanggal_lahir   = view.findViewById(R.id.til_tanggallahiR);
        til_kebutuhan_khusus= view.findViewById(R.id.til_kebutuhan_khusus);

        Calendar calendar = Calendar.getInstance();


        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, (datePicker, i, i1, i2) -> {//i adalah tahun, i1 adalah bulan dan i2 adalah hari
            //Respon dari dialog, di convert ke format tanggal yang diinginkan lalu setelah itu ditampilkan
            et_tanggal.setText(convertDate(i, i1, i2));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        et_tanggal.setOnClickListener(view1 -> {
            datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
        });

        et_tanggal.setOnFocusChangeListener((view12, b) -> {
            if (b) {
                datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
            }
        });


        loadSpinnerData();

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

        data_student_get();

        sharedanak  = getActivity().getSharedPreferences(my_shared_anak, Context.MODE_PRIVATE);
        Nama_lengkap        = sharedanak.getString(TAG_NAMA_LENGKAP,null);
        Nis                 = sharedanak.getString(TAG_NIS,null);
        Nisn                = sharedanak.getString(TAG_NISN,null);
        Nik                 = sharedanak.getString(TAG_NIK,null);
        Rombel              = sharedanak.getString(TAG_ROMBEL,null);
        Jenis_kelamin       = sharedanak.getString(TAG_JENIS_KELAMIN,null);
        Agama               = sharedanak.getString(TAG_AGAMA,null);
        Tingkatan           = sharedanak.getString(TAG_TINGKATAN,null);
        Negara              = sharedanak.getString(TAG_KEWARGANEGARAAN,null);
        Kebutuhankhusus     = sharedanak.getString(TAG_KEBUTUHAN_KHUSUS,null);
        Tempat_lahir        = sharedanak.getString(TAG_TEMPAT_LAHIR,null);
        Tanggal_lahir       = sharedanak.getString(TAG_TANGGAL_LAHIR,null);


        buttonBerikutnya.setOnClickListener(view13 -> submitForm());

        buttonKembali.setOnClickListener(view14 -> ParentPager.setCurrentItem(getItem(-1), true));

        et_nis.setEnabled(false);
        et_nis.setFocusable(false);
        et_nisn.setFocusable(false);
        et_nisn.setEnabled(false);
        et_nik.setEnabled(false);
        et_nik.setFocusable(false);
        rb_laki.setEnabled(false);
        rb_wanita.setEnabled(false);
        rb_wna.setEnabled(false);
        rb_wni.setEnabled(false);
        sp_agama.setEnabled(false);
        sp_tingkatan.setEnabled(false);

        return view;
    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(getApplicationContext());

        final List<String> myData = db.getAllLabels();

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text, myData);
        //int spinnerPosition = spinnerArrayAdapter.getPosition(myString);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_negara_asal.setAdapter(spinnerArrayAdapter);
        et_negara_asal.setOnItemSelectedListener((parent, view, position, id) ->
                negaraasal = myData.get(position).toString());

    }
    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
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

    public void data_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization.toString(), school_code.toString(), student_id.toString(),parent_nik.toString());
        call.enqueue(new Callback<JSONResponse.DetailStudent>() {
            @Override
            public void onResponse(Call<JSONResponse.DetailStudent> call, Response<JSONResponse.DetailStudent> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.DetailStudent resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String DTS_SCS_0001 = getResources().getString(R.string.DTS_SCS_0001);
                    String DTS_ERR_0001 = getResources().getString(R.string.DTS_ERR_0001);

                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tingkatan_kelas = response.body().data.getEdulevel_id();
                        nama_lengkap = response.body().data.getFullname();
                        nis = response.body().data.getMember_code();
                        nisn = response.body().data.getNisn();
                        nik = response.body().data.getNik();
                        rombel = response.body().data.getRombel();
                        jenis_kelamin = response.body().data.getGender();
                        tempat_lahir = response.body().data.getBirth_place();
                        tanggal_lahir = response.body().data.getBirth_date();
                        religion = response.body().data.getReligion();
                        kebutuhan_khusus = response.body().data.getSpecial_needs();
                        kewarganegaraan = response.body().data.getCitizen_status();

                        et_nama_lengkap.setText(nama_lengkap);
                        et_nis.setText(nis);
                        et_nisn.setText(nisn);
                        et_nik.setText(nik);
                        et_rombel.setText(rombel);
                        et_tempat_lahir.setText(tempat_lahir);
                        et_tanggal.setText(tanggal_lahir);
                        et_kebutuhan_khusus.setText(kebutuhan_khusus);

                        if (tingkatan_kelas.equals("4")) {
                            kelas = "SD 1";
                        } else if (tingkatan_kelas.equals("5")) {
                            kelas = "SD 2";
                        } else if (tingkatan_kelas.equals("6")) {
                            kelas = "SD 3";
                        } else if (tingkatan_kelas.equals("7")) {
                            kelas = "SD 4";
                        } else if (tingkatan_kelas.equals("8")) {
                            kelas = "SD 5";
                        } else if (tingkatan_kelas.equals("9")) {
                            kelas = "SD 6";
                        } else if (tingkatan_kelas.equals("10")) {
                            kelas = "SMP 1";
                        } else if (tingkatan_kelas.equals("11")) {
                            kelas = "SMP 2";
                        } else if (tingkatan_kelas.equals("12")) {
                            kelas = "SMP 3";
                        } else if (tingkatan_kelas.equals("13")) {
                            kelas = "SMA/SMK 1";
                        } else if (tingkatan_kelas.equals("14")) {
                            kelas = "SMA/SMK 2";
                        } else if (tingkatan_kelas.equals("15")) {
                            kelas = "SMA/SMK 3";
                        }
                        final List<String> penghasil = new ArrayList<>(Arrays.asList(listSekolah));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, penghasil) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint
                                    return false;
                                } else {
                                    return true;
                                }
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

                        int spinnerPosition = ArrayAdapter.getPosition(kelas);
                        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_tingkatan.setAdapter(ArrayAdapter);
                        sp_tingkatan.setSelection(spinnerPosition);
                        sp_tingkatan.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                kelas = penghasil.get(position);

                            }
                        });

                        kelas = sp_tingkatan.getSelectedItem().toString();

                        if (kelas.toString().equals("SD 1")) {
                            levelkelas = "4";
                        } else if (kelas.toString().equals("SD 2")) {
                            levelkelas = "5";
                        } else if (kelas.toString().equals("SD 3")) {
                            levelkelas = "6";
                        } else if (kelas.toString().equals("SD 4")) {
                            levelkelas = "7";
                        } else if (kelas.toString().equals("SD 5")) {
                            levelkelas = "8";
                        } else if (kelas.toString().equals("SD 6")) {
                            levelkelas = "9";
                        } else if (kelas.toString().equals("SMP 1")) {
                            levelkelas = "10";
                        } else if (kelas.toString().equals("SMP 2")) {
                            levelkelas = "11";
                        } else if (kelas.toString().equals("SMP 3")) {
                            levelkelas = "12";
                        } else if (kelas.toString().equals("SMA/SMK 1")) {
                            levelkelas = "13";
                        } else if (kelas.toString().equals("SMA/SMK 2")) {
                            levelkelas = "14";
                        } else if (kelas.toString().equals("SMA/SMK 3")) {
                            levelkelas = "15";
                        }
                        final List<String> agama = new ArrayList<>(Arrays.asList(listAgama));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> agamaadapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, agama) {
                            @Override
                            public boolean isEnabled(int position) {
                                if (position == 0) {
                                    // Disable the first item from Spinner
                                    // First item will be use for hint

                                    return false;
                                } else {
                                    return true;
                                }
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
                        int spinneragama = agamaadapter.getPosition(religion);
                        agamaadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_agama.setAdapter(agamaadapter);
                        sp_agama.setSelection(spinneragama);

                        if (jenis_kelamin.equals("Pria")) {
                            rb_laki.setChecked(true);
                            rb_wanita.setChecked(false);
                        } else if (jenis_kelamin.equals("Wanita")) {
                            rb_wanita.setChecked(true);
                            rb_laki.setChecked(false);
                        }
                        rb_laki.setOnClickListener(v ->
                                jenis_kelamin = getResources().getString(R.string.rb_laki));
                        rb_wanita.setOnClickListener(v ->
                                jenis_kelamin = getResources().getString(R.string.rb_wanita));

                        if (kewarganegaraan.equals("WNI")) {
                            rb_wni.setChecked(true);
                            rb_wna.setChecked(false);
                        } else if (kewarganegaraan.equals("WNA")) {
                            rb_wna.setChecked(true);
                            rb_wni.setChecked(false);
                        }

                        rb_wni.setOnClickListener(v -> {
                            kewarganegaraan = getResources().getString(R.string.rb_wni);
                            et_negara_asal.setVisibility(View.GONE);
                        });

                        rb_wna.setOnClickListener(v -> {
                            et_negara_asal.setVisibility(View.VISIBLE);
                            kewarganegaraan = et_negara_asal.getSelectedItem().toString();
                        });

                    } else {
                        if (status == 0 && code.equals("DTS_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), DTS_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();

                }
            }
            @Override
            public void onFailure(Call<JSONResponse.DetailStudent> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
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

    public void submitForm() {
        if (!validateTingkatan()) {
            return;
        }
        if (!validateNamaLengkap()) {
            return;
        }
        if (!validateNis()) {
            return;
        }
        if (!validateNisn()) {
            return;
        }
        if (!validateNik()){
            return;
        }
        if (!validateRombel()){
            return;
        }
        if (!validateJeniskelamin()){
            return;
        }
        if (!validateTempatlahir()){
            return;
        }
        if (!validateTanggallahir()){
            return;
        }
        if (!validateKebutuhankhusus()){
            return;
        }
        if (!validateAgama()){
            return;
        }
        if (!validateNegara()){
            return;
        }else {
            send_data();
            ParentPager.setCurrentItem(getItem(+1), true);
        }
    }

    private boolean validateNamaLengkap() {
        if (et_nama_lengkap.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi nama anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nama_lengkap);
            return false;
        } else {
            til_nama_lengkap.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNis() {
        if (et_nis.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi Nis anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nis);
            return false;
        } else {
            til_nis.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNisn() {
        if (et_nisn.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi nisn anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nisn);
            return false;
        } else {
            til_nisn.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNik() {
        if (et_nik.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi nik anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nik);
            return false;
        } else {
            til_nik.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateRombel() {
        if (et_rombel.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi rombel anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rombel);
            return false;
        } else {
            til_rombel.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateJeniskelamin() {
        if (jenis_kelamin.toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi jenis kelamin anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTempatlahir() {
        if (et_tempat_lahir.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi tempat lahir anak",Toast.LENGTH_LONG).show();
            requestFocus(et_tempat_lahir);
            return false;
        } else {
            til_tempat_lahir.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateTanggallahir() {
        if (et_tanggal.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi tanggal lahir anak",Toast.LENGTH_LONG).show();
            requestFocus(et_tanggal);
            return false;
        } else {
            til_tanggal_lahir.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateAgama() {
        if (sp_agama.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi agama anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateKebutuhankhusus() {
        if (et_kebutuhan_khusus.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi kebutuhan khusus anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kebutuhan_khusus);
            return false;
        } else {
            til_kebutuhan_khusus.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNegara() {
        if (kewarganegaraan == null) {
            Toast.makeText(getContext(),"Harap di isi negara anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTingkatan() {
        if (sp_tingkatan.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi tingkatan anak",Toast.LENGTH_LONG).show();
            return false;
        } else {

        }
        return true;
    }


    public void send_data(){
        SharedPreferences.Editor editor = sharedanak.edit();
        editor.putBoolean(session_status, true);
        editor.putString(TAG_NAMA_LENGKAP,et_nama_lengkap.getText().toString());
        editor.putString(TAG_NIS,et_nis.getText().toString());
        editor.putString(TAG_NISN,et_nisn.getText().toString());
        editor.putString(TAG_NIK,et_nik.getText().toString());
        editor.putString(TAG_TEMPAT_LAHIR,et_tempat_lahir.getText().toString());
        editor.putString(TAG_TANGGAL_LAHIR,et_tanggal.getText().toString());
        editor.putString(TAG_ROMBEL,et_rombel.getText().toString());
        editor.putString(TAG_JENIS_KELAMIN,(String)jenis_kelamin);
        editor.putString(TAG_KEBUTUHAN_KHUSUS,et_kebutuhan_khusus.getText().toString());
        editor.putString(TAG_TINGKATAN,sp_tingkatan.getSelectedItem().toString());
        editor.putString(TAG_AGAMA,sp_agama.getSelectedItem().toString());
        editor.putString(TAG_KEWARGANEGARAAN,(String) kewarganegaraan.toString());
        editor.commit();
        KontakAnakFragment kontakAnakFragment = new KontakAnakFragment();
        Bundle kontakanak = new Bundle();
        kontakanak.putString(TAG_NAMA_LENGKAP,et_nama_lengkap.getText().toString());
        kontakanak.putString(TAG_NIS,et_nis.getText().toString());
        kontakanak.putString(TAG_NISN,et_nisn.getText().toString());
        kontakanak.putString(TAG_NIK,et_nik.getText().toString());
        kontakanak.putString(TAG_TEMPAT_LAHIR,et_tempat_lahir.getText().toString());
        kontakanak.putString(TAG_TANGGAL_LAHIR,et_tanggal.getText().toString());
        kontakanak.putString(TAG_ROMBEL,et_rombel.getText().toString());
        kontakanak.putString(TAG_KEBUTUHAN_KHUSUS,et_kebutuhan_khusus.getText().toString());
        kontakanak.putString(TAG_JENIS_KELAMIN,(String) jenis_kelamin);
        kontakanak.putString(TAG_TINGKATAN,sp_tingkatan.getSelectedItem().toString());
        kontakanak.putString(TAG_AGAMA,sp_agama.getSelectedItem().toString());
        kontakanak.putString(TAG_KEWARGANEGARAAN,(String)kewarganegaraan.toString());
        kontakAnakFragment.setArguments(kontakanak);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragKontakAnak,kontakAnakFragment);
        fragmentTransaction.commit();
    }
}
