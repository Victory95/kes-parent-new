package com.fingertech.kes.Activity.Pesan;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.fingertech.kes.R;

public class Content_Pesan_Guru extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_pesan_guru);
        BottomNavigationView bottomNavigationView= findViewById(R.id.navbottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(navlistener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Pesan()).commit();
    }
    private BottomNavigationView .OnNavigationItemSelectedListener navlistener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem Item) {
                    Fragment selectedfragment = null;
                    switch (Item.getItemId()){
                        case R.id.navpesanmasuk:
                            selectedfragment = new Pesan();
                            break;
                        case R.id.navpesanterkirim:
                            selectedfragment = new PesanTerkirim();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedfragment).commit();
                    return true;
                }
            };
}

