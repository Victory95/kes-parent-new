package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class PekerjaanFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraIdleListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnInfoWindowClickListener {

    private static final Object Location = null;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Marker mCurrLocationMarker;
    private Location mLastLocation;
    private LocationManager lm;
    private double latitude = -6.110880;
    private double longitude = 106.776701;
    private TextView namakerja;
    final Marker[] marker = new Marker[1];

    private boolean needsInit=false;

    public static PekerjaanFragment newInstance() {
        // Required empty public constructor
        PekerjaanFragment Fragment = new PekerjaanFragment();
        return Fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pekerjaan, container, false);
        TextView namakerja = (TextView) view.findViewById(R.id.nama_kerja);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapKerja);
        mapFragment.getMapAsync(this);


        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        // Show Sydney on the map.
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);



    }


    @Override
    public void onCameraIdle() {
        CameraPosition position=mMap.getCameraPosition();

        Log.d("onCameraIdle",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
    }

    @Override
    public void onCameraMoveCanceled() {
        CameraPosition position=mMap.getCameraPosition();

        Log.d("onCameraMoveCanceled",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));

        if(mCurrLocationMarker != null){
            mCurrLocationMarker.remove();}
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=mMap.getCameraPosition();

        Log.d("onCameraMove",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .title("im here");

        if(mCurrLocationMarker!= null){
           mCurrLocationMarker.remove();}
        mCurrLocationMarker = mMap.addMarker(options);
    }


    @Override
    public void onCameraMoveStarted(int i) {
        CameraPosition position=mMap.getCameraPosition();

        Log.d("onCameraMoveStarted",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
    }


    @Override
    public void onLocationChanged(Location location) {
        double lattitude = location.getLatitude();
        double longitude = location.getLongitude();

        //Place current location marker
        LatLng latLng = new LatLng(lattitude, longitude);


        if(mCurrLocationMarker!=null){
            mCurrLocationMarker.setPosition(latLng);
        }else{
            mCurrLocationMarker = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    .title("I am here"));
        }

        namakerja.append("Lattitude: " + lattitude + "  Longitude: " + longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}
