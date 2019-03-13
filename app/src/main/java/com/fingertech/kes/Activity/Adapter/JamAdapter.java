package com.fingertech.kes.Activity.Adapter;



import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.Activity.Model.JamModel;
import com.fingertech.kes.R;

import java.util.List;

public class JamAdapter extends RecyclerView.Adapter<JamAdapter.MyHolder> {

    private List<JamModel> viewItemList;

    public int row_index = 0;
    public JamAdapter(List<JamModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_jam_absen, parent, false);

        MyHolder myHolder = new MyHolder(itemView);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        JamModel viewItem = viewItemList.get(position);
        holder.jam_mulai.setText(viewItem.getJam_mulai());
        holder.jam_selesai.setText(viewItem.getJam_selesai());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView jam_mulai,jam_selesai;

        public MyHolder(View itemView) {
            super(itemView);
            jam_mulai       = itemView.findViewById(R.id.jam_mulai);
            jam_selesai     = itemView.findViewById(R.id.jam_selesai);

        }
    }
}