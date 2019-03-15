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

        TextView tvSch = (TextView) view.findViewById(R.id.nama_school);

        // Getting reference to the TextView to set longitude
        TextView tvAkr = (TextView) view.findViewById(R.id.akreditasi);

        // Getting reference to the TextView to set latitude
        TextView tvJrk = (TextView) view.findViewById(R.id.jarak);

        // Getting reference to the TextView to set longitude
        TextView tvAlm = (TextView) view.findViewById(R.id.alamat_school);

        // Getting reference to the TextView to set longitude
        TextView tvLht = (TextView) view.findViewById(R.id.Lihat);


        ImageView img = view.findViewById(R.id.imageS);
        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        if (view!=null) {

            tvSch.setText(infoWindowData.getNama());


            tvAkr.setText("Akreditasi " + infoWindowData.getAkreditasi());

            tvJrk.setText("Jarak > " + String.format("%.2f", infoWindowData.getJarak()) + "Km");
            tvAlm.setText(infoWindowData.getAlamat());
            final String SchoolDetailId = infoWindowData.getSchooldetailid();
        }else {

        }

        return view;
    }
}