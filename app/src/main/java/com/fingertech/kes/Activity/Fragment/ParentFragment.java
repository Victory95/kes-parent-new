package com.fingertech.kes.Activity.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fingertech.kes.Activity.ParentMain;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParentFragment extends Fragment {


    public ParentFragment() {
        // Required empty public constructor
    }

    ViewPager ParentPager;
    ParentMain parentMain;
    Button buttonBerikutnya,buttonKembali;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ParentMain.FragmentAdapter fragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parent, container, false);

        parentMain          = (ParentMain)getActivity();
        ParentPager         = (ViewPager) parentMain.findViewById(R.id.PagerParent);
        indicator           = (LinearLayout) view.findViewById(R.id.indicators);
        buttonKembali       = (Button)view.findViewById(R.id.btn_kembali);
        buttonBerikutnya    = (Button)view.findViewById(R.id.btn_berikut);
        fragmentAdapter     = new ParentMain.FragmentAdapter(getActivity().getSupportFragmentManager());

        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(+1), true);
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            getActivity().finish();
            }
        });

        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[0].setBackgroundResource(R.drawable.selected_item);
        return view;
    }

    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
    }
    private void setUiPageViewController() {
        mDotCount = fragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            mDots[i] = new LinearLayout(getContext());
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


}
