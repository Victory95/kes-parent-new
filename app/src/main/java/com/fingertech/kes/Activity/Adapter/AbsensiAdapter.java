package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.AbsensiModel;
import com.fingertech.kes.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kes.Service.App.getContext;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.MyHolder> {

    private List<AbsensiModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    private List<String> mItems = new ArrayList<>();
    String dates;
    Date date,date_now;
    private SimpleDateFormat jamformat  = new SimpleDateFormat("hh:mm",Locale.getDefault());
    String jam_sekarang;
    DateFormat format = new SimpleDateFormat("hh:mm", Locale.getDefault());
    public AbsensiAdapter(List<AbsensiModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removedata(){
        this.viewItemList.clear();
        notifyDataSetChanged();
    }
    public void updateData(List<AbsensiModel> viewModels) {
        this.viewItemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_absensi, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        AbsensiModel viewItem = viewItemList.get(position);
        Calendar cal = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        holder.times.setText("Jam "+viewItem.getTimez_star() + " - "+viewItem.getTimez_finish());

        // Set car item title.
        jam_sekarang    = jamformat.format(Calendar.getInstance().getTime());
        try {
            date_now    = format.parse(jam_sekarang);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        now.setTime(date_now);
        setToMidnight(now);
        Long times_now = now.getTimeInMillis();

        try {
            date = format.parse(viewItem.timez_finish);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.setTime(date);
        setToMidnight(cal);
        Long times_start = cal.getTimeInMillis();
        if (times_now >= times_start){
            if (viewItem.getDay_id().equals("0")) {
//                holder.absen_status.setText("Tidak Masuk");
                Glide.with(getContext()).load(R.drawable.ic_false).into(holder.image_absen);
            }else if (viewItem.getDay_id().equals("1")){
                Glide.with(getContext()).load(R.drawable.ic_true).into(holder.image_absen);
//                holder.absen_status.setText("Masuk");
            }
        }else {
            Glide.with(getContext()).load(R.drawable.ic_kuning).into(holder.image_absen);
//            holder.absen_status.setText("Belum Absen");
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView times,absen_status;
        OnItemClickListener onItemClickListener;
        ImageView image_absen;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            times   = itemView.findViewById(R.id.jam_absen);
            image_absen =   itemView.findViewById(R.id.image_absen);
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
    public void clear() {
        final int size = viewItemList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                viewItemList.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
    private void setToMidnight(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}