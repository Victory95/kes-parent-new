package com.fingertech.kes.Activity.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.RaportModel;
import com.fingertech.kes.R;

import java.util.List;

public class RaporAdapter extends RecyclerView.Adapter<RaporAdapter.MyHolder>{

    private List<RaportModel> viewItemList;

    public int row_index = 0;
    public RaporAdapter(List<RaportModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rapor, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        RaportModel viewItem = viewItemList.get(position);
        holder.name.setText(viewItem.getMapel());
        // Set car item title.
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView name;

        public MyHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nama_mapel);

//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
        }


    }
}
