package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.HariAdapter.JumatAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.KamisAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.RabuAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.SelasaAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.SeninAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.HariModel.JadwalJumat;
import com.fingertech.kes.Activity.Model.HariModel.JadwalKamis;
import com.fingertech.kes.Activity.Model.HariModel.JadwalRabu;
import com.fingertech.kes.Activity.Model.HariModel.JadwalSenin;
import com.fingertech.kes.Activity.Model.HariModel.JadwalSelasa;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private List<JadwalSenin> itemlist;
    private List<JadwalSelasa> itemselasa;
    private List<JadwalRabu> itemRabu;
    private List<JadwalKamis> itemKamis;
    private List<JadwalJumat> itemJumat;
    SeninAdapter seninAdapter;
    SelasaAdapter selasaAdapter;
    RabuAdapter rabuAdapter;
    KamisAdapter kamisAdapter;
    JumatAdapter jumatAdapter;
    private Boolean clicked = false;

    String authorization,school_code,student_id,classroom_id;
    String days_name;
    String mapel;
    int lamber;
    String jamber;
    String jam_mulai;
    String jam_selesai;
    String guru,daysid,day_type,day_status;
    String date,day;
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

        if (getIntent() != null) {
            authorization = getIntent().getStringExtra("authorization");
            school_code = getIntent().getStringExtra("school_code");
            student_id = getIntent().getStringExtra("student_id");
            classroom_id = getIntent().getStringExtra("classroom_id");
            Jadwal_pelajaran();
        }else {
            Toast.makeText(JadwalPelajaran.this,"harap refersh kembali",Toast.LENGTH_LONG).show();
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        date = df.format(Calendar.getInstance().getTime());


        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dater = null;
        try {
            dater = inFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        day = outFormat.format(dater);

        if (day.equals("Senin")){
            hint_senin.setVisibility(View.GONE);
            rv_senin.setVisibility(View.VISIBLE);
            arrow_senin.setBackgroundResource(R.drawable.ic_up_arrow_white);
        }else if (day.equals("Selasa")){
            hint_selasa.setVisibility(View.GONE);
            rv_selasa.setVisibility(View.VISIBLE);
            arrow_selasa.setBackgroundResource(R.drawable.ic_up_arrow_white);
        }else if (day.equals("Rabu")){
            hint_rabu.setVisibility(View.GONE);
            rv_rabu.setVisibility(View.VISIBLE);
            arrow_rabu.setBackgroundResource(R.drawable.ic_up_arrow_white);
        }else if (day.equals("Kamis")){
            hint_kamis.setVisibility(View.GONE);
            rv_kamis.setVisibility(View.VISIBLE);
            arrow_kamis.setBackgroundResource(R.drawable.ic_up_arrow_white);
        }else if (day.equals("Jumat")){
            hint_jumat.setVisibility(View.GONE);
            rv_jumat.setVisibility(View.VISIBLE);
            arrow_jumat.setBackgroundResource(R.drawable.ic_up_arrow_white);
        }


        btn_senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_senin.setVisibility(View.VISIBLE);
                    rv_senin.setVisibility(View.GONE);
                    arrow_senin.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_senin.setVisibility(View.GONE);
                    rv_senin.setVisibility(View.VISIBLE);
                    arrow_senin.setBackgroundResource(R.drawable.ic_up_arrow_white);
                }
            }
        });
        btn_selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_selasa.setVisibility(View.VISIBLE);
                    rv_selasa.setVisibility(View.GONE);
                    arrow_selasa.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_selasa.setVisibility(View.GONE);
                    rv_selasa.setVisibility(View.VISIBLE);
                    arrow_selasa.setBackgroundResource(R.drawable.ic_up_arrow_white);
                }
            }
        });
        btn_rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_rabu.setVisibility(View.VISIBLE);
                    rv_rabu.setVisibility(View.GONE);
                    arrow_rabu.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_rabu.setVisibility(View.GONE);
                    rv_rabu.setVisibility(View.VISIBLE);
                    arrow_rabu.setBackgroundResource(R.drawable.ic_up_arrow_white);
                }
            }
        });
        btn_kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_kamis.setVisibility(View.VISIBLE);
                    rv_kamis.setVisibility(View.GONE);
                    arrow_kamis.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_kamis.setVisibility(View.GONE);
                    rv_kamis.setVisibility(View.VISIBLE);
                    arrow_kamis.setBackgroundResource(R.drawable.ic_up_arrow_white);
                }
            }
        });
        btn_jumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_jumat.setVisibility(View.VISIBLE);
                    rv_jumat.setVisibility(View.GONE);
                    arrow_jumat.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_jumat.setVisibility(View.GONE);
                    rv_jumat.setVisibility(View.VISIBLE);
                    arrow_jumat.setBackgroundResource(R.drawable.ic_up_arrow_white);
                }
            }
        });

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

    private void Jadwal_pelajaran(){
        progressBar();
        showDialog();
        if (authorization != null && school_code != null && student_id !=null && classroom_id != null) {

            Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization.toString(), school_code.toString().toLowerCase(), student_id.toString(), classroom_id.toString());

            call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {

                @Override
                public void onResponse(Call<JSONResponse.JadwalPelajaran> call, final Response<JSONResponse.JadwalPelajaran> response) {
                    Log.i("KES", response.code() + "");
                    hideDialog();

                    JSONResponse.JadwalPelajaran resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    JadwalSenin jadwalSenin = null;
                    JadwalSelasa jadwalSelasa = null;
                    JadwalRabu jadwalRabu = null;
                    JadwalKamis jadwalKamis = null;
                    JadwalJumat jadwalJumat = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        itemlist = new ArrayList<JadwalSenin>();
                        itemselasa = new ArrayList<JadwalSelasa>();
                        itemRabu = new ArrayList<JadwalRabu>();
                        itemKamis = new ArrayList<JadwalKamis>();
                        itemJumat = new ArrayList<JadwalJumat>();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            JSONResponse.JadwalData jadwalData = resource.data.get(i);
                            days_name = jadwalData.getDayName();
                            day_status = jadwalData.getDayStatus();
                            daysid = jadwalData.getDayid();
                            day_type = jadwalData.getDayType();
                            if (daysid.toString().equals("1") && days_name.toString().equals("Senin") && day_status.toString().equals("1") && day_type.toString().equals("1")) {

                                for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                    mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                    jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                    jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                    jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                    lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                    guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();

                                    jadwalSenin = new JadwalSenin();
                                    jadwalSenin.setDay_name(days_name);
                                    jadwalSenin.setFullname(guru);
                                    jadwalSenin.setCources_name(mapel);
                                    jadwalSenin.setDuration(String.valueOf(lamber));
                                    jadwalSenin.setJam_mulai(jam_mulai);
                                    jadwalSenin.setJam_selesai(jam_selesai);
                                    itemlist.add(jadwalSenin);
                                }
                                seninAdapter = new SeninAdapter(itemlist);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                rv_senin.setLayoutManager(layoutManager);
                                rv_senin.setAdapter(seninAdapter);
                            } else if (daysid.toString().equals("2") && days_name.toString().equals("Selasa") && day_status.toString().equals("1") && day_type.toString().equals("1")) {
                                for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                    mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                    jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                    jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                    jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                    lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                    guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();
                                    jadwalSelasa = new JadwalSelasa();
                                    jadwalSelasa.setFullname(guru);
                                    jadwalSelasa.setDay_name(days_name);
                                    jadwalSelasa.setCources_name(mapel);
                                    jadwalSelasa.setDuration(String.valueOf(lamber));
                                    jadwalSelasa.setJam_mulai(jam_mulai);
                                    jadwalSelasa.setJam_selesai(jam_selesai);
                                    itemselasa.add(jadwalSelasa);
                                }
                                selasaAdapter = new SelasaAdapter(itemselasa);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                rv_selasa.setLayoutManager(layoutManager);
                                rv_selasa.setAdapter(selasaAdapter);
                            } else if (daysid.toString().equals("3") && days_name.toString().equals("Rabu") && day_status.toString().equals("1") && day_type.toString().equals("1")) {
                                for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                    mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                    jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                    jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                    jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                    lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                    guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();
                                    jadwalRabu = new JadwalRabu();
                                    jadwalRabu.setFullname(guru);
                                    jadwalRabu.setDay_name(days_name);
                                    jadwalRabu.setCources_name(mapel);
                                    jadwalRabu.setDuration(String.valueOf(lamber));
                                    jadwalRabu.setJam_mulai(jam_mulai);
                                    jadwalRabu.setJam_selesai(jam_selesai);
                                    itemRabu.add(jadwalRabu);
                                }
                                rabuAdapter = new RabuAdapter(itemRabu);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                rv_rabu.setLayoutManager(layoutManager);
                                rv_rabu.setAdapter(rabuAdapter);
                            } else if (daysid.toString().equals("4") && days_name.toString().equals("Kamis") && day_status.toString().equals("1") && day_type.toString().equals("1")) {
                                for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                    mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                    jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                    jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                    jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                    lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                    guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();
                                    jadwalKamis = new JadwalKamis();
                                    jadwalKamis.setFullname(guru);
                                    jadwalKamis.setDay_name(days_name);
                                    jadwalKamis.setCources_name(mapel);
                                    jadwalKamis.setDuration(String.valueOf(lamber));
                                    jadwalKamis.setJam_mulai(jam_mulai);
                                    jadwalKamis.setJam_selesai(jam_selesai);
                                    itemKamis.add(jadwalKamis);
                                }
                                kamisAdapter = new KamisAdapter(itemKamis);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                rv_kamis.setLayoutManager(layoutManager);
                                rv_kamis.setAdapter(kamisAdapter);
                            } else if (daysid.toString().equals("5") && days_name.toString().equals("Jumat") && day_status.toString().equals("1") && day_type.toString().equals("1")) {
                                for (int o = 0; o < response.body().getData().get(i).getScheduleClass().size(); o++) {
                                    mapel = response.body().getData().get(i).getScheduleClass().get(o).getCourcesName();
                                    jam_mulai = response.body().getData().get(i).getScheduleClass().get(o).getTimezOk();
                                    jam_selesai = response.body().getData().get(i).getScheduleClass().get(o).getTimezFinish();
                                    jamber = response.body().getData().get(i).getScheduleClass().get(o).getScheduleTime();
                                    lamber = response.body().getData().get(i).getScheduleClass().get(o).getLessonDuration();
                                    guru = response.body().getData().get(i).getScheduleClass().get(o).getTeacherName();
                                    jadwalJumat = new JadwalJumat();
                                    jadwalJumat.setFullname(guru);
                                    jadwalJumat.setDay_name(days_name);
                                    jadwalJumat.setCources_name(mapel);
                                    jadwalJumat.setDuration(String.valueOf(lamber));
                                    jadwalJumat.setJam_mulai(jam_mulai);
                                    jadwalJumat.setJam_selesai(jam_selesai);
                                    itemJumat.add(jadwalJumat);
                                }
                                jumatAdapter = new JumatAdapter(itemJumat);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                rv_jumat.setLayoutManager(layoutManager);
                                rv_jumat.setAdapter(jumatAdapter);
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
        }else {
            Toast.makeText(JadwalPelajaran.this,"harap refersh kembali",Toast.LENGTH_LONG).show();
        }
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
