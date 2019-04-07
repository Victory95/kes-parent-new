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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import com.fingertech.kes.Activity.Anak.EditProfileAnak;
import com.fingertech.kes.Activity.CustomView.MySupportMapFragment;
import com.fingertech.kes.Activity.Maps.full_maps;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.ParentMain;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
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
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.fingertech.kes.Activity.Fragment.DataFragment.session_status;

/**
 * A simple {@link Fragment} subclass.
 */
public class KontakFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener {

    double CurrentLatitude;
    double CurrentLongitude;
    private GoogleMap mmap;
    private LocationRequest mlocationRequest;
    private Marker mcurrLocationMarker;
    private Location mlastLocation;
    private TextView namaalamat,alamatrumah;
    private ImageView arros;
    GoogleApiClient mGoogleApiClient;
    EditText Nomorrumah,Nomorponsel;
    String nomorrumah,nomorponsel,nomorlain,pendidikan,namaperusahaan,jabatan,penghasilan,alamatkerja;

    double currentLatitude;
    double currentLongitude;
    ProgressDialog dialog;

    String location;
    Auth mApiInterface;

    SharedPreferences sharedpreferences;

    public static final String TAG_EMAIL        = "email";
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
    public static final String my_shared_viewpager   = "my_shared_viewpager";

    public static final String TAG_PARENT_NAME       = "nama_parent";
    public static final String TAG_NIK_PARENT        = "nik_parent";
    public static final String TAG_EMAIL_PARENT      = "email_parent";
    public static final String TAG_TEMPAT_LAHIR      = "tempat_lahir";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_HUBUNGAN          = "hubungan";
    public static final String TAG_KEWARGANEGARAAN   = "kewarganegaraan";

    public static final String TAG_NOMOR_RUMAH       = "nomor_rumah";
    public static final String TAG_NOMOR_PONSEL      = "nomor_ponsel";
    public static final String TAG_ALAMAT_RUMAH      = "alamat_rumah";
    public static final String TAG_LATITUDE_RUMAH    = "latitude_rumah";
    public static final String TAG_LONGITUDE_RUMAH   = "longitude_rumah";


    String authorization;
    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;
    Integer status;
    double latitude_parent,longitude_parent;
    String code;


    Button next,back;
    ParentMain parentMain;
    private ViewPager ParentPager;
    SharedPreferences sharedviewpager;
    String namaparent,nikparent,emailparent,hubungan,tanggallahir,tempatlahir,kewarganegaraan;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ParentMain.FragmentAdapter fragmentAdapter;
    TextView til_alamat_rumah;
    TextInputLayout til_nomor_rumah,til_nomor_ponsel;

