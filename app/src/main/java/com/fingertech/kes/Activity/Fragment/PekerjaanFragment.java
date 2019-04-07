package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
//import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.CustomView.MySupportMapFragment;
import com.fingertech.kes.Activity.KodeAksesAnak;
import com.fingertech.kes.Activity.Masuk;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.ParentMain;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.common.GoogleApiAvailability;
import com.rey.material.widget.Spinner;
import com.fingertech.kes.Activity.Maps.full_maps;
import com.fingertech.kes.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.location.LocationListener;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;

public class PekerjaanFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private Marker mCurrLocationMarker;
    private TextView namakerja;

    private LocationRequest mlocationRequest;
    private Location mlastLocation;
    private ImageView arro;

    GoogleApiClient mGoogleApiClient;
    EditText Jabatan,Namaperusahaan;
    double CurrentLatitude;
    double CurrentLongitude;
    TextView alamatkerja;
    String location,pendidikan,namaperusahaan,jabatan,penghasilan,employment,gaji,studentparentid;
    Spinner et_pekerjaan,et_penghasilan;
    private String[] pedapatan = {"Penghasilan","< 1 juta","1 - 2 juta","3 - 5 juta","5 - 8 juta","8 - 15 juta","15 - 25 juta","25 - 40 juta","40 - 60 juta","60 - 100 juta","> 100 juta"};
    private String[] education = {
            "Pendidikan",
            "SD",
            "SMP",
            "SMA",
            "SMU",
            "D3",
            "S1",
            "S2",
            "S3"};
    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;
    Integer status;
    String code;
    double latitude_kerja,longitude_kerja;
    String latitude_parent,longitude_parent;

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
    public static final String TAG_PARENT_NAME       = "nama_parent";
    public static final String TAG_NIK_PARENT        = "nik_parent";
    public static final String TAG_EMAIL_PARENT      = "email_parent";
    public static final String TAG_TEMPAT_LAHIR      = "tempat_lahir";
    public static final String TAG_TANGGAL_LAHIR     = "tanggal_lahir";
    public static final String TAG_HUBUNGAN          = "hubungan";
    public static final String TAG_KEWARGANEGARAAN   = "kewarganegaraan";
    public static final String my_shared_viewpager   = "my_shared_viewpager";


    public static final String TAG_NOMOR_RUMAH       = "nomor_rumah";
    public static final String TAG_NOMOR_PONSEL      = "nomor_ponsel";
    public static final String TAG_ALAMAT_RUMAH      = "alamat_rumah";
    public static final String TAG_LATITUDE_RUMAH    = "latitude_rumah";
    public static final String TAG_LONGITUDE_RUMAH   = "longitude_rumah";

    String authorization;

    ProgressDialog dialog;
    SharedPreferences sharedviewpager;

    String namaparent,emailparent,nikparent,hubungan,tempatlahir,tanggallahir,kewarganegaraan,nomorrumah,nomorponsel,alamatrumah;

    public static PekerjaanFragment newInstance() {
        // Required empty public constructor
        PekerjaanFragment Fragment = new PekerjaanFragment();
        return Fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    Button next,back;
    ParentMain parentMain;
    private ViewPager ParentPager;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private ParentMain.FragmentAdapter fragmentAdapter;
    TextInputLayout til_nama_perusahaan,til_jabatan;
    TextView til_pendidikan,til_penghasilan,til_alamatkerja;
    NestedScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pekerjaan, container, false);

        arro                = view.findViewById(R.id.arrow);
        Jabatan             = view.findViewById(R.id.et_jabatan);
        Namaperusahaan      = view.findViewById(R.id.et_nama_perusahaan);
        namakerja           = view.findViewById(R.id.nama_kerja);
        et_pekerjaan        = view.findViewById(R.id.sp_pekerjaan);
        et_penghasilan      = view.findViewById(R.id.sp_penghasilan);
        alamatkerja         = view.findViewById(R.id.alamat_kerja);
        parentMain          = (ParentMain) getActivity();
        indicator           = view.findViewById(R.id.indicators);
        back                = view.findViewById(R.id.btn_kembali);
        next                = view.findViewById(R.id.btn_berikut);
        fragmentAdapter     = new ParentMain.FragmentAdapter(getActivity().getSupportFragmentManager());
        ParentPager         = parentMain.findViewById(R.id.PagerParent);
        til_nama_perusahaan = view.findViewById(R.id.til_nama_perusahaan);
        til_jabatan         = view.findViewById(R.id.til_jabatan);
        til_pendidikan      = view.findViewById(R.id.til_pendidikan);
        til_penghasilan     = view.findViewById(R.id.til_penghasilan);
        til_alamatkerja     = view.findViewById(R.id.til_alamat_kerja);
        scrollView          = view.findViewById(R.id.scroll_view);

        MySupportMapFragment mapFragment = (MySupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapKerja);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            mapFragment.setListener(new MySupportMapFragment.OnTouchListener() {
                @Override
                public void onTouch() {
                    scrollView.requestDisallowInterceptTouchEvent(true);
                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
        });
        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[3].setBackgroundResource(R.drawable.selected_item);


        arro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent, 1);
            }
        });

        namakerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent, 1);
            }
        });
        mApiInterface = ApiClient.getClient().create(Auth.class);

        sharedpreferences = getActivity().getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN, "token");
        parent_id = sharedpreferences.getString(TAG_MEMBER_ID, "member_id");
        student_id = sharedpreferences.getString(TAG_STUDENT_ID, "student_id");
        student_nik = sharedpreferences.getString(TAG_STUDENT_NIK, "student_nik");
        school_id = sharedpreferences.getString(TAG_SCHOOL_ID, "school_id");
        fullname = sharedpreferences.getString(TAG_FULLNAME, "fullname");
        email = sharedpreferences.getString(TAG_EMAIL, "email");
        childrenname = sharedpreferences.getString(TAG_NAMA_ANAK, "childrenname");
        school_name = sharedpreferences.getString(TAG_NAMA_SEKOLAH, "school_name");
        school_code = sharedpreferences.getString(TAG_SCHOOL_CODE, "school_code");
        parent_nik = sharedpreferences.getString(TAG_PARENT_NIK, "parent_nik");

        sharedviewpager = getActivity().getSharedPreferences(my_shared_viewpager, Context.MODE_PRIVATE);
        namaparent = sharedviewpager.getString(TAG_PARENT_NAME, "nama_parent");
        emailparent = sharedviewpager.getString(TAG_EMAIL_PARENT, "email_parent");
        nikparent = sharedviewpager.getString(TAG_NIK_PARENT, "nik_parent");
        tempatlahir = sharedviewpager.getString(TAG_TEMPAT_LAHIR, "tempat_lahir");
        tanggallahir = sharedviewpager.getString(TAG_TANGGAL_LAHIR, "tanggal_lahir");
        hubungan = sharedviewpager.getString(TAG_HUBUNGAN, "hubungan");
        kewarganegaraan = sharedviewpager.getString(TAG_KEWARGANEGARAAN, "type_warga");
        nomorrumah = sharedviewpager.getString(TAG_NOMOR_RUMAH, "nomor_rumah");
        nomorponsel = sharedviewpager.getString(TAG_NOMOR_PONSEL, "nomor_ponsel");
        latitude_parent = sharedviewpager.getString(TAG_LATITUDE_RUMAH, "latitude_rumah");
        longitude_parent = sharedviewpager.getString(TAG_LONGITUDE_RUMAH, "longitude_rumah");
        alamatrumah = sharedviewpager.getString(TAG_ALAMAT_RUMAH, "alamat_rumah");


        data_parent_student_get();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
