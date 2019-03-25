package com.fingertech.kes.Activity.Adapter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.AgendaModel;
import com.fingertech.kes.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyHolder> {

    private List<AgendaModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public AgendaAdapter(List<AgendaModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_agenda, parent, false);

        return new MyHolder(itemView,onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        AgendaModel viewItem = viewItemList.get(position);
        holder.tv_type.setText(viewItem.getType());
        holder.tv_desc.setText(viewItem.getDesc());
        holder.tv_bulan.setText(converMonth(viewItem.getDate()));
        holder.tv_tanggal.setText(converDate(viewItem.getDate()));
        switch (viewItem.getType()) {
            case "Agenda Kelas":
                holder.tv_tanggal.setTextColor(Color.parseColor("#40bfe8"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar);
                break;
            case "Ujian Negara":
                holder.tv_tanggal.setTextColor(Color.parseColor("#f0932b"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_oren);
                break;
            case "Ujian Tengah Semester":
                holder.tv_tanggal.setTextColor(Color.parseColor("#2c3e50"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_dark_blue);
                break;
            case "Ujian Akhir Semester":
                holder.tv_tanggal.setTextColor(Color.parseColor("#22a6b3"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_blue_mint);
                break;
            case "Ulangan Harian":
                holder.tv_tanggal.setTextColor(Color.parseColor("#8956FC"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_purple);
                break;
            case "Latihan Teori":
                holder.tv_tanggal.setTextColor(Color.parseColor("#3fa930"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_green);
                break;
            case "Ekstrakulikuler":
                holder.tv_tanggal.setTextColor(Color.parseColor("#941ea9"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_dark_purple);
                break;
            case "Latihan Praktikum":
                holder.tv_tanggal.setTextColor(Color.parseColor("#b08137"));
                holder.ll_agenda.setBackgroundResource(R.drawable.ic_kalendar_brown);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_tanggal,tv_bulan,tv_type,tv_desc;
        LinearLayout ll_agenda;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_tanggal      = itemView.findViewById(R.id.tv_tanggal);
            tv_bulan        = itemView.findViewById(R.id.tv_bulan);
            tv_type         = itemView.findViewById(R.id.tv_type);
            tv_desc         = itemView.findViewById(R.id.tv_desc);
            ll_agenda       = itemView.findViewById(R.id.ll_agenda);
            itemView.setOnClickListener(this);
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    private String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            return newDateFormat.format(calendarDateFormat.parse(tanggal));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String converMonth(String bulan){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM",new Locale("in","ID"));
        try {
            return newDateFormat.format(calendarDateFormat.parse(bulan));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}