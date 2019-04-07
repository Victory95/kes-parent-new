package com.fingertech.kes.Activity.Anak;


import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.RaporAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.RaportModel;
import com.fingertech.kes.Activity.CustomView.CustomLayoutManager;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.pepperonas.materialdialog.MaterialDialog;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.rey.material.widget.Spinner;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kes.Service.App.CHANNEL_2_ID;

public class RaporAnak extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    TextView tv_semester,no_rapor,nama_kelas;
    Button btn_download,btn_go;
    Toolbar toolbar;
    int status;
    Auth mApiInterface;
    String code;
    ProgressDialog dialog;
    String statusrapor,peringkat,kritik;
    String authorization,school_code,classroom_id,student_id,semester_id;
    TextView tv_teori,tv_ulangan_harian,tv_praktikum,tv_eskul,tv_ujian_sekolah,tv_ujian_negara,tv_nilai_akhir,tv_rata_rata;

    LinearSnapHelper snapHelper;
    RaporAdapter raporAdapter;
    RaportModel raportModel;
    List<RaportModel> raportModelList;
    RecyclerView snappyRecycleView;
    String teori,ulangan_harian,praktikum,eskul,ujian_sekolah,ujian_negara,mapel,nilai_akhir,rata_rata;
    CardView cardView;
    String semester_nama;
    private List<JSONResponse.DataSemester> dataSemesters;
    private List<JSONResponse.DetailScoreItem> detailScoreItemList = new ArrayList<>();

    Spinner sp_semester,sp_mapel,sp_tipe_nilai;
    TextView status_rapor,tv_peringkat,tv_kritik;
    TableLayout tableLayout;

    String date,namakelas,walikelas;
    String guru,tanggal,type,nilai,deskripsi,start_date,end_date,start_year,start_end;
    SharedPreferences sharedPreferences;
    SlidingUpPanelLayout slidingUpPanelLayout;
    ImageView star;
    ImageView arrow;
    LinearLayout drag;
    private String[] list_tipe = {
            "Semua",
            "Latihan Teori",
            "Ulangan Harian",
            "Latihan Praktikum",
            "Ekstrakulikuler",
            "Ujian Sekolah",
            "Ujian Negara"
    };
    TableRow tr_teori,tr_harian,tr_praktikum,tr_eskul,tr_ujian_sekolah,tr_ujian_negara,tr_nilai_akhir,tr_rata;
    File file = new File("Your_File_path/name");
    TextView tv_end_date;

    IndefinitePagerIndicator indefinitePagerIndicator;
    LinearLayout ll_status,ll_kritik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raport_view);
        toolbar         = findViewById(R.id.toolbar_rapor);
        snappyRecycleView    = findViewById(R.id.rv_rapor);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        sp_semester     = findViewById(R.id.sp_semester);
        sp_mapel        = findViewById(R.id.sp_mapel);
        sp_tipe_nilai   = findViewById(R.id.sp_tipe_nilai);
        tv_semester     = findViewById(R.id.tv_semester);
        no_rapor        = findViewById(R.id.tv_no_rapor);
        status_rapor    = findViewById(R.id.status_raport);
        tv_peringkat    = findViewById(R.id.peringkat);
        tv_kritik       = findViewById(R.id.kritik_saran);
        star            = findViewById(R.id.star);
        slidingUpPanelLayout    = findViewById(R.id.sliding_layout);
        tv_teori           = findViewById(R.id.nilai_teori);
        tv_end_date        = findViewById(R.id.end_date);
        tv_ulangan_harian  = findViewById(R.id.ulangan_harian);
        tv_praktikum       = findViewById(R.id.latihan_praktikum);
        tv_eskul           = findViewById(R.id.eskul);
        tv_ujian_sekolah   = findViewById(R.id.ujian_sekolah);
        tv_ujian_negara    = findViewById(R.id.ujian_negara);
        tv_rata_rata       = findViewById(R.id.rata_rata);
        tv_nilai_akhir     = findViewById(R.id.nilai_akhir);
        tr_teori           = findViewById(R.id.table1);
        tr_eskul           = findViewById(R.id.table4);
        tr_harian          = findViewById(R.id.table2);
        tr_praktikum       = findViewById(R.id.table3);
        tr_ujian_negara    = findViewById(R.id.table5);
        tr_ujian_sekolah   = findViewById(R.id.table6);
        tr_nilai_akhir     = findViewById(R.id.table7);
        tr_rata            = findViewById(R.id.table8);
        ll_kritik          = findViewById(R.id.ll_kritik);
        ll_status          = findViewById(R.id.ll_status);
        indefinitePagerIndicator    = findViewById(R.id.recyclerview_pager_indicator);
        cardView           = findViewById(R.id.card);
        arrow              = findViewById(R.id.arrow);
        drag               = findViewById(R.id.dragView);


        notificationManager = NotificationManagerCompat.from(this);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());

        Check_Semester();
        Classroom_detail();

        slidingUpPanelLayout.setFadeOnClickListener(view -> {
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            arrow.setImageResource(R.drawable.ic_up_arrow);
        });
        slidingUpPanelLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {
                    if (panelState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                        arrow.setImageResource(R.drawable.ic_up_arrow);
                    }else if (panelState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                        arrow.setImageResource(R.drawable.ic_arrow_down);
                    }
            }
        });

        drag.setOnClickListener(v -> {
            if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);
            }else if (slidingUpPanelLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            }
        });

        ll_status.setOnClickListener(v ->
                new MaterialDialog.Builder(RaporAnak.this)
                .title("Status")
                .message(statusrapor)
                .positiveText("Ok")
                .show());

        ll_kritik.setOnClickListener(v ->
                new MaterialDialog.Builder(RaporAnak.this)
                .title("Kritik dan Saran")
                .message(kritik)
                .positiveText("Ok")
                .show());

    }
    private void Check_Semester(){

        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
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
                    RaportAnak();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                if (response.isSuccessful()) {
                    JSONResponse.ListSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        dataSemesters = response.body().getData();
                        List<String> listSpinner = new ArrayList<String>();
                        listSpinner.add("Pilih Semester...");
                        for (int i = 0; i < dataSemesters.size(); i++) {
                            listSpinner.add(dataSemesters.get(i).getSemester_name());
                            if (dataSemesters.get(i).getSemester_id().equals(semester_id)) {
                                semester_nama = response.body().getData().get(i).getSemester_name();
                                start_date = response.body().getData().get(i).getStart_date();
                                end_date = response.body().getData().get(i).getEnd_date();

                                final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                        RaporAnak.this, R.layout.spinner_full, listSpinner) {
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
                                int spinnerPosition = adapterRaport.getPosition(semester_nama);
                                adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                                sp_semester.setAdapter(adapterRaport);
                                sp_semester.setOnItemSelectedListener((parent, view, position, id) -> {
                                    if (position > 0) {
                                        semester_id = dataSemesters.get(position - 1).getSemester_id();
                                    }
                                });

                                sp_semester.setSelection(spinnerPosition);
                            }
                            if (response.body().getData().get(i).getSemester_name().equals("Ganjil")) {
                                start_year = converTahun(response.body().getData().get(i).getStart_date());
                            } else if (response.body().getData().get(i).getSemester_name().equals("Genap")) {
                                start_end = converTahun(response.body().getData().get(i).getEnd_date());
                            }
                            tv_semester.setText("Semester " + semester_nama + " (" + start_year + "/" + start_end + ")");

                        }
                        final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(
                                RaporAnak.this, R.layout.spinner_full, listSpinner) {
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
                        int spinnerPosition = adapterRaport.getPosition(semester_nama);
                        adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        sp_semester.setAdapter(adapterRaport);
                        sp_semester.setOnItemSelectedListener((parent, view, position, id) -> {
                            if (position > 0) {
                                semester_id = dataSemesters.get(position - 1).getSemester_id();
                                dapat_semester();
                                RaportAnak();
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                arrow.setImageResource(R.drawable.ic_up_arrow);
                            }
                        });
                        sp_semester.setSelection(spinnerPosition);
                        tv_end_date.setText(convertTanggal(start_date) + " Sampai " + convertTanggal(end_date));
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
        dialog = new ProgressDialog(RaporAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.item_download:
                if (detailScoreItemList != null){
                    download_rapor();
                }else {
                    FancyToast.makeText(getApplicationContext(),"Rapor Belum diterbitkan oleh guru",Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private void RaportAnak(){
        progressBar();
        showDialog();
        Call<JSONResponse.Raport> call = mApiInterface.kes_rapor_score_get(authorization.toString(),school_code.toString().toLowerCase(),student_id.toString(),classroom_id.toString(),semester_id.toString());
        call.enqueue(new Callback<JSONResponse.Raport>() {
            @Override
            public void onResponse(Call<JSONResponse.Raport> call, final Response<JSONResponse.Raport> response) {
                hideDialog();
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.Raport resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    RaportModel raportModel = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        raportModelList = new ArrayList<RaportModel>();
                        detailScoreItemList = response.body().getData().getDetailScore();
                        List<String> listSpinner = new ArrayList<String>();
                        if (response.body().getData().getDetailScore() != null) {
                            snapHelper  = new LinearSnapHelper();
                            statusrapor = response.body().getData().getClassroom().getPromoteText();
                            peringkat   = response.body().getData().getClassroom().getPromoteRanking();
                            kritik      = response.body().getData().getClassroom().getDescriptionText();

                            status_rapor.setText(statusrapor);
                            tv_peringkat.setText(peringkat);
                            tv_kritik.setText(kritik);
                            if (peringkat.equals("1")) {
                                star.setVisibility(View.VISIBLE);
                            } else {
                                star.setVisibility(View.GONE);
                            }
                            for (int i = 0; i < response.body().getData().getDetailScore().size(); i++) {

                                listSpinner.add(response.body().getData().getDetailScore().get(i).getCourcesName());
                                mapel = response.body().getData().getDetailScore().get(i).getCourcesName();
                                raportModel = new RaportModel();
                                raportModel.setMapel(mapel);
                                raportModelList.add(raportModel);

                            }
                            final CustomLayoutManager layoutManager = new CustomLayoutManager(RaporAnak.this);
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

                            final ArrayAdapter<String> adapterRaport = new ArrayAdapter<String>(RaporAnak.this, R.layout.spinner_full, listSpinner);
                            adapterRaport.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_mapel.setAdapter(adapterRaport);
                            sp_mapel.setOnItemSelectedListener((parent, view, position, id) -> {
                                snappyRecycleView.smoothScrollToPosition(position);
                                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                arrow.setImageResource(R.drawable.ic_up_arrow);
                            });
                            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(RaporAnak.this, R.layout.spinner_full, list_tipe);
                            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                            sp_tipe_nilai.setAdapter(adapter);
                            sp_tipe_nilai.setOnItemSelectedListener((parent, view, position, id) -> {
                                if (position == 1) {
                                    tr_teori.setVisibility(View.VISIBLE);
                                    tr_harian.setVisibility(View.GONE);
                                    tr_praktikum.setVisibility(View.GONE);
                                    tr_eskul.setVisibility(View.GONE);
                                    tr_ujian_sekolah.setVisibility(View.GONE);
                                    tr_ujian_negara.setVisibility(View.GONE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 2) {
                                    tr_teori.setVisibility(View.GONE);
                                    tr_harian.setVisibility(View.VISIBLE);
                                    tr_praktikum.setVisibility(View.GONE);
                                    tr_eskul.setVisibility(View.GONE);
                                    tr_ujian_sekolah.setVisibility(View.GONE);
                                    tr_ujian_negara.setVisibility(View.GONE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 3) {
                                    tr_teori.setVisibility(View.GONE);
                                    tr_harian.setVisibility(View.GONE);
                                    tr_praktikum.setVisibility(View.VISIBLE);
                                    tr_eskul.setVisibility(View.GONE);
                                    tr_ujian_sekolah.setVisibility(View.GONE);
                                    tr_ujian_negara.setVisibility(View.GONE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 4) {
                                    tr_teori.setVisibility(View.GONE);
                                    tr_harian.setVisibility(View.GONE);
                                    tr_praktikum.setVisibility(View.GONE);
                                    tr_eskul.setVisibility(View.VISIBLE);
                                    tr_ujian_sekolah.setVisibility(View.GONE);
                                    tr_ujian_negara.setVisibility(View.GONE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 5) {
                                    tr_teori.setVisibility(View.GONE);
                                    tr_harian.setVisibility(View.GONE);
                                    tr_praktikum.setVisibility(View.GONE);
                                    tr_eskul.setVisibility(View.GONE);
                                    tr_ujian_sekolah.setVisibility(View.VISIBLE);
                                    tr_ujian_negara.setVisibility(View.GONE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 6) {
                                    tr_teori.setVisibility(View.GONE);
                                    tr_harian.setVisibility(View.GONE);
                                    tr_praktikum.setVisibility(View.GONE);
                                    tr_eskul.setVisibility(View.GONE);
                                    tr_ujian_sekolah.setVisibility(View.GONE);
                                    tr_ujian_negara.setVisibility(View.VISIBLE);
                                    tr_nilai_akhir.setVisibility(View.GONE);
                                    tr_rata.setVisibility(View.GONE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                } else if (position == 0) {
                                    tr_teori.setVisibility(View.VISIBLE);
                                    tr_harian.setVisibility(View.VISIBLE);
                                    tr_praktikum.setVisibility(View.VISIBLE);
                                    tr_eskul.setVisibility(View.VISIBLE);
                                    tr_ujian_sekolah.setVisibility(View.VISIBLE);
                                    tr_ujian_negara.setVisibility(View.VISIBLE);
                                    tr_nilai_akhir.setVisibility(View.VISIBLE);
                                    tr_rata.setVisibility(View.VISIBLE);
                                    slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                                    arrow.setImageResource(R.drawable.ic_up_arrow);
                                }
                            });

                            no_rapor.setVisibility(View.GONE);
                            snappyRecycleView.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.VISIBLE);
                            indefinitePagerIndicator.setVisibility(View.VISIBLE);
                            raporAdapter = new RaporAdapter(raportModelList);
                            snappyRecycleView.setOnFlingListener(null);
                            indefinitePagerIndicator.attachToRecyclerView(snappyRecycleView);
                            snapHelper.attachToRecyclerView(snappyRecycleView);
                            snappyRecycleView.setLayoutManager(layoutManager);
                            snappyRecycleView.setAdapter(raporAdapter);

                            snappyRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);

                                    int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                                    int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                                    int currentItem = 0;
                                    float itemWidth = horizontalScrollRange * 1.0f / raportModelList.size();
                                    itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                                    if (scrollOffset != 0) {
                                        currentItem = Math.round(scrollOffset / itemWidth);
                                    }
                                    currentItem = (currentItem < 0) ? 0 : currentItem;
                                    currentItem = (currentItem >= raportModelList.size()) ? raportModelList.size() - 1 : currentItem;
                                    teori = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getLatihanTeori().getScoreExam());
                                    ulangan_harian = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUlanganHarian().getScoreExam());
                                    praktikum = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getPraktikum().getScoreExam());
                                    eskul = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getEkstrakulikuler().getScoreExam());
                                    ujian_sekolah = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUjianSekolah().getScoreExam());
                                    ujian_negara = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getTypeExam().getUjianNegara().getScoreExam());
                                    nilai_akhir = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getFinalScore());
                                    rata_rata = String.valueOf(response.body().getData().getDetailScore().get(currentItem).getClassAverageScore());
                                    tv_teori.setText(convertZero(teori));
                                    tv_ulangan_harian.setText(convertZero(ulangan_harian));
                                    tv_praktikum.setText(convertZero(praktikum));
                                    tv_eskul.setText(convertZero(eskul));
                                    tv_ujian_negara.setText(convertZero(ujian_negara));
                                    tv_ujian_sekolah.setText(convertZero(ujian_sekolah));
                                    tv_nilai_akhir.setText(convertZero(nilai_akhir));
                                    tv_rata_rata.setText(convertZero(rata_rata));
                                }
                            });
                        } else {
                            no_rapor.setVisibility(View.VISIBLE);
                            snappyRecycleView.setVisibility(View.GONE);
                            star.setVisibility(View.GONE);
                            cardView.setVisibility(View.GONE);
                            status_rapor.setText("-");
                            tv_peringkat.setText("-");
                            tv_kritik.setText("-");
                            indefinitePagerIndicator.setVisibility(View.GONE);
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Raport> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
                no_rapor.setText(t.toString());
                Toast.makeText(getApplicationContext(), "Internet bermasalah", Toast.LENGTH_LONG).show();

            }

        });
    }

    String convertZero(String data) {
        if (data.equals("0.0")){
            data = "-";
        }
        return data;
    }



    private void Classroom_detail(){

        Call<JSONResponse.ClassroomDetail> call = mApiInterface.kes_classroom_detail_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ClassroomDetail>() {

            @Override
            public void onResponse(Call<JSONResponse.ClassroomDetail> call, final Response<JSONResponse.ClassroomDetail> response) {
                Log.i("KES", response.code() + "");


                JSONResponse.ClassroomDetail resource = response.body();

                status = resource.status;
                code    = resource.code;


                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    walikelas    = response.body().getData().getHomeroom_teacher();
                    namakelas    = response.body().getData().getClassroom_name();
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.ClassroomDetail> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(RaporAnak.this,t.toString(),Toast.LENGTH_LONG).show();

            }

        });
    }
    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTanggal(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }



    public void download_rapor(){
        Call<ResponseBody> call = mApiInterface.kes_rapor_pdf(authorization,school_code.toLowerCase(),student_id,classroom_id,semester_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("KES",response.code()+"");
                try {
                    File path = Environment.getExternalStorageDirectory();
                    File file = new File(path, "KES Documents");
                    file.mkdir();
                    File pdfFile = new File(file, "Nilai Rapor.pdf");
                    FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                    if (response.body() != null) {
                        final int progressMax = 100;
                        Intent intent = new Intent(RaporAnak.this, LihatPdf.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(RaporAnak.this, 2, intent,
                                PendingIntent.FLAG_ONE_SHOT);

                        final NotificationCompat.Builder notification = new NotificationCompat.Builder(RaporAnak.this, CHANNEL_2_ID)
                                .setSmallIcon(R.drawable.ic_logo_grey)
                                .setContentTitle("Download")
                                .setContentText("Download in progress")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setOngoing(true)
                                .setOnlyAlertOnce(true)
                                .setProgress(progressMax, 0, true);

                        notificationManager.notify(2, notification.build());

                        new Thread(() -> {

                            SystemClock.sleep(2000);
                            Uri soundUri1 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            for (int progress = 0; progress <= progressMax; progress += 50) {
                                notification.setContentText(progress+" %")
                                        .setProgress(progressMax, progress, false);
                                notificationManager.notify(2, notification.build());
                                SystemClock.sleep(1000);
                            }
                            notification.setContentText("Download selesai")
                                    .setProgress(0, 0, false)
                                    .setOngoing(false)
                                    .setContentIntent(pendingIntent)
                                    .setSound(soundUri1);
                            try {
                                fileOutputStream.write(response.body().bytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Your dialog code.
                                    pilihan();
                                }
                            });
                            notificationManager.notify(2, notification.build());
                        }).start();
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("gagal",t.toString());

            }
        });
    }

    private void pilihan(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RaporAnak.this,R.style.DialogTheme);
        builder.setTitle("Download Selesai");
        builder.setMessage("Apakah anda ingin melihat file yang sudah didownload?");
        builder.setIcon(R.drawable.ic_pdf);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RaporAnak.this, LihatPdf.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}