//
//                Toast.makeText(getApplicationContext(), nomorrumah + "/" + nomorponsel + "/" + alamatrumah + "/" + latitude_parent + "/" + longitude_parent, Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }
//        if (!isGooglePlayServicesAvailable()) {
//            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
//            parentMain.finish();
//        }
//        else {
//            Log.d("onCreate", "Google Play Services available. Continuing.");
//        }
//        return view;
//
//    }
//    private boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
//        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
//        if(result != ConnectionResult.SUCCESS) {
//            if(googleAPI.isUserResolvableError(result)) {
//                googleAPI.getErrorDialog(getActivity(), result,
//                        0).show();
//            }
//            return false;
//        }
//        return true;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
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


    @Override
    public void onCameraIdle() {
        LatLng LatLng = mMap.getCameraPosition().target;
        Geocoder geocode1 = new Geocoder(getContext());
        try {
            List<Address> addressList = geocode1.getFromLocation(LatLng.latitude, LatLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String address1 = addressList.get(0).getAddressLine(0);
                String number1 = addressList.get(0).getFeatureName();
                String city1 = addressList.get(0).getLocality();
                String state1 = addressList.get(0).getAdminArea();
                String country1 = addressList.get(0).getCountryName();
                String postalCode1 = addressList.get(0).getPostalCode();
                alamatkerja.setText(address1 +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude_kerja = LatLng.latitude;
        longitude_kerja = LatLng.longitude;
    }

    @Override
    public void onCameraMoveCanceled() {
        CameraPosition position=mMap.getCameraPosition();
        if(mCurrLocationMarker != null){
            mCurrLocationMarker.remove();}
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=mMap.getCameraPosition();
        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map))
                .title("im here");

        if(mCurrLocationMarker!= null){
           mCurrLocationMarker.remove();}
        mCurrLocationMarker = mMap.addMarker(options);

    }


    @Override
    public void onCameraMoveStarted(int i) {
        CameraPosition position=mMap.getCameraPosition();

    }


    @Override
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();

        }

        //Place current location marker

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
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

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
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(mCurrLocationMarker!= null){
                    mCurrLocationMarker.remove();}
                mCurrLocationMarker = mMap.addMarker(markerOptions);
                alamatkerja.setText(strEditText);
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
                        pendidikan = response.body().data.getParent_education();
                        namaperusahaan = response.body().data.getCompany_name();
                        jabatan = response.body().data.getEmployment();
                        penghasilan = response.body().data.getParent_income();
                        latitude_kerja = Double.parseDouble(response.body().data.getOffice_latitude());
                        longitude_kerja = Double.parseDouble(response.body().data.getOffice_longitude());
                        studentparentid = response.body().data.getStudentparentid();
                        Namaperusahaan.setText(namaperusahaan);
                        Jabatan.setText(jabatan);

                        final List<String> penghasil = new ArrayList<>(Arrays.asList(pedapatan));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, penghasil) {
                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        int posti = ArrayAdapter.getPosition(penghasilan);
                        et_penghasilan.setAdapter(ArrayAdapter);
                        et_penghasilan.setSelection(posti);
                        et_penghasilan.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                gaji = penghasil.get(position);
                            }
                        });
                        gaji = et_penghasilan.getSelectedItem().toString();

                        final List<String> plantsList = new ArrayList<>(Arrays.asList(education));
                        // Initializing an ArrayAdapter
                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                getActivity(), R.layout.spinner_text, plantsList) {
                            @Override
                            public boolean isEnabled(int position) {
                                return position != 0;
                            }

                            @Override
                            public View getDropDownView(int position, View convertView,
                                                        ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                if (position == 0) {
                                    // Set the hint text color gray
                                    tv.setTextColor(Color.GRAY);
                                } else {
                                    tv.setTextColor(Color.BLACK);
                                }
                                return view;
                            }
                        };
                        int posisi = spinnerArrayAdapter.getPosition(pendidikan);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        et_pekerjaan.setAdapter(spinnerArrayAdapter);
                        et_pekerjaan.setSelection(posisi);
                        et_pekerjaan.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                employment = plantsList.get(position);
                            }
                        });
                        final LatLng latLng = new LatLng(latitude_kerja, longitude_kerja);
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

                        final MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Lokasi Kerja");
                        markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));
                        mCurrLocationMarker = mMap.addMarker(markerOptions);
                        //move map camera
                        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));


                        employment = et_pekerjaan.getSelectedItem().toString();


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

    public void submitForm() {
        if (!validatePekerjaan()) {
            return;
        }
        if (!validateNamaPerusahaan()) {
            return;
        }
        if (!validateJabatan()) {
            return;
        }
        if (!validatePenghasilan()) {
            return;
        }if(!validateAlamatKerja()){
            return;
        }
        if(ParentPager.getCurrentItem() == 3){
            Jabatan.clearFocus();
            Namaperusahaan.clearFocus();
            update_parent();
        }
    }

    private boolean validateJabatan() {
        if (Jabatan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(Jabatan);
            return false;
        } else {
            til_jabatan.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateNamaPerusahaan() {
        if (Namaperusahaan.getText().toString().trim().isEmpty()) {
            Toast.makeText(getContext(),"Harap di isi data nya ",Toast.LENGTH_LONG).show();
            requestFocus(Namaperusahaan);
            return false;
        } else {
            til_nama_perusahaan.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAlamatKerja() {
        if (alamatkerja.getText().toString().trim().isEmpty()) {
            til_alamatkerja.setVisibility(View.VISIBLE);
            return false;
        } else {
            til_alamatkerja.setVisibility(View.GONE);
        }

        return true;
    }

    private boolean validatePenghasilan() {
        if (gaji == null) {
            til_penghasilan.setVisibility(View.VISIBLE);
            return false;
        } else {
            til_penghasilan.setVisibility(View.GONE);
        }

        return true;
    }

    private boolean validatePekerjaan() {
        if (employment == null) {
            til_pendidikan.setVisibility(View.VISIBLE);
            return false;
        } else {
            til_pendidikan.setVisibility(View.GONE);
        }

        return true;
    }

    public void update_parent(){
        progressBar();
        showDialog();
        Call<JSONResponse> postCall = mApiInterface.update_parent_put(authorization, studentparentid, school_code.toLowerCase(), student_id, namaparent, nikparent, hubungan, tanggallahir, tempatlahir, kewarganegaraan, nomorrumah, nomorponsel, alamatrumah,String.valueOf(latitude_parent),String.valueOf(longitude_parent), emailparent,et_pekerjaan.getSelectedItem().toString(),Namaperusahaan.getText().toString(),Jabatan.getText().toString(),et_penghasilan.getSelectedItem().toString(),alamatkerja.getText().toString(),String.valueOf(latitude_kerja),String.valueOf(longitude_kerja));
        postCall.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                hideDialog();
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    String UPA_SCS_0001 = getResources().getString(R.string.UPA_SCS_0001);
                    String UPA_ERR_0001 = getResources().getString(R.string.UPA_ERR_0001);
                    String UPA_ERR_0002 = getResources().getString(R.string.UPA_ERR_0002);
                    String UPA_ERR_0003 = getResources().getString(R.string.UPA_ERR_0003);
                    String UPA_ERR_0004 = getResources().getString(R.string.UPA_ERR_0004);
                    String UPA_ERR_0005 = getResources().getString(R.string.UPA_ERR_0005);
                    String UPA_ERR_0006 = getResources().getString(R.string.UPA_ERR_0006);
                    String UPA_ERR_0007 = getResources().getString(R.string.UPA_ERR_0007);
                    String UPA_ERR_0008 = getResources().getString(R.string.UPA_ERR_0008);
                    String UPA_ERR_0009 = getResources().getString(R.string.UPA_ERR_0009);
                    String UPA_ERR_0010 = getResources().getString(R.string.UPA_ERR_0010);
                    String UPA_ERR_0011 = getResources().getString(R.string.UPA_ERR_0011);
                    String UPA_ERR_0012 = getResources().getString(R.string.UPA_ERR_0012);
                    String UPA_ERR_0013 = getResources().getString(R.string.UPA_ERR_0013);
                    String UPA_ERR_0014 = getResources().getString(R.string.UPA_ERR_0014);
                    String UPA_ERR_0015 = getResources().getString(R.string.UPA_ERR_0015);
                    String UPA_ERR_0016 = getResources().getString(R.string.UPA_ERR_0016);
                    String UPA_ERR_0017 = getResources().getString(R.string.UPA_ERR_0017);
                    String UPA_ERR_0018 = getResources().getString(R.string.UPA_ERR_0018);
                    String UPA_ERR_0019 = getResources().getString(R.string.UPA_ERR_0019);
                    String UPA_ERR_0020 = getResources().getString(R.string.UPA_ERR_0020);
                    String UPA_ERR_0021 = getResources().getString(R.string.UPA_ERR_0021);

                    if (status == 1 && code.equals("UPA_SCS_0001")) {
                        Intent intent = new Intent(getContext(), AnakMain.class);
                        getContext().startActivity(intent);

                    } else if (status == 0 && equals("UPA_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0001, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0002")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0002, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0003")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0003, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0004")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0004, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0005")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0005, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0006")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0006, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0007")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0007, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0008")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0008, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0009")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0009, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0010")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0010, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0011")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0011, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0012")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0012, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0013")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0013, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0014")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0014, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0015")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0015, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0016")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0016, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0017")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0017, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0018")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0018, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0019")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0019, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0020")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0020, Toast.LENGTH_LONG).show();
                    } else if (status == 0 && equals("UPA_ERR_0021")) {
                        Toast.makeText(getApplicationContext(), UPA_ERR_0021, Toast.LENGTH_LONG).show();
                    }
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
