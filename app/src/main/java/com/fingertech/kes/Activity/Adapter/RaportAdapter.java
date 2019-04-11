package com.fingertech.kes.Activity.Adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.RaporModel;
import com.fingertech.kes.R;

import java.util.List;

public class RaportAdapter extends RecyclerView.Adapter<RaportAdapter.MyHolder>{

    private List<RaporModel> viewItemList;

    public int row_index = 0;
    private OnItemClickListener onItemClickListener;

    public RaportAdapter(List<RaporModel> viewItemList) {
        this.viewItemList = viewItemList;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_raport, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        RaporModel viewItem = viewItemList.get(position);
        holder.name.setText(viewItem.getMapel());
        holder.kkm.setText(viewItem.getRr_kelas());
        holder.tv_nilai.setText(viewItem.getNilaiakhir());
        holder.rr_kelas.setText(viewItem.getRr_angkatan());
        if ((position % 2) == 0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F4F8F4"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, kkm, tv_nilai, rr_kelas;
        LinearLayout linearLayout;
        OnItemClickListener onItemClickListener;
        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            name            = itemView.findViewById(R.id.tv_mapel);
            kkm             = itemView.findViewById(R.id.tv_kkm);
            rr_kelas        = itemView.findViewById(R.id.rr_kelas);
            tv_nilai        = itemView.findViewById(R.id.tv_nilai);
            linearLayout    = itemView.findViewById(R.id.ll_raport);
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
