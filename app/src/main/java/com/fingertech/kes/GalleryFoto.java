package com.fingertech.kes;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.FotoAdapter;
import com.fingertech.kes.Activity.Model.FotoModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFoto extends AppCompatActivity {

    RecyclerView rv_foto;
    FotoModel fotoModel;
    List<FotoModel> fotoModelList = new ArrayList<>();
    FotoAdapter fotoAdapter;
    Auth mApiInterface;
    int status;
    String school_id,Picture;
    ProgressDialog dialog;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_foto);
        rv_foto         = findViewById(R.id.rv_gallery);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        school_id       = getIntent().getStringExtra("school_id");
        toolbar         = findViewById(R.id.toolbar_gallery);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        dapat_picture();

    }
    public void dapat_picture(){
        progressBar();
        showDialog();
        Call<JSONResponse.Foto_sekolah> call = mApiInterface.kes_full_schoolpic_get(school_id);
        call.enqueue(new Callback<JSONResponse.Foto_sekolah>() {
            @Override
            public void onResponse(Call<JSONResponse.Foto_sekolah> call, Response<JSONResponse.Foto_sekolah> response) {
                Log.d("DetailSekolah",response.code()+"");
                hideDialog();
                JSONResponse.Foto_sekolah resource = response.body();
                if (resource==null){
                    FancyToast.makeText(getApplicationContext(),"Tidak ada foto", Toast.LENGTH_LONG,FancyToast.ERROR,false);
                }else {
                    status = resource.status;
                    if (status == 1) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            Picture = response.body().getData().get(i).getPic_url();
                            fotoModel = new FotoModel();
                            fotoModel.setPicture(Picture);
                            fotoModelList.add(fotoModel);
                        }
                        fotoAdapter = new FotoAdapter(fotoModelList);
                        GridLayoutManager layoutManager = new GridLayoutManager(GalleryFoto.this, 2);
                        rv_foto.setLayoutManager(layoutManager);
                        rv_foto.setAdapter(fotoAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Foto_sekolah> call, Throwable t) {
                Log.d("Gagal",t.toString());
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
        dialog = new ProgressDialog(GalleryFoto.this);
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
