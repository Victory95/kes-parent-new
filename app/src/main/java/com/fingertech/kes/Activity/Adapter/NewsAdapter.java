package com.fingertech.kes.Activity.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.NewsModel;
import com.fingertech.kes.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyHolder> {

    private List<NewsModel> viewItemList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    Context context;
    public NewsAdapter(Context context,List<NewsModel> viewItemList) {
        this.context      = context;
        this.viewItemList = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_news, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        NewsModel viewItem = viewItemList.get(position);
        holder.title_news.setText(viewItem.getNews_title());
        holder.tanggal_news.setText(viewItem.getDatez());
        holder.body_news.setText(Html.fromHtml(viewItem.getNews_body()));
        Glide.with(context).load(viewItem.getNews_picture()).into(holder.image_news);
        // Set car item title.
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_news,tanggal_news,body_news;
        ImageView image_news;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            title_news      = itemView.findViewById(R.id.title_news);
            tanggal_news    = itemView.findViewById(R.id.jam_news);
            body_news       = itemView.findViewById(R.id.body_news);
            image_news      = itemView.findViewById(R.id.image_news);

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