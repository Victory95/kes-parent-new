package com.fingertech.kes.Activity.Maps;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etiennelawlor.discreteslider.library.ui.DiscreteSlider;
import com.etiennelawlor.discreteslider.library.utilities.DisplayUtility;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
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
    SearchView searchView;
    DiscreteSlider discreteSlider;
    RelativeLayout tickMarkLabelsRelativeLayout;
    Button Kelurahan;

    ImageView bookmark;
    Toolbar ToolBarAtas2;
    SearchManager searchManager;
    SquareFloatButton refress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searching_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSearch);
        mapFragment.getMapAsync(this);

        discreteSlider               = (DiscreteSlider)findViewById(R.id.discrete_slider);
        tickMarkLabelsRelativeLayout = (RelativeLayout)findViewById(R.id.tick_mark_labels_rl);
        Kelurahan                    = (Button)findViewById(R.id.kelurahan);
        bookmark                     = (ImageView) findViewById(R.id.book);
        recyclerView                 = findViewById(R.id.recyclerView);
        searchView                   = (SearchView)findViewById(R.id.search);
        ToolBarAtas2                 = (Toolbar)findViewById(R.id.toolbar_satu);
        mApiInterface                = ApiClient.getClient().create(Auth.class);
        searchManager                = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        refress                      = (SquareFloatButton)findViewById(R.id.refres);

        requestFocus(searchView);
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
                        dapat_map();
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
        search_school_post(key);
        hideKeyboard(this);
        recyclerView.setVisibility(View.GONE);
        refress.setVisibility(View.VISIBLE);
        tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
        discreteSlider.setVisibility(View.VISIBLE);

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                key = query;
                search_school_post(query);
                refress.setVisibility(View.GONE);
                tickMarkLabelsRelativeLayout.setVisibility(View.GONE);
                discreteSlider.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                key = newText;
                search_school_post(newText);
                refress.setVisibility(View.GONE);
                tickMarkLabelsRelativeLayout.setVisibility(View.GONE);
                discreteSlider.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                return false;
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(SearchingMAP.this,FilterActivity.class);
                startActivityForResult(mIntent,1);
            }
        });
        setSupportActionBar(ToolBarAtas2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Kelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(SearchingMAP.this,LokasiAnda.class);
                startActivityForResult(mIntent,2);
            }
        });
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
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;
        progressBar();
        showDialog();
        //Initialize Google Play Services
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

        mmap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();
                String SchoolDetailId = infoWindowData.getSchooldetailid();
                Intent intent = new Intent(getBaseContext(),DetailSekolah.class);
                intent.putExtra("detailid",SchoolDetailId);
                startActivity(intent);
            }
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
        currentLatitude     = location.getLatitude();
        currentLongitude    = location.getLongitude();
        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
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

        refress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mmap.clear();
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(13).build();
                final MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Lokasi Anda");
                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_map));

                mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mmap.animateCamera(CameraUpdateFactory.zoomTo(14));
            }
        });
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
                        Toast.makeText(getApplicationContext(), NR_SCS_0001, Toast.LENGTH_LONG).show();
                        double lat                  = response.body().getData().get(i).getLatitude();
                        double lng                  = response.body().getData().get(i).getLongitude();
                        final String placeName      = response.body().getData().get(i).getSchool_name();
                        final String vicinity       = response.body().getData().get(i).getSchool_address();
                        final String akreditasi     = response.body().getData().get(i).getAkreditasi();
                        final double Jarak          = response.body().getData().get(i).getDistance();
                        final String schooldetailid = response.body().getData().get(i).getSchooldetailid();

                        LatLng latLng = new LatLng(lat, lng);
                        if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SD")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mmap.addMarker(markerOptions);

                        }else if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mmap.addMarker(markerOptions);
                        }else if(response.body().getData().get(i).getJenjang_pendidikan().toString().equals("SPK SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mmap.addMarker(markerOptions);
                        }
                        else {
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sma));
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mmap.addMarker(markerOptions);
                        }

                        InfoWindowData info = new InfoWindowData();
                        info.setNama(placeName);
                        info.setAlamat(vicinity);
                        info.setSchooldetailid(schooldetailid);
                        InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                        mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                        m.setTag(info);
                    }

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
                    searchMapAdapter.setOnItemClickListener(new SearchMapAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            latitudeF   = response.body().getData().get(position).getLatitude();
                            longitudeF  = response.body().getData().get(position).getLongitude();
                            String Schooldetailid   = response.body().getData().get(position).getSchooldetailid();
                            String Namasekolah      = response.body().getData().get(position).getSchool_name();
                            String Alamat           = response.body().getData().get(position).getSchool_address();
                            final LatLng latLng = new LatLng(latitudeF, longitudeF);
                            hideKeyboard(SearchingMAP.this);
                            if(response.body().getData().get(position).getJenjang_pendidikan().toString().equals("SD")){
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_sd));

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);

                            }else if(response.body().getData().get(position).getJenjang_pendidikan().toString().equals("SMP")){
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            }else if(response.body().getData().get(position).getJenjang_pendidikan().toString().equals("SPK SMP")){
                                MarkerOptions markerOptions = new MarkerOptions();

                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(SearchingMAP.this, R.drawable.ic_smp));
                                // Remove Marker

                                // Adding Marker to the Camera.
                                m = mmap.addMarker(markerOptions);
                            }
                            else {
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


                            InfoWindowData infoWindowData = new InfoWindowData();
                            infoWindowData.setNama(Namasekolah);
                            infoWindowData.setAlamat(Alamat);
                            infoWindowData.setSchooldetailid(Schooldetailid);
                            InfoWindowAdapter customInfoWindowAdapter = new InfoWindowAdapter(SearchingMAP.this);
                            mmap.setInfoWindowAdapter(customInfoWindowAdapter);
                            m.setTag(infoWindowData);
                            tickMarkLabelsRelativeLayout.setVisibility(View.VISIBLE);
                            discreteSlider.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        }
                    });

                } else {
                    if(status == 0 && code.equals("SS_ERR_0001")){
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
        if (dialog.isShowing())
            dialog.dismiss();
        dialog.setContentView(R.layout.progressbar);
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
        refress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
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

                final LatLng latLng1 = new LatLng(lati,longi);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng1.latitude, latLng1.longitude)).zoom(16).build();
                InfoWindowData indo = new InfoWindowData();
                indo.setNama(namaSek);
                indo.setAlamat(alamat);
                indo.setSchooldetailid(schoolid);

                if (jenjang.toString().equals("sd") || jenjang.toString().equals("BPK SD")){

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

                }else if(jenjang.toString().equals("smp") || jenjang.toString().equals("BPK SMP")){
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

                }else if(jenjang.toString().equals("smk")){
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
                requestFocus(searchView);
                hideKeyboard(this);
                recyclerView.setVisibility(View.GONE);
            }
        }else if (requestCode == 2){
            if(resultCode == RESULT_OK) {
                mmap.clear();
                String address  = data.getStringExtra("address");
                double lati     = data.getDoubleExtra("latitude",0.0);
                double longi    = data.getDoubleExtra("longitude",0.0);

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
                Kelurahan.setText(address);
            }
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
        return true;
    }

}


