package com.fingertech.kes.Activity.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Model.ProfileModel;
import com.fingertech.kes.R;
import com.github.florent37.shapeofview.shapes.CircleView;
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

    Context context;

    public ProfileAdapter(Context context,List<ProfileModel> viewItemList) {
        this.context       = context;
        this.profileModels = viewItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_profile, parent, false);

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
            if (position == 0){
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=1de9b6&color=fff").into(holder.imageView);
            }else if (position == 1){
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff2d6f&color=fff").into(holder.imageView);
            }else if (position == 2){
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ffea00&color=fff").into(holder.imageView);
            }else {
                Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff3d00&color=fff").into(holder.imageView);
            }
        }
        Glide.with(getContext()).load(profileModel.getPicture()).into(holder.imageView);

        if (row_index == position){
            if (profileModel.getWidth() < 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        150,
                        150
                );
                params.setMargins(0,10,0,0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        150,
                        35
                );

                holder.circle.setVisibility(View.VISIBLE);
                paramsanak.setMargins(0,10,0,10);
                holder.circleView.setLayoutParams(params);
                holder.namaanak.setLayoutParams(paramsanak);
                holder.linearLayout.setGravity(Gravity.CENTER);
                if (profileModel.getPicture().equals(base_url)){
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=1de9b6&color=fff").into(holder.imageView);
                    }else if (position == 1){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff2d6f&color=fff").into(holder.imageView);
                    }else if (position == 2){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ffea00&color=fff").into(holder.imageView);
                    }else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            }else if (profileModel.getWidth() > 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        250,
                        250
                );
                params.setMargins(0,10,0,0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        250,
                        50
                );
                holder.circle.setVisibility(View.VISIBLE);
                paramsanak.setMargins(0,10,0,10);
                holder.circleView.setLayoutParams(params);
                holder.namaanak.setLayoutParams(paramsanak);
                holder.linearLayout.setGravity(Gravity.CENTER);
                if (profileModel.getPicture().equals(base_url)){
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=1de9b6&color=fff").into(holder.imageView);
                    }else if (position == 1){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff2d6f&color=fff").into(holder.imageView);
                    }else if (position == 2){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ffea00&color=fff").into(holder.imageView);
                    }else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            } else if (profileModel.getWidth() == 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        200,
                        200
                );
                params.setMargins(0,10,0,0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        200,
                        50
                );

                holder.circle.setVisibility(View.VISIBLE);
                paramsanak.setMargins(0,10,0,10);
                holder.circleView.setLayoutParams(params);
                holder.namaanak.setLayoutParams(paramsanak);
                holder.linearLayout.setGravity(Gravity.CENTER);
                if (profileModel.getPicture().equals(base_url)){
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=1de9b6&color=fff").into(holder.imageView);
                    }else if (position == 1){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff2d6f&color=fff").into(holder.imageView);
                    }else if (position == 2){
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ffea00&color=fff").into(holder.imageView);
                    }else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama()+"&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            }


        }else {
            if (profileModel.getWidth() < 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        125,
                        125
                );
                params.setMargins(0, 30, 0, 0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        125,
                        30
                );
                paramsanak.setMargins(0, 10, 0, 5);
                holder.circle.setVisibility(View.GONE);
                holder.circleView.setLayoutParams(params);
                holder.linearLayout.setGravity(Gravity.BOTTOM);
                holder.namaanak.setLayoutParams(paramsanak);
                if (profileModel.getPicture().equals(base_url)) {
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=1de9b6&color=fff").into(holder.imageView);
                    } else if (position == 1) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff2d6f&color=fff").into(holder.imageView);
                    } else if (position == 2) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ffea00&color=fff").into(holder.imageView);
                    } else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            }else if (profileModel.getWidth() > 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        200,
                        200
                );
                params.setMargins(0,10,0,0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        200,
                        35
                );

                paramsanak.setMargins(0,10,0,10);
                holder.circle.setVisibility(View.GONE);
                holder.circleView.setLayoutParams(params);
                holder.namaanak.setLayoutParams(paramsanak);
                holder.linearLayout.setGravity(Gravity.CENTER);
                if (profileModel.getPicture().equals(base_url)){
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=1de9b6&color=fff").into(holder.imageView);
                    } else if (position == 1) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff2d6f&color=fff").into(holder.imageView);
                    } else if (position == 2) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ffea00&color=fff").into(holder.imageView);
                    } else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            } else if (profileModel.getWidth() == 1080){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        175,
                        175
                );
                params.setMargins(0, 30, 0, 0);
                LinearLayout.LayoutParams paramsanak = new LinearLayout.LayoutParams(
                        175,
                        35
                );
                paramsanak.setMargins(0, 10, 0, 5);
                holder.circle.setVisibility(View.GONE);
                holder.circleView.setLayoutParams(params);
                holder.linearLayout.setGravity(Gravity.BOTTOM);
                holder.namaanak.setLayoutParams(paramsanak);
                if (profileModel.getPicture().equals(base_url)) {
                    holder.namaprofile.setText(profileModel.getNama());
                    if (position == 0) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=1de9b6&color=fff").into(holder.imageView);
                    } else if (position == 1) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff2d6f&color=fff").into(holder.imageView);
                    } else if (position == 2) {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ffea00&color=fff").into(holder.imageView);
                    } else {
                        Glide.with(getContext()).load("https://ui-avatars.com/api/?name=" + profileModel.getNama() + "&background=ff3d00&color=fff").into(holder.imageView);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return profileModels.size();
    }

    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView namaprofile;
        OnItemClickListener onItemClickListener;
        CircleView circleView;
        RelativeLayout linearLayout;
        LinearLayout namaanak;
        ImageView circle;

        public MyHolder(View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            imageView   = itemView.findViewById(R.id.image_anak);
            namaprofile = itemView.findViewById(R.id.nama_anak);
            circleView  = itemView.findViewById(R.id.profilanak);
            linearLayout= itemView.findViewById(R.id.clicked);
            namaanak    = itemView.findViewById(R.id.namaanak);
            circle      = itemView.findViewById(R.id.star);
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
}