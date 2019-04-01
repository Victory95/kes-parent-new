package com.fingertech.kes.Activity.Anak;

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
import android.widget.TextView;

import com.fingertech.kes.Activity.Adapter.AgendaAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.AgendaModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.pepperonas.materialdialog.MaterialDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaAnak extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_agenda;
    Auth mApiInterface;
    AgendaAdapter agendaAdapter;
    List<AgendaModel> agendaModelList = new ArrayList<>();
    AgendaModel agendaModel;
    int status;
    String code,authorization,school_code,student_id,classroom_id,tanggal_agenda,type_agenda,desc_agenda,content_agenda;
    SharedPreferences sharedPreferences;
    ProgressDialog dialog;
    TextView tv_hint_agenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_anak);
        toolbar     = findViewById(R.id.toolbar_agenda);
        rv_agenda   = findViewById(R.id.rv_agenda);
        tv_hint_agenda  = findViewById(R.id.hint_agenda);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        dapat_agenda();
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
                                tanggal_agenda  = response.body().getData().getClass_agenda().get(i).getDate();
                                type_agenda     = response.body().getData().getClass_agenda().get(i).getType();
                                desc_agenda     = response.body().getData().getClass_agenda().get(i).getDesc();
                                content_agenda  = response.body().getData().getClass_agenda().get(i).getContent();
                                agendaModel = new AgendaModel();
                                agendaModel.setDate(tanggal_agenda);
                                agendaModel.setType(type_agenda);
                                agendaModel.setDesc(desc_agenda);
                                agendaModel.setContent(content_agenda);
                                agendaModelList.add(agendaModel);
                            }
                            agendaAdapter = new AgendaAdapter(agendaModelList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(AgendaAnak.this);
                            rv_agenda.setLayoutManager(layoutManager);
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
