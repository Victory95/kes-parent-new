package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.CustomView.MySupportMapFragment;
import com.fingertech.kes.Activity.Maps.full_maps;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.Data;
import com.fingertech.kes.Activity.Model.ProfileModel;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.fingertech.kes.Rest.StudentTable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempatTinggalFragment extends Fragment  implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener{

    private Marker CurrLocationMarker;
    private TextView namatempat,alamattempattinggal;
    private LocationRequest mlocationRequest;
    private Location mlastLocation;
    private GoogleMap Mmap;
    private ImageView arrom;
    GoogleApiClient mGoogleApiClient;
    double CurrentLatitude;
    double CurrentLongitude;
    TextInputLayout til_rt,til_rw,til_kelurahan,til_kecamatan,til_kodepos,til_jenistinggal,til_transportasi,til_alamat;

    public TempatTinggalFragment() {
        // Required empty public constructor

    }

    ViewPager ParentPager;
    AnakMain anakMain;
    Button buttonBerikutnya,buttonKembali;
    EditText et_rt,et_rw,et_kelurahan,et_kecamatan,et_kodepos,et_jenis_tinggal,et_trasnportasi,et_alamat,et_dusun;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private AnakMain.FragmentAdapter fragmentAdapter;
    String rt,rw,kelurahan,kecamatan,kodepos,jenis_tinggal,transportasi,alamat;
    String parent_id,student_nik,school_id,email,childrenname,school_name,fullname,student_id,member_id,parent_nik,authorization,school_code;
    int status;
    String code;
    ProgressDialog dialog;
    Auth mApiInterface;

    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";


    public static final String TAG_NAMA_LENGKAP      = "nama_lengkap";
    public static final String TAG_NIS               = "nis";
    public static final String TAG_NISN              = "nisn";
    public static final String TAG_NIK               = "nik";
    public static final String TAG_JENIS_KELAMIN     = "jenis_kelamin";
    public static final String TAG_TEMPAT_LAHIR      = "tempat_lahir";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_ROMBEL            = "rombel";
    public static final String TAG_KEBUTUHAN_KHUSUS  = "kebutuhan_khusus";
    public static final String TAG_TINGKATAN         = "tingkatan";
    public static final String TAG_KEWARGANEGARAAN   = "kewarganegaraan";
    public static final String TAG_AGAMA             = "agama";

    public static final String TAG_TELEPON_RUMAH     = "telepon_rumah";
    public static final String TAG_HANDPHONE         = "handphone";
    public static final String TAG_EMAIL             = "email";
    public static final String TAG_SKUN              = "skun";
    public static final String TAG_PENERIMAANKPS     = "penerimaan_kps";
    public static final String TAG_NOKPS             = "no_kps";


    SharedPreferences sharedpreferences,sharedanak;
    String Nama_lengkap,Nis,Nisn,Nik,Rombel,Tingkatan,Agama,Negara,Kebutuhankhusus,Tempat_lahir,Tanggal_lahir,Jenis_kelamin;
    String telepon_rumah,handphone,skun,penerimaan_kps,nokps,dusun;
    String studentdetailId,classroom_id,picture;
    double latitude_anak,longitude_anak;
    NestedScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tempat_tinggal, container, false);

        namatempat          = view.findViewById(R.id.nama_rumah);
        alamattempattinggal = view.findViewById(R.id.alamat_rumah_anak);
        arrom               = view.findViewById(R.id.arrom);
        anakMain            = (AnakMain)getActivity();
        ParentPager         = anakMain.findViewById(R.id.PagerAnak);
        indicator           = view.findViewById(R.id.indicators);
        buttonKembali       = view.findViewById(R.id.btn_kembali);
        buttonBerikutnya    = view.findViewById(R.id.btn_berikut);
        et_rt               = view.findViewById(R.id.et_rt);
        et_rw               = view.findViewById(R.id.et_rw);
        et_kelurahan        = view.findViewById(R.id.et_kelurahan);
        et_kecamatan        = view.findViewById(R.id.et_Kecamatan);
        et_kodepos          = view.findViewById(R.id.et_kode_pos);
        et_jenis_tinggal    = view.findViewById(R.id.et_status_tinggal);
        et_trasnportasi     = view.findViewById(R.id.et_transportasi);
        et_alamat           = view.findViewById(R.id.et_Alamat);
        et_dusun            = view.findViewById(R.id.et_dusun);
        til_alamat          = view.findViewById(R.id.til_Alamat);
        til_jenistinggal    = view.findViewById(R.id.til_status_tinggal);
        til_kecamatan       = view.findViewById(R.id.til_Kecamatan);
        til_kelurahan       = view.findViewById(R.id.til_kelurahan);
        til_kodepos         = view.findViewById(R.id.til_kode_pos);
        til_rt              = view.findViewById(R.id.til_rt);
        til_rw              = view.findViewById(R.id.til_rw);
        til_transportasi    = view.findViewById(R.id.til_transportasi);
        scrollView          = view.findViewById(R.id.scroll_view);
        fragmentAdapter     = new AnakMain.FragmentAdapter(getActivity().getSupportFragmentManager());

        MySupportMapFragment mapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapTinggal);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            mapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });
        }

        mApiInterface = ApiClient.getClient().create(Auth.class);
        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,"school_code");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");


        sharedanak  = getActivity().getSharedPreferences(DataAnakFragment.my_shared_anak,Context.MODE_PRIVATE);
        Nama_lengkap        = sharedanak.getString(TAG_NAMA_LENGKAP,"");
        Nis                 = sharedanak.getString(TAG_NIS,"");
        Nisn                = sharedanak.getString(TAG_NISN,"");
        Nik                 = sharedanak.getString(TAG_NIK,"");
        Tempat_lahir        = sharedanak.getString(TAG_TEMPAT_LAHIR,"");
        Tanggal_lahir       = sharedanak.getString(TAG_TANGGAL_LAHIR,"");
        Rombel              = sharedanak.getString(TAG_ROMBEL,"");
        Kebutuhankhusus     = sharedanak.getString(TAG_KEBUTUHAN_KHUSUS,"");
        Jenis_kelamin       = sharedanak.getString(TAG_JENIS_KELAMIN,"");
        Tingkatan           = sharedanak.getString(TAG_TINGKATAN,"");
        Agama               = sharedanak.getString(TAG_AGAMA,"");
        Negara              = sharedanak.getString(TAG_KEWARGANEGARAAN,"");
        telepon_rumah       = sharedanak.getString(TAG_TELEPON_RUMAH,"");
        handphone           = sharedanak.getString(TAG_HANDPHONE,"");
        email               = sharedanak.getString(TAG_EMAIL,"");
        skun                = sharedanak.getString(TAG_SKUN,"");
        penerimaan_kps      = sharedanak.getString(TAG_PENERIMAANKPS,"");
        nokps               = sharedanak.getString(TAG_NOKPS,"");


        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submitForm();
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
        });

        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[3].setBackgroundResource(R.drawable.selected_item);
        arrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent,1);
            }
        });

        namatempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent,1);
            }
        });
        data_student_get();
        return view;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(1000);
        mlocationRequest.setFastestInterval(1000);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onCameraIdle() {
        LatLng LatLng = Mmap.getCameraPosition().target;
        Geocoder geocode1 = new Geocoder(getContext());
        try {
            List<Address> addressList = geocode1.getFromLocation(LatLng.latitude, LatLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String address1 = addressList.get(0).getAddressLine(0);
                alamattempattinggal.setText(address1+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude_anak = LatLng.latitude;
        longitude_anak  = LatLng.longitude;
    }

    @Override
    public void onCameraMoveCanceled() {
        if(CurrLocationMarker != null){
            CurrLocationMarker.remove();}
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=Mmap.getCameraPosition();

        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map))
                .title("im here");

        if(CurrLocationMarker!= null){
            CurrLocationMarker.remove();}
        CurrLocationMarker = Mmap.addMarker(options);

    }

    @Override
    public void onCameraMoveStarted(int i) {
        CameraPosition position=Mmap.getCameraPosition();

    }

    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();

        }

        //Place current location marker
        final LatLng latLng = new LatLng(latitude_anak, longitude_anak);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));

        //move map camera
        Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap = googleMap;

        Mmap.setOnCameraIdleListener(this);
        Mmap.setOnCameraMoveStartedListener(this);
        Mmap.setOnCameraMoveListener(this);
        Mmap.setOnCameraMoveCanceledListener(this);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                Mmap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            Mmap.setMyLocationEnabled(true);
        }
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String strEditText = data.getStringExtra("alamat");
                double lati = data.getDoubleExtra("latitude",0.0);
                double longi = data.getDoubleExtra("longitude",0.0);

                final LatLng latLng1 = new LatLng(lati,longi);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng1.latitude, latLng1.longitude)).zoom(16).build();

                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng1);
                markerOptions.title("Current Position");
                markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));

                //move map camera
                Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(CurrLocationMarker!= null){
                    CurrLocationMarker.remove();}
                CurrLocationMarker = Mmap.addMarker(markerOptions);
                alamattempattinggal.setText(strEditText);
            }
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
    }

    private void setUiPageViewController() {
        mDotCount = fragmentAdapter.getCount();
        mDots = new LinearLayout[mDotCount];

        for (int i = 0; i < mDotCount; i++) {
            mDots[i] = new LinearLayout(getContext());
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            indicator.addView(mDots[i]);
            mDots[0].setBackgroundResource(R.drawable.selected_item);
        }
    }

    public void data_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization, school_code, student_id, parent_nik);
        call.enqueue(new Callback<JSONResponse.DetailStudent>() {
            @Override
            public void onResponse(Call<JSONResponse.DetailStudent> call, Response<JSONResponse.DetailStudent> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();

                JSONResponse.DetailStudent resource = response.body();
                status = resource.status;
                code = resource.code;

                String DTS_SCS_0001 = getResources().getString(R.string.DTS_SCS_0001);
                String DTS_ERR_0001 = getResources().getString(R.string.DTS_ERR_0001);

                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    studentdetailId     = response.body().getData().getStudentdetailid();
                    dusun               = response.body().getData().getDusun();
                    rt                  = response.body().data.getRt();
                    rw                  = response.body().data.getRw();
                    kelurahan           = response.body().data.getKelurahan();
                    kecamatan           = response.body().data.getKecamatan();
                    kodepos             = response.body().data.getPost_code();
                    jenis_tinggal       = response.body().data.getJenis_tinggal();
                    transportasi        = response.body().data.getTransportasi();
                    alamat              = response.body().data.getAddress();
                    latitude_anak       = Double.parseDouble(response.body().data.getLatitude());
                    longitude_anak      = Double.parseDouble(response.body().data.getLongitude());
                    classroom_id        = response.body().getData().getClassroom_id();
                    picture             = response.body().getData().getPicture();

                    et_rt.setText(rt);
                    et_rw.setText(rw);
                    et_kelurahan.setText(kelurahan);
                    et_kecamatan.setText(kecamatan);
                    et_kodepos.setText(kodepos);
                    et_jenis_tinggal.setText(jenis_tinggal);
                    et_trasnportasi.setText(transportasi);
                    et_alamat.setText(alamat);
                    et_dusun.setText(dusun);

                } else {
                    if(status == 0 && code.equals("DTS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DTS_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.DetailStudent> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    //////// Progressbar - Loading Animation
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void submitForm() {

        if (!validateAlamat()) {
            return;
        }
        if (!validateRt()) {
            return;
        }
        if (!validateRw()) {
            return;
        }
        if (!validateKelurahan()){
            return;
        }
        if (!validateKecamatan()){
            return;
        }
        if (!validateKodepos()){
            return;

        }if (!validateJenistinggal()){
            return;

        }if (!validateTransportasi()){
            return;

        }else {
            update_detail();
        }
    }

    private boolean validateAlamat() {
        if (et_alamat.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi alamat anak",Toast.LENGTH_LONG).show();
            requestFocus(et_alamat);
            return false;
        } else {
            til_alamat.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateJenistinggal() {
        if (et_jenis_tinggal.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi jenis tinggal anak",Toast.LENGTH_LONG).show();
            requestFocus(et_jenis_tinggal);
            return false;
        } else {
            til_jenistinggal.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateKecamatan() {
        if (et_kecamatan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi kecamatan anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kecamatan);
            return false;
        } else {
            til_kecamatan.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateKelurahan() {
        if (et_kelurahan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi kelurahan anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kelurahan);
            return false;
        } else {
            til_kelurahan.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateKodepos() {
        if (et_kodepos.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi kode pos anak",Toast.LENGTH_LONG).show();
            requestFocus(et_kodepos);
            return false;
        } else {
            til_kodepos.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateRt() {
        if (et_rt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi rt anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rt);
            return false;
        } else {
            til_rt.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateRw() {
        if (et_rw.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi rw anak",Toast.LENGTH_LONG).show();
            requestFocus(et_rw);
            return false;
        } else {
            til_rw.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateTransportasi() {
        if (et_trasnportasi.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi transportasi anak",Toast.LENGTH_LONG).show();
            requestFocus(et_trasnportasi);
            return false;
        } else {
            til_transportasi.setErrorEnabled(false);
        }

        return true;
    }

    public void update_detail(){

        Call<JSONResponse> postCall = mApiInterface.update_student_detail_put(authorization, studentdetailId, school_code, student_id, Rombel, Kebutuhankhusus, et_rt.getText().toString(),et_rw.getText().toString(),et_dusun.getText().toString(),et_kelurahan.getText().toString(),et_kecamatan.getText().toString(),et_kodepos.getText().toString(),et_jenis_tinggal.getText().toString(),et_trasnportasi.getText().toString(),String.valueOf(latitude_anak),String.valueOf(longitude_anak), telepon_rumah, skun, penerimaan_kps, nokps);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;

                if (status == 1 && code.equals("UST_SCS_0001")){
                    update_member();
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal mengirim", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void update_member(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.update_student_member_put(authorization, student_id, school_code, Nama_lengkap, Jenis_kelamin, Tempat_lahir, Tanggal_lahir, Negara, Agama,et_alamat.getText().toString(), handphone);
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");

                JSONResponse resource = response.body();
                status = resource.status;
                code = resource.code;
                ProfileModel profileModel;
                if (status == 1 && code.equals("USTM_SCS_0001")){

                    Intent intent = new Intent(getContext(), MenuUtama.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(), "Gagal mengirim", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                hideDialog();
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

}
