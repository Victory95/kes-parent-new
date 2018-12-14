package com.fingertech.kes.Activity.Fragment;


import android.app.DatePickerDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.fingertech.kes.R;
import com.fingertech.kes.Service.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataAnakFragment extends Fragment {


    private EditText et_tanggal;
    private Spinner et_negara_asal;
    public DataAnakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data_anak, container, false);

        Calendar calendar = Calendar.getInstance();

        et_tanggal =(EditText)view.findViewById(R.id.et_tanggallahiR);
        final DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//i adalah tahun, i1 adalah bulan dan i2 adalah hari
                //Respon dari dialog, di convert ke format tanggal yang diinginkan lalu setelah itu ditampilkan
                et_tanggal.setText(convertDate(i, i1, i2));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        //calendar.get(Calendar.YEAR) memberikan nilai tahun awal pada dialog sesuai tahun yang didapat dari calendar

        et_tanggal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                datePickerDialog.show();//Dialog ditampilkan ketika edittext diclick
            }
        });

        et_tanggal.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    datePickerDialog.show();//Dialog ditampilkan ketika edittext mendapat fokus
                }
            }
        });

        // Spinner click listener
        et_negara_asal = (Spinner) view.findViewById(R.id.sp_negara_asal);
        loadSpinnerData();
        return view;
    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(int year, int month, int day) {
        Log.d("Tanggal", year + "/" + month + "/" + day);
        String temp = year + "-" + (month + 1) + "-" + day;
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMM yyyy");
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(temp));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void loadSpinnerData() {
        // database handler
        DBHelper db = new DBHelper(getApplicationContext());

        final Cursor myData = db.SelectAllData();

        SimpleCursorAdapter adapter;
        adapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.costum_spinner_item, myData
                ,new String[] {"negara"}
                ,new int[] { R.id.negarA});


        et_negara_asal.setAdapter(adapter);

    }
}
