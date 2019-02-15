package com.fingertech.kes.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;


import com.fingertech.kes.R;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    private List<JSONResponse.SData> viewItemList;
    private List<JSONResponse.SData> mArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    String searchString = "";
    public SearchAdapter(List<JSONResponse.SData> viewItemList, Context context) {
        this.viewItemList = viewItemList;
        this.mArrayList = viewItemList;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemsearch, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        // Get car item dto in list.
        JSONResponse.SData viewItem = viewItemList.get(position);
        holder.name.setText(viewItem.getSchool_name());
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    public Filter getFilter() {
        this.searchString = searchString;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    viewItemList = mArrayList;
                } else {

                    ArrayList<JSONResponse.SData> filteredList = new ArrayList<>();

                    for (JSONResponse.SData androidVersion : mArrayList) {

                        if (androidVersion.getSchool_name().toLowerCase().contains(charString) || androidVersion.getSchool_code().toLowerCase().contains(charString) ) {

                            filteredList.add(androidVersion);
                        }
                    }

                    viewItemList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = viewItemList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                viewItemList = (ArrayList<JSONResponse.SData>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,email;
        Button bookmark;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.namaSchool);
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