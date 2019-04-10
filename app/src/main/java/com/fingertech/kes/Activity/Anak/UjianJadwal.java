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
import com.fingertech.kes.Activity.Adapter.UjianAdapterTeratas;
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
//    DachshundTabLayout dachshundTabLayout;
//    ViewPager viewPager;
//    Toolbar toolbar;
//    List<ItemUjian> itemUjianList = new ArrayList<>();
//    ItemUjian itemUjian;
//    UjianAdapter ujianAdapter;
//    Auth mApiInterface;
//    String authorization,memberid,school_code,classroom_id;
//    RecyclerView rv_ujian,rv_teratas;
//    ProgressDialog dialog;
//    int status;
//    String code;
//    String jam,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
//    String waktu,bulan,judul,tanggal_teratas,des;
//    TextView no_ujian;
//    String date,semester_nama;
//    TextView tv_semester,tv_filter,tv_semesters,tv_reset,tv_slide;
//    EditText et_kata_kunci;
//    LinearLayout ll_slide;
//    com.rey.material.widget.Spinner sp_type;
//    private List<JSONResponse.DataSemester> dataSemesters;
//    private List<JSONResponse.DataMapel> dataMapelList;
//    Spinner sp_mapel;
//    Button btn_cari;
//    ImageView btn_down;
//    View view;
//    String mata_pelajaran,type_pelajaran;
//    SharedPreferences sharedPreferences,sharedPreferences2;
//    List<UjianModel> ujianModelList = new ArrayList<>();
//    UjianModel ujianModel;
//    FragmentTransaction ft;
//    FragmentManager fm;
//    public static final String my_viewpager_preferences = "my_ujian_preferences";
//    private Bundle bundle;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ujian_jadwal);
//        dachshundTabLayout  = findViewById(R.id.tab_layout);
//        viewPager           = findViewById(R.id.view_pager);
//        toolbar             = findViewById(R.id.toolbar_ujian);
//
//        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
//        sharedPreferences2  = getSharedPreferences(my_viewpager_preferences,Context.MODE_PRIVATE);
//        authorization       = sharedPreferences.getString("authorization",null);
//        school_code         = sharedPreferences.getString("school_code",null);
//        memberid            = sharedPreferences.getString("student_id",null);
//        classroom_id        = sharedPreferences.getString("classroom_id",null);
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//
//        setupViewPager(viewPager);
//        dachshundTabLayout.setupWithViewPager(viewPager);
//        dachshundTabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//                switch (tab.getPosition()){
//                    case 0:
////                        sendataUts();
//                        break;
//                    case 1:
////                        sendataUas();
//                        break;
//                    case 2:
////                        sendataUn();
//                        break;
//                }
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
//
//    private void sendataUts(){
//        bundle = new Bundle();
//        bundle.putString("authorization",authorization);
//        bundle.putString("school_code",school_code);
//        bundle.putString("student_id",memberid);
//        bundle.putString("classroom_id",classroom_id);
//        bundle.putString("type_ujian","MID");
//        UtsFragment utsFragment = new UtsFragment();
//        fm = getSupportFragmentManager();
//        ft = fm.beginTransaction();
//        ft.replace(R.id.fragUts, utsFragment);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack(null);
//        ft.commit();
//        utsFragment.setArguments(bundle);
//    }
//
//    private void sendataUas(){
//        Bundle bundle1 = new Bundle();
//        bundle1.putString("authorization",authorization);
//        bundle1.putString("school_code",school_code);
//        bundle1.putString("student_id",memberid);
//        bundle1.putString("classroom_id",classroom_id);
//        bundle1.putString("type_ujian","FINAL");
//        UasFragment uasFragment = new UasFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragUas,uasFragment);
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//        uasFragment.setArguments(bundle1);
//    }
//
//    private void sendataUn(){
//        Bundle bundle2 = new Bundle();
//        bundle2.putString("authorization",authorization);
//        bundle2.putString("school_code",school_code);
//        bundle2.putString("student_id",memberid);
//        bundle2.putString("classroom_id",classroom_id);
//        bundle2.putString("type_ujian","UN");
//        UnFragment unFragment = new UnFragment();
//        FragmentManager fragmentManager2 = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
//        fragmentTransaction2.replace(R.id.fragUn,unFragment);
//        fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction2.addToBackStack(null);
//        fragmentTransaction2.commit();
//        unFragment.setArguments(bundle2);
//    }
//
//    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFrag(new UtsFragment(),"UTS");
//        adapter.addFrag(new UasFragment(),"UAS");
//        adapter.addFrag(new UnFragment(),"UN");
//        viewPager.setAdapter(adapter);
//    }
//
//    public class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> mFragmentList = new ArrayList<>();
//        private final List<String> mFragmentTitleList = new ArrayList<>();
//
//        ViewPagerAdapter(FragmentManager manager) {
//            super(manager);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            switch (position){
//                case 0:
//                    sendataUts();
//                    return new UtsFragment();
//                case 1:
//                    sendataUas();
//                    return new UasFragment();
//                case 2:
//                    sendataUn();
//                    return new UnFragment();
//            }
//            return null;
//        }
//
//        @Override
//        public int getCount() {
//            return mFragmentList.size();
//        }
//
//        void addFrag(Fragment fragment, String title) {
//            mFragmentList.add(fragment);
//            mFragmentTitleList.add(title);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mFragmentTitleList.get(position);
//        }
//    }

}
