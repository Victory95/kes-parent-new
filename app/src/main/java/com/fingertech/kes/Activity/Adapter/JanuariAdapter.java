package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.JadwalModel;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.List;

public class JanuariAdapter extends RecyclerView.Adapter<JanuariAdapter.MyHolder> {

    private List<JadwalModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public JanuariAdapter(List<JadwalModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_page, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        JadwalModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.mapel.setText(viewItem.getCources_name());;
        holder.lambel.setText(viewItem.getDuration() + " Menit");
        holder.jambel.setText(viewItem.getSchedule_time());
        holder.guru.setText(viewItem.getFullname());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mapel, lambel,jambel,guru;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            mapel = (TextView) itemView.findViewById(R.id.mapel);
            lambel     = (TextView) itemView.findViewById(R.id.lamber);
            jambel   = (TextView) itemView.findViewById(R.id.jambel);
            guru = (TextView) itemView.findViewById(R.id.guru_ngajar);
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