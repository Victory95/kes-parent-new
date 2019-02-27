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

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.MyHolder> {

    private List<AbsensiModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private Date date,date_now;
    private SimpleDateFormat jamformat  = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public AbsensiAdapter(List<AbsensiModel> viewItemList) {
        this.viewItemList = viewItemList;
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_absensi, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        AbsensiModel viewItem = viewItemList.get(position);
//        holder.mapel.setText(viewItem.getMapel());
//        holder.guru.setText("Guru "+viewItem.getGuru());
        holder.times.setText("Jam "+viewItem.getTimez_star() + " - "+viewItem.getTimez_finish());
        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        // Set car item title.
        String jam_sekarang = jamformat.format(Calendar.getInstance().getTime());
        try {
            date_now    = times_format.parse(tanggal+" "+ jam_sekarang +":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_now = date_now.getTime();
        try {
            date = times_format.parse(viewItem.getTanggal()+" "+viewItem.getTimez_star()+":00");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_start = date.getTime();
        if (times_now >= times_start){
            if (viewItem.getDay_id().equals("0")) {
                Glide.with(getContext()).load(R.drawable.ic_false).into(holder.image_absen);
            }else if (viewItem.getDay_id().equals("1")){
                Glide.with(getContext()).load(R.drawable.ic_true).into(holder.image_absen);
            }
        }else {
            Glide.with(getContext()).load(R.drawable.ic_kuning).into(holder.image_absen);
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView times,mapel,guru;
        OnItemClickListener onItemClickListener;
        ImageView image_absen;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            times       = itemView.findViewById(R.id.jam_absen);
            image_absen = itemView.findViewById(R.id.image_absen);
//            guru        = itemView.findViewById(R.id.guru_absen);
//            mapel       = itemView.findViewById(R.id.mapel_absen);
//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
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