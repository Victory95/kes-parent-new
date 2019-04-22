package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.MapsModel;
import com.fingertech.kes.R;

import java.util.List;

public class MapsAdapter extends RecyclerView.Adapter<MapsAdapter.MyHolder> {

    private List<MapsModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public MapsAdapter(List<MapsModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_riwayat, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        MapsModel viewItem = viewItemList.get(position);
        holder.alamat.setText(viewItem.getAlamat());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClickListener onItemClickListener;
        TextView alamat;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            alamat      = itemView.findViewById(R.id.alamat);
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

    public void selectRow(int index){
        row_index=index;
        notifyDataSetChanged();
    }
}