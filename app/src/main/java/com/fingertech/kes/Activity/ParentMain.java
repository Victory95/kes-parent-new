package com.fingertech.kes.Activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parent_main);

        indicator = (LinearLayout) findViewById(R.id.indicators);
        ParentPager = (ViewPager) findViewById(R.id.PagerParent);
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
        ParentPager.setAdapter(fragmentAdapter);
        ParentPager.setOnPageChangeListener(this);
        buttonBerikutnya = (Button)findViewById(R.id.btn_berikut);
        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(+1), true);
            }
        });
        buttonKembali = (Button)findViewById(R.id.btn_kembali);
        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
        });
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
            indicator.addView(mDots[i], params);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

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





}


