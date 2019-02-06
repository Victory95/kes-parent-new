package com.fingertech.kes.Activity.Fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fingertech.kes.Activity.Anak.KalenderKelas;
import com.fingertech.kes.Activity.Anak.PesanAnak;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDuaFragment extends Fragment {


    public MenuDuaFragment() {
        // Required empty public constructor
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            authorization   = bundle.getString("authorization");
            parent_nik      = bundle.getString("parent_nik");
            member_id       = bundle.getString("member_id");
            school_code     = bundle.getString("school_code");
            student_id      = bundle.getString("student_id");
            classroom_id    = bundle.getString("classroom_id");
        }
    }
    String authorization,parent_nik,school_code,student_id,member_id,classroom_id;
    CardView btn_kalender,btn_pesan;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dua, container, false);
        btn_kalender    = view.findViewById(R.id.btn_kalender);
        btn_pesan       = view.findViewById(R.id.btn_pesan);
        btn_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                    Intent intent = new Intent(getContext(), KalenderKelas.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("student_id", student_id);
                    intent.putExtra("classroom_id", classroom_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (authorization != null  && school_code != null && member_id != null) {
                    Intent intent = new Intent(getContext(), PesanAnak.class);
                    intent.putExtra("authorization", authorization);
                    intent.putExtra("school_code", school_code);
                    intent.putExtra("member_id", member_id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"Harap refresh kembali", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }

}
