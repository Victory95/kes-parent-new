package com.fingertech.kes.Activity.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Lainnya extends Fragment {


    public Lainnya() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null) {
            Kepsek          = bundle.getString("kepsek");
            Operator        = bundle.getString("operator");
            Akreditasi      = bundle.getString("akreditasi");
            Kurikulum       = bundle.getString("kurikulum");
            Totalguru       = bundle.getString("totalguru");
            Siswapria       = bundle.getString("siswapria");
            Siswawanita     = bundle.getString("siswawanita");
            Rombel          = bundle.getString("rombel");
            Ruangkelas      = bundle.getString("ruangkelas");
            Laboratorium    = bundle.getString("laboratorium");
            Perpustakaan    = bundle.getString("perpustakaan");
            Sanitasi        = bundle.getString("sanitasi");
        }
    }

    String Kepsek,Operator,Akreditasi,Kurikulum,Totalguru,Siswapria,Siswawanita,Rombel,Ruangkelas,Laboratorium,Perpustakaan,Sanitasi;
    TextView kepsek,operator,akreditasi,kurikulum,totalguru,siswapria,siswawanita,rombel,ruangkelas,laboratorium,perpustakaan,sanitasi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lainnya, container, false);

        kepsek          = (TextView)view.findViewById(R.id.kepala_sekolah);
        operator        = (TextView)view.findViewById(R.id.operator_sekolah);
        akreditasi      = (TextView)view.findViewById(R.id.Akreditas);
        kurikulum       = (TextView)view.findViewById(R.id.kurikulum_sekolah);
        totalguru       = (TextView)view.findViewById(R.id.total_guru);
        siswapria       = (TextView)view.findViewById(R.id.total_pria);
        siswawanita     = (TextView)view.findViewById(R.id.total_wanita);
        rombel          = (TextView)view.findViewById(R.id.rombongan_belajar);
        ruangkelas      = (TextView)view.findViewById(R.id.ruang_kelas);
        laboratorium    = (TextView)view.findViewById(R.id.laboratorium);
        perpustakaan    = (TextView)view.findViewById(R.id.perpustakaan);
        sanitasi        = (TextView)view.findViewById(R.id.sanitasi);

        kepsek.setText(Kepsek);
        operator.setText(Operator);
        akreditasi.setText(Akreditasi);
        kurikulum.setText(Kurikulum);
        totalguru.setText(Totalguru);
        siswapria.setText(Siswapria);
        siswawanita.setText(Siswawanita);
        rombel.setText(Rombel);
        ruangkelas.setText(Ruangkelas);
        laboratorium.setText(Laboratorium);
        perpustakaan.setText(Perpustakaan);
        sanitasi.setText(Sanitasi);
        return view;
    }

}
