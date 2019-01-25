package com.fingertech.kes.Activity.Fragment.Bulan;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingertech.kes.Activity.Adapter.JanuariAdapter;
import com.fingertech.kes.R;
import com.nshmura.recyclertablayout.RecyclerTabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class JanuariFragment extends Fragment implements ViewPager.OnPageChangeListener {


    public JanuariFragment() {
        // Required empty public constructor
    }
    private int mScrollState;
    private JanuariAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;
    String last_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_januari, container, false);
        mItems = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            mItems.add(String.valueOf(i));
        }


        mAdapter = new JanuariAdapter();
        mAdapter.addAll(mItems);
        DateFormat df = new SimpleDateFormat("dd");
        last_login = df.format(Calendar.getInstance().getTime());
        if(last_login.substring(0,1).equals("0"))
        {
            last_login = last_login.substring(1);
        }

        mViewPager = view.findViewById(R.id.view_pager);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mAdapter.getCenterPosition(Integer.parseInt(last_login) - 1));
        mViewPager.addOnPageChangeListener(this);

        RecyclerTabLayout recyclerTabLayout = view.findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithViewPager(mViewPager);
        return view;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        boolean nearLeftEdge = (position <= mItems.size());
        boolean nearRightEdge = (position >= mAdapter.getCount() - mItems.size());
        if (nearLeftEdge || nearRightEdge) {
            mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mScrollState = state;
    }
}
