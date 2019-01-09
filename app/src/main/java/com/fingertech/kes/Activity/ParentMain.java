package com.fingertech.kes.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fingertech.kes.Activity.Fragment.AnakFragment;
import com.fingertech.kes.Activity.Fragment.DataAnakFragment;
import com.fingertech.kes.Activity.Fragment.DataFragment;
import com.fingertech.kes.Activity.Fragment.KontakAnakFragment;
import com.fingertech.kes.Activity.Fragment.KontakFragment;
import com.fingertech.kes.Activity.Fragment.ParentFragment;
import com.fingertech.kes.Activity.Fragment.PekerjaanFragment;
import com.fingertech.kes.Activity.Fragment.TempatTinggalFragment;
import com.fingertech.kes.R;


public class ParentMain extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ViewPager ParentPager;
    private FragmentAdapter fragmentAdapter;
    private Button buttonBerikutnya, buttonKembali;
    public static int PAGE_COUNT = 8;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_main);

        Window window = ParentMain.this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(ParentMain.this,R.color.colorPrimary));

        indicator = (LinearLayout) findViewById(R.id.indicators);
        ParentPager = (ViewPager) findViewById(R.id.PagerParent);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        ParentPager.setAdapter(fragmentAdapter);
        ParentPager.setOnPageChangeListener(this);
        buttonBerikutnya = (Button)findViewById(R.id.btn_berikut);
        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ParentPager.getCurrentItem() == 7){
                    Intent intent = new Intent(ParentMain.this, MenuUtama.class);
                    startActivity(intent);
                }else {
                    ParentPager.setCurrentItem(getItem(+1), true);
                }

            }
        });
        buttonKembali = (Button)findViewById(R.id.btn_kembali);
        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(ParentPager.getCurrentItem() == 0){
                finish();
            }else {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
            }
        });

        checkLocationPermission();
        setUiPageViewController();
    }

    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
    }

    private void setUiPageViewController() {
        mDotCount = fragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            mDots[i] = new LinearLayout(this);
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            indicator.addView(mDots[i]);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (ParentPager.getCurrentItem() == 0) {
            buttonKembali.setText("Batal");
        } else {
            buttonKembali.setText("Kembali");
        }
        if (ParentPager.getCurrentItem() == 7) {
            buttonBerikutnya.setText("Submit");
        } else {
            buttonBerikutnya.setText("Berikutnya");
        }

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[position].setBackgroundResource(R.drawable.selected_item);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new ParentFragment();
                case 1:
                    return new DataFragment();
                case 2:
                    return new KontakFragment();
                case 3:
                    return new PekerjaanFragment();
                case 4:
                    return new AnakFragment();
                case 5:
                    return new DataAnakFragment();
                case 6:
                    return new KontakAnakFragment();
                case 7:
                    return new TempatTinggalFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

}


