package com.fingertech.kes.Activity.Maps;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
//import android.widget.Spinner;
import android.widget.Toast;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.fingertech.kes.Activity.Adapter.CustomInfoWindowAdapter;
import com.fingertech.kes.Activity.Adapter.InfoWindowAdapter;
import com.fingertech.kes.Activity.CustomView.SnappyLinearLayoutManager;
import com.google.android.gms.maps.model.MapStyleOptions;
import  com.rey.material.widget.Spinner;
import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.Model.ClusterItemSekolah;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.CustomView.SnappyRecycleView;
import com.fingertech.kes.Activity.Adapter.ItemSekolahAdapter;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullMap extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener,ClusterManager.OnClusterClickListener<ClusterItemSekolah> {


    private List<ItemSekolah> ItemList;
    private ItemSekolahAdapter cUstomRecyclerViewDataAdapter = null;
    private GoogleMap mapF;
    private LocationRequest mlocationRequest;
    private Marker CurrlocationMarker,m,sd,smp,sma,c;
    private Location LastLocation;
    private Button lock,Nearby;
    private Boolean clicked = false;
    public SnappyRecycleView snappyrecyclerView;
    private String[] colors = {"#46bfee", "#7f31c7c5", "#7fff8a00"};
    private SlidingUpPanelLayout slidingLayout;
    ImageView imageView;
    private ClusterManager<ClusterItemSekolah> mClusterManager;
    private Cluster<ClusterItemSekolah> clusterItemSekolahCluster;
    private ClusterItemSekolah clusterItemSekolah;
    private RadioButton rb_sma,rb_smp,rb_smk,rb_sd;
    private List<JSONResponse.Prov> arrayList;
    private Button tampilProv;
    private MarkerOptions markerOptions;
    String provid;
    String jenjang;
    GoogleApiClient mGoogleApiClient;
    ArrayList<LatLng> markerPoints;
    Polyline lines;
    LatLngBounds.Builder builder;
    LatLng Lat;

    PolylineOptions lineOptions;
    Double PROXIMITY_RADIUS = 2.5;
    Double currentLatitudef,latitudef;
    Double currentLongitudef,longitudef;
    String locationf;
    String code;
    Spinner et_provinsi;
    String provi;
    Auth mApiInterface;
    int status;
    private ProgressDialog dialog;
    double lat,lng,Jarak;
    String placeName,vicinity,schooldetailid,akreditasi,member_id;
    LinearLayout drag;
    ImageView arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_map);
        mApiInterface = ApiClient.getClient().create(Auth.class);
        arrow   = findViewById(R.id.arrow);
        drag    = findViewById(R.id.dragView);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.full_maps);
        mapFragment.getMapAsync(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }
        findViewById(R.id.zoom_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onBackPressed();
            }
        });
        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mapF.clear();
                final LatLng lati = new LatLng(currentLatitudef, currentLongitudef);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lati.latitude, lati.longitude)).zoom(13).build();
                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(lati);
                markerOptions.title("Current Position");
                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_map));

                //move map camera
                mapF.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mapF.animateCamera(CameraUpdateFactory.zoomTo(14));
                CurrlocationMarker = mapF.addMarker(markerOptions);

                dapat_map();
                snappyrecyclerView.setVisibility(View.VISIBLE);
            }
        });
        //set layout slide listener
        slidingLayout = findViewById(R.id.sliding_layout);
        imageView = findViewById(R.id.arrowF);
        et_provinsi = findViewById(R.id.sp_provinsi);
        rb_sd = findViewById(R.id.sd);
        rb_smp = findViewById(R.id.smp);
        rb_sma = findViewById(R.id.sma);
        rb_smk = findViewById(R.id.smk);
        tampilProv = findViewById(R.id.TampilProv);
        member_id = getIntent().getStringExtra("member_id");

        dapat_provinsi();

        et_provinsi.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                provid= arrayList.get(position).getProvinsiid();
            }
        });


        rb_sd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenjang = getResources().getString(R.string.rb_sd);
            }
        });

        rb_smp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenjang = getResources().getString(R.string.rb_smp);
            }
        });
        rb_smk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenjang = getResources().getString(R.string.rb_smk);
            }
        });

        rb_sma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenjang = getResources().getString(R.string.rb_sma);
            }
        });

        slidingLayout.setFadeOnClickListener(view -> {
            slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            arrow.setImageResource(R.drawable.ic_up_arrow);
        });

        slidingLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {

            }

            @Override
            public void onPanelStateChanged(View view, SlidingUpPanelLayout.PanelState panelState, SlidingUpPanelLayout.PanelState panelState1) {
                if (panelState.equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                    arrow.setImageResource(R.drawable.ic_up_arrow);
                }else if (panelState.equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                    arrow.setImageResource(R.drawable.ic_arrow_down);
                }
            }
        });

        drag.setOnClickListener(v -> {
            if (slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.EXPANDED)){
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);
            }else if (slidingLayout.getPanelState().equals(SlidingUpPanelLayout.PanelState.COLLAPSED)){
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            }
        });

        tampilProv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(mapF != null){
                    mapF.clear();
                }

                mClusterManager = new ClusterManager<ClusterItemSekolah>(FullMap.this, mapF);

                final LatLng lati = new LatLng(currentLatitudef, currentLongitudef);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lati.latitude, lati.longitude)).zoom(10).build();

                mapF.setOnCameraIdleListener(mClusterManager);
                mapF.setOnMarkerClickListener(mClusterManager);
                mapF.setOnInfoWindowClickListener(mClusterManager);
                mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(
                        new InfoWindowAdapter(FullMap.this));
                dapat_sekolah();
                mClusterManager.setOnClusterClickListener(FullMap.this);
                mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterItemSekolah>() {
                            @Override
                            public boolean onClusterItemClick(ClusterItemSekolah item) {
                                clusterItemSekolah = item;
                                String schooldetail = item.getSchooldetailid();
                                Intent intent = new Intent(FullMap.this,DetailSekolah.class);
                                intent.putExtra("detailid", schooldetail);
                                intent.putExtra("member_id",member_id);
                                startActivity(intent);
                                return false;
                            }
                        });

                mClusterManager.setRenderer(new MarkerClusterRenderer(FullMap.this, mapF, mClusterManager));
                mapF.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lati.latitude, lati.longitude), 10));
                mapF.animateCamera(CameraUpdateFactory.zoomTo(11));
                mClusterManager.cluster();
                snappyrecyclerView.setVisibility(View.GONE);
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                arrow.setImageResource(R.drawable.ic_up_arrow);

            }
        });
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        LastLocation = location;
        if (CurrlocationMarker != null) {
            CurrlocationMarker.remove();
        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(13).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_map));

        //move map camera
        mapF.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapF.animateCamera(CameraUpdateFactory.zoomTo(14));
        CurrlocationMarker = mapF.addMarker(markerOptions);
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.connect();
        }

        currentLatitudef = location.getLatitude();
        currentLongitudef = location.getLongitude();
        updateLocation(location);
        getAddress();
        dapat_map();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapF = googleMap;
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
                mapF.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mapF.setMyLocationEnabled(true);
        }
        mapF.getUiSettings().setMyLocationButtonEnabled(false);
        mapF.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
                if (infoWindowData != null) {
                    String SchoolDetailId = infoWindowData.getSchooldetailid();
                    Intent intent = new Intent(getBaseContext(), DetailSekolah.class);
                    intent.putExtra("detailid", SchoolDetailId);
                    intent.putExtra("member_id", member_id);
                    startActivity(intent);
                }else {
                    Log.d("Lokasi","Lokasi Anda");
                }
            }
        });
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

            List<Address> addresses = gcd.getFromLocation(currentLatitudef,

                    currentLongitudef, 100);

            StringBuilder result = new StringBuilder();



            if (addresses.size() > 0) {



                Address address = addresses.get(1);

                int maxIndex = address.getMaxAddressLineIndex();

                for (int x = 0; x <= maxIndex; x++) {

                    result.append(address.getAddressLine(x));

                    result.append(",");

                }



            }

            locationf = result.toString();

        } catch (IOException ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();



        }

    }

    void updateLocation(Location location) {

        LastLocation = location;

        currentLatitudef = LastLocation.getLatitude();

        currentLongitudef = LastLocation.getLongitude();

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void dapat_map(){
        progressBar();
        showDialog();
        Call<JSONResponse.Nearby_School> call = mApiInterface.nearby_radius_post(currentLatitudef,currentLongitudef,PROXIMITY_RADIUS);

        call.enqueue(new Callback<JSONResponse.Nearby_School>() {

            @Override
            public void onResponse(Call<JSONResponse.Nearby_School> call, final Response<JSONResponse.Nearby_School> response) {
                Log.i("KES", response.code() + "");
                hideDialog();
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
                        ItemList = new ArrayList<ItemSekolah>();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            //Toast.makeText(getApplicationContext(), NR_SCS_0001, Toast.LENGTH_LONG).show();
                            lat = response.body().getData().get(i).getLatitude();
                            lng = response.body().getData().get(i).getLongitude();
                            placeName = response.body().getData().get(i).getSchool_name();
                            vicinity = response.body().getData().get(i).getSchool_address();
                            akreditasi = response.body().getData().get(i).getAkreditasi();
                            Jarak = response.body().getData().get(i).getDistance();
                            schooldetailid = response.body().getData().get(i).getSchooldetailid();

                            final LatLng latLng = new LatLng(lat, lng);

                            if (response.body().getData().get(i).getJenjang_pendidikan().equals("SD")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sd));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mapF.addMarker(markerOptions);

                            } else if (response.body().getData().get(i).getJenjang_pendidikan().equals("SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mapF.addMarker(markerOptions);
                            } else if (response.body().getData().get(i).getJenjang_pendidikan().equals("SPK SMP")) {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mapF.addMarker(markerOptions);
                            } else {
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sma));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mapF.addMarker(markerOptions);
                            }

                            InfoWindowData info = new InfoWindowData();
                            info.setNama(placeName);
                            info.setAlamat(vicinity);
                            info.setSchooldetailid(schooldetailid);
                            info.setAkreditasi(akreditasi);
                            info.setJarak(Jarak);
                            CustomInfoWindowAdapter customInfoWindowAdapter = new CustomInfoWindowAdapter(FullMap.this);
                            mapF.setInfoWindowAdapter(customInfoWindowAdapter);
                            m.setTag(info);

                            Item = new ItemSekolah();
                            Item.setName(placeName);
                            Item.setAkreditas(akreditasi);
                            Item.setJarak(Jarak);
                            Item.setLat(lat);
                            Item.setLng(lng);
                            ItemList.add(Item);
                        }
                        // Create the recyclerview.
                        snappyrecyclerView = findViewById(R.id.recycler_view2);
                        // Create the grid layout manager with 2 columns.
//                    final SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(FullMap.this);
//                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//                    snappyrecyclerView.setLayoutManager(new SnappyLinearLayoutManager(FullMap.this));
                        final CarouselLayoutManager layoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, true);
                        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());

                        snappyrecyclerView.addOnScrollListener(new CenterScrollListener());
                        snappyrecyclerView.setHasFixedSize(true);


                        //getSnapHelper().attachToRecyclerView(snappyRecyclerView);
                        // Set layout manager.
                        snappyrecyclerView.setLayoutManager(layoutManager);

                        // Create car recycler view data adapter with car item list.
                        cUstomRecyclerViewDataAdapter = new ItemSekolahAdapter(ItemList);

                        cUstomRecyclerViewDataAdapter.setOnItemClickListener(new ItemSekolahAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                progressBar();
                                showDialog();

                                final LatLng latLng = new LatLng(currentLatitudef, currentLongitudef);
                                latitudef = response.body().getData().get(position).getLatitude();
                                longitudef = response.body().getData().get(position).getLongitude();
                                final LatLng StartlatLng = new LatLng(latitudef, longitudef);
                                GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
                                String $key = getResources().getString(R.string.google_maps_key);

                                GoogleDirection.withServerKey($key)
                                        .from(latLng)
                                        .to(StartlatLng)
                                        .transportMode(TransportMode.DRIVING)
                                        .execute(new DirectionCallback() {
                                            @Override
                                            public void onDirectionSuccess(Direction direction, String rawBody) {
                                                hideDialog();
                                                Log.d("GoogleDirection", "Response Direction Status: " + direction.toString() + "\n" + rawBody);

                                                if (direction.isOK()) {
                                                    Route route = direction.getRouteList().get(0);
                                                    ArrayList<LatLng> directionPositionList = route.getLegList().get(0).getDirectionPoint();
                                                    lines = mapF.addPolyline(DirectionConverter.createPolyline(FullMap.this, directionPositionList, 5, Color.RED));
                                                    setCameraWithCoordinationBounds(direction.getRouteList().get(0));


                                                } else {
                                                    // Do something
                                                }
                                            }

                                            @Override
                                            public void onDirectionFailure(Throwable t) {
                                                // Do something
                                                hideDialog();
                                                Log.e("GoogleDirection", "Response Direction Status: " + t.getMessage() + "\n" + t.getCause());
                                            }
                                        });
                                if (lines != null) {
                                    lines.remove();
                                }
                            }

                        });

                        snappyrecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                                int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                                int currentItem = 0;
                                float itemWidth = horizontalScrollRange * 1.0f / ItemList.size();
                                itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                                if (scrollOffset != 0) {
                                    currentItem = Math.round(scrollOffset / itemWidth);
                                }
                                currentItem = (currentItem < 0) ? 0 : currentItem;
                                currentItem = (currentItem >= ItemList.size()) ? ItemList.size() - 1 : currentItem;
                                if (lines != null) {
                                    lines.remove();
                                }
                                currentItem = layoutManager.getCenterItemPosition();
                                if (response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SD")) {
                                    latitudef = response.body().getData().get(currentItem).getLatitude();
                                    longitudef = response.body().getData().get(currentItem).getLongitude();
                                    final LatLng latLng = new LatLng(latitudef, longitudef);
                                    final MarkerOptions markerOptions = new MarkerOptions();
                                    // Position of Marker on Map
                                    markerOptions.position(latLng);
                                    // Adding colour to the marker
                                    markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sd60));
                                    // Remove Marker
                                    if (m != null) {
                                        m.remove();
                                    }
                                    // Adding Marker to the Camera.
                                    m = mapF.addMarker(markerOptions);
                                    Lat = latLng;
                                } else if (response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SMP")) {
                                    latitudef = response.body().getData().get(currentItem).getLatitude();
                                    longitudef = response.body().getData().get(currentItem).getLongitude();
                                    final LatLng latLng = new LatLng(latitudef, longitudef);
                                    final MarkerOptions markerOptions = new MarkerOptions();
                                    // Position of Marker on Map
                                    markerOptions.position(latLng);
                                    // Adding colour to the marker
                                    markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_smp60));
                                    // Remove Marker
                                    if (m != null) {
                                        m.remove();
                                    }
                                    // Adding Marker to the Camera.
                                    m = mapF.addMarker(markerOptions);
                                    Lat = latLng;
                                } else if (response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SMA")) {
                                    latitudef = response.body().getData().get(currentItem).getLatitude();
                                    longitudef = response.body().getData().get(currentItem).getLongitude();
                                    final LatLng latLng = new LatLng(latitudef, longitudef);
                                    final MarkerOptions markerOptions = new MarkerOptions();
                                    // Position of Marker on Map
                                    markerOptions.position(latLng);
                                    // Adding colour to the marker
                                    markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sma60));
                                    // Remove Marker
                                    if (m != null) {
                                        m.remove();
                                    }
                                    // Adding Marker to the Camera.
                                    m = mapF.addMarker(markerOptions);
                                    Lat = latLng;
                                } else if (response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SPK SMP")) {
                                    latitudef = response.body().getData().get(currentItem).getLatitude();
                                    longitudef = response.body().getData().get(currentItem).getLongitude();
                                    final LatLng latLng = new LatLng(latitudef, longitudef);
                                    final MarkerOptions markerOptions = new MarkerOptions();
                                    // Position of Marker on Map
                                    markerOptions.position(latLng);
                                    // Adding colour to the marker
                                    markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_smp60));
                                    // Remove Marker
                                    if (m != null) {
                                        m.remove();
                                    }
                                    // Adding Marker to the Camera.
                                    m = mapF.addMarker(markerOptions);
                                    Lat = latLng;
                                }

                                if (currentItem == 0) {
                                    final LatLng lati = new LatLng(currentLatitudef, currentLongitudef);
                                    CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lati.latitude, lati.longitude)).zoom(13).build();
                                    mapF.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                    mapF.animateCamera(CameraUpdateFactory.zoomTo(14));
                                    m.setVisible(false);
                                } else {
                                    mapF.moveCamera(CameraUpdateFactory.newLatLng(Lat));
                                    mapF.animateCamera(CameraUpdateFactory.zoomTo(16));
                                    m.setVisible(true);
                                }
                            }
                        });
                        // Set data adapter.
                        snappyrecyclerView.setAdapter(cUstomRecyclerViewDataAdapter);


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


    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mapF.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }


    public void dapat_provinsi(){

        Call<JSONResponse.Provinsi> call = mApiInterface.provinsi_get();

        call.enqueue(new Callback<JSONResponse.Provinsi>() {

            @Override
            public void onResponse(Call<JSONResponse.Provinsi> call, final Response<JSONResponse.Provinsi> response) {
                Log.i("KES", response.code() + "");
                if (response.isSuccessful()) {
                    JSONResponse.Provinsi resource = response.body();

                    status = resource.status;
                    code = resource.code;

                    String SOP_SCS_0001 = getResources().getString(R.string.SOP_SCS_0001);
                    String SOP_ERR_0001 = getResources().getString(R.string.SOP_ERR_0001);

                    ItemSekolah Item = null;
                    List<String> provinsi = null;
                    if (status == 1) {
                        arrayList = response.body().getData();
                        List<String> listSpinner = new ArrayList<String>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            String provID = arrayList.get(i).getProvinsiid();
                            listSpinner.add(arrayList.get(i).getNamaProvinsi());
                        }
                        //String myString = "DKI JAKARTA";
                        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(FullMap.this, R.layout.spinner_full, listSpinner);
                        //int spinnerPosition = spinnerArrayAdapter.getPosition(myString);
                        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                        et_provinsi.setAdapter(spinnerArrayAdapter);
                        //et_provinsi.setSelection(spinnerPosition);

                    } else {
                        if (status == 0) {
                            Toast.makeText(getApplicationContext(), SOP_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.Provinsi> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }

    public void dapat_sekolah(){

        if (TextUtils.isEmpty(jenjang)){
            Toast.makeText(getApplicationContext(),"Silahkan pilih jenjang sekolah",Toast.LENGTH_SHORT).show();
        }else {
            progressBar();
            showDialog();
            Call<JSONResponse.School_Provinsi> call = mApiInterface.school_onprov_get(provid, jenjang);

            call.enqueue(new Callback<JSONResponse.School_Provinsi>() {

                @Override
                public void onResponse(Call<JSONResponse.School_Provinsi> call, final Response<JSONResponse.School_Provinsi> response) {
                    Log.i("KES", response.code() + "");
                    hideDialog();
                    if (response.isSuccessful()) {
                        JSONResponse.School_Provinsi resource = response.body();

                        status = resource.status;
                        code = resource.code;


                        String SOP_SCS_0001 = getResources().getString(R.string.SOP_SCS_0001);
                        String SOP_ERR_0001 = getResources().getString(R.string.SOP_ERR_0001);


                        if (status == 1 && code.equals("SOP_SCS_0001")) {
                            for (int i = 0; i < response.body().getData().size(); i++) {
                                double lat = response.body().getData().get(i).getLatitude();
                                double lng = response.body().getData().get(i).getLongitude();
                                String nama = response.body().getData().get(i).getSchoolName();
                                String akreditas = response.body().getData().get(i).getAkreditasi();
                                String Alamat = response.body().getData().get(i).getSchoolAddress();
                                String schooldetailid = response.body().getData().get(i).getSchooldetailid();
                                mClusterManager.addItem(new ClusterItemSekolah(lat, lng, nama, akreditas, Alamat, schooldetailid));

                            }

                        } else {
                            if (status == 0 && code.equals("SOP_ERR_0001")) {
                                Toast.makeText(getApplicationContext(), SOP_ERR_0001, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JSONResponse.School_Provinsi> call, Throwable t) {
                    hideDialog();
                    Log.d("onFailure", t.toString());
                }

            });
        }
        }

    public class MarkerClusterRenderer extends DefaultClusterRenderer<ClusterItemSekolah> {

        public MarkerClusterRenderer(Context context, GoogleMap map,
                                     ClusterManager<ClusterItemSekolah> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(ClusterItemSekolah item, MarkerOptions markerOptions) {
            // use this to make your change to the marker option
            // for the marker before it gets render on the map
            if(jenjang.equals("SD")){
            markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sd));
            }else if (jenjang.equals("SMP")){
                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_smp));
            }
            else if (jenjang.equals("SMA")){
                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sma));
            }
            else if (jenjang.equals("SMK")){
                markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sma));
            }
            //markerOptions.icon(bitmapDescriptorFromVector(FullMap.this, R.drawable.ic_sma));
        }
    }

    @Override
    public boolean onClusterClick(Cluster<ClusterItemSekolah> cluster) {
        double minLat = 0;
        double minLng = 0;
        double maxLat = 0;
        double maxLng = 0;
        for(ClusterItemSekolah p : cluster.getItems()){
            double lat = p.getPosition().latitude;
            double lng = p.getPosition().longitude;
            if(minLat == 0 & minLng ==0 & maxLat ==0& maxLng == 0){
                minLat = maxLat = lat;
                minLng = maxLng = lng;
            }
            if(lat > maxLat){
                maxLat = lat;
            }
            if(lng > maxLng){
                maxLng = lng;
            }
            if(lat < minLat){
                minLat = lat;
            }
            if(lng < minLng){
                minLng = lng;
            }
        }

        LatLng sw = new LatLng(minLat, minLng);
        LatLng ne = new LatLng(maxLat, maxLng);
        LatLngBounds bounds = new LatLngBounds(sw, ne);

        mapF.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30));

        return true;
    }
//

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
        dialog = new ProgressDialog(FullMap.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
