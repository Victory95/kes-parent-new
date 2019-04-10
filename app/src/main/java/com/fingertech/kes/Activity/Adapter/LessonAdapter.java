package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.LessonModel;
import com.fingertech.kes.R;

import java.util.List;

public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyHolder> {

    private List<LessonModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public LessonAdapter(List<LessonModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_lesson, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        LessonModel viewItem = viewItemList.get(position);
        holder.tanggal.setText(viewItem.getTanggal());
        holder.bulan.setText(viewItem.getBulan());
        holder.tahun.setText(viewItem.getTahun());
        holder.guru.setText(viewItem.getGuru());
        holder.title.setText(viewItem.getTitle());
        holder.materi.setText(viewItem.getMateri());
        holder.desc.setText(viewItem.getDesc());
        // Set car item title.
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, bulan, tahun,guru,title,materi,desc;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tv_tanggal);
            bulan       = itemView.findViewById(R.id.tv_bulan);
            tahun       = itemView.findViewById(R.id.tv_tahun);
            guru        = itemView.findViewById(R.id.guru_lesson);
            title       = itemView.findViewById(R.id.title_lesson);
            materi      = itemView.findViewById(R.id.materi_lesson);
            desc        = itemView.findViewById(R.id.desc_lesson);
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