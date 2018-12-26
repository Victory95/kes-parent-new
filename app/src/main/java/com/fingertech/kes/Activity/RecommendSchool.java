package com.fingertech.kes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fingertech.kes.R;

public class RecommendSchool extends AppCompatActivity {
    private ImageView iv_checked,iv_uncheck;
    private TextView tv_nama_sekolah,tv_jumblah_rekomendasi,tv_info_recommanded;
    private Button btn_recommanded_school,btn_share_recommanded_school;
    private RelativeLayout rel_recommanded_school,rel_share_recommanded_school;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_school);
        getSupportActionBar().setElevation(0);

        iv_checked                   =(ImageView)findViewById(R.id.iv_checked);
        iv_uncheck                   =(ImageView)findViewById(R.id.iv_uncheck);
        tv_nama_sekolah              =(TextView)findViewById(R.id.tv_nama_sekolah);
        tv_jumblah_rekomendasi       =(TextView)findViewById(R.id.tv_jumblah_rekomendasi);
        tv_info_recommanded          =(TextView)findViewById(R.id.tv_info_recommanded);
        btn_recommanded_school       =(Button)findViewById(R.id.btn_recommanded_school);
        btn_share_recommanded_school =(Button)findViewById(R.id.btn_share_recommanded_school);
        rel_recommanded_school       =(RelativeLayout)findViewById(R.id.rel_recommanded_school);
        rel_share_recommanded_school =(RelativeLayout)findViewById(R.id.rel_share_recommanded_school);

    }
}
