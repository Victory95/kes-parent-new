package com.fingertech.kes.Activity.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kes.Service.App.getContext;

public class AbsenAdapter extends PagerAdapter {

    private static final int NUMBER_OF_LOOPS = 10000;

    private List<String> mItems = new ArrayList<>();
    String date,day;
    private List<AbsenModel> profileModels;
    private List<AbsensiModel>absensiModels;
    AbsensiModel absensiModel;
    AbsensiModel.dataAbsensi dataAbsensi;
    AbsensiAdapter absensiAdapter;
    private List<AbsensiModel.dataAbsensi>dataAbsensiList = new ArrayList<>();

    public AbsenAdapter(List<AbsenModel> viewItemList) {
        this.profileModels = viewItemList;
    }

    String timez_star,timez_finish,day_type,absent_status,day_id;
    RecyclerView recyclerView;
    TextView jam7,jam8,jam9,jam10,jam11,jam12;
    String Jam7,Jam8,Jam9,Jam10,Jam11,Jam12;
    String tidak_masuk,masuk;
    int daye;
    TextView no_absen;
    CardView card;
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.layout_hari, container, false);


        TextView textView = view.findViewById(R.id.hari);
        recyclerView    = view.findViewById(R.id.absen);

        DateFormat df = new SimpleDateFormat("-MM-yyyy", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());

        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date dater = null;
        try {
            dater = inFormat.parse(getValueAt(position)+date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
        day = outFormat.format(dater);
        absensiModels = new ArrayList<>();
        absensiAdapter = new AbsensiAdapter(absensiModels);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(absensiAdapter);
        if (absensiModels!=null){
            absensiModels.clear();
            for (AbsenModel absenModel : profileModels){
                absensiModel = new AbsensiModel();
                absensiModel.setTimez_star(absenModel.getTimez_start());
                absensiModel.setTimez_finish(absenModel.getTimes_finish());
                absensiModel.setDay_id(absenModel.getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status());
                absensiModels.add(absensiModel);
            }
            absensiAdapter.notifyDataSetChanged();
        }

//
//        for (int i = 0 ; i < profileModels.size();i++){
//            if (profileModels.get(i).getDays().get(Integer.parseInt(getValueAt(position))-1).getDay_type().equals("1")){
//                no_absen.setVisibility(View.GONE);
//                card.setVisibility(View.VISIBLE);
//                Jam7     = profileModels.get(0).getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status();
//                Jam8     = profileModels.get(1).getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status();
//                Jam9     = profileModels.get(2).getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status();
//                Jam10    = profileModels.get(3).getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status();
//                Jam12    = profileModels.get(4).getDays().get(Integer.parseInt(getValueAt(position))-1).getAbsen_status();
//                if (Jam7.equals("0") || Jam8.equals("0") || Jam9.equals("0") || Jam10.equals("0") || Jam12.equals("0")){
//                    tidak_masuk = "Tidak Masuk";
//                    jam7.setText(tidak_masuk);
//                    jam8.setText(tidak_masuk);
//                    jam9.setText(tidak_masuk);
//                    jam10.setText(tidak_masuk);
//                    jam12.setText(tidak_masuk);
//                }else if (Jam7.equals("1") || Jam8.equals("1") || Jam9.equals("1") || Jam10.equals("1") | Jam12.equals("1")){
//                    masuk = "Masuk";
//                    jam7.setText(masuk);
//                    jam8.setText(masuk);
//                    jam9.setText(masuk);
//                    jam10.setText(masuk);
//                    jam12.setText(masuk);
//                }
//            }else if (profileModels.get(i).getDays().get(Integer.parseInt(getValueAt(position))-1).getDay_type().equals("2")){
//                no_absen.setVisibility(View.VISIBLE);
//                card.setVisibility(View.GONE);
//            }
//        }


        textView.setText(day);
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mItems.size() * NUMBER_OF_LOOPS;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public String getPageTitle(int position) {
        return getValueAt(position);
    }

    public void addAll(List<String> items) {
        mItems = new ArrayList<>(items);
    }

    public int getCenterPosition(int position) {
        return mItems.size() * NUMBER_OF_LOOPS / 2 + position;
    }

    public String getValueAt(int position) {
        if (mItems.size() == 0) {
            return null;
        }
        return mItems.get(position % mItems.size());
    }
}