package com.fingertech.kes.Activity.Pesan;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Balas_chat extends AppCompatActivity {
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//    @BindView(R.id.sekolah_pesan)
//    TextView sekolahPesan;
//    @BindView(R.id.kelas_pesan)
//    TextView kelasPesan;
//    @BindView(R.id.etpesan)
//    EditText etpesan;
//    @BindView(R.id.btn_balas)
//    CardView btnBalas;

    Auth mApiInterface;
    TextView sekolahpesan,kelaspesan,pesan;

    int status;

    String authorization,school_code,parent_id,message_id,parent_message_id,fullname,code;
    EditText pesanbaru;
    CardView balas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balas_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        sekolahpesan = findViewById(R.id.sekolah_pesan);
        kelaspesan = findViewById(R.id.kelas_pesan);
        pesan=findViewById(R.id.tv_pesan_guru);
        pesanbaru=findViewById(R.id.etpesan);
        balas=findViewById(R.id.btn_balas);
        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        parent_id           = getIntent().getStringExtra("parent_id");
        message_id          = getIntent().getStringExtra("message_id");
        parent_message_id   = getIntent().getStringExtra("parent_message_id");
        fullname            = getIntent().getStringExtra("fullname");

        mApiInterface   = ApiClient.getClient().create(Auth.class);
        dapat_pesan();
        balas_pesan();


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);





    }


    public void balas_pesan(){


        balas.setOnClickListener(v -> {
            Intent intent = new Intent(Balas_chat.this, MenuUtama.class);
            intent.putExtra("authorization",authorization);
            intent.putExtra("school_code",school_code);
            intent.putExtra("parent_id",parent_id);
            intent.putExtra("message_id",message_id);
            intent.putExtra("parent_message_id",parent_message_id);
            intent.putExtra("fullname",fullname);
            balaspesan();
            FancyToast.makeText(getApplicationContext(),"pesan terkirim",Toast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
            startActivity(intent);
        });
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
                    sekolahpesan.setText(response.body().getData().getDataMessage().getCreated_by());
                    kelaspesan.setText(fullname);
                    pesan.setText(response.body().getData().getDataMessage().getMessage_cont());
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {
                Log.i("onFailure",t.toString());
            }
        });
    }

    public void balaspesan(){
        Call<JSONResponse.BalasPesan>call = mApiInterface.kes_reply_message_post(authorization.toString(),school_code.toLowerCase().toString(),parent_id.toString(),message_id.toString(),pesanbaru.getText().toString());
        call.enqueue(new Callback<JSONResponse.BalasPesan>() {
            @Override
            public void onResponse(Call<JSONResponse.BalasPesan> call, Response<JSONResponse.BalasPesan> response) {
                Log.d("onRespone",response.code()+"");
                JSONResponse.BalasPesan resource = response.body();

                status = resource.status;
                code   = resource.code;
                if(status==1 && code.equals("DTS_SCS_0001")){
                    FancyToast.makeText(getApplicationContext(),"pesan terkirim",Toast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.BalasPesan> call, Throwable t) {
                Log.i("onFailure",t.toString());
            }
        });

    }





}
