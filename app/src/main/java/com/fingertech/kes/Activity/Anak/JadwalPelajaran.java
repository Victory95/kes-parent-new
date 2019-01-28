package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalPelajaran extends AppCompatActivity {

    CardView btn_senin,btn_selasa,btn_rabu,btn_kamis,btn_jumat,btn_sabtu;
    ImageView arrow_senin,arrow_selasa,arrow_rabu,arrow_kamis,arrow_jumat,arrow_sabtu;
    TextView hint_senin,hint_selasa,hint_rabu,hint_kamis,hint_jumat,hint_sabtu;
    RecyclerView rv_senin,rv_selasa,rv_rabu,rv_kamis,rv_jumat,rv_sabtu;
    Toolbar toolbar;
    ProgressDialog dialog;
    Auth mApiInterface;
    int status;
    String code,schedule;

    String authorization,school_code,student_id,classroom_id;
    String days_name;
    String mapel;
    int lamber;
    String jamber;
    String jam_mulai;
    String jam_selesai;
    String guru,daysid,day_type,day_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_pelajaran);

        toolbar         = findViewById(R.id.toolbar);
        btn_senin       = findViewById(R.id.klik_senin);
        btn_selasa      = findViewById(R.id.klik_selasa);
        btn_rabu        = findViewById(R.id.klik_rabu);
        btn_kamis       = findViewById(R.id.klik_kamis);
        btn_jumat       = findViewById(R.id.klik_jumat);
        btn_sabtu       = findViewById(R.id.klik_sabtu);
        arrow_senin     = findViewById(R.id.arrow_senin);
        arrow_selasa    = findViewById(R.id.arrow_selasa);
        arrow_rabu      = findViewById(R.id.arrow_rabu);
        arrow_kamis     = findViewById(R.id.arrow_kamis);
        arrow_jumat     = findViewById(R.id.arrow_jumat);
        arrow_sabtu     = findViewById(R.id.arrow_sabtu);
        hint_senin      = findViewById(R.id.hint_senin);
        hint_selasa     = findViewById(R.id.hint_selasa);
        hint_rabu       = findViewById(R.id.hint_rabu);
        hint_kamis      = findViewById(R.id.hint_kamis);
        hint_jumat      = findViewById(R.id.hint_jumat);
        hint_sabtu      = findViewById(R.id.hint_sabtu);
        rv_senin        = findViewById(R.id.senin);
        rv_selasa       = findViewById(R.id.selasa);
        rv_rabu         = findViewById(R.id.rabu);
        rv_kamis        = findViewById(R.id.kamis);
        rv_jumat        = findViewById(R.id.jumat);
        rv_sabtu        = findViewById(R.id.sabtu);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        toolbar.setTitle("Jadwal Pelajaran");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        student_id      = getIntent().getStringExtra("student_id");
        classroom_id    = getIntent().getStringExtra("classroom_id");

        Jadwal_pelajaran();
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

    private void Jadwal_pelajaran(){
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {

            @Override
            public void onResponse(Call<JSONResponse.JadwalPelajaran> call, final Response<JSONResponse.JadwalPelajaran> response) {
                Log.i("KES", response.code() + "");
                hideDialog();

                JSONResponse.JadwalPelajaran resource = response.body();

                status = resource.status;
                code    = resource.code;

                ItemUjian itemUjian= null;
                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        JSONResponse.JadwalData jadwalData = resource.data.get(i);
                        days_name = jadwalData.getDayName();
                        day_status  = jadwalData.getDayStatus();
                        daysid      = jadwalData.getDayid();
                        day_type    = jadwalData.getDayType();
                        if (daysid.toString().equals("1") && days_name.toString().equals("Senin") && day_status.toString().equals("1") && day_type.toString().equals("1")) {
                            for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();
                                                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalPelajaran> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
            }

        });
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
        dialog = new ProgressDialog(JadwalPelajaran.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
