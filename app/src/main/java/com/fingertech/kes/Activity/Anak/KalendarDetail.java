package com.fingertech.kes.Activity.Anak;

import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KalendarDetail extends AppCompatActivity {

    Toolbar toolbar;
    String authorization,school_code,calendar_id;
    TextView tanggal,jam,guru,deskripsi,judul;
    Auth mApiInterface;
    int status;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_detail);
        toolbar         = findViewById(R.id.toolbar_kalendar_detail);
        tanggal         = findViewById(R.id.tanggal);
        jam             = findViewById(R.id.waktu);
        deskripsi       = findViewById(R.id.deskripsi);
        judul           = findViewById(R.id.judul);
        guru            = findViewById(R.id.dibuat);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization   = getIntent().getStringExtra("authorization");
        school_code     = getIntent().getStringExtra("school_code");
        calendar_id     = getIntent().getStringExtra("calendar_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        detail_calendar();
    }
    public void detail_calendar(){
        Call<JSONResponse.CalendarDetail> call = mApiInterface.kes_calendar_detail_get(authorization,school_code.toLowerCase(),calendar_id);
        call.enqueue(new Callback<JSONResponse.CalendarDetail>() {
            @Override
            public void onResponse(Call<JSONResponse.CalendarDetail> call, Response<JSONResponse.CalendarDetail> response) {
                Log.i("onRespone",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.CalendarDetail resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tanggal.setText(response.body().getData().getCalendar_date_ok());
                        jam.setText(response.body().getData().getTimez());
                        deskripsi.setText(response.body().getData().getCalendar_desc());
                        judul.setText(response.body().getData().getCalendar_title());
                        guru.setText(response.body().getData().getCreated_by());
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CalendarDetail> call, Throwable t) {
                Toast.makeText(KalendarDetail.this,t.toString(),Toast.LENGTH_LONG).show();
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

}
