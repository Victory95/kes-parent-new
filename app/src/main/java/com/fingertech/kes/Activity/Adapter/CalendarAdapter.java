
package com.fingertech.kes.Activity.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fingertech.kes.Activity.Model.CalendarModel;
import com.fingertech.kes.R;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.MyHolder> {

    private List<CalendarModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public CalendarAdapter(List<CalendarModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_calendar, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        CalendarModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.tanggal.setText(viewItem.getCalendar_date());
        holder.tanggal.setTextColor(Color.parseColor(viewItem.getCalendar_color()));
        holder.jam.setText(viewItem.getCalendar_time());
        holder.mapel.setText(viewItem.getCalendar_title());
        holder.type_id.setText(viewItem.getCalendar_desc());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal,jam,mapel,type_id;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal = (TextView) itemView.findViewById(R.id.tanggal_kalendar);
            jam     = (TextView) itemView.findViewById(R.id.jam_kalendar);
            mapel   = (TextView) itemView.findViewById(R.id.title_kalender);
            type_id = (TextView) itemView.findViewById(R.id.desc_kalender);
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