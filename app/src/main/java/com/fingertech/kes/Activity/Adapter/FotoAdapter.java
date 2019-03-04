package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.FotoModel;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kes.Service.App.getContext;

public class FotoAdapter extends RecyclerView.Adapter<FotoAdapter.MyHolder> {
    private List<FotoModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    public FotoAdapter(List<FotoModel> viewItemList) {
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_foto, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        FotoModel viewItem = viewItemList.get(position);
        // Set car item title.
        Glide.with(getContext()).load(viewItem.getPicture()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView   = itemView.findViewById(R.id.image_foto);
//            itemView.setOnClickListener(this);
//            this.onItemClickListener = onItemClickListener;
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
