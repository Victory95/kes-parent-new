package com.fingertech.kes.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fingertech.kes.R;

public class RecommendSchool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_school);
        getSupportActionBar().setElevation(0);
    }
}
