package com.fingertech.kes.Activity.Anak;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanDetail extends AppCompatActivity {

    Toolbar toolbar;
    Auth mApiInterface;
    TextView tanggal,sekolah,dibuat,mapel,anak,kelas,pesan;
    CardView balas;
    String authorization,school_code,parent_id,message_id,parent_message_id;
    String code;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pesan_detail);
        toolbar             = findViewById(R.id.toolbar_pesan_detail);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        tanggal             = findViewById(R.id.tanggal);
        sekolah             = findViewById(R.id.sekolah);
        dibuat              = findViewById(R.id.dibuat_oleh);
        mapel               = findViewById(R.id.mapel_pesan);
        anak                = findViewById(R.id.anak);
        kelas               = findViewById(R.id.kelas_pesan);
        pesan               = findViewById(R.id.isi_pesan);
        balas               = findViewById(R.id.btn_balas);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        parent_id           = getIntent().getStringExtra("parent_id");
        message_id          = getIntent().getStringExtra("message_id");
        parent_message_id   = getIntent().getStringExtra("parent_message_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        dapat_pesan();

        balas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PesanDetail.this,BalasPesan.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("parent_id",parent_id);
                intent.putExtra("message_id",message_id);
                intent.putExtra("parent_message_id",parent_message_id);
                intent.putExtra("guru",dibuat.getText().toString());
                intent.putExtra("sekolah",sekolah.getText().toString());
                intent.putExtra("anak",anak.getText().toString());
                intent.putExtra("kelas",kelas.getText().toString());
                intent.putExtra("mapel",mapel.getText().toString());
                startActivity(intent);
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

    public void dapat_pesan(){
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_message_inbox_detail_get(authorization.toString(),school_code.toLowerCase().toString(),parent_id.toString(),message_id.toString(),parent_message_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.i("onResponse",response.code()+"");
                JSONResponse.PesanDetail resource = response.body();
                status  = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    tanggal.setText(response.body().getData().getDataMessage().getDatez());
                    sekolah.setText(response.body().getData().getDataMessage().getSchool_name());
                    dibuat.setText(response.body().getData().getDataMessage().getCreated_by());
                    mapel.setText(response.body().getData().getDataMessage().getCources_name());
                    anak.setText(response.body().getData().getDataMessage().getStudent_name());
                    kelas.setText(response.body().getData().getDataMessage().getClassroom_name());
                    pesan.setText(response.body().getData().getDataMessage().getMessage_cont());
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {
            Log.i("onFailure",t.toString());
            }
        });
    }
}
