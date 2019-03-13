package com.fingertech.kes.Activity.Adapter;



import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Activity.Model.DasarModel;
import com.fingertech.kes.Activity.Model.HariAbsenModel;
import com.fingertech.kes.Activity.Model.JamModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.fingertech.kes.Service.App.getContext;

public class HariAbsenAdapter extends RecyclerView.Adapter<HariAbsenAdapter.MyHolder> {

    private List<HariAbsenModel> viewItemList;
    private  List<AbsenModel> absenModelList;
    private  List<AbsensiModel> absensiModelList;
    List<JamModel> jamModelList ;
    public int row_index = 0;
    List<AbsensiModel> absensiModelLis = new ArrayList<>();
    RekapAdapter rekapAdapter;

    Context context;

    public HariAbsenAdapter(Context context,List<HariAbsenModel> viewItemList, List<AbsensiModel> absensiModelList) {
        this.context = context;
        this.viewItemList = viewItemList;
        this.absensiModelList = absensiModelList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rekap_absen, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        HariAbsenModel viewItem = viewItemList.get(position);

        int n = Integer.parseInt(viewItem.getTanggal());

        if ((n % 2) == 0) {
            // number is even
            holder.hari.setTextColor(Color.parseColor("#651fff"));
        }
        else {
            holder.hari.setTextColor(Color.parseColor("#00b0ff"));
            // number is odd
        }
        holder.tanggal.setText(converTahun(viewItem.getTahun()+"-"+viewItem.getBulan()+"-"+viewItem.getTanggal()));
        holder.hari.setText(converHari(viewItem.getTahun()+"-"+viewItem.getBulan()+"-"+viewItem.getTanggal()));
        rekapAdapter = new RekapAdapter(absensiModelList,absenModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.rv_rekap.setLayoutManager(layoutManager);
        holder.rv_rekap.setAdapter(rekapAdapter);

    }

    @Override
    public int getItemCount() {
        int firstListSize = 0;
        int secondListSize = 0;

        if (viewItemList == null && absensiModelList == null) return 0;

        if (viewItemList != null)
            secondListSize = viewItemList.size();
        if (absensiModelList != null)
            firstListSize = absensiModelList.size();

        if (secondListSize > 0 && firstListSize > 0)
            return 1 + firstListSize + 1 + secondListSize + 1;   // first list header, first list size, second list header , second list size, footer
        else if (secondListSize > 0 && firstListSize == 0)
            return 1 + secondListSize + 1;                       // second list header, second list size, footer
        else if (secondListSize == 0 && firstListSize > 0)
            return 1 + firstListSize;                            // first list header , first list size
        else return 0;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tanggal,hari;
        RecyclerView rv_rekap;
        public MyHolder(View itemView) {
            super(itemView);
            tanggal  = itemView.findViewById(R.id.tanggal_absen);
            hari     = itemView.findViewById(R.id.hari_absen);
            rv_rekap = itemView.findViewById(R.id.rv_rekap);
        }
    }

    private String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String converHari(String hari){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-M-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("EEEE",new Locale("in","ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(hari));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }


}