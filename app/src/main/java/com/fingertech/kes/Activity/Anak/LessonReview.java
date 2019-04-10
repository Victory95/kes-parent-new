package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fingertech.kes.Activity.Adapter.LessonAdapter;
import com.fingertech.kes.Activity.Model.LessonModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kes.Activity.Anak.JadwalPelajaran.my_lesson_preferences;

public class LessonReview extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    String authorization,school_code,classroom_id,student_id,cources_id;
    Auth mApiInterface;
    Toolbar toolbar;
    RecyclerView rv_lesson;
    TextView tv_no_lesson;
    String tanggal,tahun,bulan,guru,title,materi,desc;
    int status;
    String code;
    LessonAdapter lessonAdapter;
    LessonModel lessonModel;
    List<LessonModel> lessonModelList = new ArrayList<>();
    ProgressDialog dialog;
    List<JSONResponse.ListLesson> listLessonList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_review);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        toolbar             = findViewById(R.id.toolbar);
        rv_lesson           = findViewById(R.id.rv_lesson);
        tv_no_lesson        = findViewById(R.id.hint_lesson);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        sharedPreferences   = getSharedPreferences(my_lesson_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        cources_id          = sharedPreferences.getString("cources_id",null);
        dapat_lesson();
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
        dialog = new ProgressDialog(LessonReview.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    private void dapat_lesson(){
        progressBar();
        showDialog();
        Call<JSONResponse.LessonReview> call = mApiInterface.kes_lesson_review_get(authorization,school_code.toLowerCase(),student_id,classroom_id,cources_id);
        call.enqueue(new Callback<JSONResponse.LessonReview>() {
            @Override
            public void onResponse(Call<JSONResponse.LessonReview> call, Response<JSONResponse.LessonReview> response) {
                Log.d("lessonSukses",response.code()+"");
                hideDialog();
                if (response.isSuccessful()){
                    JSONResponse.LessonReview lessonReview = response.body();
                    status  = lessonReview.status;
                    code    = lessonReview.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")){
                        listLessonList  = response.body().getData().getData();
                        if (listLessonList.size() > 0) {
                            tv_no_lesson.setVisibility(View.GONE);
                            rv_lesson.setVisibility(View.VISIBLE);
                            for (JSONResponse.ListLesson listLesson : listLessonList) {
                                tanggal = listLesson.getReview_date();
                                guru = listLesson.getFullname();
                                title = listLesson.getReview_title();
                                materi = listLesson.getReview_materi();
                                desc = listLesson.getReview_desc();
                                lessonModel = new LessonModel();
                                lessonModel.setBulan(converBulan(tanggal));
                                lessonModel.setTahun(converTahun(tanggal));
                                lessonModel.setTanggal(converDate(tanggal));
                                lessonModel.setGuru(guru);
                                lessonModel.setTitle(title);
                                lessonModel.setDesc(desc);
                                lessonModel.setMateri(materi);
                                lessonModelList.add(lessonModel);
                            }
                            lessonAdapter = new LessonAdapter(lessonModelList);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(LessonReview.this);
                            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                            rv_lesson.setLayoutManager(layoutManager);
                            rv_lesson.setAdapter(lessonAdapter);
                        }else {
                            tv_no_lesson.setVisibility(View.VISIBLE);
                            rv_lesson.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.LessonReview> call, Throwable t) {
                hideDialog();
                Log.e("lessonGagal",t.toString());

            }
        });
    }
    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converBulan(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM",new Locale("in","ID"));
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converTahun(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}
