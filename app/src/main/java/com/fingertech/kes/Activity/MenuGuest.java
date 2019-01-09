package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.fingertech.kes.Activity.Maps.FullMap;
import com.fingertech.kes.Activity.Maps.MapWrapperLayout;
import com.fingertech.kes.Activity.Maps.OnInfoWindowElemTouchListener;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.RecycleView.SnappyRecycleView;
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
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;

public class MenuGuest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{


    CarouselView customCarouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_4, R.drawable.image_5};
    String[] sampleTitles = {"Orange", "Grapes", "Strawberry", "Cherry", "Apricot"};


    private List<ItemSekolah> itemList;
    private ItemSekolahAdapter itemSekolahAdapter = null;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private GoogleMap.OnCameraMoveListener onCameraMove;
    private GoogleMap mapG;
    private LocationRequest mlocationRequest;
    private Marker CurrLocationMarker,m,sd,smp,sma;
    private Location lastLocation;
    private Button lock,Nearby;
    private Boolean clicked = false;
    public SnappyRecycleView snappyRecyclerView;
    GoogleApiClient mGoogleApiClient;
    ArrayList<LatLng> markerPoints;
    Polyline line;
    LatLngBounds.Builder builder;

    PolylineOptions lineOptions;
    Double PROXIMITY_RADIUS = 2.0;
    Double currentLatitude,latitude;
    Double currentLongitude,longitude;
    Double CurrentLatitude;
    Double CurrentLongitude;
    String location;
    String code;
    Auth mApiInterface;
    Button carisekolah,carisekolah2;
    int status;
    private OnInfoWindowElemTouchListener infoButtonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_guest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
        mapWrapperLayout.init(mapG, getPixelsFromDp(this, 39 + 20));

        mApiInterface = ApiClient.getClient().create(Auth.class);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        customCarouselView = (CarouselView) findViewById(R.id.customCarouselView);
        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);
        customCarouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MenuGuest.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapGuest);
        mapFragment.getMapAsync(this);

