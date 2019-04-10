package com.fingertech.kes.Activity.Fragment.UjianFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.BulanAdapter.UasAdapter;
import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Adapter.UjianAdapterTeratas;
import com.fingertech.kes.Activity.Anak.JadwalUjian;
import com.fingertech.kes.Activity.Anak.UjianJadwal;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.ModelUas;
import com.fingertech.kes.Activity.Model.UjianModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class UasFragment extends Fragment {


    public UasFragment() {
        // Required empty public constructor
    }

    UjianAdapter ujianAdapter;
    RecyclerView recyclerView;
    int status;
    String code;
    SharedPreferences sharedPreferences;
    String authorization, school_code, memberid, classroom_id, date, bulan_sekarang;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy", new Locale("in", "ID"));
    private DateFormat times_format = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
    Auth mApiInterface;
    String jam, tanggal, type, nilai, mapel, deskripsi, semester_id, start_date, end_date, semester, start_year, start_end;
    String jam_db, tanggal_db;
    Date month_now, month_db;
    UjianModel ujianModel;
    TextView hint_ujian;
    List<UjianModel> ujianModelList = new ArrayList<>();
    List<ItemUjian> itemUjianList = new ArrayList<>();

    private List<JSONResponse.DataSemester> dataSemesters;
    private List<JSONResponse.DataMapel> dataMapelList;
    private String[] tipe = {
            "FINAL",
            "MID",
            "UN"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uas, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization = sharedPreferences.getString("authorization", null);
        school_code = sharedPreferences.getString("school_code", null);
        memberid = sharedPreferences.getString("student_id", null);
        classroom_id = sharedPreferences.getString("classroom_id", null);

        mApiInterface = ApiClient.getClient().create(Auth.class);
        recyclerView = view.findViewById(R.id.recycleview_ujian);
        hint_ujian = view.findViewById(R.id.hint_ujian);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        bulan_sekarang = dateFormat.format(Calendar.getInstance().getTime());

            Check_Semester();
        return view;
    }

    private void Check_Semester() {

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(), school_code.toString().toLowerCase(), classroom_id.toString(), date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.CheckSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;
                    semester_id = response.body().getData();
                    dapat_semester();
                    Jadwal_ujian();
                    dapat_mapel();
                }
            }


            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }

    public void dapat_mapel() {
        Call<JSONResponse.ListMapel> call = mApiInterface.kes_list_cources_get(authorization.toString(), school_code.toLowerCase().toString(), classroom_id.toString());
        call.enqueue(new Callback<JSONResponse.ListMapel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapel> call, Response<JSONResponse.ListMapel> response) {
                Log.d("onResponse", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.ListMapel resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("KLC_SCS_0001")) {
                        dataMapelList = response.body().getData();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapel> call, Throwable t) {

            }
        });
    }

    private void Jadwal_ujian() {

        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(), school_code.toString().toLowerCase(), memberid.toString(), classroom_id.toString(), semester_id.toString());
        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
                Log.i("KES", response.code() + "");

                if (response.isSuccessful()) {
                    JSONResponse.JadwalUjian resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    ItemUjian itemUjian = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getType_id().equals("2")) {
                                jam = response.body().getData().get(i).getExam_time_ok();
                                tanggal = response.body().getData().get(i).getExam_date();
                                mapel = response.body().getData().get(i).getCources_name();
                                type = response.body().getData().get(i).getType_name();
                                deskripsi = response.body().getData().get(i).getExam_desc();
                                nilai = response.body().getData().get(i).getScore_value();
                                tanggal_db = converDate(response.body().getData().get(i).getExam_date());
                                jam_db = converJam(response.body().getData().get(i).getExam_time());
                                try {
                                    month_now = times_format.parse(bulan_sekarang);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Long bulan_now = month_now.getTime();

                                try {
                                    month_db = times_format.parse(tanggal_db);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Long bulan_db = month_db.getTime();
                                if (bulan_db.equals(bulan_now)) {
                                    ujianModel = new UjianModel();
                                    ujianModel.setDeskripsi(deskripsi);
                                    ujianModel.setNilai(nilai);
                                    ujianModel.setJam(jam_db);
                                    ujianModel.setMapel(mapel);
                                    ujianModel.setWaktu(converttanggal(tanggal) + " " + jam_db);
                                    ujianModel.setBulan(convertBulan(tanggal));
                                    ujianModel.setTanggal(convertTanggal(tanggal));
                                    ujianModel.setType_id(type);
                                    ujianModelList.add(ujianModel);
                                } else {
                                    itemUjian = new ItemUjian();
                                    itemUjian.setJam(jam);
                                    itemUjian.setTanggal(converttanggal(tanggal));
                                    itemUjian.setMapel(mapel);
                                    itemUjian.setType_id(type);
                                    itemUjian.setDeskripsi(deskripsi);
                                    itemUjian.setNilai(nilai);
                                    itemUjianList.add(itemUjian);
                                }
                            }
                            hint_ujian.setVisibility(View.GONE);
                            ujianAdapter = new UjianAdapter(itemUjianList, getActivity());
                            recyclerView.setOnFlingListener(null);
                            recyclerView.setLayoutManager(new VegaLayoutManager());
                            recyclerView.setAdapter(ujianAdapter);

                        }
                    }else {
                        hint_ujian.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }

    private void dapat_semester() {

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(), school_code.toLowerCase(), classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                if (response.isSuccessful()) {
                    JSONResponse.ListSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    String tahun_mulai, tahun_akhir;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getSemester_id().equals(semester_id)) {
                                semester = response.body().getData().get(i).getSemester_name();
                                start_date = response.body().getData().get(i).getStart_date();
                                end_date = response.body().getData().get(i).getEnd_date();
                            }
                            if (response.body().getData().get(i).getSemester_name().equals("Ganjil")) {
                                start_year = converTahun(response.body().getData().get(i).getStart_date());
                            } else if (response.body().getData().get(i).getSemester_name().equals("Genap")) {
                                start_end = converTahun(response.body().getData().get(i).getEnd_date());
                            }

                        }

                        dataSemesters = response.body().getData();

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    String converDate(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String convertTanggal(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String converttanggal(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("in", "ID"));

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("in", "ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String convertBulan(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String converJam(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String converTahun(String tahun) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }


    }
}


//    String authorization,memberid,school_code,classroom_id,type_ujian;
//    SharedPreferences sharedPreferences;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        DateFormat df   = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        date            = df.format(Calendar.getInstance().getTime());
//        mApiInterface   = ApiClient.getClient().create(Auth.class);
//        Bundle bundle   = this.getArguments();
//        if (bundle!=null) {
//            authorization   = getArguments().getString("authorization");
//            school_code     = getArguments().getString("school_code");
//            memberid        = getArguments().getString("student_id");
//            classroom_id    = getArguments().getString("classroom_id");
//            type_ujian      = getArguments().getString("type_ujian");
//            Check_Semester();
//        }
//    }
//
//    List<ModelUas> itemUjianList = new ArrayList<>();
//    UasAdapter ujianAdapter;
//    Auth mApiInterface;
//    RecyclerView rv_ujian;
//    ProgressDialog dialog;
//    int status;
//    String code;
//    String jam,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
//    TextView no_ujian;
//    String date;
//    public static final String my_viewpager_preferences = "my_ujian_preferences";
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_uas, container, false);
//        rv_ujian    = view.findViewById(R.id.recycleview_ujian);
//        no_ujian    = view.findViewById(R.id.hint_ujian);
//
//        return view;
//    }
//
//    private void Check_Semester(){
//        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
//        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
//            @Override
//            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
//                Log.i("KES", response.code() + "");
//                if (response.isSuccessful()) {
//                    JSONResponse.CheckSemester resource = response.body();
//
//                    status = resource.status;
//                    code = resource.code;
//                    semester_id = response.body().getData();
//                    dapat_semester();
//                    Jadwal_ujian();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
//                Log.d("onFailure", t.toString());
//
//            }
//
//        });
//    }
//    public void dapat_semester(){
//
//        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());
//
//        call.enqueue(new Callback<JSONResponse.ListSemester>() {
//
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
//                Log.i("KES", response.code() + "");
//
//                if (response.isSuccessful()) {
//                    JSONResponse.ListSemester resource = response.body();
//
//                    status = resource.status;
//                    code = resource.code;
//
//                    String tahun_mulai, tahun_akhir;
//                    if (status == 1 && code.equals("DTS_SCS_0001")) {
//                        for (int i = 0; i < response.body().getData().size(); i++) {
//                            if (response.body().getData().get(i).getSemester_id().equals(semester_id)) {
//                                semester = response.body().getData().get(i).getSemester_name();
//                                start_date = response.body().getData().get(i).getStart_date();
//                                end_date = response.body().getData().get(i).getEnd_date();
//                            }
//                            if (response.body().getData().get(i).getSemester_name().equals("Ganjil")) {
//                                start_year = converTahun(response.body().getData().get(i).getStart_date());
//                            } else if (response.body().getData().get(i).getSemester_name().equals("Genap")) {
//                                start_end = converTahun(response.body().getData().get(i).getEnd_date());
//                            }
////                            tv_semester.setText("Semester " + semester + " (" + start_year + "/" + start_end + ")");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
//                Log.d("onFailure", t.toString());
//                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        });
//    }
//    String converTahun(String tahun){
//        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//
//        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
//        try {
//            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
//            return e;
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//    private void Jadwal_ujian(){
//        progressBar();
//        showDialog();
//        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(),school_code.toString().toLowerCase(),memberid.toString(),classroom_id.toString(),semester_id.toString());
//        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {
//            @Override
//            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
//                Log.i("Ujian", response.code() + "");
//                hideDialog();
//                if (response.isSuccessful()) {
//                    JSONResponse.JadwalUjian resource = response.body();
//
//                    status = resource.status;
//                    code = resource.code;
//
//                    ModelUas itemUjian = null;
//                    if (status == 1 && code.equals("DTS_SCS_0001")) {
//                        if (itemUjianList!=null) {
//                            itemUjianList.clear();
//                            for (int i = 0; i < response.body().getData().size(); i++) {
//                                if (response.body().getData().get(i).getType_name().equals(type_ujian)) {
//                                    jam         = response.body().getData().get(i).getExam_time_ok();
//                                    tanggal     = response.body().getData().get(i).getExam_date();
//                                    mapel       = response.body().getData().get(i).getCources_name();
//                                    type        = response.body().getData().get(i).getType_name();
//                                    deskripsi   = response.body().getData().get(i).getExam_desc();
//                                    nilai       = response.body().getData().get(i).getScore_value();
//                                    itemUjian   = new ModelUas();
//                                    itemUjian.setJam(jam);
//                                    itemUjian.setTanggal(converttanggal(tanggal));
//                                    itemUjian.setMapel(mapel);
//                                    itemUjian.setType_id(type);
//                                    itemUjian.setDeskripsi(deskripsi);
//                                    itemUjian.setNilai(nilai);
//                                    itemUjianList.add(itemUjian);
//                                    no_ujian.setVisibility(View.GONE);
//                                    rv_ujian.setVisibility(View.VISIBLE);
//                                } else if (!response.body().getData().get(i).getType_name().equals(type_ujian)) {
//                                    no_ujian.setVisibility(View.VISIBLE);
//                                    rv_ujian.setVisibility(View.GONE);
//                                }
//                            }
//                            ujianAdapter = new UasAdapter(itemUjianList, getContext());
//                            ujianAdapter.notifyDataSetChanged();
//                            rv_ujian.setOnFlingListener(null);
//                            rv_ujian.setLayoutManager(new VegaLayoutManager());
//                            rv_ujian.setAdapter(ujianAdapter);
//                        }
//                    } else {
//                        hideKeyboard(getActivity());
//                        no_ujian.setVisibility(View.VISIBLE);
//                        rv_ujian.setVisibility(View.GONE);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
//                Log.d("UjianGagal", t.toString());
//                hideDialog();
//            }
//
//        });
//    }
//
//    String converttanggal(String tanggal){
//        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",new Locale("in","ID"));
//
//        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
//        try {
//            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
//            return e;
//        } catch (java.text.ParseException e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//    private void showDialog() {
//        if (!dialog.isShowing())
//            dialog.show();
//        dialog.setContentView(R.layout.progressbar);
//    }
//    private void hideDialog() {
//        if (dialog.isShowing())
//            dialog.dismiss();
//        dialog.setContentView(R.layout.progressbar);
//    }
//    public void progressBar(){
//        dialog = new ProgressDialog(getContext());
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        dialog.setIndeterminate(true);
//        dialog.setCancelable(false);
//    }
//    public static void hideKeyboard(Activity activity) {
//        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        //Find the currently focused view, so we can grab the correct window token from it.
//        View view = activity.getCurrentFocus();
//        //If no view currently has focus, create a new one, just so we can grab a window token from it
//        if (view == null) {
//            view = new View(activity);
//        }
//        if (imm != null) {
//            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
//    }