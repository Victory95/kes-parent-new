package com.fingertech.kes.Activity.Anak;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewCompat;
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
import com.fingertech.kes.Activity.Adapter.HariAdapter.SabtuAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.SelasaAdapter;
import com.fingertech.kes.Activity.Adapter.HariAdapter.SeninAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.HariModel.JadwalJumat;
import com.fingertech.kes.Activity.Model.HariModel.JadwalKamis;
import com.fingertech.kes.Activity.Model.HariModel.JadwalRabu;
import com.fingertech.kes.Activity.Model.HariModel.JadwalSabtu;
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
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalPelajaran extends AppCompatActivity {

    String authorization, memberid, username, member_type, fullname, school_code;
    Auth mApiInterface;
    int status;
    String code;
    ProgressDialog dialog;
    private List<JadwalSenin> itemlist;
    private List<JadwalSelasa> itemselasa;
    private List<JadwalRabu> itemRabu;
    private List<JadwalKamis> itemKamis;
    private List<JadwalJumat> itemJumat;
    private List<JadwalSabtu> itemSabtu;

    SeninAdapter seninAdapter;
    SelasaAdapter selasaAdapter;
    RabuAdapter rabuAdapter;
    KamisAdapter kamisAdapter;
    JumatAdapter jumatAdapter;
    SabtuAdapter sabtuAdapter;


    String classroom_id;
    String days_name;
    String mapel;
    int lamber;
    String jamber;
    String jam_mulai;
    String jam_selesai;
    String guru, daysid, day_type, day_status;
    String date, day;
    RecyclerView rv_senin, rv_selasa, rv_rabu, rv_kamis, rv_jumat, rv_sabtu;
    Toolbar toolbar;
    TextView tv_senin,tv_selasa,tv_rabu,tv_kamis,tv_jumat,tv_sabtu;
    TextView hint_senin,hint_selasa,hint_rabu,hint_kamis,hint_jumat,hint_sabtu;
    CardView btn_senin,btn_selasa,btn_rabu,btn_kamis,btn_jumat,btn_sabtu;
    ImageView arrow_senin,arrow_selasa,arrow_rabu,arrow_kamis,arrow_jumat,arrow_sabtu;
    SharedPreferences sharedPreferences;
    private boolean isExpandedSelasa    = false;
    private boolean isExpandedRabu      = false;
    private boolean isExpandedKamis     = false;
    private boolean isExpandedJumat     = false;
    private boolean isExpandedSabtu     = false;
    private boolean isExpandedSenin     = false;
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
        rv_senin        = findViewById(R.id.senin);
        rv_selasa       = findViewById(R.id.selasa);
        rv_rabu         = findViewById(R.id.rabu);
        rv_kamis        = findViewById(R.id.kamis);
        rv_jumat        = findViewById(R.id.jumat);
        rv_sabtu        = findViewById(R.id.sabtu);
        tv_senin        = findViewById(R.id.jumlah_senin);
        tv_selasa       = findViewById(R.id.jumlah_selasa);
        tv_rabu         = findViewById(R.id.jumlah_rabu);
        tv_kamis        = findViewById(R.id.jumlah_kamis);
        tv_jumat        = findViewById(R.id.jumlah_jumat);
        tv_sabtu        = findViewById(R.id.jumlah_sabtu);
        hint_senin      = findViewById(R.id.hint_senin);
        hint_selasa     = findViewById(R.id.hint_selasa);
        hint_rabu       = findViewById(R.id.hint_rabu);
        hint_kamis      = findViewById(R.id.hint_kamis);
        hint_jumat      = findViewById(R.id.hint_jumat);
        hint_sabtu      = findViewById(R.id.hint_sabtu);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        memberid            = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());


        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dater = null;
        try {
            dater = inFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("in","ID"));
        day = outFormat.format(dater);
        Jadwal_pelajaran();

        itemlist    = new ArrayList<JadwalSenin>();
        itemselasa  = new ArrayList<JadwalSelasa>();
        itemRabu    = new ArrayList<JadwalRabu>();
        itemKamis   = new ArrayList<JadwalKamis>();
        itemJumat   = new ArrayList<JadwalJumat>();
        itemSabtu   = new ArrayList<JadwalSabtu>();

        switch (day) {
            case "Senin":
                btn_senin.callOnClick();
                break;
            case "Selasa":
                btn_selasa.callOnClick();
                break;
            case "Rabu":
                btn_rabu.callOnClick();
                break;
            case "Kamis":
                btn_kamis.callOnClick();
                break;
            case "Jumat":
                btn_jumat.callOnClick();
                break;
            case "Sabtu":
                btn_sabtu.callOnClick();
                break;
            case "Minggu":
                break;
        }
        btn_senin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSenin ? 0 : 90;
                ViewCompat.animate(arrow_senin).rotation(rotation).start();
                isExpandedSenin = !isExpandedSenin;
                if (itemlist.size() == 0) {
                    if (isExpandedSenin){
                        hint_senin.setVisibility(View.VISIBLE);
                        rv_senin.setVisibility(View.GONE);
                    }else {
                        rv_senin.setVisibility(View.GONE);
                        hint_senin.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSenin){
                        hint_senin.setVisibility(View.GONE);
                        rv_senin.setVisibility(View.VISIBLE);
                    }else {
                        rv_senin.setVisibility(View.GONE);
                        hint_senin.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_selasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSelasa ? 0 : 90;
                ViewCompat.animate(arrow_selasa).rotation(rotation).start();
                isExpandedSelasa = !isExpandedSelasa;
                if (itemselasa.size() == 0) {
                    if (isExpandedSelasa){
                        hint_selasa.setVisibility(View.VISIBLE);
                        rv_selasa.setVisibility(View.GONE);
                    }else {
                        rv_selasa.setVisibility(View.GONE);
                        hint_selasa.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSelasa){
                        hint_selasa.setVisibility(View.GONE);
                        rv_selasa.setVisibility(View.VISIBLE);
                    }else {
                        rv_selasa.setVisibility(View.GONE);
                        hint_selasa.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_rabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedRabu ? 0 : 90;
                ViewCompat.animate(arrow_rabu).rotation(rotation).start();
                isExpandedRabu = !isExpandedRabu;
                if (itemRabu.size() == 0) {
                    if (isExpandedRabu){
                        hint_rabu.setVisibility(View.VISIBLE);
                        rv_rabu.setVisibility(View.GONE);
                    }else {
                        rv_rabu.setVisibility(View.GONE);
                        hint_rabu.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedRabu){
                        hint_rabu.setVisibility(View.GONE);
                        rv_rabu.setVisibility(View.VISIBLE);
                    }else {
                        rv_rabu.setVisibility(View.GONE);
                        hint_rabu.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_kamis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedKamis ? 0 : 90;
                ViewCompat.animate(arrow_kamis).rotation(rotation).start();
                isExpandedKamis = !isExpandedKamis;
                if (itemKamis.size() == 0) {
                    if (isExpandedKamis){
                        hint_kamis.setVisibility(View.VISIBLE);
                        rv_kamis.setVisibility(View.GONE);
                    }else {
                        rv_kamis.setVisibility(View.GONE);
                        hint_kamis.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedKamis){
                        hint_kamis.setVisibility(View.GONE);
                        rv_kamis.setVisibility(View.VISIBLE);
                    }else {
                        rv_kamis.setVisibility(View.GONE);
                        hint_kamis.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_jumat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedJumat ? 0 : 90;
                ViewCompat.animate(arrow_jumat).rotation(rotation).start();
                isExpandedJumat = !isExpandedJumat;
                if (itemJumat.size() == 0) {
                    if (isExpandedJumat){
                        hint_jumat.setVisibility(View.VISIBLE);
                        rv_jumat.setVisibility(View.GONE);
                    }else {
                        rv_jumat.setVisibility(View.GONE);
                        hint_jumat.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedJumat){
                        hint_jumat.setVisibility(View.GONE);
                        rv_jumat.setVisibility(View.VISIBLE);
                    }else {
                        rv_jumat.setVisibility(View.GONE);
                        hint_jumat.setVisibility(View.GONE);
                    }
                }
            }
        });
        btn_sabtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rotation = isExpandedSabtu ? 0 : 90;
                ViewCompat.animate(arrow_sabtu).rotation(rotation).start();
                isExpandedSabtu = !isExpandedSabtu;
                if (itemSabtu.size() == 0) {
                    if (isExpandedSabtu){
                        hint_sabtu.setVisibility(View.VISIBLE);
                        rv_sabtu.setVisibility(View.GONE);
                    }else {
                        rv_sabtu.setVisibility(View.GONE);
                        hint_sabtu.setVisibility(View.GONE);
                    }
                }else {
                    if (isExpandedSabtu){
                        hint_sabtu.setVisibility(View.GONE);
                        rv_sabtu.setVisibility(View.VISIBLE);
                    }else {
                        rv_sabtu.setVisibility(View.GONE);
                        hint_sabtu.setVisibility(View.GONE);
                    }
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
        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization.toString(), school_code.toString().toLowerCase(), memberid.toString(), classroom_id.toString());
        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalPelajaran> call, final Response<JSONResponse.JadwalPelajaran> response) {
                Log.i("KES", response.code() + "");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.JadwalPelajaran resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    JadwalSenin jadwalSenin = null;
                    JadwalSelasa jadwalSelasa = null;
                    JadwalRabu jadwalRabu = null;
                    JadwalKamis jadwalKamis = null;
                    JadwalJumat jadwalJumat = null;
                    JadwalSabtu jadwalSabtu = null;
                    if (status == 1 && code.equals("CSCH_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().getClass_schedule().size(); i++) {
                            JSONResponse.JadwalData jadwalData = resource.data.getClass_schedule().get(i);
                            days_name = jadwalData.getDayName();
                            day_status = jadwalData.getDayStatus();
                            daysid = jadwalData.getDayid();
                            day_type = jadwalData.getDayType();
                            switch (days_name.toString()) {
                                case "Senin": {
                                    tv_senin.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();

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

                                    break;
                                }
                                case "Selasa": {
                                    tv_selasa.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
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
                                    break;
                                }
                                case "Rabu": {
                                    tv_rabu.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
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
                                    break;
                                }
                                case "Kamis": {
                                    tv_kamis.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
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

                                    break;
                                }
                                case "Jumat": {
                                    tv_jumat.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
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

                                    break;
                                }
                                case "Sabtu": {
                                    tv_sabtu.setText(String.valueOf(response.body().getData().getClass_schedule().get(i).getScheduleClass().size()) + " Mata Pelajaran");
                                    for (int o = 0; o < response.body().getData().getClass_schedule().get(i).getScheduleClass().size(); o++) {
                                        mapel = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getCourcesName();
                                        jam_mulai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezOk();
                                        jam_selesai = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTimezFinish();
                                        jamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getScheduleTime();
                                        lamber = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getLessonDuration();
                                        guru = response.body().getData().getClass_schedule().get(i).getScheduleClass().get(o).getTeacherName();
                                        jadwalSabtu = new JadwalSabtu();
                                        jadwalSabtu.setFullname(guru);
                                        jadwalSabtu.setDay_name(days_name);
                                        jadwalSabtu.setCources_name(mapel);
                                        jadwalSabtu.setDuration(String.valueOf(lamber));
                                        jadwalSabtu.setJam_mulai(jam_mulai);
                                        jadwalSabtu.setJam_selesai(jam_selesai);
                                        itemSabtu.add(jadwalSabtu);
                                    }
                                    sabtuAdapter = new SabtuAdapter(itemSabtu);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(JadwalPelajaran.this);
                                    rv_sabtu.setLayoutManager(layoutManager);
                                    rv_sabtu.setAdapter(sabtuAdapter);
                                    break;
                                }
                            }
                        }
                        switch (day) {
                            case "Senin":
                                btn_senin.performClick();
                                break;
                            case "Selasa":
                                btn_selasa.performClick();
                                break;
                            case "Rabu":
                                btn_rabu.performClick();
                                break;
                            case "Kamis":
                                btn_kamis.performClick();
                                break;
                            case "Jumat":
                                btn_jumat.performClick();
                                break;
                            case "Sabtu":
                                btn_sabtu.performClick();
                                break;
                            case "Minggu":

                                break;
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

