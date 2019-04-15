package com.fingertech.kes.Activity.Pesan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fingertech.kes.Activity.Anak.PesanAnak;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.R;

public class PilihPesan extends AppCompatActivity {

    CardView btn_ortu,btn_siswa;
    SharedPreferences sharedPreferences;
    String authorization,school_code,parent_id,student_id,school_name,classroom_id,fullname,nama_anak;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pilih_pesan);

        toolbar     = findViewById(R.id.toolbar_pesan);
        btn_ortu    = findViewById(R.id.btn_pesan_ortu);
        btn_siswa   = findViewById(R.id.btn_pesan_anak);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        parent_id           = sharedPreferences.getString("member_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        fullname            = sharedPreferences.getString("fullname",null);
        nama_anak           = sharedPreferences.getString("student_name",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);


        btn_ortu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("member_id", parent_id);
                editor.putString("school_code", school_code);
                editor.putString("authorization", authorization);
                editor.putString("fullname",fullname);
                editor.apply();
                Intent intent = new Intent(PilihPesan.this, Content_Pesan_Guru.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("parent_id",parent_id);
                intent.putExtra("fullname",fullname);
                startActivity(intent);
            }
        });

        btn_siswa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("member_id",parent_id);
                editor.putString("school_code",school_code.toLowerCase());
                editor.putString("authorization",authorization);
                editor.putString("classroom_id",classroom_id);
                editor.putString("school_name",school_name);
                editor.putString("student_id",student_id);
                editor.putString("student_name",nama_anak);
                editor.apply();
                Intent intent = new Intent(PilihPesan.this, PesanAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code.toLowerCase());
                intent.putExtra("member_id", parent_id);
                intent.putExtra("classroom_id", classroom_id);
                intent.putExtra("school_name",school_name);
                intent.putExtra("student_id", student_id);
                intent.putExtra("student_name",nama_anak);
                startActivity(intent);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
