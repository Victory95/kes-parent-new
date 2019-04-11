package com.fingertech.kes.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;

public class UjianAdapter extends RecyclerView.Adapter<UjianAdapter.MyHolder> {

    private List<ItemUjian> viewItemList;
    private List<ItemUjian> itemUjianList;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;


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

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, waktu,mapel,deskripsi,bulan;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tanggal);
            waktu        = itemView.findViewById(R.id.waktu);
            mapel       = itemView.findViewById(R.id.mapel_ujian);
            deskripsi   = itemView.findViewById(R.id.desc_ujian);
            bulan =itemView.findViewById(R.id.bulan);
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