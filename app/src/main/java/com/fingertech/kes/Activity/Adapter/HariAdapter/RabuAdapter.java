package com.fingertech.kes.Activity.Adapter.HariAdapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.HariModel.JadwalRabu;
import com.fingertech.kes.R;

import java.util.List;

public class RabuAdapter extends RecyclerView.Adapter<RabuAdapter.MyHolder> {

    private List<JadwalRabu> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public RabuAdapter(List<JadwalRabu> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jadwal, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        JadwalRabu viewItem = viewItemList.get(position);
        // Set car item title.
        holder.mapel.setText(viewItem.getCources_name());
        holder.jambel.setText(viewItem.getJam_mulai() +" - "+ viewItem.getJam_selesai());
        holder.guru.setText(viewItem.getFullname());
        holder.ll_jadwal.setBackgroundColor(Color.parseColor(viewItem.getCources_color()));

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mapel,jambel,guru;
        LinearLayout ll_jadwal;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            mapel       = itemView.findViewById(R.id.mapel);
            jambel      = itemView.findViewById(R.id.jam);
            guru        = itemView.findViewById(R.id.guru);
            ll_jadwal   = itemView.findViewById(R.id.ll_jadwal);
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
}