/*
        findViewById(R.id.locks).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(clicked) {
                    mapG.getUiSettings().setScrollGesturesEnabled(false);
                    clicked = false;
                    view.setBackgroundResource(R.drawable.ic_lock);
                }
                else {
                    mapG.getUiSettings().setScrollGesturesEnabled(true);
                    clicked = true;
                    view.setBackgroundResource(R.drawable.ic_unlock);
                }
            }
        });*/

        findViewById(R.id.squareFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(MenuGuest.this,FullMap.class);
                startActivity(mIntent);

            }
        });
        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }
        Nearby = (Button)findViewById(R.id.cari_sekolah2);
        carisekolah = (Button)findViewById(R.id.cari_sekolah);
        carisekolah2 = (Button)findViewById(R.id.cari_sekolah2);
        carisekolah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(MenuGuest.this,SearchingMAP.class);
                startActivity(mIntent);

            }
        });
        carisekolah2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(MenuGuest.this,SearchingMAP.class);
                startActivity(mIntent);

            }
        });
        checkLocationPermission();

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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Apa kalian ingin Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }


    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(final int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);

            fruitImageView.setImageResource(sampleImages[position]);
            labelTextView.setText(sampleTitles[position]);
            Button Baca = (Button) customView.findViewById(R.id.baca);
                Baca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MenuGuest.this, "Clicked item: " + position, Toast.LENGTH_SHORT).show();
                    }
                });
            customCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM |Gravity.LEFT);
        return customView;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_guest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            // Handle the camera action
        } else if (id == R.id.nav_cari_sekolah) {

        } else if (id == R.id.nav_keluar) {
            Intent intent = new Intent(MenuGuest.this, OpsiMasuk.class);
            startActivity(intent);
        } else if (id == R.id.nav_kontak) {

        } else if (id == R.id.nav_Pengaturan) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    @Override
    public void onLocationChanged(Location location) {
        lastLocation = location;
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();

        }

            //Place current location marker
            final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(13).build();

            final MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_map));

            //move map camera
            mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mapG.animateCamera(CameraUpdateFactory.zoomTo(14));
            CurrLocationMarker = mapG.addMarker(markerOptions);
            //stop location updates
            if (mGoogleApiClient != null) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                mGoogleApiClient.connect();
            }

            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
            updateLocation(location);
            getAddress();
            dapat_map();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapG = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mapG.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mapG.setMyLocationEnabled(true);
        }


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

        lastLocation = location;

        currentLatitude = lastLocation.getLatitude();

        currentLongitude = lastLocation.getLongitude();

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

        private Context context;

        public CustomInfoWindowGoogleMap(Context ctx){
            context = ctx;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = ((Activity)context).getLayoutInflater()
                    .inflate(R.layout.custom_snippet, null);

            TextView tvSch = (TextView) view.findViewById(R.id.nama_school);

            // Getting reference to the TextView to set longitude
            TextView tvAkr = (TextView) view.findViewById(R.id.akreditasi);

            // Getting reference to the TextView to set latitude
            TextView tvJrk = (TextView) view.findViewById(R.id.jarak);

            // Getting reference to the TextView to set longitude
            TextView tvAlm = (TextView) view.findViewById(R.id.alamat_school);

            // Getting reference to the TextView to set longitude
            TextView tvLht = (TextView) view.findViewById(R.id.Lihat);


            ImageView img = view.findViewById(R.id.imageS);

            tvSch.setText(marker.getTitle());
            tvAkr.setText("Akreditasi "+marker.getSnippet());

            InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

            tvJrk.setText("Jarak > "+ String.format("%.2f", infoWindowData.getJarak())+ "Km");
            tvAlm.setText(infoWindowData.getAlamat());
            final String SchoolDetailId = infoWindowData.getSchooldetailid();

            mapG.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getBaseContext(),DetailSekolah.class);
                    intent.putExtra("detailid",SchoolDetailId);
                    startActivity(intent);
                }
            });
            return view;
        }
    }


    public void dapat_map(){

        Call<JSONResponse.Nearby_School> call = mApiInterface.nearby_radius_post(currentLatitude,currentLongitude,PROXIMITY_RADIUS);

        call.enqueue(new Callback<JSONResponse.Nearby_School>() {

            @Override
            public void onResponse(Call<JSONResponse.Nearby_School> call, final Response<JSONResponse.Nearby_School> response) {
                Log.i("KES", response.code() + "");

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
                    itemList = new ArrayList<ItemSekolah>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        Toast.makeText(getApplicationContext(), NR_SCS_0001, Toast.LENGTH_LONG).show();
                        double lat                  = response.body().getData().get(i).getLatitude();
                        double lng                  = response.body().getData().get(i).getLongitude();
                        final String placeName      = response.body().getData().get(i).getSchool_name();
                        final String vicinity       = response.body().getData().get(i).getSchool_address();
                        final String akreditasi     = response.body().getData().get(i).getAkreditasi();
                        final String schooldetailid = response.body().getData().get(i).getSchooldetailid();
                        final double Jarak          = response.body().getData().get(i).getDistance();

                        LatLng latLng = new LatLng(lat, lng);
                        if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SD")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_sd));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mapG.addMarker(markerOptions);

                        }else if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_smp));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mapG.addMarker(markerOptions);
                        }else if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SPK SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_smp));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mapG.addMarker(markerOptions);
                        }
                        else {
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_sma));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mapG.addMarker(markerOptions);
                        }



//                        mapG.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                            public boolean onMarkerClick(Marker marker) {
//                                // Check if there is an open info window
//                                if (m != null) {
//                                    // Close the info window
//                                    m.hideInfoWindow();
//
//                                }
//
//                                // Open the info window for the marker
//                                marker.showInfoWindow();
//                                // Re-assign the last openned such that we can close it later
//                                m = marker;
//
//                                // Event was handled by our code do not launch default behaviour.
//                                return true;
//                            }
//                        });

                        InfoWindowData info = new InfoWindowData();
