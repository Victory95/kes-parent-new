package com.fingertech.kes.Activity.Anak;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.Maps.full_maps;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.ProfileModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Service.DBHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EditProfileAnak extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

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
    int status;
    String code;
    ProgressDialog dialog;
    Auth mApiInterface;
    Spinner sp_tingkatan,sp_agama;
    RadioButton rb_laki,rb_wanita,rb_wni,rb_wna;
    EditText et_nama_lengkap,et_nis,et_nisn,et_nik,et_tempat_lahir,et_rombel,et_kebutuhan_khusus;
    String email,parent_id,student_nik,school_id,childrenname,school_name,fullname,student_id,member_id,parent_nik,authorization,school_code;
    String tingkatan_kelas,nama_lengkap,nis,nisn,nik,rombel,jenis_kelamin,tanggal_lahir,tempat_lahir,religion,kebutuhan_khusus,kewarganegaraan;
    String rt,rw,kelurahan,kecamatan,kodepos,jenis_tinggal,transportasi,alamat;
    String teleponrumah,handphone,skun,penerimaan_kps,nomor_kps;
    EditText et_teleponrumah,et_handphone,et_email,et_skun,et_penerimaankps,et_nomorkps;
    TextInputLayout til_email,til_handphone,til_teleponrumah,til_skun,til_nokps,til_penerimaankps;
    private EditText et_tanggal;
    private Spinner et_negara_asal;
    private Marker CurrLocationMarker;
    private TextView namatempat,alamattempattinggal;
    private LocationRequest mlocationRequest;
    private Location mlastLocation;
    private GoogleMap Mmap;
    private ImageView arrom;
    GoogleApiClient mGoogleApiClient;
    double CurrentLatitude;
    double CurrentLongitude;
    String Nama_lengkap,Nis,Nisn,Nik,Rombel,Tingkatan,Agama,Negara,Kebutuhankhusus,Tempat_lahir,Tanggal_lahir,Jenis_kelamin;
    String nokps,dusun;
    String studentdetailId,classroom_id,picture;
    EditText et_rt,et_rw,et_kelurahan,et_kecamatan,et_kodepos,et_jenis_tinggal,et_trasnportasi,et_alamat,et_dusun;
    Button cv_data,cv_kontak,cv_alamat;
    LinearLayout show_data,show_kontak,show_alamat;
    private TextView tv_line_boundaryLeft, tv_line_boundaryRight;
    CardView btn_search,btn_simpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_anak);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapTinggal);
        mapFragment.getMapAsync(this);
        namatempat          = (TextView)findViewById(R.id.nama_rumah);
        alamattempattinggal = (TextView)findViewById(R.id.alamat_rumah_anak);
        arrom               = (ImageView)findViewById(R.id.arrom);
        et_negara_asal      = (Spinner) findViewById(R.id.sp_negara_asal);
        et_tanggal          = (EditText)findViewById(R.id.et_tanggallahiR);
        et_nama_lengkap     = (EditText)findViewById(R.id.et_nama_lengkap_anak);
        et_nis              = (EditText)findViewById(R.id.et_nama_nis);
        et_nisn             = (EditText)findViewById(R.id.et_nama_nisn);
        et_rombel           = (EditText)findViewById(R.id.et_rombel);
        et_tempat_lahir     = (EditText)findViewById(R.id.et_tempatlahiR);
        et_nik              = (EditText)findViewById(R.id.et_Nik);
        et_kebutuhan_khusus = (EditText)findViewById(R.id.et_kebutuhan_khusus);
        sp_tingkatan        = (Spinner)findViewById(R.id.sp_tingkatan);
        sp_agama            = (Spinner)findViewById(R.id.sp_agama);
        rb_laki             = (RadioButton)findViewById(R.id.rb_laki_lakI);
        rb_wanita           = (RadioButton)findViewById(R.id.rb_perempuaN);
        rb_wni              = (RadioButton)findViewById(R.id.rb_wnI);
        rb_wna              = (RadioButton)findViewById(R.id.rb_wnA);
        et_teleponrumah     = (EditText)findViewById(R.id.et_nomor_Rumah);
        et_handphone        = (EditText)findViewById(R.id.et_nomor_Ponsel);
        et_email            = (EditText)findViewById(R.id.et_email_student);
        et_skun             = (EditText)findViewById(R.id.et_skun);
        et_penerimaankps    = (EditText)findViewById(R.id.et_PKPS);
        et_nomorkps         = (EditText)findViewById(R.id.et_kps);
        et_rt               = (EditText)findViewById(R.id.et_rt);
        et_rw               = (EditText)findViewById(R.id.et_rw);
        et_kelurahan        = (EditText)findViewById(R.id.et_kelurahan);
        et_kecamatan        = (EditText)findViewById(R.id.et_Kecamatan);
        et_kodepos          = (EditText)findViewById(R.id.et_kode_pos);
        et_jenis_tinggal    = (EditText)findViewById(R.id.et_status_tinggal);
        et_trasnportasi     = (EditText)findViewById(R.id.et_transportasi);
        et_alamat           = (EditText)findViewById(R.id.et_Alamat);
        et_dusun            = findViewById(R.id.et_dusun);
        btn_search          = findViewById(R.id.btn_search);
        btn_simpan          = findViewById(R.id.btn_simpan);
        cv_data         = findViewById(R.id.btn_data);
        cv_kontak       = findViewById(R.id.btn_kontak);
        cv_alamat       = findViewById(R.id.btn_alamat);
        show_data       = (LinearLayout)findViewById(R.id.show_data_anak);
        show_kontak     = (LinearLayout)findViewById(R.id.show_kontak);
        show_alamat     = (LinearLayout)findViewById(R.id.show_alamat);
        tv_line_boundaryLeft   = (TextView) findViewById(R.id.tv_line_boundaryLeft);
        tv_line_boundaryRight  = (TextView) findViewById(R.id.tv_line_boundaryRight);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        Calendar calendar = Calendar.getInstance();

        authorization = getIntent().getStringExtra("authorization");
        school_code   = getIntent().getStringExtra("school_code");
        student_id    = getIntent().getStringExtra("student_id");
        parent_nik    = getIntent().getStringExtra("parent_nik");
        loadSpinnerData();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbaredit);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileAnak.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//i adalah tahun, i1 adalah bulan dan i2 adalah hari
                //Respon dari dialog, di convert ke format tanggal yang diinginkan lalu setelah itu ditampilkan
                et_tanggal.setText(convertDate(i, i1, i2));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        et_tanggal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
            }
        });

        et_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
                }
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfileAnak.this, full_maps.class);
                startActivityForResult(intent,1);
            }
        });

        cv_data.setBackground(ContextCompat.getDrawable(EditProfileAnak.this, R.drawable.rectangle_line_blue));
        cv_data.setTextColor(getResources().getColor(R.color.default_background));
        tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

        ////// deactive
        cv_kontak.setBackgroundColor(Color.TRANSPARENT);
        cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));

        tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

        cv_alamat.setBackgroundColor(Color.TRANSPARENT);
        cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
        show_data.setVisibility(View.VISIBLE);
        show_alamat.setVisibility(View.GONE);
        show_kontak.setVisibility(View.GONE);

        cv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_data.setBackground(ContextCompat.getDrawable(EditProfileAnak.this, R.drawable.rectangle_line_blue));
                cv_data.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

                ////// deactive
                cv_kontak.setBackgroundColor(Color.TRANSPARENT);
                cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

                cv_alamat.setBackgroundColor(Color.TRANSPARENT);
                cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.VISIBLE);
                show_alamat.setVisibility(View.GONE);
                show_kontak.setVisibility(View.GONE);
            }
        });

        cv_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_kontak.setBackground(ContextCompat.getDrawable(EditProfileAnak.this, R.drawable.rectangle_line_blue));
                cv_kontak.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.colorPrimary));

                ////// deactive
                cv_data.setBackgroundColor(Color.TRANSPARENT);
                cv_data.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.default_background));

                cv_alamat.setBackgroundColor(Color.TRANSPARENT);
                cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.GONE);
                show_alamat.setVisibility(View.GONE);
                show_kontak.setVisibility(View.VISIBLE);
            }
        });

        cv_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_alamat.setBackground(ContextCompat.getDrawable(EditProfileAnak.this, R.drawable.rectangle_line_blue));
                cv_alamat.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.colorPrimary));

                ////// deactive
                cv_data.setBackgroundColor(Color.TRANSPARENT);
                cv_data.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

                cv_kontak.setBackgroundColor(Color.TRANSPARENT);
                cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.GONE);
                show_alamat.setVisibility(View.VISIBLE);
                show_kontak.setVisibility(View.GONE);
            }
        });

        data_student_get();
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
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

        }if (!validateEmail()) {
            return;
        }
        if (!validateHandphone()) {
            return;
        }
        if (!validateTelepon()) {
            return;
        }
        if (!validateNomorkps()){
            return;
        }
        if (!validatePenerimaan()){
            return;
        }
        if (!validateSkun()){
            return;

        }if (!validateAlamat()) {
            return;
        }
        if (!validateRt()) {
            return;
        }
        if (!validateRw()) {
            return;
        }
        if (!validateKelurahan()){
            return;
        }
        if (!validateKecamatan()){
            return;
        }
        if (!validateKodepos()){
            return;

        }if (!validateJenistinggal()){
            return;

        }if (!validateTransportasi()){
            return;

        } else {
            update_detail();
        }
    }

    private boolean validateNamaLengkap() {
        if (et_nama_lengkap.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi nama anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nama_lengkap);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateNis() {
        if (et_nis.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi Nis anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nis);
            return false;
        } else {

        }

        return true;
    }
    private boolean validateNisn() {
        if (et_nisn.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi nisn anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nisn);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateNik() {
        if (et_nik.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi nik anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nik);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateRombel() {
        if (et_rombel.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi rombel anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rombel);
            return false;
        } else {

        }

        return true;
    }
    private boolean validateJeniskelamin() {
        if (jenis_kelamin.toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi jenis kelamin anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTempatlahir() {
        if (et_tempat_lahir.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi tempat lahir anak",Toast.LENGTH_LONG).show();
            requestFocus(et_tempat_lahir);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTanggallahir() {
        if (et_tanggal.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi tanggal lahir anak",Toast.LENGTH_LONG).show();
            requestFocus(et_tanggal);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateAgama() {
        if (sp_agama.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi agama anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateKebutuhankhusus() {
        if (et_kebutuhan_khusus.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi kebutuhan khusus anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kebutuhan_khusus);
            return false;
        } else {

        }

        return true;
    }
    private boolean validateNegara() {
        if (kewarganegaraan == null) {
            Toast.makeText(getApplicationContext(),"Harap di isi negara anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTingkatan() {
        if (sp_tingkatan.getSelectedItem().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi tingkatan anak",Toast.LENGTH_LONG).show();
            return false;
        } else {
        }

        return true;
    }
    private boolean validateEmail() {
        if (et_email.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi email anak anda",Toast.LENGTH_LONG).show();
            requestFocus(et_email);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateHandphone() {
        if (et_handphone.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi no hp anak",Toast.LENGTH_LONG).show();
            requestFocus(et_handphone);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTelepon() {
        if (et_teleponrumah.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi telepon rumah anak",Toast.LENGTH_LONG).show();
            requestFocus(et_teleponrumah);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateNomorkps() {
        if (et_nomorkps.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi no kps anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nomorkps);
            return false;
        } else {
        }

        return true;
    }
    private boolean validatePenerimaan() {
        if (et_penerimaankps.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi penerimaan anak",Toast.LENGTH_LONG).show();
            requestFocus(et_penerimaankps);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateSkun() {
        if (et_skun.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi skun anak",Toast.LENGTH_LONG).show();
            requestFocus(et_skun);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateAlamat() {
        if (et_alamat.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi alamat anak",Toast.LENGTH_LONG).show();
            requestFocus(et_alamat);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateJenistinggal() {
        if (et_jenis_tinggal.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi jenis tinggal anak",Toast.LENGTH_LONG).show();
            requestFocus(et_jenis_tinggal);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateKecamatan() {
        if (et_kecamatan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi kecamatan anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kecamatan);
            return false;
        } else {

        }

        return true;
    }
    private boolean validateKelurahan() {
        if (et_kelurahan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi kelurahan anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kelurahan);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateKodepos() {
        if (et_kodepos.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi kode pos anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kodepos);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateRt() {
        if (et_rt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi rt anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rt);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateRw() {
        if (et_rw.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi rw anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rw);
            return false;
        } else {
        }

        return true;
    }
    private boolean validateTransportasi() {
        if (et_trasnportasi.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),"Harap di isi transportasi anak",Toast.LENGTH_LONG).show();
            requestFocus(et_trasnportasi);
            return false;
        } else {
        }

        return true;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


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
    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(EditProfileAnak.this);

        final List<String> myData = db.getAllLabels();

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(EditProfileAnak.this,R.layout.spinner_full, myData);
        //int spinnerPosition = spinnerArrayAdapter.getPosition(myString);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_negara_asal.setAdapter(spinnerArrayAdapter);
        et_negara_asal.setOnItemSelectedListener((parent, view, position, id) -> kewarganegaraan = myData.get(position).toString());

    }

    public void data_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization.toString(), school_code.toLowerCase().toString(), student_id.toString(),parent_nik.toString());
        call.enqueue(new Callback<JSONResponse.DetailStudent>() {
            @Override
            public void onResponse(Call<JSONResponse.DetailStudent> call, Response<JSONResponse.DetailStudent> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();

                JSONResponse.DetailStudent resource = response.body();
                status = resource.status;
                code = resource.code;

                String DTS_SCS_0001 = getResources().getString(R.string.DTS_SCS_0001);
                String DTS_ERR_0001 = getResources().getString(R.string.DTS_ERR_0001);

                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    tingkatan_kelas     = response.body().data.getEdulevel_id();
                    nama_lengkap        = response.body().data.getFullname();
                    nis                 = response.body().data.getMember_code();
                    nisn                = response.body().data.getNisn();
                    nik                 = response.body().data.getNik();
                    rombel              = response.body().data.getRombel();
                    jenis_kelamin       = response.body().data.getGender();
                    tempat_lahir        = response.body().data.getBirth_place();
                    tanggal_lahir       = response.body().data.getBirth_date();
                    religion            = response.body().data.getReligion();
                    kebutuhan_khusus    = response.body().data.getSpecial_needs();
                    kewarganegaraan     = response.body().data.getCitizen_status();
                    teleponrumah        = response.body().data.getHome_phone();
                    handphone           = response.body().data.getMobile_phone();
                    email               = response.body().data.getEmail();
                    skun                = response.body().data.getSkhun();
                    penerimaan_kps      = response.body().data.getPenerima_kps();
                    nomor_kps           = response.body().data.getNo_kps();
                    studentdetailId     = response.body().getData().getStudentdetailid();
                    dusun               = response.body().getData().getDusun();
                    rt                  = response.body().data.getRt();
                    rw                  = response.body().data.getRw();
                    kelurahan           = response.body().data.getKelurahan();
                    kecamatan           = response.body().data.getKecamatan();
                    kodepos             = response.body().data.getPost_code();
                    jenis_tinggal       = response.body().data.getJenis_tinggal();
                    transportasi        = response.body().data.getTransportasi();
                    alamat              = response.body().data.getAddress();
                    CurrentLatitude     = Double.parseDouble(response.body().data.getLatitude());
                    CurrentLongitude    = Double.parseDouble(response.body().data.getLongitude());
                    classroom_id        = response.body().getData().getClassroom_id();
                    picture             = response.body().getData().getPicture();

                    et_rt.setText(rt);
                    et_rw.setText(rw);
                    et_kelurahan.setText(kelurahan);
                    et_kecamatan.setText(kecamatan);
                    et_kodepos.setText(kodepos);
                    et_jenis_tinggal.setText(jenis_tinggal);
                    et_trasnportasi.setText(transportasi);
                    et_alamat.setText(alamat);
                    et_dusun.setText(dusun);

                    et_teleponrumah.setText(teleponrumah);
                    et_handphone.setText(handphone);
                    et_email.setText(email);
                    et_skun.setText(skun);
                    et_penerimaankps.setText(penerimaan_kps);
                    et_nomorkps.setText(nomor_kps);

                    et_nama_lengkap.setText(nama_lengkap);
                    et_nis.setText(nis);
                    et_nisn.setText(nisn);
                    et_nik.setText(nik);
                    et_rombel.setText(rombel);
                    et_tempat_lahir.setText(tempat_lahir);
                    et_tanggal.setText(tanggal_lahir);
                    et_kebutuhan_khusus.setText(kebutuhan_khusus);

                    if(tingkatan_kelas.equals("4")){
                        kelas = "SD 1";
                    }else if(tingkatan_kelas.equals("5")){
                        kelas = "SD 2";
                    }else if(tingkatan_kelas.equals("6")){
                        kelas = "SD 3";
                    }else if(tingkatan_kelas.equals("7")){
                        kelas = "SD 4";
                    }else if(tingkatan_kelas.equals("8")){
                        kelas = "SD 5";
                    }else if(tingkatan_kelas.equals("9")){
                        kelas = "SD 6";
                    }else if(tingkatan_kelas.equals("10")){
                        kelas = "SMP 1";
                    }else if(tingkatan_kelas.equals("11")){
                        kelas = "SMP 2";
                    }else if(tingkatan_kelas.equals("12")){
                        kelas = "SMP 3";
                    }else if(tingkatan_kelas.equals("13")){
                        kelas = "SMA/SMK 1";
                    }else if(tingkatan_kelas.equals("14")){
                        kelas = "SMA/SMK 2";
                    }else if(tingkatan_kelas.equals("15")){
                        kelas = "SMA/SMK 3";
                    }
                    final List<String> penghasil = new ArrayList<>(Arrays.asList(listSekolah));
                    // Initializing an ArrayAdapter
                    final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                            EditProfileAnak.this,R.layout.spinner_full,penghasil){
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

                    if (kelas.toString().equals("SD 1")){
                        levelkelas = "4";
                    }else if (kelas.toString().equals("SD 2")){
                        levelkelas = "5";
                    }else if (kelas.toString().equals("SD 3")){
                        levelkelas = "6";
                    }else if (kelas.toString().equals("SD 4")){
                        levelkelas = "7";
                    }else if (kelas.toString().equals("SD 5")){
                        levelkelas = "8";
                    }else if (kelas.toString().equals("SD 6")){
                        levelkelas = "9";
                    }else if (kelas.toString().equals("SMP 1")){
                        levelkelas = "10";
                    }else if (kelas.toString().equals("SMP 2")){
                        levelkelas = "11";
                    }else if (kelas.toString().equals("SMP 3")){
                        levelkelas = "12";
                    }else if (kelas.toString().equals("SMA/SMK 1")){
                        levelkelas = "13";
                    }else if (kelas.toString().equals("SMA/SMK 2")){
                        levelkelas = "14";
                    }else if (kelas.toString().equals("SMA/SMK 3")){
                        levelkelas = "15";
                    }
                    final List<String> agama = new ArrayList<>(Arrays.asList(listAgama));
                    // Initializing an ArrayAdapter
                    final ArrayAdapter<String> agamaadapter = new ArrayAdapter<String>(
                            EditProfileAnak.this,R.layout.spinner_full,agama){
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
                    int spinneragama = agamaadapter.getPosition(religion);
                    agamaadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                    sp_agama.setAdapter(agamaadapter);
                    sp_agama.setSelection(spinneragama);

                    if (jenis_kelamin.equals("Pria")){
                        rb_laki.setChecked(true);
                        rb_wanita.setChecked(false);
                    }else if(jenis_kelamin.equals("Wanita")){
                        rb_wanita.setChecked(true);
                        rb_laki.setChecked(false);
                    }
                    rb_laki.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jenis_kelamin = getResources().getString(R.string.rb_laki);
                        }
                    });
                    rb_wanita.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jenis_kelamin = getResources().getString(R.string.rb_wanita);
                        }
                    });
                    if (kewarganegaraan.equals("WNI")){
                        rb_wni.setChecked(true);
                        rb_wna.setChecked(false);
                    }else if (kewarganegaraan.equals("WNA")){
                        rb_wna.setChecked(true);
                        rb_wni.setChecked(false);
                    }

                    rb_wni.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            kewarganegaraan = getResources().getString(R.string.rb_wni);
                            et_negara_asal.setVisibility(View.GONE);
                        }
                    });

                    rb_wna.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_negara_asal.setVisibility(View.VISIBLE);
                            kewarganegaraan = et_negara_asal.getSelectedItem().toString();
                        }
                    });

                } else {
                    if(status == 0 && code.equals("DTS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DTS_ERR_0001, Toast.LENGTH_LONG).show();
                    }
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
        dialog = new ProgressDialog(EditProfileAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(1000);
        mlocationRequest.setFastestInterval(1000);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(EditProfileAnak.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();

        }
        //Place current location marker
        final LatLng latLng = new LatLng(CurrentLatitude, CurrentLongitude);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(bitmapDescriptorFromVector(EditProfileAnak.this, R.drawable.ic_map));

        //move map camera
        Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }
        CurrentLatitude     = latLng.latitude;
        CurrentLongitude    = latLng.longitude;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(EditProfileAnak.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                Mmap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            Mmap.setMyLocationEnabled(true);
        }
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();
        }
    }
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(EditProfileAnak.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("alamat");
                double lati = data.getDoubleExtra("latitude",0.0);
                double longi = data.getDoubleExtra("longitude",0.0);

                final LatLng latLng1 = new LatLng(lati,longi);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng1.latitude, latLng1.longitude)).zoom(16).build();

                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng1);
                markerOptions.title("Current Position");
                markerOptions.icon(bitmapDescriptorFromVector(EditProfileAnak.this, R.drawable.ic_map));

                //move map camera
                Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(CurrLocationMarker!= null){
                    CurrLocationMarker.remove();}
                CurrLocationMarker = Mmap.addMarker(markerOptions);
                alamattempattinggal.setText(strEditText);
                CurrentLatitude     = lati;
                CurrentLongitude    = longi;
            }
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public void update_detail(){

        Call<JSONResponse> postCall = mApiInterface.update_student_detail_put(authorization.toString(),studentdetailId.toString(), school_code.toLowerCase().toString(), student_id.toString(), et_rombel.getText().toString(), et_kebutuhan_khusus.getText().toString(), et_rt.getText().toString(),et_rw.getText().toString(),et_dusun.getText().toString(),et_kelurahan.getText().toString(),et_kecamatan.getText().toString(),et_kodepos.getText().toString(),et_jenis_tinggal.getText().toString(),et_trasnportasi.getText().toString(),String.valueOf(CurrentLatitude),String.valueOf(CurrentLongitude),et_teleponrumah.getText().toString(),et_skun.getText().toString(),et_penerimaankps.getText().toString(),et_nomorkps.getText().toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("UST_SCS_0001")){
                    update_member();
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal mengirim", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void update_member(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.update_student_member_put(authorization.toString(),student_id.toString(), school_code.toLowerCase().toString(), et_nama_lengkap.getText().toString(), jenis_kelamin.toString(), et_tempat_lahir.getText().toString(), et_tanggal.getText().toString(),kewarganegaraan.toString(),sp_agama.getSelectedItem().toString(),et_alamat.getText().toString(),et_handphone.getText().toString());
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;
                ProfileModel profileModel;
                if (status == 1 && code.equals("USTM_SCS_0001")){
                    Intent intent = new Intent(EditProfileAnak.this, ProfilAnak.class);
                    intent.putExtra("authorization",authorization);
                    intent.putExtra("school_code",school_code);
                    intent.putExtra("student_id",student_id);
                    intent.putExtra("parent_nik",parent_nik);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal mengirim", Toast.LENGTH_LONG).show();
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
