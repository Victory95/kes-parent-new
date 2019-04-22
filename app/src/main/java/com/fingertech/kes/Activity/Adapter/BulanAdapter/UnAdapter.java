package com.fingertech.kes.Activity.Adapter.BulanAdapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import com.fingertech.kes.Activity.Model.UnModel;
import com.fingertech.kes.R;

import java.util.ArrayList;
import java.util.List;

public class UnAdapter extends RecyclerView.Adapter<UnAdapter.MyHolder> {

    private List<UnModel> viewItemList;
    private List<UnModel> itemUjianList;
    private Context context;

    private OnItemClickListener onItemClickListener;
    public int row_index = 0;
    String searchString = "";
    String mapel ="";
    String type = "";

    public UnAdapter(List<UnModel> viewItemList,Context context) {
        this.viewItemList = viewItemList;
        itemUjianList = viewItemList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ujian, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        // Get car item dto in list.
        UnModel viewItem = viewItemList.get(position);
        // Set car item title.
        holder.tanggal.setText(viewItem.getTanggal());;
        holder.jam.setText(viewItem.getJam());
        holder.mapel.setText(viewItem.getMapel());
        holder.type_id.setText(viewItem.getType_id());
        holder.nilai.setText(viewItem.getNilai());
        holder.deskripsi.setText(Html.fromHtml(viewItem.getDeskripsi()));
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

                    viewItemList = itemUjianList;
                } else {

                    ArrayList<UnModel> filteredList = new ArrayList<>();

                    for (UnModel androidVersion : itemUjianList) {

                        if (androidVersion.getMapel().toLowerCase().contains(charString) || androidVersion.getType_id().toLowerCase().contains(charString) ) {

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
                viewItemList = (ArrayList<UnModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public Filter getfilter(String searchString) {
        this.searchString = searchString;
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty() || searchString.isEmpty()) {
                    viewItemList = itemUjianList;
                } else {

                    ArrayList<UnModel> filteredList = new ArrayList<>();

                    for (UnModel androidVersion : itemUjianList) {

                        if (androidVersion.getMapel().toLowerCase().contains(searchString) && androidVersion.getType_id().toLowerCase().contains(charString) ) {

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
                viewItemList = (ArrayList<UnModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tanggal, jam,mapel,type_id,nilai,guru,deskripsi;
        OnItemClickListener onItemClickListener;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            tanggal     = itemView.findViewById(R.id.tanggal_ujian);
            jam         = itemView.findViewById(R.id.jam_ujian);
            mapel       = itemView.findViewById(R.id.mapel_ujian);
            type_id     = itemView.findViewById(R.id.type_ujian);
            nilai       = itemView.findViewById(R.id.nilai_ujian);
            deskripsi   = itemView.findViewById(R.id.desc_ujian);
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
}