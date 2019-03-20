package com.fingertech.kes.Activity.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.MyHolder> {

    private List<Data> viewItemList;
    private Context context;
    private  List<Data> mArrayList;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    String searchString="";

    public BookmarkAdapter(List<Data> viewItemList, Context context) {
        this.viewItemList = viewItemList;
        this.mArrayList     = viewItemList;
        this.context        = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        Data viewItem = viewItemList.get(position);
        // Set car item title.
        holder.name.setText(viewItem.getName());;
        holder.address.setText(viewItem.getAddress());
        holder.id.setText(viewItem.getId());

        String nama = viewItem.getName().toLowerCase(Locale.getDefault());
        String alamat = viewItem.getAddress().toLowerCase(Locale.getDefault());
        if (nama.contains(searchString)) {
            Log.e("AbsensiAnak", nama + " contains: " + searchString);
            int startPos = nama.indexOf(searchString);
            int endPos = startPos + searchString.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.name.getText());
            spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.name.setText(spanText, TextView.BufferType.SPANNABLE);
        }else if (alamat.contains(searchString)){
            int StarPos = alamat.indexOf(searchString);
            int endPos = StarPos + searchString.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.address.getText());
            spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), StarPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            holder.address.setText(spanText, TextView.BufferType.SPANNABLE);
        }
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, address,id;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_school);
            address = (TextView) itemView.findViewById(R.id.address_sekolah);
            id= (TextView) itemView.findViewById(R.id.id_school);
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

    public void selectRow(int index){
        row_index=index;
        notifyDataSetChanged();
    }
    public Filter getFilter(String searchString) {
        this.searchString = searchString;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    viewItemList = mArrayList;
                } else {

                    ArrayList<Data> filteredList = new ArrayList<>();

                    for (Data androidVersion : mArrayList) {

                        if (androidVersion.getName().toLowerCase().contains(charString) || androidVersion.getAddress().toLowerCase().contains(charString) ) {

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
                viewItemList = (ArrayList<Data>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}