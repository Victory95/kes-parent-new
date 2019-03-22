package com.fingertech.kes.Activity.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fingertech.kes.Activity.Anak.JadwalUjian;
import com.fingertech.kes.Activity.Anak.KalenderKelas;
import com.fingertech.kes.Activity.Anak.PesanAnak;
import com.fingertech.kes.Activity.Anak.RaporAnak;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuDuaFragment extends Fragment {


    public MenuDuaFragment() {
        // Required empty public constructor
    }

    String authorization,parent_nik,school_code,student_id,member_id,classroom_id,school_name,nama_anak;
    CardView btn_jadwal_ujian, btn_raport;
    SharedPreferences sharedPreferences,sharedPreferences2;

    public static final String myViewpagerPreferences = "myViewpagerPreferences";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences   = getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        sharedPreferences2  = getActivity().getSharedPreferences(myViewpagerPreferences,Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        member_id           = sharedPreferences.getString("member_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        parent_nik          = sharedPreferences.getString("parent_nik",null);
        nama_anak           = sharedPreferences.getString("student_name",null);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu_dua, container, false);

        btn_jadwal_ujian = view.findViewById(R.id.btn_jadwal_ujian);
        btn_raport = view.findViewById(R.id.btn_raport);


        btn_jadwal_ujian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("school_code",school_code.toLowerCase());
                editor.putString("authorization",authorization);
                editor.putString("classroom_id",classroom_id);
                editor.putString("student_id",student_id);
                editor.apply();
                Intent intent = new Intent(getContext(), JadwalUjian.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code.toLowerCase());
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);

            }
        });
        btn_raport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences2.edit();
                editor.putString("school_code",school_code);
                editor.putString("authorization",authorization);
                editor.putString("classroom_id",classroom_id);
                editor.putString("student_id",student_id);
                editor.apply();
                Intent intent = new Intent(getContext(), RaporAnak.class);
                intent.putExtra("authorization", authorization);
                intent.putExtra("school_code", school_code.toLowerCase());
                intent.putExtra("student_id", student_id);
                intent.putExtra("classroom_id", classroom_id);
                startActivity(intent);
            }
        });
        return view;
    }

}
