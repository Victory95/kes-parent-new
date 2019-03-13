package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.AbsenModel;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.Activity.Model.HariModel.JadwalSenin;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kes.Service.App.getContext;

public class RekapAdapter extends RecyclerView.Adapter<RekapAdapter.MyHolder> {

    private List<AbsensiModel> viewItemList;
    private List<AbsenModel> absenModels;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private Date date,date_now,date_mulai,date_selesai;
    private SimpleDateFormat jamformat  = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public RekapAdapter(List<AbsensiModel> viewItemList,List<AbsenModel> absenModels) {
        this.viewItemList = viewItemList;
        this.absenModels = absenModels;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removedata(){
        this.viewItemList.clear();
        notifyDataSetChanged();
    }
    public void updateData(List<AbsensiModel> viewModels) {
        this.viewItemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rekap, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        AbsensiModel viewItem = viewItemList.get(position);
        AbsenModel absenModel = absenModels.get(position);
        String times_ok = absenModel.getTimez_star();

        holder.mapel.setText(viewItem.getMapel());
//        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
//        // Set car item title.
//        String jam_sekarang = jamformat.format(Calendar.getInstance().getTime());
//        try {
//            date_now    = times_format.parse(tanggal+" "+ jam_sekarang +":00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Long times_now = date_now.getTime();
//        try {
//            date = times_format.parse(absenModel.getTanggal()+" "+viewItem.getTimez_star()+":00");
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Long times_start = date.getTime();
//
//        try {
//            date_mulai = times_format.parse(absenModel.getTanggal()+" "+absenModel.getTimez_star()+":00");
//        }catch (ParseException e){
//            e.printStackTrace();
//        }
//        Long times_mulai = date_mulai.getTime();

//        if (times_start.equals(times_mulai)){
//            switch (absenModel.getDay_id()) {
//                case "0":
//                    Glide.with(getContext()).load(R.drawable.ic_false).into(holder.image_absen);
//                    break;
//                case "1":
//                    Glide.with(getContext()).load(R.drawable.ic_true).into(holder.image_absen);
//                    break;
//                case "2":
//                    Glide.with(getContext()).load(R.drawable.ic_kuning).into(holder.image_absen);
//                    break;
//            }
//        }else {
//            Glide.with(getContext()).load(R.drawable.ic_kuning).into(holder.image_absen);
//        }
    }

    @Override
    public int getItemCount() {

        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mapel;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            mapel       = itemView.findViewById(R.id.mapel_rekap);
        }

        @Override
        public void onClick(View v) {
//            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }
}