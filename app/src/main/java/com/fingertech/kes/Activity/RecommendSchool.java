package com.fingertech.kes.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.share.Share;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendSchool extends AppCompatActivity {
    ImageView iv_checked,iv_uncheck;
    TextView tv_nama_sekolah,tv_jumblah_rekomendasi,tv_info_recommanded,tv_recommanded_school;
    Button btn_share_recommanded_school;
    RelativeLayout rel_share_recommanded_school;
    String member_id,school_id,school_code,school_name,authorization;
    CardView cv_recommanded_school;
    SharedPreferences sharedPreferences;
    Auth mApiInterface;
    int status;
    String code;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_school);
        getSupportActionBar().setElevation(0);

        iv_checked                   = findViewById(R.id.iv_checked);
        iv_uncheck                   = findViewById(R.id.iv_uncheck);
        tv_nama_sekolah              = findViewById(R.id.tv_nama_sekolah);
        tv_jumblah_rekomendasi       = findViewById(R.id.tv_jumblah_rekomendasi);
        tv_info_recommanded          = findViewById(R.id.tv_info_recommanded);
        tv_recommanded_school        = findViewById(R.id.btn_recommanded_school);
        btn_share_recommanded_school = findViewById(R.id.btn_share_recommanded_school);
        cv_recommanded_school        = findViewById(R.id.rel_recommanded_school);
        rel_share_recommanded_school = findViewById(R.id.rel_share_recommanded_school);
        mApiInterface                = ApiClient.getClient().create(Auth.class);

        sharedPreferences = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization     = sharedPreferences.getString("authorization",null);

        school_code = getIntent().getStringExtra("school_code");
        school_id   = getIntent().getStringExtra("school_id");
        school_name = getIntent().getStringExtra("school_name");
        member_id   = getIntent().getStringExtra("member_id");

        tv_nama_sekolah.setText(school_name);
        cv_recommanded_school.setOnClickListener(v -> {
            recommend_sekolah();
        });
    }

    private void recommend_sekolah(){
        progressBar();
        showDialog();
        Call<JSONResponse.BalasPesan> call = mApiInterface.kes_recommend_school_post(authorization,school_id,school_code.toLowerCase(),school_name,member_id);
        call.enqueue(new Callback<JSONResponse.BalasPesan>() {
            @Override
            public void onResponse(Call<JSONResponse.BalasPesan> call, Response<JSONResponse.BalasPesan> response) {
                Log.d("Sukses",response.code()+"");
                JSONResponse.BalasPesan resource = response.body();

                hideDialog();
                status = resource.status;
                code   = resource.code;
                if (status== 1&& code.equals("REC_SCS_0001")){
                    FancyToast.makeText(getApplicationContext(),"Anda sukses merekommendasikan sekolah anak anda ke KES",FancyToast.LENGTH_LONG,FancyToast.SUCCESS,false).show();
                    finish();
                }else if (status == 0 && code.equals("REC_ERR_0006")){
                    FancyToast.makeText(getApplicationContext(),"Anda sudah merekommendasikan sekolah anak anda ke KES",FancyToast.LENGTH_LONG,FancyToast.INFO,false).show();

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.BalasPesan> call, Throwable t) {
            hideDialog();
            Log.d("gagal",t.toString());

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
        dialog = new ProgressDialog(RecommendSchool.this);
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
