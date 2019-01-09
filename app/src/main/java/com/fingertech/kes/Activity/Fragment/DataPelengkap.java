package com.fingertech.kes.Activity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataPelengkap extends Fragment {


    public DataPelengkap() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            Skpendiri   = getArguments().getString("skpendiri");
            Tanggalsk   = getArguments().getString("tanggalsk");
            Skizin      = getArguments().getString("skizin");
            Tanggalizin = getArguments().getString("tanggalizin");
            Statusmilik = getArguments().getString("statusmilik");
            Kebutuhan   = getArguments().getString("kebutuhankhusus");
            Norek       = getArguments().getString("norekening");
            Namabank    = getArguments().getString("namabank");
            Cabang      = getArguments().getString("cabang");
            Narek       = getArguments().getString("namarekening");
            Mbs         = getArguments().getString("mbs");
            Tanahmilik  = getArguments().getString("tanahmilik");
            Tanahbukan  = getArguments().getString("tanahbukan");
            Namapajak   = getArguments().getString("namapajak");
            Npwp        = getArguments().getString("npwp");
        }
//        Toast.makeText(getActivity(), ""+bundle, Toast.LENGTH_LONG).show();
    }
        String Skpendiri,Tanggalsk,Skizin,Tanggalizin,Statusmilik,Kebutuhan,Norek,Namabank,Cabang,Narek,Mbs,Tanahmilik,Tanahbukan,Namapajak,Npwp;
    TextView skpendiri,tanggalsk,skizin,tanggalizin,kebutuhan,statusmilik,norek,namabank,cabang,mbs,tanahmilik,tanahbukan,namapajak,npwp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_pelengkap, container, false);

        skpendiri   = (TextView)view.findViewById(R.id.SkSekolah);
        tanggalsk   = (TextView)view.findViewById(R.id.tanggalSK);
        skizin      = (TextView)view.findViewById(R.id.Skizin);
        tanggalizin = (TextView)view.findViewById(R.id.TanggalIzin);
        statusmilik = (TextView)view.findViewById(R.id.StatusPemilik);
        kebutuhan   = (TextView)view.findViewById(R.id.KebutuhanKhusus);
        norek       = (TextView)view.findViewById(R.id.nomor_rekening);
        namabank    = (TextView)view.findViewById(R.id.nama_bank);
        cabang      = (TextView)view.findViewById(R.id.cabang);
        mbs         = (TextView)view.findViewById(R.id.mbs);
        tanahmilik  = (TextView)view.findViewById(R.id.luas_tanah);
        tanahbukan  = (TextView)view.findViewById(R.id.luas_tanah_bukan);
        namapajak   = (TextView)view.findViewById(R.id.nama_pajak);
        npwp        = (TextView)view.findViewById(R.id.npwp);

        skpendiri.setText(Skpendiri);
        tanggalsk.setText(Tanggalsk);
        skizin.setText(Skizin);
        tanggalizin.setText(Tanggalizin);
        statusmilik.setText(Statusmilik);
        kebutuhan.setText(Kebutuhan);
        norek.setText(Norek);
        namabank.setText(Namabank);
        cabang.setText(Cabang);
        mbs.setText(Mbs);
        tanahmilik.setText(Tanahmilik);
        tanahbukan.setText(Tanahbukan);
        namapajak.setText(Namapajak);
        npwp.setText(Npwp);
        return view;
    }

}
