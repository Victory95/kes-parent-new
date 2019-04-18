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
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.UjianModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
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
    String waktu, tanggal,bulan, mapel, deskripsi, semester_id, start_date, end_date, semester, start_year, start_end;
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

    TextView tv_star,tv_semester;
    SpinKitView spinKitView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_uas, container, false);

        sharedPreferences   = this.getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization", null);
        school_code         = sharedPreferences.getString("school_code", null);
        memberid            = sharedPreferences.getString("student_id", null);
        classroom_id        = sharedPreferences.getString("classroom_id", null);

        mApiInterface   = ApiClient.getClient().create(Auth.class);
        recyclerView    = view.findViewById(R.id.recycleview_ujian);
        hint_ujian      = view.findViewById(R.id.hint_ujian);
        tv_star         = view.findViewById(R.id.tv_tanggal);
        tv_semester     = view.findViewById(R.id.tv_semesters);
        spinKitView     = view.findViewById(R.id.spin_kits);


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
                }
            }


            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }

    private void show_dialog(){
        Sprite sprite = new Wave();
        spinKitView.setIndeterminateDrawable(sprite);
        spinKitView.setVisibility(View.VISIBLE);
    }
    private void hide_dialog(){
        spinKitView.setVisibility(View.GONE);
    }
    private void Jadwal_ujian() {
        show_dialog();
        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(), school_code.toString().toLowerCase(), memberid.toString(), classroom_id.toString(), semester_id.toString());
        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
                Log.i("KES", response.code() + "");
                    hide_dialog();
                if (response.isSuccessful()) {
                    JSONResponse.JadwalUjian resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    ItemUjian itemUjian = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        if (itemUjianList!=null){
                            itemUjianList.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                if (contains(response.body().getData())) {
                                    if (response.body().getData().get(i).getType_id().equals("2")) {
                                        waktu = response.body().getData().get(i).getExam_time_ok();
                                        tanggal = response.body().getData().get(i).getExam_date();
                                        mapel = response.body().getData().get(i).getCources_name();
                                        deskripsi = response.body().getData().get(i).getExam_desc();
                                        itemUjian = new ItemUjian();
                                        itemUjian.setJam(convertjam(waktu));
                                        itemUjian.setTanggalujian(tanggal);
                                        itemUjian.setTanggal(convertTanggal(tanggal));
                                        itemUjian.setMapel(mapel);
                                        itemUjian.setDeskripsi(deskripsi);
                                        itemUjian.setBulan(convertBulan(tanggal));
                                        itemUjianList.add(itemUjian);
                                    }
                                    hint_ujian.setVisibility(View.GONE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                    ujianAdapter = new UjianAdapter(itemUjianList, getActivity());
                                    recyclerView.setOnFlingListener(null);
                                    recyclerView.setLayoutManager(new VegaLayoutManager());
                                    recyclerView.setAdapter(ujianAdapter);
                                }else {
                                    hint_ujian.setVisibility(View.VISIBLE);
                                    recyclerView.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }else {
                        hint_ujian.setVisibility(View.VISIBLE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hide_dialog();
            }

        });
    }
    boolean contains(List<JSONResponse.DataUjian> list) {
        for (JSONResponse.DataUjian item : list) {
            if (item.getType_id().equals("2")) {
                return true;
            }
        }
        return false;
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

                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getSemester_id().equals(semester_id)) {
                                semester    = response.body().getData().get(i).getSemester_name();
                                start_date  = response.body().getData().get(i).getStart_date();
                                end_date    = response.body().getData().get(i).getEnd_date();
                                tv_semester.setText("Semester "+semester);
                                tv_star.setText(converttanggal(start_date)+" sampai "+converttanggal(end_date));
                            }
                        }
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
    String converttanggal(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    String convertBulan(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM", new Locale("in","ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertjam(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("hh:mm a",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm",new Locale("in","ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}


