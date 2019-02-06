package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.List;

public class PesanAdapter extends RecyclerView.Adapter<PesanAdapter.MyHolder> {

    private List<PesanModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public PesanAdapter(List<PesanModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_pesan, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        PesanModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.tanggal.setText(viewItem.getTanggal());;
        holder.mapel.setText(viewItem.getMapel());
        holder.guru.setText(viewItem.getDari());
        holder.pesan.setText(viewItem.getPesan());
        holder.kelas.setText("Kelas : "+viewItem.getKelas());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, kelas,mapel;
        TextView guru,pesan;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_pesan);
            mapel   = (TextView) itemView.findViewById(R.id.mata_pelajaran_pesan);
            guru    = itemView.findViewById(R.id.guru);
            pesan   = itemView.findViewById(R.id.pesan);
            kelas   = itemView.findViewById(R.id.kelas);
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