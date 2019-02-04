package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.MyHolder> {

    private List<AbsensiModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private List<String> mItems = new ArrayList<>();
    String date;
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_absen, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        AbsensiModel viewItem = viewItemList.get(position);
        // Set car item title.
        DateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        if (date.substring(0,1).equals("0")){
            date    = date.substring(1);
        }
        holder.times.setText(viewItem.getTimez_star() + "-"+viewItem.getTimez_finish());
        if (viewItem.getDay_id().equals("0")) {
            holder.absen_status.setText("Tidak Masuk");
        }else if (viewItem.getDay_id().equals("1")){
            holder.absen_status.setText("Masuk");
        }
//        holder.absen_status.setText(viewItem.getDataAbsensis().get(Integer.parseInt(date)).getDay_id());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView times,absen_status;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);

            times   = itemView.findViewById(R.id.jam);
            absen_status    = itemView.findViewById(R.id.keterangan);
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
    public void clear() {
        final int size = viewItemList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                viewItemList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}