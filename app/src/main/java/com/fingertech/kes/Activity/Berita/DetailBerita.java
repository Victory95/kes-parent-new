package com.fingertech.kes.Activity.Berita;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Adapter.NewsAdapter;
import com.fingertech.kes.Activity.Model.NewsModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Rest.UtilsApi;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailBerita extends AppCompatActivity {

    Auth mApi;
    TextView tv_title,tv_jam,tv_body;
    ImageView iv_berita;
    int status;
    String news_title,news_id,news_body,news_date,news_image,base_url_news;
    ProgressDialog dialog;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_berita);
        toolbar         = findViewById(R.id.toolbar_berita);
        tv_title        = findViewById(R.id.title_berita);
        tv_jam          = findViewById(R.id.tanggal_berita);
        tv_body         = findViewById(R.id.body_berita);
        iv_berita       = findViewById(R.id.image_berita);
        mApi            = UtilsApi.getAPIService();
        base_url_news = "http://www.kes.co.id/schoolm/assets/images/news/mm_";
        news_id = getIntent().getStringExtra("news_id");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        detail_berita();
    }
    private void detail_berita(){
        progressBar();
        showDialog();
        mApi.detail_news_get(news_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse.DetailBerita>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONResponse.DetailBerita detailBerita) {
                        status = detailBerita.status;
                        if (status == 1){
                            news_title = detailBerita.getData().getNewstitle();
                            news_date   = detailBerita.getData().getDatez();
                            news_body   = detailBerita.getData().getNewsbody();
                            news_image  = detailBerita.getData().getNewspicture();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Log.d("Eror",e.toString());
                    }

                    @Override
                    public void onComplete() {
                        hideDialog();
                        tv_title.setText(news_title);
                        tv_body.setText(Html.fromHtml(news_body));
                        tv_jam.setText(news_date);
                        Glide.with(DetailBerita.this).load(base_url_news+news_image).into(iv_berita);
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
        dialog = new ProgressDialog(DetailBerita.this);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
