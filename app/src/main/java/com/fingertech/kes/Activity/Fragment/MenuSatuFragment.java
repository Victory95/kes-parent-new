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

import com.fingertech.kes.Activity.Anak.JadwalPelajaran;
import com.fingertech.kes.Activity.Anak.JadwalUjian;
import com.fingertech.kes.Activity.Anak.KalenderKelas;
import com.fingertech.kes.Activity.Anak.ProfilAnak;
import com.fingertech.kes.Activity.Maps.TentangKami;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSatuFragment extends Fragment {


    public MenuSatuFragment() {
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
    CardView btn_profile,btn_jadwal,btn_ujian,btn_absensi,btn_tugas_anak,btn_raport;
    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_satu, container, false);
        frameLayout     = (FrameLayout) view.findViewById(R.id.fragMenuSatu);
        btn_profile     = (CardView)view.findViewById(R.id.btn_profil);
        btn_jadwal      = (CardView)view.findViewById(R.id.btn_jadwal);
        btn_ujian       = (CardView)view.findViewById(R.id.btn_jadwal_ujian);
        btn_absensi     = (CardView)view.findViewById(R.id.btn_absen);
        btn_tugas_anak  = (CardView)view.findViewById(R.id.btn_tugas);
        btn_raport      = (CardView)view.findViewById(R.id.btn_raport);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfilAnak.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("parent_nik",parent_nik);
                intent.putExtra("school_code",school_code);
                intent.putExtra("student_id",student_id);
                startActivity(intent);
            }
        });

        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JadwalPelajaran.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("student_id",student_id);
                intent.putExtra("classroom_id",classroom_id);
                startActivity(intent);
            }
        });

        btn_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), JadwalUjian.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("student_id",student_id);
                intent.putExtra("classroom_id",classroom_id);
                startActivity(intent);
            }
        });

        btn_absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), KalenderKelas.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
