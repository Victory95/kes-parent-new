package com.fingertech.kes.Activity.Maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.DetailSekolah;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.Search.LokasiAnda;
import com.fingertech.kes.R;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapLokasi extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraIdleListener,GoogleMap.OnCameraMoveListener{


    private GoogleMap mmap;
    private LocationRequest mlocationRequest;
    private Marker mcurrLocationMarker;
    private Location mlastLocation;
    GoogleApiClient mGoogleApiClient;
    Button Pilih;
    TextView lokasi;
    String address,city;
    double latitude1,longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_lokasi);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapLokasi);
        mapFragment.getMapAsync(this);
        final Toolbar toolbar = findViewById(R.id.searc);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        Pilih   = findViewById(R.id.pilih_map);
        lokasi  = findViewById(R.id.lokasi_anda);

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
        mlastLocation = location;
        if (mcurrLocationMarker != null) {
            mcurrLocationMarker.remove();

        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();


        //move map camera
        mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }

    }

    @Override
    public void onCameraIdle() {
        CameraPosition position=mmap.getCameraPosition();
        Log.d("onCameraIdle",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
        final LatLng latLng = mmap.getCameraPosition().target;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                address = addressList.get(0).getAddressLine(0);
                String number = addressList.get(0).getFeatureName();
                city = addressList.get(0).getSubLocality();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
                latitude1  = addressList.get(0).getLatitude();
                longitude1 = addressList.get(0).getLongitude();

                lokasi.setText(address);

                Pilih.setOnClickListener(v -> {
                    Intent intent = new Intent(MapLokasi.this,LokasiAnda.class);
                    intent.putExtra("address", city);
                    intent.putExtra("latitude",latitude1);
                    intent.putExtra("longitude", longitude1);
                    setResult(RESULT_OK, intent);
                    finish();
                });
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=mmap.getCameraPosition();

        Log.d("onCameraMove",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_map));

        if(mcurrLocationMarker!= null){
            mcurrLocationMarker.remove();}

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapLokasi.this);
        mmap.setInfoWindowAdapter(customInfoWindow);

        mcurrLocationMarker = mmap.addMarker(options);
        mcurrLocationMarker.showInfoWindow();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;

        try {
            // Customise map styling via JSON file
            boolean success = googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle( this, R.raw.json_style));

            if (!success) {
                Log.e("KES", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("KES", "Can't find style. Error: ", e);
        }
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


        mmap.setOnCameraMoveListener(this);
        mmap.setOnCameraIdleListener(this);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
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
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mmap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(MapLokasi.this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
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
                    .inflate(R.layout.snippet_lokasi, null);

            mmap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(MapLokasi.this,LokasiAnda.class);
                    intent.putExtra("address", city);
                    intent.putExtra("latitude",latitude1);
                    intent.putExtra("longitude", longitude1);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            return view;
        }
    }
}
