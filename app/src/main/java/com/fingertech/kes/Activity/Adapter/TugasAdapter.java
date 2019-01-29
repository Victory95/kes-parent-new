package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.HariModel.JadwalSenin;
import com.fingertech.kes.Activity.Model.TugasModel;
import com.fingertech.kes.R;

import java.util.List;

public class TugasAdapter extends RecyclerView.Adapter<TugasAdapter.MyHolder> {

    private List<TugasModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public TugasAdapter(List<TugasModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_tugas, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        TugasModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.tanggal.setText(viewItem.getTanggal());;
        holder.mapel.setText(viewItem.getMapel());
        holder.tipe.setText(viewItem.getTipe());
        holder.deskripsi.setText(viewItem.getDeskripsi());
        holder.guru.setText(viewItem.getGuru());
        holder.nilai.setText(viewItem.getNilai());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal,mapel,tipe,deskripsi,guru,nilai;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal      = (TextView) itemView.findViewById(R.id.tanggal_tugas);
            mapel        = (TextView) itemView.findViewById(R.id.mapel_tugas);
            tipe         = (TextView) itemView.findViewById(R.id.type_tugas);
            deskripsi    = (TextView) itemView.findViewById(R.id.desc_tugas);
            guru         = (TextView) itemView.findViewById(R.id.guru_tugas);
            nilai        = (TextView) itemView.findViewById(R.id.nilai_tugas);

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