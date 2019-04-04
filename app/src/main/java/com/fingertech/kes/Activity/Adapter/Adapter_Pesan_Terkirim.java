package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


import static com.fingertech.kes.Service.App.getContext;

public class Adapter_Pesan_Terkirim extends RecyclerView.Adapter<Adapter_Pesan_Terkirim.MyHolder> {

    private List<PesanModel> viewItemList;
    String base_url = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";
    private SimpleDateFormat tanggalFormat  = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

    private DateFormat times_format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public Adapter_Pesan_Terkirim(List<PesanModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_pesan, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }
    @SuppressLint("SetTextI18n")

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {


        // Get car item dto in list.
        PesanModel viewItem = viewItemList.get(position);
        // Set car item tit

        holder.tanggal.setText(convertDate(viewItem.getTanggal()));
        holder.pengirim.setText(viewItem.getDari());
        holder.pesan.setText(viewItem.getPesan());
        holder.title.setText(viewItem.getTitle());
        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + viewItem.getDari()+"&background=1de9b6&color=fff&font-size=0.40&length=1").into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView pengirim,pesan,title,tanggal,waktu;
        ImageView imageView;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.Tv_tanggal);
            pengirim = itemView.findViewById(R.id.Tvpengirim);
            pesan    = itemView.findViewById(R.id.Tvpesan);
            title = itemView.findViewById(R.id.Tvsubject);
            waktu = itemView.findViewById(R.id.Tvwaktu);
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
}