    public static KontakFragment newInstance(){
        // Required empty public constructor
        KontakFragment  Fragment = new KontakFragment();
        return Fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    NestedScrollView scrollView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kontak, container, false);
        namaalamat          = view.findViewById(R.id.nama_alamat);
        arros               = view.findViewById(R.id.arroW);
        alamatrumah         = view.findViewById(R.id.alamat_rumah);
        Nomorrumah          = view.findViewById(R.id.et_nomor_rumah);
        Nomorponsel         = view.findViewById(R.id.et_nomor_ponsel);
        parentMain          = (ParentMain)getActivity();
        indicator           = view.findViewById(R.id.indicators);
        back                = view.findViewById(R.id.btn_kembali);
        next                = view.findViewById(R.id.btn_berikut);
        scrollView          = view.findViewById(R.id.scroll_view);
        fragmentAdapter     = new ParentMain.FragmentAdapter(getActivity().getSupportFragmentManager());
        ParentPager         = parentMain.findViewById(R.id.PagerParent);
        til_nomor_rumah     = view.findViewById(R.id.til_nomor_rumah);
        til_nomor_ponsel    = view.findViewById(R.id.til_nomor_ponsel);
        til_alamat_rumah    = view.findViewById(R.id.til_alamat_rumah);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentPager.setCurrentItem(getItem(-1),true);
            }
        });
        MySupportMapFragment mapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapKontak);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            mapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });
        }
        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[2].setBackgroundResource(R.drawable.selected_item);

        mApiInterface   = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,"student_id");
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,"student_nik");
        school_id     = sharedpreferences.getString(TAG_SCHOOL_ID,"school_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        email         = sharedpreferences.getString(TAG_EMAIL,"email");
        childrenname  = sharedpreferences.getString(TAG_NAMA_ANAK,"childrenname");
        school_name   = sharedpreferences.getString(TAG_NAMA_SEKOLAH,"school_name");
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,"school_code");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");

        sharedviewpager     = getActivity().getSharedPreferences(my_shared_viewpager, Context.MODE_PRIVATE);
        namaparent          = sharedviewpager.getString(TAG_PARENT_NAME,"nama_parent");
        emailparent         = sharedviewpager.getString(TAG_EMAIL_PARENT,"email_parent");
        nikparent           = sharedviewpager.getString(TAG_NIK_PARENT,"nik_parent");
        tempatlahir         = sharedviewpager.getString(TAG_TEMPAT_LAHIR,"tempat_lahir");
        tanggallahir        = sharedviewpager.getString(TAG_TANGGAL_LAHIR,"tanggal_lahir");
        hubungan            = sharedviewpager.getString(TAG_HUBUNGAN,"hubungan");
        kewarganegaraan     = sharedviewpager.getString(TAG_KEWARGANEGARAAN,"type_warga");


        arros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent,1);
            }
        });
        namaalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent,1);
            }
        });
        data_parent_student_get();
        return view;
    }

    private int getItem(int i) {
        return ParentPager.getCurrentItem() + i;
    }

    public void submitForm() {
        if (!validateNomorRumah()) {
            return;
        }
        if (!validateNomorPonsel()) {
            return;
        }
        if (!validateAlamatRumah()) {
            return;
        }else {
            SharedPreferences.Editor editor = sharedviewpager.edit();
            editor.putBoolean(session_status, true);
            editor.putString(TAG_NOMOR_RUMAH,  Nomorrumah.getText().toString()) ;
            editor.putString(TAG_NOMOR_PONSEL, Nomorponsel.getText().toString());
            editor.putString(TAG_LATITUDE_RUMAH,String.valueOf(latitude_parent));
            editor.putString(TAG_LONGITUDE_RUMAH, String.valueOf(longitude_parent));
            editor.putString(TAG_ALAMAT_RUMAH,  alamatrumah.getText().toString());
            editor.apply();
            PekerjaanFragment pekerjaanFragment = new PekerjaanFragment();
            Bundle Dataparent = new Bundle();
            Dataparent.putString(TAG_NOMOR_RUMAH,Nomorrumah.getText().toString());
            Dataparent.putString(TAG_NOMOR_PONSEL,Nomorponsel.getText().toString());
            Dataparent.putString(TAG_ALAMAT_RUMAH,alamatrumah.getText().toString());
            Dataparent.putString(TAG_LATITUDE_RUMAH,String.valueOf(latitude_parent));
            Dataparent.putString(TAG_LONGITUDE_RUMAH,String.valueOf(longitude_parent));
            pekerjaanFragment.setArguments(Dataparent);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragPekerjaan,pekerjaanFragment);
            fragmentTransaction.commit();
//            Toast.makeText(getApplicationContext(), Nomorrumah.getText().toString() + Nomorponsel.getText().toString() + latitude_parent + longitude_parent, Toast.LENGTH_LONG).show();
            ParentPager.setCurrentItem(getItem(+1), true);
        }
    }
    private boolean validateNomorRumah() {
        if (Nomorrumah.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(Nomorrumah);
            return false;
        } else {
            til_nomor_rumah.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateNomorPonsel() {
        if (Nomorponsel.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(Nomorponsel);
            return false;
        } else {
            til_nomor_ponsel.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateAlamatRumah() {
        if (alamatrumah.getText().toString().trim().isEmpty()) {
            til_alamat_rumah.setVisibility(View.VISIBLE);
            return false;
        } else {
            til_alamat_rumah.setVisibility(View.GONE);
        }

        return true;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;



        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mmap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mmap.setMyLocationEnabled(true);
        }
        if (mcurrLocationMarker != null) {
            mcurrLocationMarker.remove();
        }
        mmap.setOnCameraIdleListener(this);
        mmap.setOnCameraMoveStartedListener(this);
        mmap.setOnCameraMoveListener(this);
        mmap.setOnCameraMoveCanceledListener(this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        if (mcurrLocationMarker != null) {
            mcurrLocationMarker.remove();

        }



        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }

//        updateLocation(location);
//        getAddress();

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
    public void onCameraMoveStarted(int i) {
        CameraPosition position = mmap.getCameraPosition();
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=mmap.getCameraPosition();
        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map))
                .title("im here");

        if(mcurrLocationMarker!= null){
            mcurrLocationMarker.remove();}

        mcurrLocationMarker = mmap.addMarker(options);
    }

    @Override
    public void onCameraMoveCanceled() {
        CameraPosition position=mmap.getCameraPosition();
    }

    @Override
    public void onCameraIdle() {
        CameraPosition position=mmap.getCameraPosition();
        LatLng latLng = mmap.getCameraPosition().target;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String address = addressList.get(0).getAddressLine(0);
                String number = addressList.get(0).getFeatureName();
                String city = addressList.get(0).getLocality();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
                alamatrumah.setText(address +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude_parent = latLng.latitude;
        longitude_parent = latLng.longitude;

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
                mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(mcurrLocationMarker!= null){
                    mcurrLocationMarker.remove();}
                     mcurrLocationMarker = mmap.addMarker(markerOptions);
                alamatrumah.setText(strEditText);
                CurrentLatitude     = lati;
                CurrentLongitude    = longi;
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

    public void data_parent_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.Data_parent_student> call = mApiInterface.data_parent_student_get(authorization, school_code.toLowerCase(), parent_nik, student_id);
        call.enqueue(new Callback<JSONResponse.Data_parent_student>() {
            @Override
            public void onResponse(Call<JSONResponse.Data_parent_student> call, Response<JSONResponse.Data_parent_student> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();
                if (response.isSuccessful()) {
                    JSONResponse.Data_parent_student resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String DPG_SCS_0001 = getResources().getString(R.string.DPG_SCS_0001);
                    String DPG_ERR_0001 = getResources().getString(R.string.DPG_ERR_0001);
                    String DPG_ERR_0002 = getResources().getString(R.string.DPG_ERR_0002);
                    String DPG_ERR_0003 = getResources().getString(R.string.DPG_ERR_0003);

                    if (status == 1 && code.equals("DPG_SCS_0001")) {
                        nomorrumah = response.body().data.getParent_home_phone();
                        nomorponsel = response.body().data.getParent_phone();
                        latitude_parent = Double.parseDouble(response.body().data.getParent_latitude());
                        longitude_parent = Double.parseDouble(response.body().data.getParent_longitude());
                        Nomorrumah.setText(nomorrumah);
                        Nomorponsel.setText(nomorponsel);
                        final LatLng latLng = new LatLng(latitude_parent, longitude_parent);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

                        final MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Lokasi Rumah");
                        markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));
                        mcurrLocationMarker = mmap.addMarker(markerOptions);
                        //move map camera
                        mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

                    } else {
                        if (status == 0 && code.equals("DPG_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("DPG_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("DPG_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), DPG_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                    }
                }else if (response.code() == 500){
                    FancyToast.makeText(getApplicationContext(),"Sedang perbaikan",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.Data_parent_student> call, Throwable t) {
                hideDialog();
                Log.i("onfailure",t.toString());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
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
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void send_data(){
        PekerjaanFragment pekerjaanFragment = new PekerjaanFragment();
        Bundle Dataparent = new Bundle();
        Dataparent.putString("nomor_rumah",Nomorrumah.getText().toString());
        Dataparent.putString("nomor_ponsel",Nomorrumah.getText().toString());
        Dataparent.putString("alamat_rumah",alamatrumah.getText().toString());
        Dataparent.putDouble("parent_latitude",latitude_parent);
        Dataparent.putDouble("parent_longitude",longitude_parent);
        pekerjaanFragment.setArguments(Dataparent);
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
}