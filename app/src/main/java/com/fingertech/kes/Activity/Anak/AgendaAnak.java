package com.fingertech.kes.Activity.Anak;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.fingertech.kes.Activity.Adapter.AgendaAdapter;
import com.fingertech.kes.Activity.Adapter.AgendaAdapter;
import com.fingertech.kes.Activity.Adapter.AgendaDataTanggal;
import com.fingertech.kes.Activity.Adapter.RaporAdapter;
import com.fingertech.kes.Activity.CustomView.CustomLayoutManager;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.AgendaModel;
import com.fingertech.kes.Activity.Model.AgendaTanggalModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.pepperonas.materialdialog.MaterialDialog;
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator;
import com.rey.material.widget.Spinner;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.stone.vega.library.VegaLayoutManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AgendaAnak extends AppCompatActivity {
    IndefinitePagerIndicator indefinitePagerIndicator;
    Toolbar toolbar;
    RecyclerView rv_agenda,rvtanggal;
    Auth mApiInterface;
    AgendaDataTanggal agendaDataTanggal;
    AgendaAdapter agendaAdapter;
    List<AgendaModel> agendaModelList = new ArrayList<>();
    List<AgendaTanggalModel> agendaModeltanggalbaru = new ArrayList<>();
    AgendaModel agendaModel;
    AgendaTanggalModel agendaTanggalModel;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM", new Locale("in", "ID"));
    private SimpleDateFormat bulanFormat = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    SimpleDateFormat fmt = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
    SimpleDateFormat tanggalformat = new SimpleDateFormat("d",new Locale("in","ID"));
    SimpleDateFormat bulanformat = new SimpleDateFormat("M",new Locale("in","ID"));
    SimpleDateFormat tahunformat = new SimpleDateFormat("yyyy",new Locale("in","ID"));
    SimpleDateFormat formattanggal = new SimpleDateFormat("yyyy-MM-dd",new Locale("in","ID"));
    SimpleDateFormat formatfull = new SimpleDateFormat("yyyy-MM-dd HH:mm",new Locale("in","ID"));
    DateFormat ds = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    int status;
    String tanggalagenda,bulan_sekarang,date,semester,start_date,end_date,semester_id,color,code,authorization,school_code,student_id,classroom_id,tanggal_agenda,type_agenda,desc_agenda,content_agenda;
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    TextView tv_hint_agenda,tvsemester,tvtanggalsemester,tv_hint_agenda_hari;
    int day;
    List<JSONResponse.DataAgenda> dataAgendaList;
    String tanggal,bulan,tahun;
    SlidingUpPanelLayout slidingUpPanelLayout;
    ImageView arrow;
    LinearLayout drag;
    DatePicker datePicker;
    String tanggal_awal,tanggal_akhir,tanggalawalan,tanggalakhiran;
    Date dateawal,dateakhir,bulannow,bulanpicker,datenow,datejam;
    Long times_awal,times_akhir,times_sekarang,times_picker;
    CardView btn_pilih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_view);
        indefinitePagerIndicator    = findViewById(R.id.recyclerview_pager_indicator);
        rvtanggal                   = findViewById(R.id.rv_tanggalrv);
        tvsemester                  = findViewById(R.id.tv_semestersagenda);
        tvtanggalsemester           = findViewById(R.id.tvtanggalagenda);
        toolbar                     = findViewById(R.id.toolbar_agenda);
        rv_agenda                   = findViewById(R.id.rv_agenda);
        tv_hint_agenda              = findViewById(R.id.hint_agenda);
        slidingUpPanelLayout        = findViewById(R.id.sliding_layout);
        arrow                       = findViewById(R.id.arrow);
        drag                        = findViewById(R.id.dragView);
        datePicker                  = findViewById(R.id.datePicker);
        btn_pilih                   = findViewById(R.id.btn_pilih);
        tv_hint_agenda_hari         = findViewById(R.id.hint_harian);
        mApiInterface               = ApiClient.getClient().create(Auth.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        date = df.format(Calendar.getInstance().getTime());
        bulan_sekarang = bulanFormat.format(Calendar.getInstance().getTime());
        Calendar calendar = Calendar.getInstance();
        day  = calendar.get(Calendar.DAY_OF_WEEK);
        dapat_Agenda();
        Check_Semester();
        tanggal = tanggalformat.format(Calendar.getInstance().getTime());
        bulan   = bulanformat.format(Calendar.getInstance().getTime());
        tahun   = tahunformat.format(Calendar.getInstance().getTime());
        tanggalawalan   = formattanggal.format(Calendar.getInstance().getTime());


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
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tahun           = String.valueOf(year);
                bulan           = String.valueOf(monthOfYear+1);
                tanggal         = String.valueOf(dayOfMonth);
                try {
                    bulannow    = dateFormat.parse(bulan_sekarang);
                    bulanpicker = dateFormat.parse(convertDate(year,monthOfYear));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                times_sekarang = bulannow.getTime();
                times_picker   = bulanpicker.getTime();
            }
        });
        btn_pilih.setOnClickListener(v -> {
            if (times_sekarang.equals(times_picker)){
                rvtanggal.smoothScrollToPosition(Integer.parseInt(tanggal)-1);
            }else {
                datatanggal(Integer.parseInt(tahun),Integer.parseInt(bulan));
                rvtanggal.smoothScrollToPosition(0);
                bulan_sekarang = convertDate(Integer.parseInt(tahun),Integer.parseInt(bulan)-1);
                dapat_Agenda();
            }
            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            arrow.setImageResource(R.drawable.ic_up_arrow);
        });

        datatanggal(Integer.parseInt(tahun),Integer.parseInt(bulan));

        LinearSnapHelper snapHelper = new LinearSnapHelper();
        agendaDataTanggal = new AgendaDataTanggal(agendaModeltanggalbaru);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AgendaAnak.this, LinearLayoutManager.HORIZONTAL, false);
        indefinitePagerIndicator.attachToRecyclerView(rvtanggal);
        rvtanggal.setOnFlingListener(null);
        snapHelper.attachToRecyclerView(rvtanggal);
        rvtanggal.setLayoutManager(layoutManager);
        rvtanggal.setAdapter(agendaDataTanggal);
        rvtanggal.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                int currentItem = 0;
                float itemWidth = horizontalScrollRange * 1.0f / agendaModeltanggalbaru.size();
                itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                if (scrollOffset != 0) {
                    currentItem = Math.round(scrollOffset / itemWidth);
                }
                currentItem = (currentItem < 0) ? 0 : currentItem;
                currentItem = (currentItem >= agendaModeltanggalbaru.size()) ? agendaModeltanggalbaru.size() - 1 : currentItem;
                String tanggals = agendaModeltanggalbaru.get(currentItem).getDate();
                if (dataAgendaList != null) {
                    if (agendaModelList != null) {
                        agendaModelList.clear();
                        if (contains(dataAgendaList, convertTanggal(tanggals))) {
                            for (JSONResponse.DataAgenda dataAgenda : dataAgendaList) {
                                if (dataAgenda.getDate().equals(convertTanggal(tanggals))) {
                                    type_agenda = dataAgenda.getType();
                                    desc_agenda = dataAgenda.getDesc();
                                    color = dataAgenda.getAgenda_color();
                                    content_agenda = dataAgenda.getAgenda_desc();
                                    agendaModel = new AgendaModel();
                                    agendaModel.setColour(color);
                                    agendaModel.setDate(tanggal_agenda);
                                    agendaModel.setType(type_agenda);
                                    agendaModel.setDesc(desc_agenda);
                                    agendaModel.setContent(content_agenda);
                                    agendaModelList.add(agendaModel);
                                    rv_agenda.setVisibility(View.VISIBLE);
                                    tv_hint_agenda_hari.setVisibility(View.GONE);
                                }
                            }
                            agendaAdapter = new AgendaAdapter(agendaModelList);
                            rv_agenda.setOnFlingListener(null);
                            rv_agenda.setLayoutManager(new VegaLayoutManager());
                            rv_agenda.setAdapter(agendaAdapter);
                            agendaAdapter.notifyDataSetChanged();
                            agendaAdapter.setOnItemClickListener(new AgendaAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String type = agendaModelList.get(position).getType();
                                    String desc = agendaModelList.get(position).getDesc();
                                    String content = agendaModelList.get(position).getContent();
                                    new MaterialDialog.Builder(AgendaAnak.this)
                                            .title(type)
                                            .message(desc + "\n\n" + content)
                                            .positiveText("Ok")
                                            .show();
                                }
                            });
                        } else {
                            rv_agenda.setVisibility(View.GONE);
                            tv_hint_agenda_hari.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    String convertDate(int year, int month) {
        String temp = year + "-" + (month + 1);
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM");
        try {
            return newDateFormat.format(calendarDateFormat.parse(temp));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


    String convertTanggal(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
        try {
            return calendarDateFormat.format(newDateFormat.parse(tahun));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    boolean contains(List<JSONResponse.DataAgenda> list,String posisi) {
        for (JSONResponse.DataAgenda item : list) {
            if (item.getDate().equals(posisi)) {
                return true;
            }
        }
        return false;
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
                }
            }


            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
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

                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            Collections.sort(response.body().getData(), new Comparator<JSONResponse.DataSemester>() {
                                @Override
                                public int compare(JSONResponse.DataSemester o1, JSONResponse.DataSemester o2) {
                                    return (o1.getStart_date().compareTo(o2.getStart_date()));
                                }
                            });
                            tanggal_awal    = response.body().getData().get(0).getStart_date();
                            tanggal_akhir   = response.body().getData().get(response.body().getData().size()-1).getEnd_date();
                            try {
                                dateawal    = df.parse(tanggal_awal);
                                dateakhir   = df.parse(tanggal_akhir);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            times_awal  = dateawal.getTime();
                            times_akhir = dateakhir.getTime();
                            datePicker.setMaxDate(times_akhir);
                            datePicker.setMinDate(times_awal);

                            if (response.body().getData().get(i).getSemester_id().equals(semester_id)) {
                                semester    = response.body().getData().get(i).getSemester_name();
                                start_date  = response.body().getData().get(i).getStart_date();
                                end_date    = response.body().getData().get(i).getEnd_date();
                                tvsemester.setText("Semester "+semester);
                                tvtanggalsemester.setText(converttanggal(start_date)+" sampai "+converttanggal(end_date));
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
    private void dapat_Agenda(){
        progressBar();
        showDialog();
        Call<JSONResponse.ListAgenda> call = mApiInterface.kes_class_agenda_student_get(authorization,school_code.toLowerCase(),student_id,classroom_id,bulan_sekarang, String.valueOf(day-1));
        call.enqueue(new Callback<JSONResponse.ListAgenda>() {
            @Override
            public void onResponse(Call<JSONResponse.ListAgenda> call, Response<JSONResponse.ListAgenda> response) {
                Log.d("sukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.ListAgenda resource = response.body();
                    status  = resource.status;
                    code    = resource.code;
                    if (status == 1&& code.equals("AGN_SCS_0001")){
                        indefinitePagerIndicator.setVisibility(View.VISIBLE);
                        rv_agenda.setVisibility(View.VISIBLE);
                        rvtanggal.setVisibility(View.VISIBLE);
                        tv_hint_agenda.setVisibility(View.GONE);
                        if (response.body() != null) {
                            dataAgendaList = response.body().getData();
                        }
                        String tanngall = formatfull.format(Calendar.getInstance().getTime());
                        if (response.body().getClass_schedule() != null) {
                            tanggalakhiran  = response.body().getClass_schedule().get(0).getScheduleClass().get(0).getTimezFinish();
                        }
                        try {
                            datenow = ds.parse(tanngall);
                            datejam = ds.parse(tanggalawalan+" "+tanggalakhiran);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Long timesnow = datenow.getTime();
                        Long timesakhir = datejam.getTime();
                        if (timesnow > timesakhir) {
                            rvtanggal.smoothScrollToPosition(Integer.parseInt(tanggal));
                            datePicker.updateDate(Integer.parseInt(tahun),Integer.parseInt(bulan)-1,Integer.parseInt(tanggal)+1);
                        }else {
                            rvtanggal.smoothScrollToPosition(Integer.parseInt(tanggal) - 1);
                        }
                    }else {
                        indefinitePagerIndicator.setVisibility(View.GONE);
                        rv_agenda.setVisibility(View.GONE);
                        rvtanggal.setVisibility(View.GONE);
                        tv_hint_agenda.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.ListAgenda> call, Throwable t) {
                Log.d("gagal",t.toString());
                hideDialog();
            }
        });
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
        dialog = new ProgressDialog(AgendaAnak.this);
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
        }

        return super.onOptionsItemSelected(item);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void datatanggal(int tahun,int bulan){
        Calendar cal = Calendar.getInstance();
        cal.set(tahun,bulan-1, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (agendaModeltanggalbaru!=null) {
            agendaModeltanggalbaru.clear();
            for (int i = 0; i < daysInMonth; i++) {
                tanggalagenda = fmt.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 1);
                agendaTanggalModel = new AgendaTanggalModel();
                agendaTanggalModel.setDate(tanggalagenda);
                agendaModeltanggalbaru.add(agendaTanggalModel);
            }
        }
    }
}
