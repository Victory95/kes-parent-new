package com.fingertech.kes.Activity.Pesan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Adapter.Adapter_Pesan_Terkirim;
import com.fingertech.kes.Activity.Adapter.PesanGuruAdapter;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesanTerkirim extends Fragment {

    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String authorization,school_code,parent_id,student_id,school_name,classroom_id,fullname,date_from,date_to,code;
    int status;
    SharedPreferences sharedPreferences;
    Auth mApiInterface;
    RecyclerView recyclerView;
    TextView tanggal,pengirim,pesan,title;
    ProgressDialog dialog;
    String kirim,pesanku,titleku,tanggalku;
    PesanModel pesanModel;
    Adapter_Pesan_Terkirim adapter_pesan_terkirim;
    List<PesanModel> pesanModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pesan_terkirim, container, false);

        mApiInterface   = ApiClient.getClient().create(Auth.class);
        recyclerView    = v.findViewById(R.id.Rv_terkirim);
        tanggal         = v.findViewById(R.id.tanggal_pesan);
        pengirim        = v.findViewById(R.id.Tvpengirim);
        pesan           = v.findViewById(R.id.Tvpesan);
        title           = v.findViewById(R.id.Tvsubject);
        mApiInterface   = ApiClient.getClient().create(Auth.class);



        sharedPreferences   = this.getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        parent_id           = sharedPreferences.getString("member_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        fullname            = sharedPreferences.getString("fullname",null);

        date_from = "2018-12-30";
        date_to=dateFormatForMonth.format(Calendar.getInstance().getTime());



        dapat_pesan();
        return v;

    }


    public void dapat_pesan(){
        progressBar();
        showDialog();
        Call<JSONResponse.PesanAnak> call = mApiInterface.kes_message_send_get(authorization.toString(),school_code.toLowerCase(),parent_id.toString(),date_from.toString(),date_to.toString());
        call.enqueue(new Callback<JSONResponse.PesanAnak>() {
            @Override
            public void onResponse(Call<JSONResponse.PesanAnak> call, final Response<JSONResponse.PesanAnak> response) {
                Log.d("onRespone",response.code()+"");
                hideDialog();
//                setUserVisibleHint(isVisible());
                JSONResponse.PesanAnak resource = response.body();

                status  = resource.status;
                code    = resource.code;


                if (status == 1 & code.equals("DTS_SCS_0001")){
                    hideKeyboard(getActivity());
//                    date_from.clearFocus();
//                    date_to.clearFocus();
                    pesanModelList  = new ArrayList<PesanModel>();
                    for (int i = 0; i < response.body().getData().size();i++){
                        tanggalku = response.body().getData().get(i).getDatez();
                        kirim= response.body().getData().get(i).getSender_name();
                        pesanku=response.body().getData().get(i).getMessage_cont();
                        titleku=response.body().getData().get(i).getMessage_title();
                        pesanModel = new PesanModel();
                        pesanModel.setTanggal(tanggalku);
                        pesanModel.setDari(kirim);
                        pesanModel.setPesan(pesanku);
                        pesanModel.setTitle(titleku);
                        pesanModelList.add(pesanModel);
                    }
                    adapter_pesan_terkirim = new Adapter_Pesan_Terkirim(pesanModelList);
                    adapter_pesan_terkirim.setOnItemClickListener(new Adapter_Pesan_Terkirim.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent(getActivity(), PesanTerkirim.class);
                            intent.putExtra("fullname",fullname);
                            intent.putExtra("authorization",authorization);
                            intent.putExtra("school_code",school_code);
                            intent.putExtra("parent_id",parent_id);
                            intent.putExtra("message_id",response.body().getData().get(position).getMessageid());
                            intent.putExtra("parent_message_id",response.body().getData().get(position).getParent_message_id());
//                            startActivity(intent);
                        }
                    });
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter_pesan_terkirim);

                }
                else if (status == 0 & code.equals("DTS_ERR_0001")){
                    hideKeyboard(getActivity());

                    recyclerView.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JSONResponse.PesanAnak> call, Throwable t) {
                Log.i("onFailure",t.toString());
                Toast.makeText(getActivity(),"Data Tidak Ada",Toast.LENGTH_LONG).show();
                hideDialog();
            }
        });
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

    private void onBackPressed() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
        dialog = new ProgressDialog(getActivity());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }


}
