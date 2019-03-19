package com.fingertech.kes.Activity.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowAdapter(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_snippet, null);

        TextView tvSch = view.findViewById(R.id.nama_school);

        // Getting reference to the TextView to set longitude
        TextView tvAkr = view.findViewById(R.id.akreditasi);

        // Getting reference to the TextView to set latitude
        TextView tvJrk = view.findViewById(R.id.jarak);

        // Getting reference to the TextView to set longitude
        TextView tvAlm = view.findViewById(R.id.alamat_school);

        // Getting reference to the TextView to set longitude
        TextView tvLht = view.findViewById(R.id.Lihat);


        ImageView img = view.findViewById(R.id.imageS);
        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        if (infoWindowData != null){
            img.setVisibility(View.VISIBLE);
            tvLht.setVisibility(View.VISIBLE);
            tvAlm.setVisibility(View.VISIBLE);
            tvAkr.setVisibility(View.VISIBLE);
            tvJrk.setVisibility(View.VISIBLE);
            tvSch.setText(infoWindowData.getNama());
            tvAkr.setText("Akreditasi " + infoWindowData.getAkreditasi());
            tvJrk.setText("Jarak > " + String.format("%.2f", infoWindowData.getJarak()) + "Km");
            tvAlm.setText(infoWindowData.getAlamat());
        }
        else {
            tvSch.setText("I'm here");
            img.setVisibility(View.GONE);
            tvLht.setVisibility(View.GONE);
            tvAlm.setVisibility(View.GONE);
            tvAkr.setVisibility(View.GONE);
            tvJrk.setVisibility(View.GONE);
        }

        return view;
    }
}