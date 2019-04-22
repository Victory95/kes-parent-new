package com.fingertech.kes.Activity.NextProject;

import android.annotation.SuppressLint;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;
import com.fingertech.kes.R;
import java.util.Objects;

public class TestKalender extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_kalender);
        button  = findViewById(R.id.show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoBottomSheetFragment demoBottomSheetFragment = new DemoBottomSheetFragment();
                demoBottomSheetFragment.show(getSupportFragmentManager(), "DemoBottomSheetFragment");
            }
        });
    }

    @SuppressLint("ValidFragment")
    public static class DemoBottomSheetFragment extends SuperBottomSheetFragment{
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View view = inflater.inflate(R.layout.fragment_anak, container, false);
            return view;
        }
        @SuppressLint("ResourceType")
        @Override
        public float getCornerRadius(){
            return Objects.requireNonNull(getActivity()).getResources().getDimension(R.dimen.radius);
        }
        @Override
        public int getStatusBarColor(){
            return Color.parseColor("#3cb0d6");
        }
    }
}
