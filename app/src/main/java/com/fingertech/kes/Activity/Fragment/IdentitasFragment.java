package com.fingertech.kes.Activity.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentitasFragment extends Fragment {

    int color;
    TextView npsn,namasekolah,jenjang,status,provinsi,kabupaten,kecamatan,kelurahan,rt,alamat,kodepos;
    String Npsn,Namasekolah,Jenjang,Status,Provinsi,KAbupaten,KEcamatan,KElurahan,Rt,Rw,ALamat,Kodepos;
    public IdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            Npsn             = bundle.getString("npsn");
            Namasekolah      = bundle.getString("namasekolah");
            Jenjang          = this.getArguments().getString("jenjangpendidikan");
            Status           = this.getArguments().getString("statussekolah");
            Provinsi         = this.getArguments().getString("provinsi");
            KAbupaten        = this.getArguments().getString("kabupaten");
            KEcamatan        = this.getArguments().getString("kecamatan");
            KElurahan        = this.getArguments().getString("kelurahan");
            Rt               = this.getArguments().getString("rt");
            Rw               = this.getArguments().getString("rw");
            ALamat           = this.getArguments().getString("alamat");
            Kodepos          = this.getArguments().getString("kodepos");

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identitas, container, false);
        npsn        = (TextView)view.findViewById(R.id.NPSN);
        namasekolah = (TextView)view.findViewById(R.id.name_sekolah);
        jenjang     = (TextView)view.findViewById(R.id.jenjangSekolah);
        status      = (TextView)view.findViewById(R.id.status_sekolah);
        provinsi    = (TextView)view.findViewById(R.id.provinsi);
        kabupaten   = (TextView)view.findViewById(R.id.kabupaten);
        kecamatan   = (TextView)view.findViewById(R.id.kecamatan);
        kelurahan   = (TextView)view.findViewById(R.id.desa);
        rt          = (TextView)view.findViewById(R.id.rt);
        alamat      = (TextView)view.findViewById(R.id.alamat);
        kodepos     = (TextView)view.findViewById(R.id.kode_pos);


        npsn.setText(Npsn);
        namasekolah.setText(Namasekolah);
        jenjang.setText(Jenjang);
        status.setText(Status);
        provinsi.setText(Provinsi);
        kabupaten.setText(KAbupaten);
        kecamatan.setText(KEcamatan);
        kelurahan.setText(KElurahan);
        rt.setText(Rt + "/" + Rw);
        alamat.setText(ALamat);
        kodepos.setText(Kodepos);
        return view;
    }

}
