package com.fingertech.kes.Activity.Anak;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.PesanAdapter;
import com.fingertech.kes.Activity.Adapter.PesanAnakAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.HariModel.PesanAnakModel;
import com.fingertech.kes.Activity.Model.PesanModel;
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

public class PesanAnakDua extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_pesan;
    Auth mApiInterface;
    String authorization,school_code,student_id;
    int status;
    String code;
    SharedPreferences sharedPreferences;
    PesanAnakAdapter pesanAnakAdapter;
    List<PesanAnakModel> pesanAnakModelList = new ArrayList<>();
    PesanAnakModel pesanAnakModel;
    ProgressDialog dialog;
    String tanggal,jam,mapel,pesan,guru,classroom_id,school_name,title,read_status;
    TextView no_pesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_anak);
        toolbar         = findViewById(R.id.toolbar_pesan_anak);
        rv_pesan        = findViewById(R.id.rv_pesan_anak);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        no_pesan        = findViewById(R.id.tv_no_pesan);


        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        dapat_pesan();
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

    public void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.Pesan_Anak> call = mApiInterface.kes_message_anak_get(authorization.toString(),school_code.toLowerCase(),student_id.toString());
        call.enqueue(new Callback<JSONResponse.Pesan_Anak>() {
            @Override
            public void onResponse(Call<JSONResponse.Pesan_Anak> call, final Response<JSONResponse.Pesan_Anak> response) {
                Log.d("onRespone",response.code()+"");
                hideDialog();
                JSONResponse.Pesan_Anak resource = response.body();

                status  = resource.status;
                code    = resource.code;

                if (status == 1 & code.equals("DTS_SCS_0001")){
                    for (int i = 0; i < response.body().getData().size();i++){
                        jam     = response.body().getData().get(i).getDatez();
                        tanggal     = response.body().getData().get(i).getMessage_date();
                        mapel       = response.body().getData().get(i).getCources_name();
                        pesan       = response.body().getData().get(i).getMessage_cont();
                        guru        = response.body().getData().get(i).getSender_name();
                        title       = response.body().getData().get(i).getMessage_title();
                        read_status = response.body().getData().get(i).getRead_status();
                        pesanAnakModel = new PesanAnakModel();
                        pesanAnakModel.setTanggal(tanggal);
                        pesanAnakModel.setJam(jam);
                        pesanAnakModel.setTitle(title);
                        pesanAnakModel.setPesan(pesan);
                        pesanAnakModel.setDari(guru);
                        pesanAnakModel.setRead_status(read_status);
                        pesanAnakModelList.add(pesanAnakModel);
                    }
                    no_pesan.setVisibility(View.GONE);
                    pesanAnakAdapter = new PesanAnakAdapter(pesanAnakModelList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PesanAnakDua.this);
                    rv_pesan.setLayoutManager(layoutManager);
                    rv_pesan.setAdapter(pesanAnakAdapter);
                }
                else if (status == 0 & code.equals("DTS_ERR_0001")){
                    hideKeyboard(PesanAnakDua.this);
                    no_pesan.setVisibility(View.VISIBLE);
                    rv_pesan.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Pesan_Anak> call, Throwable t) {
                Log.i("onFailure",t.toString());
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
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
        dialog = new ProgressDialog(PesanAnakDua.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    String convertjam(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}
