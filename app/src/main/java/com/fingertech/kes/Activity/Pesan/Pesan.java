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
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.fingertech.kes.Activity.Adapter.PesanGuruAdapter;
import com.fingertech.kes.Activity.Anak.PesanAnak;
import com.fingertech.kes.Activity.Anak.PesanDetail;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.PesanModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Rest.UtilsApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.Subject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Pesan extends Fragment {

    TextView pengirim,pesan,title,tanggal;
    ProgressDialog dialog;
    Auth mApiInterface,mApi;
    SharedPreferences sharedPreferences;
    String authorization,school_code,parent_id,student_id,school_name,classroom_id,fullname;
    RecyclerView recyclerView;
    int status;
    String code,date_from,date_to,statusku;
    List<PesanModel> pesanModelList;
    PesanGuruAdapter pesanGuruAdapter;
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String kirim,pesanku,titleku,tanggalku,jam;
    SwipeRefreshLayout swipeRefreshLayout;
    PesanModel pesanModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pesan, container, false);

        tanggal         = v.findViewById(R.id.tanggal_pesan);
        pengirim        = v.findViewById(R.id.Tvpengirim);
        pesan           = v.findViewById(R.id.Tvpesan);
        title           = v.findViewById(R.id.Tvsubject);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        recyclerView    = v.findViewById(R.id.rv_chat);
        swipeRefreshLayout  = v.findViewById(R.id.pullToRefresh);
        mApi            = UtilsApi.getAPIService();

        sharedPreferences   = this.getActivity().getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        parent_id           = sharedPreferences.getString("member_id",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        classroom_id        = sharedPreferences.getString("classroom_id",null);
        fullname            = sharedPreferences.getString("fullname",null);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-4);
        date_from   =  dateFormatForMonth.format(calendar.getTime());
        date_to     =  dateFormatForMonth.format(Calendar.getInstance().getTime());
        dapatPesan();
        refresh();

        return v;
    }


//

    public  void refresh(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1;
            @Override
            public void onRefresh() {
                dapatPesan();
                Refreshcounter = Refreshcounter + 1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }
    public void dapatPesan(){
        progressBar();
        showDialog();
        mApi.kes_message_inbox_get(authorization,school_code.toLowerCase(),parent_id,date_from,date_to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONResponse.PesanAnak>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONResponse.PesanAnak response) {
                        status  = response.status;
                        code    = response.code;
                        if (status == 1 & code.equals("DTS_SCS_0001")){
                            hideKeyboard(getActivity());
                            pesanModelList  = new ArrayList<PesanModel>();
                            for (int i = 0; i < response.getData().size();i++){
                                jam         = response.getData().get(i).getDatez();
                                tanggalku   = response.getData().get(i).getMessage_date();
                                kirim       = response.getData().get(i).getSender_name();
                                pesanku     =response.getData().get(i).getMessage_cont();
                                titleku     =response.getData().get(i).getMessage_title();
                                statusku    =response.getData().get(i).getRead_status();


                                pesanModel  = new PesanModel();
                                pesanModel.setTanggal(tanggalku);
                                pesanModel.setJam(jam);
                                pesanModel.setDari(kirim);
                                pesanModel.setPesan(pesanku);
                                pesanModel.setTitle(titleku);
                                pesanModel.setStatus(statusku);
                                pesanModel.setMessage_id(response.getData().get(i).getMessageid());
                                pesanModel.setParent_message_id(response.getData().get(i).getParent_message_id());
                                pesanModelList.add(pesanModel);

                            }

                        }
                        else if (status == 0 & code.equals("DTS_ERR_0001")){
                            hideKeyboard(getActivity());
                            recyclerView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideDialog();
                        Log.d("eror",e.toString());
                        Toast.makeText(getContext(),"Internet bermasalah",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete() {
                        hideDialog();
                        pesanGuruAdapter = new PesanGuruAdapter(pesanModelList);
                        pesanGuruAdapter.setOnItemClickListener(new PesanGuruAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getActivity(), Detail_Pesan_Guru.class);
                                intent.putExtra("fullname",fullname);
                                intent.putExtra("authorization",authorization);
                                intent.putExtra("school_code",school_code);
                                intent.putExtra("parent_id",parent_id);
                                intent.putExtra("message_id",pesanModelList.get(position).getMessage_id());
                                intent.putExtra("parent_message_id",pesanModelList.get(position).getParent_message_id());
                                startActivityForResult(intent,1);
                            }
                        });
//                    setUserVisibleHint(isVisible());
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(pesanGuruAdapter);
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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                authorization = data.getStringExtra("authorization");
                school_code   = data.getStringExtra("school_code");
                parent_id     = data.getStringExtra("parent_id");

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH,-4);
                date_from   =  dateFormatForMonth.format(calendar.getTime());
                date_to=dateFormatForMonth.format(Calendar.getInstance().getTime());
                dapatPesan();
            }
        }
    }

}
