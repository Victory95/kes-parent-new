package com.fingertech.kes.Activity.Anak;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BalasPesan extends AppCompatActivity {

    Toolbar toolbar;
    String authorization,school_code,parent_id,message_id,balas,sekolah,anak,kelas,mapel,parent_message_id;
    Auth mApiInterface;
    TextView kepada,tv_sekolah,tv_anak,tv_kelas,tv_mapel;
    EditText et_pesan;
    int status;
    String code;
    CardView balas_pesan;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balas_pesan);
        toolbar     = findViewById(R.id.toolbar_balas);
        kepada      = findViewById(R.id.balas_kepada);
        tv_sekolah  = findViewById(R.id.sekolah);
        tv_anak     = findViewById(R.id.anak);
        tv_kelas    = findViewById(R.id.kelas_pesan);
        tv_mapel    = findViewById(R.id.mapel_pesan);
        et_pesan    = findViewById(R.id.isi_pesan);
        balas_pesan = findViewById(R.id.btn_balas);
        mApiInterface = ApiClient.getClient().create(Auth.class);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        authorization       = getIntent().getStringExtra("authorization");
        school_code         = getIntent().getStringExtra("school_code");
        parent_id           = getIntent().getStringExtra("parent_id");
        message_id          = getIntent().getStringExtra("message_id");
        balas               = getIntent().getStringExtra("guru");
        sekolah             = getIntent().getStringExtra("sekolah");
        anak                = getIntent().getStringExtra("anak");
        kelas               = getIntent().getStringExtra("kelas");
        mapel               = getIntent().getStringExtra("mapel");
        parent_message_id   = getIntent().getStringExtra("parent_message_id");
        kepada.setText(balas);
        tv_sekolah.setText(sekolah);
        tv_anak.setText(anak);
        tv_kelas.setText(kelas);
        tv_mapel.setText(mapel);
        balas_pesan.setOnClickListener(v -> balas_pesan());
        hideKeyboard(BalasPesan.this);
        et_pesan.clearFocus();
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
        dialog = new ProgressDialog(BalasPesan.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void balas_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.BalasPesan> call = mApiInterface.kes_reply_message_post(authorization.toString(),school_code.toLowerCase().toString(),parent_id.toString(),message_id.toString(),et_pesan.getText().toString());
        call.enqueue(new Callback<JSONResponse.BalasPesan>() {
            @Override
            public void onResponse(Call<JSONResponse.BalasPesan> call, Response<JSONResponse.BalasPesan> response) {
                hideDialog();
                Log.i("onRespon",response.code()+"");
                JSONResponse.BalasPesan resource = response.body();
                status = resource.status;
                code    = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    Intent intent = new Intent(BalasPesan.this,PesanDetail.class);
                    intent.putExtra("authorization",authorization);
                    intent.putExtra("school_code",school_code);
                    intent.putExtra("parent_id",parent_id);
                    intent.putExtra("message_id",message_id);
                    intent.putExtra("parent_message_id",parent_message_id);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.BalasPesan> call, Throwable t) {
            hideDialog();
            Log.i("onFailure",t.toString());
            }
        });
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
