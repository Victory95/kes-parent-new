package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.Model.FotoModel;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.R;
import com.squareup.picasso.Picasso;

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
        Glide.with(getContext()).load(viewItem.getPicture()).into(holder.imageView);
        Glide.with(getContext())
                .load(viewItem.getPicture())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Picasso.get().load(R.drawable.image_profill).into(holder.imageView);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageView);
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

    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(225);
    }

    public static void  setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }
}
