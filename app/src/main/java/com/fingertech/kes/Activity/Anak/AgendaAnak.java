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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.stone.vega.library.VegaLayoutManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy", new Locale("in", "ID"));
    private DateFormat times_format = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
    int status;
    String tanggalagenda,bulan_sekarang,date,semester,start_date,end_date,semester_id,color,code,authorization,school_code,student_id,classroom_id,tanggal_agenda,type_agenda,desc_agenda,content_agenda;
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    TextView tv_hint_agenda,tvsemester,tvtanggalsemester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_anak);
        indefinitePagerIndicator    = findViewById(R.id.recyclerview_pager_indicator);
        rvtanggal                   = findViewById(R.id.rv_tanggalrv);
        tvsemester                  = findViewById(R.id.tv_semestersagenda);
        tvtanggalsemester           = findViewById(R.id.tvtanggalagenda);
        toolbar                     = findViewById(R.id.toolbar_agenda);
        rv_agenda                   = findViewById(R.id.rv_agenda);
        tv_hint_agenda              = findViewById(R.id.hint_agenda);
        mApiInterface               = ApiClient.getClient().create(Auth.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);


        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        bulan_sekarang = dateFormat.format(Calendar.getInstance().getTime());

        Check_Semester();

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
                    dapat_agenda();
                    dapat_semester();
                    dapattanggal();
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

    private void dapat_agenda(){
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization,school_code.toLowerCase(),student_id,classroom_id);
        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponse.JadwalPelajaran> call, @NonNull Response<JSONResponse.JadwalPelajaran> response) {
                Log.d("sukses",response.code()+"");
                hideDialog();
                JSONResponse.JadwalPelajaran resource = response.body();
                if (resource != null) {
                    status = resource.status;
                    code   = resource.code;
                    if (status == 1 && code.equals("CSCH_SCS_0001")){
                        if (response.body().getData().getClass_agenda().size() == 0){
                            tv_hint_agenda.setVisibility(View.VISIBLE);
                            rv_agenda.setVisibility(View.GONE);
                        }else {
                            tv_hint_agenda.setVisibility(View.GONE);
                            rv_agenda.setVisibility(View.VISIBLE);
                            for (int i = 0 ; i < response.body().getData().getClass_agenda().size();i++){
                                type_agenda     = response.body().getData().getClass_agenda().get(i).getType();
                                desc_agenda     = response.body().getData().getClass_agenda().get(i).getDesc();
                                content_agenda  = response.body().getData().getClass_agenda().get(i).getContent();
                                color           = response.body().getData().getClass_agenda().get(i).getColour();
                                agendaModel = new AgendaModel();
                                agendaModel.setColour(color);
                                agendaModel.setDate(tanggal_agenda);
                                agendaModel.setType(type_agenda);
                                agendaModel.setDesc(desc_agenda);
                                agendaModel.setContent(content_agenda);
                                agendaModelList.add(agendaModel);
                            }

                            agendaAdapter = new AgendaAdapter(agendaModelList);
                            rv_agenda.setLayoutManager(new VegaLayoutManager());
                            rv_agenda.setAdapter(agendaAdapter);
                            agendaAdapter.setOnItemClickListener(new AgendaAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    String type     = agendaModelList.get(position).getType();
                                    String desc     = agendaModelList.get(position).getDesc();
                                    String content = agendaModelList.get(position).getContent();
                                    if (type.equals("Agenda Kelas")){
                                        new MaterialDialog.Builder(AgendaAnak.this)
                                                .title(type)
                                                .message(desc+"\n\n"+ content)
                                                .positiveText("Ok")
                                                .show();
                                    }else {
                                        new MaterialDialog.Builder(AgendaAnak.this)
                                                .title(type)
                                                .message(desc)
                                                .positiveText("Ok")
                                                .show();
                                    }
                                }
                            });
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<JSONResponse.JadwalPelajaran> call, @NonNull Throwable t) {
                Log.d("Gagal response",t.toString());
                hideDialog();
            }
        });
    }


    private void dapattanggal(){

        Call<JSONResponse.JadwalPelajaran> call = mApiInterface.kes_class_schedule_get(authorization,school_code.toLowerCase(),student_id,classroom_id);
        call.enqueue(new Callback<JSONResponse.JadwalPelajaran>() {
            @Override
            public void onResponse(@NonNull Call<JSONResponse.JadwalPelajaran> call, @NonNull Response<JSONResponse.JadwalPelajaran> response) {
                Log.d("sukses",response.code()+"");
                JSONResponse.JadwalPelajaran resource = response.body();
                if (resource != null) {
                    status = resource.status;
                    code   = resource.code;
                    if (status == 1 && code.equals("CSCH_SCS_0001")){
                        if (response.body().getData().getClass_agenda().size() == 0){
                            tv_hint_agenda.setVisibility(View.VISIBLE);
                            rvtanggal.setVisibility(View.GONE);
                        }else {
                            tv_hint_agenda.setVisibility(View.GONE);
                            rvtanggal.setVisibility(View.VISIBLE);
                            for (int i = 0 ; i < response.body().getData().getClass_agenda().size();i++){
                                tanggalagenda            = response.body().getData().getClass_agenda().get(i).getDate();
                                agendaTanggalModel       = new AgendaTanggalModel();
                                agendaTanggalModel.setDate(tanggalagenda);
                                agendaModeltanggalbaru.add(agendaTanggalModel);
                            }
                            agendaDataTanggal = new AgendaDataTanggal(agendaModeltanggalbaru);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(AgendaAnak.this, LinearLayoutManager.HORIZONTAL, false);
                            indefinitePagerIndicator.attachToRecyclerView(rvtanggal);
                            rvtanggal.setLayoutManager(layoutManager);
                            rvtanggal.setAdapter(agendaDataTanggal);
                        }
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<JSONResponse.JadwalPelajaran> call, @NonNull Throwable t) {
                Log.d("Gagal response",t.toString());

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

}
