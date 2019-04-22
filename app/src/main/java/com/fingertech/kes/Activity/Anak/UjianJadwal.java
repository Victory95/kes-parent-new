package com.fingertech.kes.Activity.Anak;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Fragment.UjianFragment.UasFragment;
import com.fingertech.kes.Activity.Fragment.UjianFragment.UnFragment;
import com.fingertech.kes.Activity.Fragment.UjianFragment.UtsFragment;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.UjianModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;
import com.kekstudio.dachshundtablayout.DachshundTabLayout;

import java.util.ArrayList;
import java.util.List;

public class UjianJadwal extends AppCompatActivity {
    ProgressDialog dialog;

    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewpager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ujian_jadwal);

        mToolbar= findViewById(R.id.toolbar);
        mTablayout=findViewById(R.id.tablayout);
        mViewpager=findViewById(R.id.viewpager);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        setTitle("Jadwal Ujian");
        mToolbar.setTitleTextColor(Color.WHITE);
        setupviewpager(mViewpager);
        mTablayout.setupWithViewPager(mViewpager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


    private void setupviewpager(ViewPager viewPager){

        viewpageradapter adapter = new viewpageradapter(getSupportFragmentManager());
        adapter.addFragment(new UtsFragment(),"UTS");
        adapter.addFragment(new UasFragment(),"UAS");
        adapter.addFragment(new UnFragment(),"UN");
        viewPager.setAdapter(adapter);

    }

    class viewpageradapter extends FragmentPagerAdapter{

        private final List<Fragment> mFragment = new ArrayList<>();
        private final List<String> mFragmentTittlelist = new ArrayList<>();


        public viewpageradapter(FragmentManager fm){

            super(fm);

        }

        @Override
        public Fragment getItem(int i) {
            return mFragment.get(i);

        }


        @Override
        public int getCount() {
            return mFragmentTittlelist.size();
        }

        public void addFragment (Fragment fragment,String title){
            mFragment.add(fragment);
            mFragmentTittlelist.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTittlelist.get(position);
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
        public void progress(){
            dialog = new ProgressDialog(UjianJadwal.this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
        }




    }
//

}
