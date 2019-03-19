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

import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.BookmarkTabel;
import com.fingertech.kes.Rest.JSONResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchMapAdapter extends RecyclerView.Adapter<SearchMapAdapter.MyHolder> {

    private List<JSONResponse.SData> viewItemList;
    private List<JSONResponse.SData> mArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    String searchString = "";
    BookmarkTabel bookmarkTabel = new BookmarkTabel();
    Data data = new Data();
    private Boolean clicked = false;

    public SearchMapAdapter(List<JSONResponse.SData> viewItemList, Context context) {
        this.viewItemList = viewItemList;
        this.mArrayList = viewItemList;
        this.context = context;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        // Get car item dto in list.
        JSONResponse.SData viewItem = viewItemList.get(position);
        holder.name.setText(viewItem.getSchool_name());
        holder.email.setText(viewItem.getSchool_address());
        // Find charText in wp
        String country                  = viewItem.getSchool_name().toLowerCase(Locale.getDefault());
        String name                     = viewItem.getSchool_address().toLowerCase(Locale.getDefault());
        final Double latitude           = viewItem.getLatitude();
        final Double longitude          = viewItem.getLongitude();
        final String SchooldetailId     = viewItem.getSchooldetailid();
        final String jenjang            = viewItem.getJenjang_pendidikan();
        if (country.contains(searchString )) {
            Log.e("AbsensiAnak", country + " contains: " + searchString);
            int startPos    = country.indexOf(searchString);
            int endPos      = startPos + searchString.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.name.getText());
            spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.name.setText(spanText, TextView.BufferType.SPANNABLE);
        }else if (name.contains(searchString)){
            int StarPos     = name.indexOf(searchString);
            int endPos      = StarPos + searchString.length();
            Spannable spanText = Spannable.Factory.getInstance().newSpannable(holder.email.getText());
            spanText.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary)), StarPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            spanText.setSpan(new ForegroundColorSpan(Color.RED), StarPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.email.setText(spanText, TextView.BufferType.SPANNABLE);
        }
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    bookmarkTabel.delete(holder.name.getText().toString().trim());
                    v.setBackgroundResource(R.drawable.ic_unbookmark);
                    clicked = false;
                }else {
                    clicked = true;
                    data.setName(holder.name.getText().toString().trim());
                    data.setAddress(holder.email.getText().toString().trim());
                    data.setLat(latitude.doubleValue());
                    data.setLng(longitude.doubleValue());
                    data.setSchooldetailid(SchooldetailId);
                    data.setJenjang(jenjang);
                    bookmarkTabel.insert(data);
                    v.setBackgroundResource(R.drawable.ic_bookmark);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return viewItemList.size();
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

                    ArrayList<JSONResponse.SData> filteredList = new ArrayList<>();

                    for (JSONResponse.SData androidVersion : mArrayList) {

                        if (androidVersion.getSchool_name().toLowerCase().contains(charString) || androidVersion.getSchool_address().toLowerCase().contains(charString) || androidVersion.getJenjang_pendidikan().toLowerCase().contains(charString) ) {

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
            name = itemView.findViewById(R.id.school_name);
            email = itemView.findViewById(R.id.Address_school);
            bookmark = itemView.findViewById(R.id.bookmark);
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