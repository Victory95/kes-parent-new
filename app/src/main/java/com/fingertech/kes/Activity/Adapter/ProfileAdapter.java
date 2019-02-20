package com.fingertech.kes.Activity.Adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ProfileModel;
import com.fingertech.kes.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fingertech.kes.Service.App.getContext;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.MyHolder> {


    private List<ProfileModel> profileModels;
    int focusedItem = 0;

    private OnItemClickListener onItemClickListener;
    public int row_index = -1;
    String base_url = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;

    public ProfileAdapter(List<ProfileModel> viewItemList) {
        this.profileModels = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_profile, parent, false);

        MyHolder myHolder = new MyHolder(itemView,onItemClickListener);
        return myHolder;
    }


    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {

        int[] androidColors = getContext().getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        String random  = String.valueOf(randomAndroidColor);
        // Get car item dto in list.
        ProfileModel profileModel = profileModels.get(position);
        holder.namaprofile.setText(profileModel.getNama());

        if (profileModel.getPicture().equals(base_url)){
            holder.namaprofile.setText(profileModel.getNama());
            if (random.length() == 5 || random.length() == 6){
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background="+random.substring(1)+"&color=fff").into(holder.imageView);
            }else if (random.length() > 6){
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=" + random.substring(2, 7) + "&color=fff").into(holder.imageView);
            }
        }
        Picasso.with(getContext()).load(profileModel.getPicture()).into(holder.imageView);
        if (row_index == position){
            holder.namaanak.setCardBackgroundColor(Color.parseColor("#40bfe8"));
            holder.namaprofile.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.namaprofile.setTextColor(Color.parseColor("#000000"));
            holder.namaanak.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return profileModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView imageView;
        TextView namaprofile;
        OnItemClickListener onItemClickListener;

        CardView btn_profile,namaanak;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView   = itemView.findViewById(R.id.image_anak);
            namaprofile = itemView.findViewById(R.id.nama_anak);
            btn_profile = itemView.findViewById(R.id.profilanak);
            namaanak    = itemView.findViewById(R.id.namaanak);
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
        this.onItemClickListener = onItemClickListener;
        notifyDataSetChanged();

    }
}