package com.fingertech.kes.Activity.Adapter;


import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.DetailModel;
import com.fingertech.kes.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.MyHolder>{

    private List<DetailModel> viewItemList;

    public int row_index = 0;
    public DetailAdapter(List<DetailModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_nilai_rapor, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        DetailModel viewItem = viewItemList.get(position);
        holder.detail.setText(viewItem.getType_name());
        holder.nilai.setText(viewItem.getScore_exam());
        if ((position % 2) == 0){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F4F8F4"));
        }else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        // Set car item title.
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView detail, nilai;
        LinearLayout linearLayout;

        public MyHolder(View itemView) {
            super(itemView);
            detail            = itemView.findViewById(R.id.detail_nilai);
            nilai             = itemView.findViewById(R.id.nilai_detail);
            linearLayout      = itemView.findViewById(R.id.ll_raport);
//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
        }


    }
}