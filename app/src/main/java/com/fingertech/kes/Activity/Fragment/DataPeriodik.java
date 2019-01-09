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
public class DataPeriodik extends Fragment {


    public DataPeriodik() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Lessonhour          = bundle.getString("lessonhour");
            Bos                 = bundle.getString("bos");
            Iso                 = bundle.getString("iso");
            Sumberlistrik       = bundle.getString("sumberlistrik");
            Dayalistrik         = bundle.getString("dayalistrik");
            Aksesinternet       = bundle.getString("aksesinternet");
            AlternatifInternet  = bundle.getString("alternatifinternet");
        }
    }

    String Lessonhour,Bos,Iso,Sumberlistrik,Dayalistrik,Aksesinternet,AlternatifInternet;
    TextView lessonhour,bos,iso,sumberlistrik,dayalistrik,aksesinternet,alternatifinternet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_periodik, container, false);
        lessonhour          = (TextView)view.findViewById(R.id.waktu_sekolah);
        bos                 = (TextView)view.findViewById(R.id.terima_bos);
        iso                 = (TextView)view.findViewById(R.id.sertifikasi_iso);
        sumberlistrik       = (TextView)view.findViewById(R.id.listrik);
        dayalistrik         = (TextView)view.findViewById(R.id.daya_listrik);
        aksesinternet       = (TextView)view.findViewById(R.id.akses_internet);
        alternatifinternet  = (TextView)view.findViewById(R.id.akses_internet_alternatif);

        lessonhour.setText(Lessonhour);
        bos.setText(Bos);
        iso.setText(Iso);
        sumberlistrik.setText(Sumberlistrik);
        dayalistrik.setText(Dayalistrik);
        aksesinternet.setText(Aksesinternet);
        alternatifinternet.setText(AlternatifInternet);
        return view;
    }

}
