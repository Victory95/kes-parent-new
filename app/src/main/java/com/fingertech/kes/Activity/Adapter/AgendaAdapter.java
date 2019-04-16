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
        holder.tvjudul.setText(viewItem.getType());
        holder.tv_title.setText(viewItem.getDesc());
        holder.tvdeskripsi.setText(viewItem.getContent());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title,tvdeskripsi,tvjudul;
        LinearLayout ll_agenda;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tv_title           = itemView.findViewById(R.id.titleagenda);
            tvdeskripsi        = itemView.findViewById(R.id.deskripsiagenda);
            tvjudul            = itemView.findViewById(R.id.judulagenda);

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