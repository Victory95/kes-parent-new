package com.fingertech.kes.Activity.Adapter;

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

public class InfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public InfoWindowAdapter(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.custom_snippet, null);

        TextView tvSch = (TextView) view.findViewById(R.id.nama_school);

        // Getting reference to the TextView to set longitude
        TextView tvAkr = (TextView) view.findViewById(R.id.alamat_school);

        ImageView img = view.findViewById(R.id.imageS);

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
        tvSch.setText(infoWindowData.getNama());
        tvAkr.setText(infoWindowData.getAlamat());
        return view;
    }
}
