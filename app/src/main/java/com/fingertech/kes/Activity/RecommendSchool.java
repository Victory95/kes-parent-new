package com.fingertech.kes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fingertech.kes.R;

public class RecommendSchool extends AppCompatActivity {
    ImageView iv_checked,iv_uncheck;
    TextView tv_nama_sekolah,tv_jumblah_rekomendasi,tv_info_recommanded;
    Button btn_recommanded_school,btn_share_recommanded_school;
    RelativeLayout rel_recommanded_school,rel_share_recommanded_school;
    String member_id,school_id,school_code,school_name;

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
        btn_recommanded_school       = findViewById(R.id.btn_recommanded_school);
        btn_share_recommanded_school = findViewById(R.id.btn_share_recommanded_school);
        rel_recommanded_school       = findViewById(R.id.rel_recommanded_school);
        rel_share_recommanded_school = findViewById(R.id.rel_share_recommanded_school);
        school_code = getIntent().getStringExtra("school_code");
        school_id   = getIntent().getStringExtra("school_id");
        school_name = getIntent().getStringExtra("school_name");
        member_id   = getIntent().getStringExtra("member_id");
    }
}
