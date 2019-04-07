package com.fingertech.kes.Activity.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.CustomView.DialogFactorykps;
import com.fingertech.kes.Activity.CustomView.DialogKps;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class KontakAnakFragment extends Fragment {


    public static KontakAnakFragment newInstance(){
        // Required empty public constructor
        KontakAnakFragment  Fragment = new KontakAnakFragment();
        return Fragment;
    }
    ViewPager ParentPager;
    AnakMain anakMain;
    Button buttonBerikutnya,buttonKembali;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private AnakMain.FragmentAdapter fragmentAdapter;
    String Nama_lengkap,Nis,Nisn,Nik,Rombel,Tingkatan,Agama,Negara,Kebutuhankhusus,Tempat_lahir,Tanggal_lahir,Jenis_kelamin;

    private String[] listkps ={
            "Apakah anda mempunyai KPS",
            "Ya",
            "Tidak"
    };

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    public static final String my_shared_anak   = "my_shared_anak";


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

    public static final String TAG_TELEPON_RUMAH     = "telepon_rumah";
    public static final String TAG_HANDPHONE         = "handphone";
    public static final String TAG_EMAIL             = "email";
    public static final String TAG_SKUN              = "skun";
    public static final String TAG_PENERIMAANKPS     = "penerimaan_kps";
    public static final String TAG_NOKPS             = "no_kps";

    SharedPreferences sharedanak;

    SharedPreferences sharedpreferences,sharedviewpager;
    String teleponrumah,handphone,email,skun,penerimaan_kps,nomor_kps;
    EditText et_teleponrumah,et_handphone,et_email,et_skun,et_nomorkps;
    String parent_id,student_nik,school_id,childrenname,school_name,fullname,student_id,member_id,parent_nik,authorization,school_code;
    TextInputLayout til_email,til_handphone,til_teleponrumah,til_skun,til_nokps;
    int status;
    String code;
    ProgressDialog dialog;
    Auth mApiInterface;
    Spinner sp_kps;
    TextView hint_kps;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kontak_anak, container, false);
        anakMain          = (AnakMain)getActivity();
        ParentPager         = anakMain.findViewById(R.id.PagerAnak);
        indicator           = view.findViewById(R.id.indicators);
        buttonKembali       = view.findViewById(R.id.btn_back);
        buttonBerikutnya    = view.findViewById(R.id.btn_next);
        fragmentAdapter     = new AnakMain.FragmentAdapter(getActivity().getSupportFragmentManager());
        et_teleponrumah     = view.findViewById(R.id.et_nomor_Rumah);
        et_handphone        = view.findViewById(R.id.et_nomor_Ponsel);
        et_email            = view.findViewById(R.id.et_email_student);
        et_skun             = view.findViewById(R.id.et_skun);
        et_nomorkps         = view.findViewById(R.id.et_kps);
        til_email           = view.findViewById(R.id.til_e_mail);
        til_handphone       = view.findViewById(R.id.til_nomor_Ponsel);
        til_nokps           = view.findViewById(R.id.til_kps);
        til_teleponrumah    = view.findViewById(R.id.til_nomor_Rumah);
        til_skun            = view.findViewById(R.id.til_skun);
        sp_kps              = view.findViewById(R.id.sp_kps);
        hint_kps            = view.findViewById(R.id.kps_hint);

        hint_kps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] close = new String[1];
                DialogKps dialogKps =
                        DialogFactorykps.makeSuccessDialog("Selamat! \n Anda telah berhasil mengakses anak anda yang bernama '"+childrenname+" ' yang bersekolah di '"+school_name,
                                "Demi kelancaran akses dalam memantau perkembangan pendidikan anak anda melalui KES, silahkan isi dengan sebaik-baiknya form berikut ini.",
                                "Ok",
                                new DialogKps.ButtonDialogAction() {
                                    @Override
                                    public void onButtonClicked() {
                                        close[0] = "ok";
                                    }
                                });

                dialogKps.show(getActivity().getSupportFragmentManager(), DialogKps.TAG);

                if (close.equals("ok")){
                    dialogKps.closeDialog();
                }
            }
        });
        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm(); }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
        });

        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[2].setBackgroundResource(R.drawable.selected_item);

        mApiInterface     = ApiClient.getClient().create(Auth.class);
        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        school_id     = sharedpreferences.getString(TAG_SCHOOL_ID,"school_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        childrenname  = sharedpreferences.getString(TAG_NAMA_ANAK,"childrenname");
        school_name   = sharedpreferences.getString(TAG_NAMA_SEKOLAH,"school_name");
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,"school_code");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");


        sharedanak  = getActivity().getSharedPreferences(DataAnakFragment.my_shared_anak,Context.MODE_PRIVATE);
        Nama_lengkap        = sharedanak.getString(TAG_NAMA_LENGKAP,"");
        Nis                 = sharedanak.getString(TAG_NIS,"");
        Nisn                = sharedanak.getString(TAG_NISN,"");
        Nik                 = sharedanak.getString(TAG_NIK,"");
        Tempat_lahir        = sharedanak.getString(TAG_TEMPAT_LAHIR,"");
        Tanggal_lahir       = sharedanak.getString(TAG_TANGGAL_LAHIR,"");
        Rombel              = sharedanak.getString(TAG_ROMBEL,"");
        Kebutuhankhusus     = sharedanak.getString(TAG_KEBUTUHAN_KHUSUS,"");
        Jenis_kelamin       = sharedanak.getString(TAG_JENIS_KELAMIN,"");
        Tingkatan           = sharedanak.getString(TAG_TINGKATAN,"");
        Agama               = sharedanak.getString(TAG_AGAMA,"");
        Negara              = sharedanak.getString(TAG_KEWARGANEGARAAN,"");


        data_student_get();

        return view;
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
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization, school_code, student_id, parent_nik);
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
                        int tingkat = Integer.parseInt(response.body().data.getEdulevel_id());
                        teleponrumah = response.body().data.getHome_phone();
                        handphone = response.body().data.getMobile_phone();
                        email = response.body().data.getEmail();
                        skun = response.body().data.getSkhun();
                        penerimaan_kps = response.body().data.getPenerima_kps();
                        nomor_kps = response.body().data.getNo_kps();

                        et_teleponrumah.setText(teleponrumah);
                        et_handphone.setText(handphone);
                        et_email.setText(email);
                        et_skun.setText(skun);
                        if (tingkat < 10) {
                            et_skun.setText("-");
                            til_skun.setVisibility(View.GONE);
                            et_skun.setVisibility(View.GONE);
                        } else {
                            til_skun.setVisibility(View.VISIBLE);
                            et_skun.setVisibility(View.VISIBLE);
                        }
                        final List<String> kps = new ArrayList<>(Arrays.asList(listkps));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> ArrayAdapters = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, kps) {
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

                        int spinnerPositions = ArrayAdapters.getPosition(penerimaan_kps);
                        ArrayAdapters.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_kps.setAdapter(ArrayAdapters);
                        sp_kps.setSelection(spinnerPositions);

                        sp_kps.setOnItemSelectedListener((parent, view, position, id) -> {
                            if (position > 0) {
                                if (position == 1) {
                                    penerimaan_kps = "Ya";
                                    til_nokps.setVisibility(View.VISIBLE);
                                    et_nomorkps.setVisibility(View.VISIBLE);
                                } else if (position == 2) {
                                    penerimaan_kps = "Tidak";
                                    til_nokps.setVisibility(View.GONE);
                                    et_nomorkps.setVisibility(View.GONE);
                                    et_nomorkps.setText("-");
                                }
                            }
                        });

                        if (penerimaan_kps.equals("Ya")) {
                            til_nokps.setVisibility(View.VISIBLE);
                            et_nomorkps.setVisibility(View.VISIBLE);
                        } else if (penerimaan_kps.equals("Tidak")) {
                            til_nokps.setVisibility(View.GONE);
                            et_nomorkps.setVisibility(View.GONE);
                            et_nomorkps.setText("-");
                        }
                        et_nomorkps.setText(nomor_kps);

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

        if (!validateEmail()) {
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
        if (!validateSkun()){
            return;

        }else {
            send_data();
            ParentPager.setCurrentItem(getItem(+1), true);
        }
    }

    private boolean validateEmail() {
        if (et_email.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi email anak anda",Toast.LENGTH_LONG).show();
            requestFocus(et_email);
            return false;
        } else {
            til_email.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateHandphone() {
        if (et_handphone.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi no hp anak",Toast.LENGTH_LONG).show();
            requestFocus(et_handphone);
            return false;
        } else {
            til_handphone.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateTelepon() {
        if (et_teleponrumah.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi telepon rumah anak",Toast.LENGTH_LONG).show();
            requestFocus(et_teleponrumah);
            return false;
        } else {
            til_teleponrumah.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNomorkps() {
        if (et_nomorkps.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi no kps anak",Toast.LENGTH_LONG).show();
            requestFocus(et_nomorkps);
            return false;
        } else {
            til_nokps.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateSkun() {
        if (et_skun.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi skun anak",Toast.LENGTH_LONG).show();
            requestFocus(et_skun);
            return false;
        } else {
            til_skun.setErrorEnabled(false);
        }

        return true;
    }

    public void send_data(){
        SharedPreferences.Editor editor = sharedanak.edit();
        editor.putBoolean(session_status, true);
        editor.putString(TAG_TELEPON_RUMAH,et_teleponrumah.getText().toString());
        editor.putString(TAG_HANDPHONE,et_handphone.getText().toString());
        editor.putString(TAG_EMAIL,et_email.getText().toString());
        editor.putString(TAG_SKUN,et_skun.getText().toString());
        editor.putString(TAG_PENERIMAANKPS,penerimaan_kps.toString());
        editor.putString(TAG_NOKPS,et_nomorkps.getText().toString());
        editor.commit();
        TempatTinggalFragment tempatTinggalFragment = new TempatTinggalFragment();
        Bundle tempattinggal = new Bundle();
        tempattinggal.putString(TAG_TELEPON_RUMAH,et_teleponrumah.getText().toString());
        tempattinggal.putString(TAG_HANDPHONE,et_handphone.getText().toString());
        tempattinggal.putString(TAG_EMAIL,et_email.getText().toString());
        tempattinggal.putString(TAG_SKUN,et_skun.getText().toString());
        tempattinggal.putString(TAG_PENERIMAANKPS,penerimaan_kps.toString());
        tempattinggal.putString(TAG_NOKPS,et_nomorkps.getText().toString());
        tempatTinggalFragment.setArguments(tempattinggal);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragTempatTinggal,tempatTinggalFragment);
        fragmentTransaction.commit();
    }

}
