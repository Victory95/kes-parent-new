package com.fingertech.kes.Activity.Fragment.UjianFragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.BulanAdapter.UnAdapter;
import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.UnModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnFragment extends Fragment {


    public UnFragment() {
        // Required empty public constructor
    }

    String authorization,memberid,school_code,classroom_id,type_ujian;
    SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DateFormat df   = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date            = df.format(Calendar.getInstance().getTime());
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        Bundle bundle   = this.getArguments();
        if (bundle!=null) {
            authorization   = getArguments().getString("authorization");
            school_code     = getArguments().getString("school_code");
            memberid        = getArguments().getString("student_id");
            classroom_id    = getArguments().getString("classroom_id");
            type_ujian      = getArguments().getString("type_ujian");
            Check_Semester();
        }
    }

    List<UnModel> itemUjianList = new ArrayList<>();
    UnAdapter ujianAdapter;
    Auth mApiInterface;
    RecyclerView rv_ujian;
    ProgressDialog dialog;
    int status;
    String code;
    String jam,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
    TextView no_ujian;
    String date;
    public static final String my_viewpager_preferences = "my_ujian_preferences";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view       = inflater.inflate(R.layout.fragment_un, container, false);
        rv_ujian        = view.findViewById(R.id.recycleview_ujian);
        no_ujian        = view.findViewById(R.id.hint_ujian);


//        Check_Semester();
        return view;
    }

    private void Check_Semester(){
        Call<JSONResponse.CheckSemester> call = mApiInterface.kes_check_semester_get(authorization.toString(),school_code.toString().toLowerCase(),classroom_id.toString(),date.toString());
        call.enqueue(new Callback<JSONResponse.CheckSemester>() {
            @Override
            public void onResponse(Call<JSONResponse.CheckSemester> call, final Response<JSONResponse.CheckSemester> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.CheckSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;
                    semester_id = response.body().getData();
                    dapat_semester();
                    Jadwal_ujian();
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.CheckSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }
    public void dapat_semester(){

        Call<JSONResponse.ListSemester> call = mApiInterface.kes_list_semester_get(authorization.toString(),school_code.toLowerCase(),classroom_id.toString());

        call.enqueue(new Callback<JSONResponse.ListSemester>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<JSONResponse.ListSemester> call, final Response<JSONResponse.ListSemester> response) {
                Log.i("KES", response.code() + "");

                if (response.isSuccessful()) {
                    JSONResponse.ListSemester resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    String tahun_mulai, tahun_akhir;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get(i).getSemester_id().equals(semester_id)) {
                                semester = response.body().getData().get(i).getSemester_name();
                                start_date = response.body().getData().get(i).getStart_date();
                                end_date = response.body().getData().get(i).getEnd_date();
                            }
                            if (response.body().getData().get(i).getSemester_name().equals("Ganjil")) {
                                start_year = converTahun(response.body().getData().get(i).getStart_date());
                            } else if (response.body().getData().get(i).getSemester_name().equals("Genap")) {
                                start_end = converTahun(response.body().getData().get(i).getEnd_date());
                            }
//                            tv_semester.setText("Semester " + semester + " (" + start_year + "/" + start_end + ")");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }
    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tahun));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void Jadwal_ujian(){
        progressBar();
        showDialog();
        Call<JSONResponse.JadwalUjian> call = mApiInterface.kes_exam_schedule_get(authorization.toString(),school_code.toString().toLowerCase(),memberid.toString(),classroom_id.toString(),semester_id.toString());
        call.enqueue(new Callback<JSONResponse.JadwalUjian>() {
            @Override
            public void onResponse(Call<JSONResponse.JadwalUjian> call, final Response<JSONResponse.JadwalUjian> response) {
                Log.i("Ujian", response.code() + "");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.JadwalUjian resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    UnModel itemUjian = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        if (itemUjianList!=null) {
                            itemUjianList.clear();
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                Log.d("UjianHariini", type_ujian + "/" + response.body().getData().get(i).getType_name());
                                if (response.body().getData().get(i).getType_name().equals(type_ujian)) {
                                    jam         = response.body().getData().get(i).getExam_time_ok();
                                    tanggal     = response.body().getData().get(i).getExam_date();
                                    mapel       = response.body().getData().get(i).getCources_name();
                                    type        = response.body().getData().get(i).getType_name();
                                    deskripsi   = response.body().getData().get(i).getExam_desc();
                                    nilai       = response.body().getData().get(i).getScore_value();
                                    itemUjian = new UnModel();
                                    itemUjian.setJam(jam);
                                    itemUjian.setTanggal(converttanggal(tanggal));
                                    itemUjian.setMapel(mapel);
                                    itemUjian.setType_id(type);
                                    itemUjian.setDeskripsi(deskripsi);
                                    itemUjian.setNilai(nilai);
                                    itemUjianList.add(itemUjian);
                                    no_ujian.setVisibility(View.GONE);
                                    rv_ujian.setVisibility(View.VISIBLE);
                                } else if (!response.body().getData().get(i).getType_name().equals(type_ujian)) {
                                    no_ujian.setVisibility(View.VISIBLE);
                                    rv_ujian.setVisibility(View.GONE);
                                }
                            }
                            ujianAdapter = new UnAdapter(itemUjianList, getContext());
                            ujianAdapter.notifyDataSetChanged();
                            rv_ujian.setOnFlingListener(null);
                            rv_ujian.setLayoutManager(new VegaLayoutManager());
                            rv_ujian.setAdapter(ujianAdapter);
                        }
                    } else {
                        hideKeyboard(getActivity());
                        no_ujian.setVisibility(View.VISIBLE);
                        rv_ujian.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("UjianGagal", t.toString());
                hideDialog();
            }

        });
    }

    String converttanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",new Locale("in","ID"));

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("in","ID"));
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
        dialog.setContentView(R.layout.progressbar);
    }
    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
    }
    public void progressBar(){
        dialog = new ProgressDialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
