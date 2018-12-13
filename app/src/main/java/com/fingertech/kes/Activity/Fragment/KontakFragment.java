package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 */
public class KontakFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener {


    private GoogleMap mmap;
    private LocationRequest mlocationRequest;
    private Marker mcurrLocationMarker;
    private Location mlastLocation;
    private TextView namaalamat;
    private ImageView arros;
    GoogleApiClient mGoogleApiClient;

    Double currentLatitude;
    Double currentLongitude;

    String location;


    public static KontakFragment newInstance(){
        // Required empty public constructor
        KontakFragment  Fragment = new KontakFragment();
        return Fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kontak, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapKontak);
        mapFragment.getMapAsync(this);
        namaalamat = (TextView)view.findViewById(R.id.nama_alamat);
        arros = (ImageView)view.findViewById(R.id.arroW);
        arros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), maps_kerja.class);
                startActivityForResult(intent,1);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mmap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
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

        mmap.setOnCameraMoveStartedListener(this);
        mmap.setOnCameraMoveListener(this);
        mmap.setOnCameraMoveCanceledListener(this);
        mmap.setOnCameraIdleListener(this);


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

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map));

        //move map camera
        mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mmap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(final Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..."+arg0.getPosition().latitude+"..."+arg0.getPosition().longitude);
                mmap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                mmap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        mmap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                        Toast.makeText(getContext(), "The camera is moving.",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }

        updateLocation(location);
        getAddress();

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
        CameraPosition position=mmap.getCameraPosition();
        Log.d("onCameraStarted",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
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
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map))
                .title("im here");

        if(mcurrLocationMarker!= null){
            mcurrLocationMarker.remove();}

        mcurrLocationMarker = mmap.addMarker(options);
    }

    @Override
    public void onCameraMoveCanceled() {
        CameraPosition position=mmap.getCameraPosition();

        Log.d("onCameraMoveStarted",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
    }

    @Override
    public void onCameraIdle() {
        CameraPosition position=mmap.getCameraPosition();
        Log.d("onCameraIdle",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
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
                namaalamat.setText(address +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void getAddress() {

        try {

            Geocoder gcd = new Geocoder(getContext()
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

            Toast.makeText(getContext(), ex.getMessage(),

                    Toast.LENGTH_LONG).show();



        }

    }

    void updateLocation(Location location) {

        mlastLocation = location;

        currentLatitude = mlastLocation.getLatitude();

        currentLongitude = mlastLocation.getLongitude();



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
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map));

                //move map camera
                mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(mcurrLocationMarker!= null){
                    mcurrLocationMarker.remove();}
                mcurrLocationMarker = mmap.addMarker(markerOptions);
                namaalamat.setText(strEditText);
            }
        }
    }


}