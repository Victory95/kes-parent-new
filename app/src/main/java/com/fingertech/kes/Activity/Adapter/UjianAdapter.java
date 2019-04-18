package com.fingertech.kes.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UjianAdapter extends RecyclerView.Adapter<UjianAdapter.MyHolder> {

    private List<ItemUjian> viewItemList;
    private List<ItemUjian> itemUjianList;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;


    private Date date,date_now,date_mulai,date_selesai;
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault());
    private DateFormat times_format         = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

    public UjianAdapter(List<ItemUjian> viewItemList,Context context) {
        this.viewItemList = viewItemList;
        itemUjianList = viewItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ujian, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        ItemUjian viewItem = viewItemList.get(position);
        // Set car item title.
        holder.tanggal.setText(viewItem.getTanggal());
        holder.waktu.setText(viewItem.getJam());
        holder.bulan.setText(viewItem.getBulan());
        holder.mapel.setText(viewItem.getMapel());
        holder.deskripsi.setText(Html.fromHtml(viewItem.getDeskripsi()));
        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        try {
            date_now = times_format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long time_now = date_now.getTime();

        try {
            date_mulai = times_format.parse(viewItem.getTanggalujian()+" "+viewItem.getJam());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long time_ujian = date_mulai.getTime();
        if (time_now >= time_ujian){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#f0f0f0"));
            holder.ll_bulan.setBackgroundColor(Color.parseColor("#808080"));
            holder.ll_card.setBackgroundColor(Color.parseColor("#f0f0f0"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
            holder.ll_bulan.setBackgroundColor(Color.parseColor("#C41515"));
            holder.ll_card.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, waktu,mapel,deskripsi,bulan;
        LinearLayout linearLayout,ll_card,ll_bulan;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tanggal);
            waktu       = itemView.findViewById(R.id.waktu);
            mapel       = itemView.findViewById(R.id.mapel_ujian);
            deskripsi   = itemView.findViewById(R.id.desc_ujian);
            bulan       =itemView.findViewById(R.id.bulan);
            linearLayout    = itemView.findViewById(R.id.ll_ujian);
            ll_card         = itemView.findViewById(R.id.ll_cardview);
            ll_bulan        = itemView.findViewById(R.id.ll_bulan);
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