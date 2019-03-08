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

import com.fingertech.kes.Activity.Anak.JadwalPelajaran;
import com.fingertech.kes.Activity.Anak.KalenderKelas;
import com.fingertech.kes.Activity.Anak.PesanAnak;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDuaFragment extends Fragment {


    public MenuDuaFragment() {
        // Required empty public constructor
    }

    String authorization,parent_nik,school_code,student_id,member_id,classroom_id,school_name;
    CardView btn_kalender,btn_pesan;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dua, container, false);

        btn_kalender    = view.findViewById(R.id.btn_kalender);
        btn_pesan       = view.findViewById(R.id.btn_pesan);

        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        parent_nik          = sharedPreferences.getString("parent_nik",null);

        btn_kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("school_code",school_code);
                editor.putString("authorization",authorization);
                editor.putString("classroom_id",classroom_id);
                editor.putString("student_id",student_id);
                editor.commit();
                Intent intent = new Intent(getContext(), KalenderKelas.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code.toLowerCase());
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);

            }
        });
        btn_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("member_id",member_id);
                editor.putString("school_code",school_code);
                editor.putString("authorization",authorization);
                editor.putString("classroom_id",classroom_id);
                editor.putString("school_name",school_name);
                editor.putString("student_id",student_id);
                editor.commit();
                Intent intent = new Intent(getContext(), PesanAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code);
                intent.putExtra("member_id", member_id);
                intent.putExtra("classroom_id", classroom_id);
                intent.putExtra("school_name",school_name);
                intent.putExtra("student_id", student_id);
                startActivity(intent);

            }
        });
        return view;
    }

}
