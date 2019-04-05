package com.fingertech.kes.Activity.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.UjianModel;
import com.fingertech.kes.R;

import java.util.List;

public class UjianAdapterTeratas extends RecyclerView.Adapter<UjianAdapterTeratas.MyHolder> {


    private List<UjianModel> viewItemList;
    private Context context;

    private OnItemClickListener onItemClickListener;



    public UjianAdapterTeratas(List<UjianModel> viewItemList,Context context) {
        this.viewItemList = viewItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ujian_teratas, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);

        return myHolder;

    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        UjianModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.bulan.setText(viewItem.getBulan());
        holder.tanggal.setText(viewItem.getTanggal());
        holder.waktu.setText(viewItem.getJam());
        holder.judul.setText(viewItem.getMapel());
        holder.deskripsi.setText(Html.fromHtml(viewItem.getDeskripsi()));
        holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv_materi,tv_waktu,tv_tipe,tv_desc;
                ImageView iv_close;
                final Dialog myDialog = new Dialog(context);
                myDialog.setContentView(R.layout.custom_popup);
                tv_materi   = myDialog.findViewById(R.id.materi_ujian);
                tv_waktu    = myDialog.findViewById(R.id.waktu_ujian);
                tv_tipe     = myDialog.findViewById(R.id.tipe_ujian);
                tv_desc     = myDialog.findViewById(R.id.deskripsi_ujian);
                iv_close    = myDialog.findViewById(R.id.btn_close);
                tv_materi.setText(viewItem.getMapel());
                tv_waktu.setText(viewItem.getWaktu());
                tv_tipe.setText(viewItem.getType_id());
                tv_desc.setText(Html.fromHtml(viewItem.getDeskripsi()));
                iv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bulan,tanggal,waktu,judul,deskripsi;
        CardView btn_detail;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tv_tanggal);
            deskripsi   = itemView.findViewById(R.id.tv_deskripsi);
            bulan       = itemView.findViewById(R.id.tv_bulan);
            waktu       = itemView.findViewById(R.id.tv_waktu);
            judul       = itemView.findViewById(R.id.tv_judul);
            btn_detail  = itemView.findViewById(R.id.btn_pilih);
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
