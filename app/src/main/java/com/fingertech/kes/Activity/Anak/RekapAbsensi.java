package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fingertech.kes.Activity.Adapter.HariAbsenAdapter;
import com.fingertech.kes.Activity.Adapter.JamAdapter;
import com.fingertech.kes.Activity.Adapter.RekapAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.Activity.Model.DasarModel;
import com.fingertech.kes.Activity.Model.HariAbsenModel;
import com.fingertech.kes.Activity.Model.JamModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RekapAbsensi extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String authorization,school_code,classroom_id,student_id,bulan,tahun;
    Toolbar toolbar;
    Auth mApiInterface;
    int status;
    String code,jam_mulai,jam_selesai,tanggal_absen;
    ProgressDialog dialog;
    RecyclerView rv_jam_absen,rv_absen;
    List<JamModel> jamModelList = new ArrayList<>();
    JamModel jamModel;
    DasarModel dasarModel;
    JamAdapter jamAdapter;
    HariAbsenAdapter hariAbsenAdapter;
    HariAbsenModel hariAbsenModel;
    List<DasarModel> dasarModelList = new ArrayList<>();
    List<HariAbsenModel> hariAbsenModelList = new ArrayList<>();
    List<JSONResponse.DataHari> dataHariList = new ArrayList<>();

    private List<AbsensiModel> absensiModels;
    private List <AbsenModel> absenModelList = new ArrayList<>();

    private List<JSONResponse.ScheduleClassItem> scheduleClassItemList = new ArrayList<>();
    private List<JSONResponse.JadwalData> jadwalDataList = new ArrayList<>();
    private AbsensiModel absensiModel;
    private AbsenModel absenModel;
    private List<JSONResponse.DataJam> dataJamList = new ArrayList<>();

    private RekapAdapter rekapAdapter;
    String days_name;
    String  daysid, day_type,month, day_status;
    String hari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rekap_absensi);
        toolbar         = findViewById(R.id.toolbar_rekap);
        rv_absen        = findViewById(R.id.rv_absen);
        rv_jam_absen    = findViewById(R.id.rv_jam_absen);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        bulan               = sharedPreferences.getString("bulan",null);
        tahun               = sharedPreferences.getString("tahun",null);
        dapat_absen();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
        dialog = new ProgressDialog(RekapAbsensi.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public void dapat_absen(){
        progressBar();
        showDialog();
        Call<JSONResponse.AbsenSiswa> call = mApiInterface.kes_class_attendance_get(authorization.toString(),school_code.toLowerCase(),student_id.toString(),classroom_id.toString(),bulan.toString(),tahun.toString());
        call.enqueue(new Callback<JSONResponse.AbsenSiswa>() {
            @Override
            public void onResponse(Call<JSONResponse.AbsenSiswa> call, Response<JSONResponse.AbsenSiswa> response) {
                Log.i("KES",response.code() + "");
                hideDialog();
                JSONResponse.AbsenSiswa resource = response.body();
                status = resource.status;
                code   = resource.code;

                if (status == 1 & code.equals("DTS_SCS_0001")){
                    for (int i = 0;i < response.body().getData().size();i++){
                        jam_mulai = response.body().getData().get(i).getTimez_ok();
                        jam_selesai = response.body().getData().get(i).getTimez_finish();
                        jamModel = new JamModel();
                        jamModel.setJam_mulai(jam_mulai);
                        jamModel.setJam_selesai(jam_selesai);
                        jamModelList.add(jamModel);
                        for (int o = 0;o <response.body().getData().get(i).getDays().size();o++){
                            dataHariList = response.body().getData().get(i).getDays();
                        }
                    }

                    jamAdapter = new JamAdapter(jamModelList);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(RekapAbsensi.this);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    rv_jam_absen.setLayoutManager(layoutManager);
                    rv_jam_absen.setAdapter(jamAdapter);

                    for (JSONResponse.DataHari dataHari : dataHariList){
                        hariAbsenModel = new HariAbsenModel();
                        hariAbsenModel.setTanggal(dataHari.getDay_id());
                        hariAbsenModel.setBulan(bulan);
                        hariAbsenModel.setTahun(tahun);
                        hariAbsenModelList.add(hariAbsenModel);
                    }
                    dapat_mapel();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.AbsenSiswa> call, Throwable t) {
                Log.d("Gagal",t.toString());
                hideDialog();
            }
        });
    }
    public void dapat_mapel(){
        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization,school_code.toLowerCase(),student_id,classroom_id);
        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalPelajaran> call, Response<JSONResponse.JadwalPelajaran> response) {
                Log.d("Success",response.code()+"");
                JSONResponse.JadwalPelajaran resource = response.body();
                status = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("CSCH_SCS_0001")) {
                    jadwalDataList = response.body().getData().getClass_schedule();
                    for (int i = 0; i < response.body().getData().getClass_schedule().size(); i++) {
                        scheduleClassItemList = response.body().getData().getClass_schedule().get(i).getScheduleClass();
                        days_name = response.body().getData().getClass_schedule().get(i).getDayName();
                        day_status = response.body().getData().getClass_schedule().get(i).getDayStatus();
                        daysid = response.body().getData().getClass_schedule().get(i).getDayid();
                        day_type = response.body().getData().getClass_schedule().get(i).getDayType();

                        for (JSONResponse.ScheduleClassItem dataJam : scheduleClassItemList) {
                            absensiModel = new AbsensiModel();
//                                     absensiModel.setTanggal(tanggals);
                            absensiModel.setTimez_star(dataJam.getTimezOk());
                            absensiModel.setTimez_finish(dataJam.getTimezFinish());
                            absensiModel.setMapel(dataJam.getCourcesName());
                            absensiModel.setGuru(dataJam.getTeacherName());
                            absensiModels.add(absensiModel);
                        }
                        if (absenModelList != null) {
                            absenModelList.clear();
                            for (JSONResponse.DataJam dataJam : dataJamList) {
                                absenModel = new AbsenModel();
//                                      absenModel.setTanggal(tanggals);
                                absenModel.setTimez_star(dataJam.getTimez_ok());
                                absenModel.setTimez_finish(dataJam.getTimez_finish());
//                                      absenModel.setDay_id(dataJam.getDays().get(Integer.parseInt(day)-1).getAbsen_status());
                                absenModelList.add(absenModel);
                            }
                        }
                    }
                    hariAbsenAdapter = new HariAbsenAdapter(RekapAbsensi.this,hariAbsenModelList,absensiModels);
                    LinearLayoutManager layoutManagers = new LinearLayoutManager(RekapAbsensi.this);
                    layoutManagers.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rv_absen.setLayoutManager(layoutManagers);
                    rv_absen.setAdapter(hariAbsenAdapter);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalPelajaran> call, Throwable t) {
                Log.d("onfailure",t.toString());
            }
        });
    }
}
