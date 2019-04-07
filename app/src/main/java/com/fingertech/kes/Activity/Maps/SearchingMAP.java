package com.fingertech.kes.Activity.Maps;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.etiennelawlor.discreteslider.library.utilities.DisplayUtility;
import com.fingertech.kes.Activity.Adapter.CustomInfoWindowAdapter;
import com.fingertech.kes.Activity.Adapter.InfoWindowAdapter;
import com.fingertech.kes.Activity.Adapter.SearchMapAdapter;
import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.Model.SquareFloatButton;
import com.fingertech.kes.Activity.Search.FilterActivity;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Search.LokasiAnda;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
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
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.pepperonas.materialdialog.MaterialDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchingMAP extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnMapClickListener {

    private GoogleMap mmap;
    private LocationRequest mlocationRequest;
    private Marker mcurrLocationMarker;
    private Location mlastLocation;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<JSONResponse.SData> arraylist;
    private SearchMapAdapter searchMapAdapter;
    private ProgressDialog dialog;
    Marker m;
    GoogleApiClient mGoogleApiClient;
    Auth mApiInterface;
    Double currentLatitude,latitudeF,currentLongitude,longitudeF,Jarak;
    String location,key = "",code;
    int status,Zoom;
    DiscreteSlider discreteSlider;
    RelativeLayout tickMarkLabelsRelativeLayout;
    Button Kelurahan;

    ImageView bookmark;
    Toolbar ToolBarAtas2;

    double lat,lng;
    String placeName,vicinity,schooldetailid,akreditasi,member_id;
    MaterialSearchView materialSearchView;
    LinearLayout loc,search;

    SquareFloatButton iv_filter;

    private String[] ITEMS = new String[]{"Semua", "SD", "SMP", "SMA", "SMK"};

    String pilihan;
    int posisi = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSearch);
        mapFragment.getMapAsync(this);

        discreteSlider               = findViewById(R.id.discrete_slider);
        tickMarkLabelsRelativeLayout = findViewById(R.id.tick_mark_labels_rl);
        Kelurahan                    = findViewById(R.id.kelurahan);
        bookmark                     = findViewById(R.id.book);
        recyclerView                 = findViewById(R.id.recyclerView);
        ToolBarAtas2                 = findViewById(R.id.toolbar_satu);
        mApiInterface                = ApiClient.getClient().create(Auth.class);
        member_id                    = getIntent().getStringExtra("member_id");
        materialSearchView           = findViewById(R.id.search_view);
        loc                          = findViewById(R.id.loc);
        search                       = findViewById(R.id.searchh);
        iv_filter                    = findViewById(R.id.iv_filter);

        discreteSlider.setOnDiscreteSliderChangeListener(new DiscreteSlider.OnDiscreteSliderChangeListener() {
            @Override
            public void onPositionChanged(int position) {
                int childCount = tickMarkLabelsRelativeLayout.getChildCount();
                for(int i= 0; i<childCount; i++){
                    TextView tv = (TextView) tickMarkLabelsRelativeLayout.getChildAt(i);
                    if(i == position) {
                        if (i == 0) {
                            Jarak = 2.5;
                            Zoom = 14;
                        }else if(i == 1){
                            Jarak = 5.0;
                            Zoom = 13;
                        }else if(i== 2){
                            Jarak = 7.5;
                            Zoom = 12;
                        }else if (i==3){
                            Jarak = 10.0;
                            Zoom = 11;
                        }
                        mmap.clear();
                        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (pilihan!=null){
                            if (pilihan.equals("Semua")){
                                dapat_map();
                            }else {
                                dapat_filter();
                            }
                        }else {
                            dapat_map();
                        }
                        mmap.animateCamera(CameraUpdateFactory.zoomTo(Zoom));
                    }else{
                        tv.setTextColor(getResources().getColor(R.color.grey_400));
                    }
                }
            }
        });

        tickMarkLabelsRelativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tickMarkLabelsRelativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                addTickMarkTextLabels();
            }
        });

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
//        search_school_post(key);
        hideKeyboard(this);
        recyclerView.setVisibility(View.GONE);
        tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
        discreteSlider.setVisibility(View.VISIBLE);

        bookmark.setOnClickListener(v -> {
            Intent mIntent = new Intent(SearchingMAP.this,FilterActivity.class);
            startActivityForResult(mIntent,1);
        });
        setSupportActionBar(ToolBarAtas2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Kelurahan.setOnClickListener(v -> {
            Intent mIntent = new Intent(SearchingMAP.this,LokasiAnda.class);
            startActivityForResult(mIntent,2);
        });


        materialSearchView.setVoiceSearch(true);
        materialSearchView.setCursorDrawable(R.drawable.color_cursor_white);
        

        materialSearchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search_school_post(query);
                tickMarkLabelsRelativeLayout.setVisibility(View.GONE);
                discreteSlider.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                search_school_post(newText);
                tickMarkLabelsRelativeLayout.setVisibility(View.GONE);
                discreteSlider.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //Do some magic
                return false;
            }
        });
        materialSearchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
                tickMarkLabelsRelativeLayout.setVisibility(View.GONE);
                discreteSlider.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                loc.setVisibility(View.GONE);
                search.setVisibility(View.GONE);

            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
                discreteSlider.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                loc.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
            }
        });

        iv_filter.setOnClickListener(v -> {
            pilihan();
        });
    }

    private  void pilihan(){
        pilihan = ITEMS[posisi].toString();
        new MaterialDialog.Builder(this,R.style.DialogAlert)
                .title("Filter Sekolah")
                .message(null)
                .positiveText("OK")
                .negativeText("CANCEL")
                .positiveColor(R.color.colorPrimary)
                .negativeColor(R.color.pink_700)
                .listItemsSingleSelection(true, ITEMS)
                .selection(posisi)
                .itemClickListener(new MaterialDialog.ItemClickListener() {
                    @Override
                    public void onClick(View v, int position, long id) {
                        super.onClick(v, position, id);
                        posisi = position;
                        pilihan = ITEMS[posisi].toString();
                    }
                })
                .showListener(new MaterialDialog.ShowListener() {
                    @Override
                    public void onShow(AlertDialog dialog) {
                        super.onShow(dialog);
                    }
                })
                .buttonCallback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        if (pilihan!=null){
                            if (pilihan.equals("Semua")){
                                dapat_map();
                            }else {
                                if (mmap!= null){
                                    mmap.clear();
                                }
                                dapat_filter();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(),"belum memilih",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        super.onNegative(dialog);
                        Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(1000);
        mlocationRequest.setFastestInterval(1000);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mlocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onBackPressed() {
        if (materialSearchView.isSearchOpen()) {
            materialSearchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;
        //Initialize Google Play Services
        try {
            // Customise map styling via JSON file
            boolean success = googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( this, R.raw.json_style));

            if (!success) {
                Log.e("KES", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("KES", "Can't find style. Error: ", e);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
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
        mmap.setOnMapClickListener(this);

        mmap.setOnInfoWindowClickListener(marker -> {
            InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
            String SchoolDetailId = infoWindowData.getSchooldetailid();
            Intent intent = new Intent(getBaseContext(),DetailSekolah.class);
            intent.putExtra("detailid",SchoolDetailId);
            intent.putExtra("member_id",member_id);
            startActivity(intent);
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        hideDialog();
        mlastLocation = location;
        if (mcurrLocationMarker != null) {
            mcurrLocationMarker.remove();

        }
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        currentLatitude     = latLng.latitude;
        currentLongitude    = latLng.longitude;
        Jarak = 2.5;
        dapat_map();
        //Place current location marker

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Lokasi Anda");
        markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_map));

        //move map camera
        mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mcurrLocationMarker = mmap.addMarker(markerOptions);
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String address      = addressList.get(0).getAddressLine(0);
                String number       = addressList.get(0).getFeatureName();
                String city         = addressList.get(0).getSubLocality();
                String state        = addressList.get(0).getAdminArea();
                String country      = addressList.get(0).getCountryName();
                String postalCode   = addressList.get(0).getPostalCode();
                Kelurahan.setText(city +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        updateLocation(location);
        getAddress();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    void getAddress() {

        try {

            Geocoder gcd = new Geocoder(this
                    , Locale.getDefault());

            List<Address> addresses = gcd.getFromLocation(currentLatitude,

                    currentLongitude, 100);

            StringBuilder result = new StringBuilder();



            if (addresses.size() > 0) {



                Address address = addresses.get(1);

                int maxIndex = address.getMaxAddressLineIndex();

                for (int x = 0; x <= maxIndex; x++) {

                    result.append(address.getAddressLine(x));

                    result.append(",");

                }



            }

            location = result.toString();

        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(),

                    Toast.LENGTH_LONG).show();



        }

    }

    void updateLocation(Location location) {

        mlastLocation = location;

        currentLatitude = mlastLocation.getLatitude();

        currentLongitude = mlastLocation.getLongitude();



    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void addTickMarkTextLabels(){
        int tickMarkCount = discreteSlider.getTickMarkCount();
        float tickMarkRadius = discreteSlider.getTickMarkRadius();
        int width = tickMarkLabelsRelativeLayout.getMeasuredWidth();

        int discreteSliderBackdropLeftMargin = DisplayUtility.dp2px(this, 32);
        int discreteSliderBackdropRightMargin = DisplayUtility.dp2px(this, 32);
        float firstTickMarkRadius = tickMarkRadius;
        float lastTickMarkRadius = tickMarkRadius;
        int interval = (width - (discreteSliderBackdropLeftMargin+discreteSliderBackdropRightMargin) - ((int)(firstTickMarkRadius+lastTickMarkRadius)) )
                / (tickMarkCount-1);

        String[] tickMarkLabels = {"2.5 km", "5.0 km", "7.5 km", "10.0 km"};
        int tickMarkLabelWidth = DisplayUtility.dp2px(this, 40);

        for(int i=0; i<tickMarkCount; i++) {
            TextView tv = new TextView(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    tickMarkLabelWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);

            tv.setText(tickMarkLabels[i]);
            tv.setGravity(Gravity.CENTER);
            if(i==discreteSlider.getPosition()) {
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            }else {
                tv.setTextColor(getResources().getColor(R.color.grey_400));
//                    tv.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
            }
            int left = discreteSliderBackdropLeftMargin + (int)firstTickMarkRadius + (i * interval) - (tickMarkLabelWidth/2);

            layoutParams.setMargins(left,
                    0,
                    0,
                    0);
            tv.setLayoutParams(layoutParams);

            tickMarkLabelsRelativeLayout.addView(tv);
        }
    }

    public void dapat_map(){
        progressBar();
        showDialog();
        Call<JSONResponse.Nearby_School> call = mApiInterface.nearby_radius_post(currentLatitude,currentLongitude,Jarak);
        call.enqueue(new Callback<JSONResponse.Nearby_School>() {
            @Override
            public void onResponse(Call<JSONResponse.Nearby_School> call, final Response<JSONResponse.Nearby_School> response) {
                hideDialog();
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.Nearby_School resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    String NR_SCS_0001 = getResources().getString(R.string.NR_SCS_0001);
                    String NR_ERR_0001 = getResources().getString(R.string.NR_ERR_0001);
                    String NR_ERR_0002 = getResources().getString(R.string.NR_ERR_0002);
                    String NR_ERR_0003 = getResources().getString(R.string.NR_ERR_0003);
                    String NR_ERR_0004 = getResources().getString(R.string.NR_ERR_0004);

                    ItemSekolah Item = null;

                    if (status == 1 && code.equals("NR_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            double lat = response.body().getData().get(i).getLatitude();
                            double lng = response.body().getData().get(i).getLongitude();
                            final String placeName = response.body().getData().get(i).getSchool_name();
                            final String vicinity = response.body().getData().get(i).getSchool_address();
                            final String akreditasi = response.body().getData().get(i).getAkreditasi();
                            final double Jarak = response.body().getData().get(i).getDistance();
                            final String schooldetailid = response.body().getData().get(i).getSchooldetailid();

                            LatLng latLng = new LatLng(lat, lng);
                            if (response.body().getData().get(i).getJenjang_pendidikan().equals("SD")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);

                            } else if (response.body().getData().get(i).getJenjang_pendidikan().equals("SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            } else if (response.body().getData().get(i).getJenjang_pendidikan().equals("SPK SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            } else {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            }

                            InfoWindowData info = new InfoWindowData();
                            info.setNama(placeName);
                            info.setAlamat(vicinity);
                            info.setSchooldetailid(schooldetailid);
                            info.setAkreditasi(akreditasi);
                            info.setJarak(Jarak);
                            CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(SearchingMAP.this);
                            mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                            m.setTag(info);
                        }

                    } else {
                        if (status == 0 && code.equals("NR_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Nearby_School> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
    }

    public void search_school_post(final String key){

        Call<JSONResponse.School> postCall = mApiInterface.search_school_post(key);
        postCall.enqueue(new Callback<JSONResponse.School>() {
            @Override
            public void onResponse(Call<JSONResponse.School> call, final Response<JSONResponse.School> response) {
                Log.d("TAG",response.code()+"");
                if (response.isSuccessful()) {
                    JSONResponse.School resource = response.body();
                    status = resource.status;
                    code = resource.code;

                    if (status == 1 && code.equals("SS_SCS_0001")) {
                        arraylist = response.body().getData();
                        searchMapAdapter = new SearchMapAdapter(arraylist, SearchingMAP.this);
                        recyclerView.setAdapter(searchMapAdapter);
                        searchMapAdapter.notifyDataSetChanged();
                        searchMapAdapter.getFilter(key).filter(key);
//                    searchMapAdapter.setFilter(arraylist,key);
                        searchMapAdapter.setOnItemClickListener((view, position) -> {
                            if (mmap != null) {
                                mmap.clear();
                            }

                            materialSearchView.clearFocus();
                            latitudeF = response.body().getData().get(position).getLatitude();
                            longitudeF = response.body().getData().get(position).getLongitude();
                            schooldetailid = response.body().getData().get(position).getSchooldetailid();
                            placeName = response.body().getData().get(position).getSchool_name();
                            vicinity = response.body().getData().get(position).getSchool_address();
                            akreditasi = response.body().getData().get(position).getAkreditasi();
                            final LatLng latLng = new LatLng(latitudeF, longitudeF);
                            hideKeyboard(SearchingMAP.this);
                            if (response.body().getData().get(position).getJenjang_pendidikan().equals("SD")) {
                                MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);

                            } else if (response.body().getData().get(position).getJenjang_pendidikan().equals("SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            } else if (response.body().getData().get(position).getJenjang_pendidikan().equals("SPK SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            } else {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            }
                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
                            mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            mmap.animateCamera(CameraUpdateFactory.zoomTo(15));


                            InfoWindowData info = new InfoWindowData();
                            info.setNama(placeName);
                            info.setAlamat(vicinity);
                            info.setSchooldetailid(schooldetailid);
                            info.setAkreditasi(akreditasi);
                            info.setJarak(distance(currentLatitude, currentLongitude, latitudeF, longitudeF));
                            CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(SearchingMAP.this);
                            mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                            m.setTag(info);
                            tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
                            discreteSlider.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        });

                    } else {
                        if (status == 0 && code.equals("SS_ERR_0001")) {
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.School> call, Throwable t) {
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
        if (dialog==null){

        }else {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog.setContentView(R.layout.progressbar);
        }
        }
    public void progressBar(){
        dialog = new ProgressDialog(this);
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
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        recyclerView.setVisibility(View.GONE);
        tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
        discreteSlider.setVisibility(View.VISIBLE);
        hideKeyboard(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                mmap.clear();
                double lati     = data.getDoubleExtra("latitude",0.0);
                double longi    = data.getDoubleExtra("longitude",0.0);
                String jenjang  = data.getStringExtra("jenjang");
                String schoolid = data.getStringExtra("schoolid");
                String namaSek     = data.getStringExtra("namasekolah");
                String alamat   = data.getStringExtra("alamat");
                currentLatitude = lati;
                currentLongitude = longi;
                Jarak = 2.5;
                dapat_map();
                Kelurahan.setText(alamat);
                final LatLng latLng1 = new LatLng(lati,longi);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng1.latitude, latLng1.longitude)).zoom(16).build();
                InfoWindowData indo = new InfoWindowData();
                indo.setNama(namaSek);
                indo.setAlamat(alamat);
                indo.setSchooldetailid(schoolid);

                if (jenjang.equals("sd") || jenjang.equals("BPK SD")){

                    final MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng1);
                    markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));
                    //move map camera
                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    m = mmap.addMarker(markerOptions);
                    InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                    mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                    m.setTag(indo);

                }else if(jenjang.equals("smp") || jenjang.equals("BPK SMP")){
                    final MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng1);
                    markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                    //move map camera
                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    m = mmap.addMarker(markerOptions);
                    InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                    mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                    m.setTag(indo);

                }else if(jenjang.equals("smk")){
                    final MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng1);
                    markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                    //move map camera
                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    m = mmap.addMarker(markerOptions);
                    InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                    mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                    m.setTag(indo);
                }else {
                    final MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng1);
                    markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                    //move map camera
                    mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    m = mmap.addMarker(markerOptions);
                    InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                    mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                    m.setTag(indo);
                }
                hideKeyboard(this);
                recyclerView.setVisibility(View.GONE);
            }
        }else if (requestCode == 2){
            if(resultCode == RESULT_OK) {
                mmap.clear();
                String address  = data.getStringExtra("address");
                double lati     = data.getDoubleExtra("latitude",0.0);
                double longi    = data.getDoubleExtra("longitude",0.0);
                currentLatitude = lati;
                currentLongitude = longi;
                Jarak = 2.5;
                Kelurahan.setText(address);
                dapat_map();
                final LatLng latLng1 = new LatLng(lati,longi);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng1.latitude, latLng1.longitude)).zoom(16).build();

                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng1);
                markerOptions.title("Current Position");
                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_map));
                //move map camera
                mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

                if(mcurrLocationMarker!= null){
                    mcurrLocationMarker.remove();}
                mcurrLocationMarker = mmap.addMarker(markerOptions);
                hideKeyboard(this);

            }
        }else if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    materialSearchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
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
        getMenuInflater().inflate(R.menu.menu_map, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        materialSearchView.setMenuItem(item);

        return true;
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public void dapat_filter(){
        progressBar();
        showDialog();
        Call<JSONResponse.Nearby_School> call = mApiInterface.nearby_radius_post(currentLatitude,currentLongitude,Jarak);
        call.enqueue(new Callback<JSONResponse.Nearby_School>() {
            @Override
            public void onResponse(Call<JSONResponse.Nearby_School> call, final Response<JSONResponse.Nearby_School> response) {
                hideDialog();
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.Nearby_School resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    String NR_SCS_0001 = getResources().getString(R.string.NR_SCS_0001);
                    String NR_ERR_0001 = getResources().getString(R.string.NR_ERR_0001);
                    String NR_ERR_0002 = getResources().getString(R.string.NR_ERR_0002);
                    String NR_ERR_0003 = getResources().getString(R.string.NR_ERR_0003);
                    String NR_ERR_0004 = getResources().getString(R.string.NR_ERR_0004);

                    ItemSekolah Item = null;

                    if (status == 1 && code.equals("NR_SCS_0001")) {
                        for (int i = 0; i < response.body().getData().size(); i++) {

                            double lat = response.body().getData().get(i).getLatitude();
                            double lng = response.body().getData().get(i).getLongitude();
                            final String placeName = response.body().getData().get(i).getSchool_name();
                            final String vicinity = response.body().getData().get(i).getSchool_address();
                            final String akreditasi = response.body().getData().get(i).getAkreditasi();
                            final double Jarak = response.body().getData().get(i).getDistance();
                            final String schooldetailid = response.body().getData().get(i).getSchooldetailid();

                            LatLng latLng = new LatLng(lat, lng);

                            final LatLng latLngs = new LatLng(currentLatitude, currentLongitude);
                            //Place current location marker

                            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLngs.latitude, latLngs.longitude)).zoom(16).build();

                            final MarkerOptions markerOptionss = new MarkerOptions();
                            markerOptionss.position(latLngs);
                            markerOptionss.title("Lokasi Anda");
                            markerOptionss.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_map));
                            mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                            mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                            mcurrLocationMarker = mmap.addMarker(markerOptionss);

                            switch (pilihan) {
                                case "SD":
                                    if (response.body().getData().get(i).getJenjang_pendidikan().equals("SD")) {
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        // Position of Marker on Map
                                        markerOptions.position(latLng);
                                        // Adding colour to the marker
                                        markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));
                                        // Adding Marker to the Camera.
                                        m = mmap.addMarker(markerOptions);
                                    }
                                    break;
                                case "SMP":
                                    if (response.body().getData().get(i).getJenjang_pendidikan().equals("SMP")) {
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        // Position of Marker on Map
                                        markerOptions.position(latLng);
                                        // Adding colour to the marker
                                        markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                        // Adding Marker to the Camera.
                                        m = mmap.addMarker(markerOptions);
                                    }
                                    break;
                                case "SMA":
                                    if (response.body().getData().get(i).getJenjang_pendidikan().equals("SMA")) {
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        // Position of Marker on Map
                                        markerOptions.position(latLng);
                                        // Adding colour to the marker
                                        markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                                        // Adding Marker to the Camera.
                                        m = mmap.addMarker(markerOptions);
                                    }
                                    break;
                                case "SMK":
                                    if (response.body().getData().get(i).getJenjang_pendidikan().equals("SMK")) {
                                        MarkerOptions markerOptions = new MarkerOptions();
                                        // Position of Marker on Map
                                        markerOptions.position(latLng);
                                        // Adding colour to the marker
                                        markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                                        // Adding Marker to the Camera.
                                        m = mmap.addMarker(markerOptions);
                                    }
                                    break;
                            }
                            InfoWindowData info = new InfoWindowData();
                            info.setNama(placeName);
                            info.setAlamat(vicinity);
                            info.setSchooldetailid(schooldetailid);
                            info.setAkreditasi(akreditasi);
                            info.setJarak(Jarak);
                            CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(SearchingMAP.this);
                            mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                            m.setTag(info);

                        }

                    } else {
                        if (status == 0 && code.equals("NR_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0002")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0002, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0003")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0003, Toast.LENGTH_LONG).show();
                        }
                        if (status == 0 && code.equals("NR_ERR_0004")) {
                            Toast.makeText(getApplicationContext(), NR_ERR_0004, Toast.LENGTH_LONG).show();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Nearby_School> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
    }



}


