package com.fingertech.kes.Activity.Anak;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.UjianAdapter;
import com.fingertech.kes.Activity.Adapter.UjianAdapterTeratas;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.ItemUjian;
import com.fingertech.kes.Activity.Model.UjianModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.stone.vega.library.VegaLayoutManager;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JadwalUjian extends AppCompatActivity {

    List<ItemUjian> itemUjianList = new ArrayList<>();
    ItemUjian itemUjian;
    UjianAdapter ujianAdapter;
    UjianAdapterTeratas ujianAdapterTeratas;
    Auth mApiInterface;
    String authorization,memberid,school_code,classroom_id;
    RecyclerView rv_ujian,rv_teratas;
    RecyclerView rv_ujian_sekarang;
    Toolbar toolbar;
    ProgressDialog dialog;
    int status;
    String code;
    String jam,tanggal,type,nilai,mapel,deskripsi,semester_id,start_date,end_date,semester,start_year,start_end;
    String waktu,bulan,judul,tanggal_teratas,des;
    TextView no_ujian;
    String date,semester_nama;
    TextView tv_semester,tv_filter,tv_semesters,tv_reset,tv_slide;
    EditText et_kata_kunci;
    LinearLayout ll_slide;
    com.rey.material.widget.Spinner sp_type;
    private List<JSONResponse.DataSemester> dataSemesters;
    private List<JSONResponse.DataMapel> dataMapelList;
    Spinner sp_mapel;
    private String[] tipe = {
            "FINAL",
            "MID",
            "UN"
    };
    Button btn_cari;
    ImageView btn_down;
    View view;
    String mata_pelajaran,type_pelajaran;
    SharedPreferences sharedPreferences;
    List<UjianModel> ujianModelList = new ArrayList<>();
    UjianModel ujianModel;
    private SimpleDateFormat dateFormat     = new SimpleDateFormat("MM-yyyy",new Locale("in","ID"));
    private DateFormat times_format         = new SimpleDateFormat("MM-yyyy", Locale.getDefault());
    String bulan_sekarang,jam_db,tanggal_db;
    Date month_now,month_db;
    TextView tv_hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ujian_sheet);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        rv_ujian        = findViewById(R.id.recycleview_ujian);
        toolbar         = findViewById(R.id.toolbar_ujian);
        no_ujian        = findViewById(R.id.tv_no_ujian);
        tv_semester     = findViewById(R.id.tv_semester);
        tv_filter       = findViewById(R.id.tv_filter);
        et_kata_kunci   = findViewById(R.id.et_kata_kunci);
        tv_hint         = findViewById(R.id.hint_ujian);
        rv_ujian_sekarang = findViewById(R.id.recycleview_ujian_bulan);

        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        memberid            = sharedPreferences.getString("student_id",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = df.format(Calendar.getInstance().getTime());
        bulan_sekarang  = dateFormat.format(Calendar.getInstance().getTime());
        Check_Semester();
        et_kata_kunci.clearFocus();

        et_kata_kunci.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ujianAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_filter.setOnClickListener(v -> openBottomSheet());
    }

    public void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.filter_sheet, null);
        LinearLayout ll_slide   = view.findViewById(R.id.slide_down);
        tv_semesters     = view.findViewById(R.id.tv_semesters);
        sp_mapel        = view.findViewById(R.id.sp_mapel);
        sp_type         = view.findViewById(R.id.sp_tipe);
        btn_cari        = view.findViewById(R.id.btn_cari);
        tv_reset        = view.findViewById(R.id.reset);
        tv_slide        = view.findViewById(R.id.name);
        btn_down        = view.findViewById(R.id.arrow_down);

        final Dialog mBottomSheetDialog = new Dialog(JadwalUjian.this,
                R.style.MaterialDialogSheet);

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        tv_semesters.setText(semester);

        List<String> listMapel = new ArrayList<>();
        for (int m = 0;m < dataMapelList.size();m++){
            listMapel.add(dataMapelList.get(m).getCources_name());
        }

        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(sp_mapel);

            // Set popupWindow height to 500px
            popupWindow.setHeight(500);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
            Log.d("eror",e.getMessage());
        }
        final  ArrayAdapter<String> adapterMapel = new ArrayAdapter<String>(JadwalUjian.this,R.layout.spinner_full,listMapel);
        adapterMapel.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_mapel.setAdapter(adapterMapel);
        sp_mapel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mata_pelajaran = listMapel.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final List<String> listtype = new ArrayList<>(Arrays.asList(tipe));
        final  ArrayAdapter<String> adapterTipe = new ArrayAdapter<String>(JadwalUjian.this,R.layout.spinner_full,listtype);
        adapterTipe.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        sp_type.setAdapter(adapterTipe);
        sp_type.setOnItemClickListener((parent, view1, position, id) -> {
            type_pelajaran = listtype.get(position).toString();
            return true;
        });
        type_pelajaran = sp_type.getSelectedItem().toString();

        btn_down.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        tv_slide.setOnClickListener(v -> mBottomSheetDialog.dismiss());
        tv_reset.setOnClickListener(v -> {
            if (ujianModelList!=null){
                ujianModelList.clear();
            }
            if (itemUjianList!=null){
                itemUjianList.clear();
            }
            Jadwal_ujian();
            mBottomSheetDialog.dismiss();
        });
        btn_cari.setOnClickListener(v -> {
            ujianAdapter.getfilter(mata_pelajaran.toLowerCase()).filter(type_pelajaran.toLowerCase());
            mBottomSheetDialog.dismiss();
        });
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
                    dapat_mapel();
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
                            tv_semester.setText("Semester " + semester + " (" + start_year + "/" + start_end + ")");
                        }

                        dataSemesters = response.body().getData();

                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListSemester> call, Throwable t) {
                Log.d("onFailure", t.toString());
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void dapat_mapel(){
        Call<JSONResponse.ListMapel> call = mApiInterface.kes_list_cources_get(authorization.toString(),school_code.toLowerCase().toString(),classroom_id.toString());
        call.enqueue(new Callback<JSONResponse.ListMapel>() {
            @Override
            public void onResponse(Call<JSONResponse.ListMapel> call, Response<JSONResponse.ListMapel> response) {
                Log.d("onResponse",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.ListMapel resource = response.body();
                    status = resource.status;
                    code = resource.code;
                    if (status == 1 && code.equals("KLC_SCS_0001")) {
                        dataMapelList = response.body().getData();
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListMapel> call, Throwable t) {

            }
        });
    }

    String converDate(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MM-yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String convertTanggal(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
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
    String convertBulan(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converJam(String tanggal){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());

        SimpleDateFormat newDateFormat = new SimpleDateFormat("HH:mm",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }
    String converTahun(String tahun){
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

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
                Log.i("KES", response.code() + "");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.JadwalUjian resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    ItemUjian itemUjian = null;
                    if (status == 1 && code.equals("DTS_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            jam         = response.body().getData().get(i).getExam_time_ok();
                            tanggal     = response.body().getData().get(i).getExam_date();
                            mapel       = response.body().getData().get(i).getCources_name();
                            type        = response.body().getData().get(i).getType_name();
                            deskripsi   = response.body().getData().get(i).getExam_desc();
                            nilai       = response.body().getData().get(i).getScore_value();
                            tanggal_db  = converDate(response.body().getData().get(i).getExam_date());
                            jam_db      = converJam(response.body().getData().get(i).getExam_time());
                            try {
                                month_now   = times_format.parse(bulan_sekarang);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Long bulan_now = month_now.getTime();

                            try {
                                month_db    = times_format.parse(tanggal_db);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Long bulan_db   = month_db.getTime();
                            if (bulan_db.equals(bulan_now)){
                                ujianModel  = new UjianModel();
                                ujianModel.setDeskripsi(deskripsi);
                                ujianModel.setNilai(nilai);
                                ujianModel.setJam(jam_db);
                                ujianModel.setMapel(mapel);
                                ujianModel.setWaktu(converttanggal(tanggal)+" "+jam_db);
                                ujianModel.setBulan(convertBulan(tanggal));
                                ujianModel.setTanggal(convertTanggal(tanggal));
                                ujianModel.setType_id(type);
                                ujianModelList.add(ujianModel);
                            }else {
                                itemUjian = new ItemUjian();
                                itemUjian.setJam(jam);
                                itemUjian.setTanggal(converttanggal(tanggal));
                                itemUjian.setMapel(mapel);
                                itemUjian.setType_id(type);
                                itemUjian.setDeskripsi(deskripsi);
                                itemUjian.setNilai(nilai);
                                itemUjianList.add(itemUjian);
                            }
                        }
                        no_ujian.setVisibility(View.GONE);
                        ujianAdapter = new UjianAdapter(itemUjianList, JadwalUjian.this);
                        rv_ujian.setOnFlingListener(null);
                        rv_ujian.setLayoutManager(new VegaLayoutManager());
                        rv_ujian.setAdapter(ujianAdapter);

                        if (ujianModelList.size() == 0) {
                            rv_ujian_sekarang.setVisibility(View.GONE);
                            tv_hint.setVisibility(View.VISIBLE);
                        }else {
                            rv_ujian_sekarang.setVisibility(View.VISIBLE);
                            tv_hint.setVisibility(View.GONE);
                            ujianAdapterTeratas = new UjianAdapterTeratas(ujianModelList, JadwalUjian.this);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(JadwalUjian.this);
                            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            rv_ujian_sekarang.setOnFlingListener(null);
                            rv_ujian_sekarang.setLayoutManager(layoutManager);
                            rv_ujian_sekarang.setAdapter(ujianAdapterTeratas);
                        }

                    } else {
                        hideKeyboard(JadwalUjian.this);
                        et_kata_kunci.clearFocus();
                        no_ujian.setVisibility(View.VISIBLE);
                        rv_ujian.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.JadwalUjian> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
            }

        });
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
        dialog = new ProgressDialog(JadwalUjian.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
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
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}

