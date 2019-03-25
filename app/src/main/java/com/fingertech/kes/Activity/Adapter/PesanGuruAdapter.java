package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.R;
import com.github.florent37.shapeofview.shapes.CircleView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fingertech.kes.Service.App.getContext;

public class PesanGuruAdapter extends RecyclerView.Adapter<PesanGuruAdapter.MyHolder> {

    private List<PesanModel> viewItemList;
    String base_url = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    Date date_now,date_pesan;
    private PesanGuruAdapter.OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public PesanGuruAdapter(List<PesanModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(PesanGuruAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PesanGuruAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesan, parent, false);

        PesanGuruAdapter.MyHolder myHolder = new PesanGuruAdapter.MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PesanGuruAdapter.MyHolder holder, int position) {

        // Get car item dto in list.
        PesanModel viewItem = viewItemList.get(position);
        // Set car item title.

        String tanggal = tanggalFormat.format(Calendar.getInstance().getTime());
        // Set car item title.
        try {
            date_now    = times_format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_now = date_now.getTime();

        try {
            date_pesan = times_format.parse(viewItem.getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long times_pesan = date_pesan.getTime();
        if (times_pesan.equals(times_now)){
            holder.tanggal.setText(convertjam(viewItem.getJam()));
        }else {
            holder.tanggal.setText(convertTanggal(viewItem.getTanggal()));
        }

        if (viewItem.getStatus().equals("0")){
            holder.tanggal.setTextColor(Color.parseColor("#000000"));
            holder.pengirim.setTextColor(Color.parseColor("#000000"));
            holder.title.setTextColor(Color.parseColor("#000000"));
        }else if (viewItem.getStatus().equals("1")){
            holder.tanggal.setTextColor(Color.parseColor("#808080"));
            holder.pengirim.setTextColor(Color.parseColor("#808080"));
            holder.title.setTextColor(Color.parseColor("#808080"));
        }

        holder.pengirim.setText(viewItem.getDari());
        if (viewItem.getTitle().equals("")){
            holder.title.setText("( Tidak ada subject )");
        }else {
            holder.title.setText(viewItem.getTitle());
        }
        holder.pesan.setText(viewItem.getPesan());
        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + viewItem.getDari()+"&background=1de9b6&color=fff&font-size=0.40&length=1").into(holder.imageView);

        holder.pengirim.setText(viewItem.getDari());
        holder.pesan.setText(viewItem.getPesan());
        holder.title.setText(viewItem.getTitle());

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pengirim,pesan,title,tanggal;
        CircleView circleView;
        ImageView imageView;
        PesanGuruAdapter.OnItemClickListener onItemClickListener;

        public MyHolder(View itemView, PesanGuruAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.Tv_tanggal);
            pengirim    = itemView.findViewById(R.id.Tvpengirim);
            pesan       = itemView.findViewById(R.id.Tvpesan);
            title       = itemView.findViewById(R.id.Tvsubject);
            circleView  = itemView.findViewById(R.id.profilanak);
            imageView   = itemView.findViewById(R.id.image_guru);
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
    String convertDate(String date) {

        DateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd ", Locale.getDefault());
        DateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy ",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(date));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    String convertjam(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        try {
            String e = calendarDateFormat.format(newDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
}