//                        info.setNama(placeName);
//                        info.setAkreditasi(akreditasi);
                        info.setJarak(Jarak);
                        info.setAlamat(vicinity);
                        info.setSchooldetailid(schooldetailid);

                        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MenuGuest.this);
                        mapG.setInfoWindowAdapter(customInfoWindow);

                        m.setTag(info);
                       // m.showInfoWindow();

                        Item = new ItemSekolah();
                        Item.setName(placeName);
                        Item.setAkreditas(akreditasi);
                        Item.setJarak(Jarak);
                        Item.setLat(lat);
                        Item.setLng(lng);
                        itemList.add(Item);
                    }
                    // Create the recyclerview.
                    snappyRecyclerView = (SnappyRecycleView) findViewById(R.id.recycler_view);
                    // Create the grid layout manager with 2 columns.
                    final SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(MenuGuest.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    snappyRecyclerView.setLayoutManager(new SnappyLinearLayoutManager(MenuGuest.this));

                    //getSnapHelper().attachToRecyclerView(snappyRecyclerView);
                    // Set layout manager.
                    snappyRecyclerView.setLayoutManager(layoutManager);

                    // Create car recycler view data adapter with car item list.
                    itemSekolahAdapter = new ItemSekolahAdapter(itemList);

                    itemSekolahAdapter.setOnItemClickListener(new ItemSekolahAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Toast.makeText(MenuGuest.this, "Clicked at index "+ position, Toast.LENGTH_SHORT).show();

                            LatLng latLng = new LatLng(currentLatitude,currentLongitude);
                            latitude = response.body().getData().get(position).getLatitude();
                            longitude = response.body().getData().get(position).getLongitude();
                            final LatLng StartlatLng = new LatLng(latitude, longitude);
                            GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
                            String $key = getResources().getString(R.string.google_maps_key);


                            GoogleDirection.withServerKey($key)
                                    .from(latLng)
                                    .to(StartlatLng)
                                    .transportMode(TransportMode.DRIVING)
                                    .transitMode(TransitMode.BUS)
                                    .unit(Unit.METRIC)
                                    .execute(new DirectionCallback() {
                                        @Override
                                        public void onDirectionSuccess(Direction direction, String rawBody) {

                                            Log.d("GoogleDirection", "Response Direction Status: " + direction.toString()+"\n"+rawBody);

                                            if(direction.isOK()) {
                                                // Do something
                                                Route route = direction.getRouteList().get(0);
                                                Leg leg = route.getLegList().get(0);
                                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplication(), directionPositionList, 5, Color.RED);
                                                line = mapG.addPolyline(polylineOptions);
                                                setCameraWithCoordinationBounds(direction.getRouteList().get(0));

                                            } else {
                                                // Do something
                                            }
                                        }

                                        @Override
                                        public void onDirectionFailure(Throwable t) {
                                            // Do something
                                            Log.e("GoogleDirection", "Response Direction Status: " + t.getMessage()+"\n"+t.getCause());
                                        }
                                    });
                            if(line != null){
                                line.remove();
                            }
                        }

                    });

                    snappyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                            int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                            int currentItem = 0;
                            float itemWidth = horizontalScrollRange * 1.0f / itemList.size();
                            itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                            if (scrollOffset != 0) {
                                currentItem = Math.round(scrollOffset / itemWidth);
                            }
                            currentItem = (currentItem < 0) ? 0 : currentItem;
                            currentItem = (currentItem >= itemList.size()) ? itemList.size() - 1 : currentItem;
                            if(line != null){
                                line.remove();
                            }
                            if(response.body().getData().get(currentItem).getJenjang_pendidikan().toString().equals("SD")) {
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_sd60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));

                            }
                            else if(response.body().getData().get(currentItem).getJenjang_pendidikan().toString().equals("SMP")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_smp60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }else if(response.body().getData().get(currentItem).getJenjang_pendidikan().toString().equals("SMA")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_sma60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }else if(response.body().getData().get(currentItem).getJenjang_pendidikan().toString().equals("SPK SMP")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuGuest.this, R.drawable.ic_smp60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }
                        }
                    });
                    // Set data adapter.
                    snappyRecyclerView.setAdapter(itemSekolahAdapter);


                } else{
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

            @Override
            public void onFailure(Call<JSONResponse.Nearby_School> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }

    public interface ISnappyLayoutManager {

        /**
         * @param velocityX
         * @param velocityY
         * @return the resultant position from a fling of the given velocity.
         */
        int getPositionForVelocity(int velocityX, int velocityY);

        /**
         * @return the position this list must scroll to to fix a state where the
         * views are not snapped to grid.
         */
        int getFixScrollPos();

    }

    public class SnappyLinearLayoutManager extends LinearLayoutManager implements ISnappyLayoutManager {
        // These variables are from android.widget.Scroller, which is used, via ScrollerCompat, by
        // Recycler View. The scrolling distance calculation logic originates from the same place. Want
        // to use their variables so as to approximate the look of normal Android scrolling.
        // Find the Scroller fling implementation in android.widget.Scroller.fling().
        private static final float INFLEXION = 0.35f; // Tension lines cross at (INFLEXION, 1)
        private float DECELERATION_RATE = (float) (Math.log(0.78) / Math.log(0.9));
        private  double FRICTION = 0.84;

        private double deceleration;

        public SnappyLinearLayoutManager(Context context) {
            super(context);
            calculateDeceleration(context);
        }

        public SnappyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
            calculateDeceleration(context);
        }

        private void calculateDeceleration(Context context) {
            deceleration = SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.3700787 // inches per meter
                    // pixels per inch. 160 is the "default" dpi, i.e. one dip is one pixel on a 160 dpi
                    // screen
                    * context.getResources().getDisplayMetrics().density * 160.0f * FRICTION;
        }

        @Override
        public int getPositionForVelocity(int velocityX, int velocityY) {
            if (getChildCount() == 0) {
                return 0;
            }
            if (getOrientation() == HORIZONTAL) {
                return calcPosForVelocity(velocityX, getChildAt(0).getLeft(), getChildAt(0).getWidth(),
                        getPosition(getChildAt(0)));
            } else {
                return calcPosForVelocity(velocityY, getChildAt(0).getTop(), getChildAt(0).getHeight(),
                        getPosition(getChildAt(0)));
            }
        }

        private int calcPosForVelocity(int velocity, int scrollPos, int childSize, int currPos) {
            final double dist = getSplineFlingDistance(velocity);

            final double tempScroll = scrollPos + (velocity > 0 ? dist : -dist);

            if (velocity < 0) {
                // Not sure if I need to lower bound this here.
                return (int) Math.max(currPos + tempScroll / childSize, 0);
            } else {
                return (int) (currPos + (tempScroll / childSize) + 1);
            }
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            final LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {

                        // I want a behavior where the scrolling always snaps to the beginning of
                        // the list. Snapping to end is also trivial given the default implementation.
                        // If you need a different behavior, you may need to override more
                        // of the LinearSmoothScrolling methods.
                        protected int getHorizontalSnapPreference() {
                            return SNAP_TO_START;
                        }

                        protected int getVerticalSnapPreference() {
                            return SNAP_TO_START;
                        }

                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return SnappyLinearLayoutManager.this
                                    .computeScrollVectorForPosition(targetPosition);
                        }
                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);

        }

        private double getSplineFlingDistance(double velocity) {
            final double l = getSplineDeceleration(velocity);
            final double decelMinusOne = DECELERATION_RATE - 1.0;
            return ViewConfiguration.getScrollFriction() * deceleration
                    * Math.exp(DECELERATION_RATE / decelMinusOne * l);
        }

        private double getSplineDeceleration(double velocity) {
            return Math.log(INFLEXION * Math.abs(velocity)
                    / (ViewConfiguration.getScrollFriction() * deceleration));
        }

        @Override
        public int getFixScrollPos() {
            if (this.getChildCount() == 0) {
                return 0;
            }

            final View child = getChildAt(0);
            final int childPos = getPosition(child);

            if (getOrientation() == HORIZONTAL
                    && Math.abs(child.getLeft()) > child.getMeasuredWidth() / 2) {
                // Scrolled first view more than halfway offscreen
                return childPos + 1;
            } else if (getOrientation() == VERTICAL
                    && Math.abs(child.getTop()) > child.getMeasuredWidth() / 2) {
                // Scrolled first view more than halfway offscreen
                return childPos + 1;
            }
            return childPos;
        }

    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mapG.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}





