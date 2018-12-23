package com.fingertech.kes.Activity.RecycleView;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertech.kes.R;

import java.util.List;

public class CustomRecyclerViewDataAdapter extends RecyclerView.Adapter<CustomRecyclerViewDataAdapter.MyHolder> {

    private List<CustomRecyclerViewItem> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public CustomRecyclerViewDataAdapter(List<CustomRecyclerViewItem> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

            // Get car item dto in list.
            CustomRecyclerViewItem viewItem = viewItemList.get(position);
                // Set car item title.
                holder.name.setText(viewItem.getName());;
                holder.jarak.setText("Jarak > "+String.format("%.2f", viewItem.getJarak())+"Km");
                holder.akreditas.setText("Akreditasi "+viewItem.getAkreditas());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, akreditas,jarak;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.nama_sekolah);
            akreditas = (TextView) itemView.findViewById(R.id.Akre);
            jarak= (TextView) itemView.findViewById(R.id.jarak_sekolah);
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