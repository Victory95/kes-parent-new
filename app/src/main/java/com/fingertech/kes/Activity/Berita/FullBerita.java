package com.fingertech.kes.Activity.Berita;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.NewsAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.NewsModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Rest.UtilsApi;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class FullBerita extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_berita;
    NewsModel newsModel;
    List<NewsModel> newsModelList = new ArrayList<>();
    NewsAdapter newsAdapter;
    Auth mApi;
    int status;
    TextView tv_no_berita;
    String news_title,news_id,news_body,news_date,news_image;
    String base_url_news;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_berita);
        toolbar         = findViewById(R.id.toolbar_berita);
        rv_berita       = findViewById(R.id.rv_berita);
        tv_no_berita    = findViewById(R.id.no_berita);
        mApi            = UtilsApi.getAPIService();
        base_url_news = "http://www.kes.co.id/schoolm/assets/images/news/mm_";

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        Daftar_berita();
    }
    private void Daftar_berita(){
        progressBar();
        showDialog();
        mApi.full_news_get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse.last_news>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONResponse.last_news last_news) {
                        status = last_news.status;
                        if (status == 1){
                            tv_no_berita.setVisibility(GONE);
                            rv_berita.setVisibility(VISIBLE);
                            for (int i = 0;i < last_news.getData().size();i++){
                                news_id    = last_news.getData().get(i).getNewsid();
                                news_title = last_news.getData().get(i).getNewstitle();
                                news_body  = last_news.getData().get(i).getNewsbody();
                                news_image = last_news.getData().get(i).getNewspicture();
                                news_date  = last_news.getData().get(i).getDatez();
                                newsModel = new NewsModel();
                                newsModel.setNews_id(news_id);
                                newsModel.setNews_title(news_title);
                                newsModel.setNews_body(news_body);
                                newsModel.setDatez(news_date);
                                newsModel.setNews_picture(base_url_news+news_image);
                                newsModelList.add(newsModel);
                            }
                        }else if (status == 0){
                            rv_berita.setVisibility(GONE);
                            tv_no_berita.setVisibility(VISIBLE);
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
                        newsAdapter = new NewsAdapter(FullBerita.this,newsModelList);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FullBerita.this);
                        rv_berita.setLayoutManager(layoutManager);
                        rv_berita.setAdapter(newsAdapter);
                        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String newsid = newsModelList.get(position).getNews_id();
                                Intent intent = new Intent(FullBerita.this, DetailBerita.class);
                                intent.putExtra("news_id",newsid);
                                startActivity(intent);
                            }
                        });
                    }
                });
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
        dialog = new ProgressDialog(FullBerita.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

}
