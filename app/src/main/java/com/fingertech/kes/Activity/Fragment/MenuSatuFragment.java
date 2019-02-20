package com.fingertech.kes.Activity.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.share.Share;
import com.fingertech.kes.Activity.Anak.AbsensiAnak;
import com.fingertech.kes.Activity.Anak.JadwalPelajaran;
import com.fingertech.kes.Activity.Anak.ProfilAnak;
import com.fingertech.kes.Activity.Anak.RaportAnak;
import com.fingertech.kes.Activity.Anak.TugasAnak;
import com.fingertech.kes.Activity.Anak.JadwalUjian;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuSatuFragment extends Fragment {


    public MenuSatuFragment() {
        // Required empty public constructor
    }
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getActivity().setContentView(R.layout.fragment_menu_satu);
//        ViewPager vp = new ViewPager(getContext());
//        vp.setId(R.id.PagerUtama);
//
//    }
    String authorization,parent_nik,school_code,student_id,member_id,classroom_id,school_name;
    CardView btn_profile,btn_jadwal,btn_ujian,btn_absensi,btn_tugas_anak,btn_raport;
    SharedPreferences sharedPreferences;
    FrameLayout frameLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_satu, container, false);

        btn_profile     = view.findViewById(R.id.btn_profil);
        btn_jadwal      = view.findViewById(R.id.btn_jadwal);
        btn_ujian       = view.findViewById(R.id.btn_jadwal_ujian);
        btn_absensi     = view.findViewById(R.id.btn_absen);
        btn_tugas_anak  = view.findViewById(R.id.btn_tugas);
        btn_raport      = view.findViewById(R.id.btn_raport);
//        frameLayout     = view.findViewById(R.id.fragMenuSatu);

        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        parent_nik          = sharedPreferences.getString("parent_nik",null);


        btn_profile.setOnClickListener(v -> {
            if (authorization != null && parent_nik != null && school_code != null && student_id != null) {
                Intent intent = new Intent(getContext(), ProfilAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("parent_nik", parent_nik);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("school_name",school_name);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });

        btn_jadwal.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                Intent intent = new Intent(getContext(), JadwalPelajaran.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });

        btn_ujian.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                Intent intent = new Intent(getContext(), JadwalUjian.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });

        btn_absensi.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                Intent intent = new Intent(getContext(), AbsensiAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });

        btn_tugas_anak.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                Intent intent = new Intent(getContext(), TugasAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });
        btn_raport.setOnClickListener(v -> {
            if (authorization != null  && school_code != null && student_id != null && classroom_id != null) {
                Intent intent = new Intent(getContext(), RaportAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }else{
                Toast.makeText(getContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

}
