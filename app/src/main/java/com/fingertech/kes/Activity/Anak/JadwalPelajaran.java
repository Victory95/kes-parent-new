package com.fingertech.kes.Activity.Anak;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fingertech.kes.Activity.Fragment.AnakFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.AgustusFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.AprilFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.DesemberFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.FebuariFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.JanuariFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.JuliFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.JuniFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.MaretFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.MeiFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.NovemberFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.OctoberFragment;
import com.fingertech.kes.Activity.Fragment.Bulan.SeptemberFragment;
import com.fingertech.kes.Activity.Fragment.DataAnakFragment;
import com.fingertech.kes.Activity.Fragment.DataFragment;
import com.fingertech.kes.Activity.Fragment.DataPelengkap;
import com.fingertech.kes.Activity.Fragment.DataPeriodik;
import com.fingertech.kes.Activity.Fragment.IdentitasFragment;
import com.fingertech.kes.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class JadwalPelajaran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jadwal_pelajaran);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Jadwal Pelajaran");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Januari", JanuariFragment.class)
                .add("Febuari", FebuariFragment.class)
                .add("Maret", MaretFragment.class)
                .add("April", AprilFragment.class)
                .add("Mei", MeiFragment.class)
                .add("Juni", JuniFragment.class)
                .add("Juli", JuliFragment.class)
                .add("Agustus", AgustusFragment.class)
                .add("September", SeptemberFragment.class)
                .add("Oktober", OctoberFragment.class)
                .add("November", NovemberFragment.class)
                .add("Desember", DesemberFragment.class)
                .create());

        DateFormat df = new SimpleDateFormat("MM");
        String last_login = df.format(Calendar.getInstance().getTime());
        if(last_login.substring(0,1).equals("0"))
        {
            last_login = last_login.substring(1);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_jadwal);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.parseInt(last_login) - 1);


        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
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
