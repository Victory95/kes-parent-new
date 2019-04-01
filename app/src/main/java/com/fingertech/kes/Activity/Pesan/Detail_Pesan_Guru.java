package com.fingertech.kes.Activity.Pesan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Anak.PesanDetail;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Pesan_Guru extends AppCompatActivity {
    Auth mApiInterface;
    TextView namapengirim,tanggal,pesanguru,namamurid;
    String authorization,school_code,parent_id,message_id,parent_message_id,code,fullname;
    ProgressDialog dialog;
    CardView balas;
    int status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pesan_guru);
        Toolbar toolbar = findViewById(R.id.toolbar);
        namapengirim = findViewById(R.id.Tvpengirim);
        tanggal      = findViewById(R.id.Tv_tanggal);
        pesanguru = findViewById(R.id.Tv_pesan_guru);
        namamurid = findViewById(R.id.Tvanak);
        balas     =findViewById(R.id.btn_balas);
        mApiInterface   = ApiClient.getClient().create(Auth.class);

        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        parent_id           = getIntent().getStringExtra("parent_id");
        message_id          = getIntent().getStringExtra("message_id");
        parent_message_id   = getIntent().getStringExtra("parent_message_id");
        fullname            = getIntent().getStringExtra("fullname");

        dapat_pesan();
        balas_pesan();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
    }

    public void balas_pesan(){
        balas.setOnClickListener(v -> {
            Intent intent = new Intent(Detail_Pesan_Guru.this, Balas_chat.class);
            intent.putExtra("authorization",authorization);
            intent.putExtra("school_code",school_code);
            intent.putExtra("parent_id",parent_id);
            intent.putExtra("message_id",message_id);
            intent.putExtra("parent_message_id",parent_message_id);
            intent.putExtra("fullname",fullname);
            startActivity(intent);
        });
    }

    public void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.PesanDetail> call = mApiInterface.kes_message_inbox_detail_get(authorization.toString(),school_code.toLowerCase().toString(),parent_id.toString(),message_id.toString(),parent_message_id.toString());
        call.enqueue(new Callback<JSONResponse.PesanDetail>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanDetail> call, Response<JSONResponse.PesanDetail> response) {
                Log.i("onResponse",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.PesanDetail resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        tanggal.setText(response.body().getData().getDataMessage().getDatez());
                        pesanguru.setText(response.body().getData().getDataMessage().getMessage_cont());
                        namapengirim.setText(response.body().getData().getDataMessage().getCreated_by());
                        namamurid.setText(response.body().getData().getDataMessage().getStudent_name());
                    }
                }else {
                    FancyToast.makeText(getApplicationContext(),"Eror database",FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanDetail> call, Throwable t) {
                Log.i("onFailure",t.toString());
                FancyToast.makeText(getApplicationContext(),"Pesan Rusak", Toast.LENGTH_LONG,FancyToast.ERROR,false).show();
                hideDialog();
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(Detail_Pesan_Guru.this,Pesan.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("parent_id", parent_id);
                setResult(RESULT_OK, intent);
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
        dialog = new ProgressDialog(Detail_Pesan_Guru.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
