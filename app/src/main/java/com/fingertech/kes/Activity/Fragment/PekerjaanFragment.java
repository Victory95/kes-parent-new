package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;

/**
 * A simple {@link Fragment} subclass.
 */
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

    Double CurrentLatitude;
    Double CurrentLongitude;

    String location;


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
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapKerja);
        mapFragment.getMapAsync(this);
        arro = (ImageView)view.findViewById(R.id.arrow);
        arro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), maps_kerja.class);
                startActivityForResult(intent,1);
            }
        });
        namakerja = (TextView) view.findViewById(R.id.nama_kerja);

        // Spinner click listener
        Spinner et_pekerjaan = (Spinner) view.findViewById(R.id.sp_pekerjaan);
        String[] pendidikan = {"Pendidikan","SD","SMP","SMA","D3","S1","S2","S3"};

        final List<String> plantsList = new ArrayList<>(Arrays.asList(pendidikan));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_text,plantsList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_pekerjaan.setAdapter(spinnerArrayAdapter);

        // Spinner click listener
        Spinner et_penghasilan = (Spinner) view.findViewById(R.id.sp_penghasilan);
        String[] penghasilan = {"Penghasilan","Dibawah 1 Juta","1 - 3 Juta","4 - 5 Juta","6 - 7 Juta","8 - 9 Juta","10 - 11 Juta","12 - 13 Juta","14 - 15 Juta","16 - 17 Juta","18 - 19 Juta","Diatas 20 Juta"};

        final List<String> penghasil = new ArrayList<>(Arrays.asList(penghasilan));
        // Initializing an ArrayAdapter
        final ArrayAdapter<String> ArrayAdapter = new ArrayAdapter<String>(
                getActivity(),R.layout.spinner_text,penghasil){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        ArrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        et_penghasilan.setAdapter(ArrayAdapter);


        return view;
    }

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
        CameraPosition position=mMap.getCameraPosition();

        Log.d("onCameraIdle",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));

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
                namakerja.setText(address1 +"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        mlastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();

        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.map));

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
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
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
                mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                    @Override
                    public boolean onMyLocationButtonClick() {
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
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

    void getAddress() {

        try {

            Geocoder gcd = new Geocoder(getContext());

            List<Address> addresses = gcd.getFromLocation(CurrentLatitude,

                    CurrentLongitude, 100);

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

        CurrentLatitude = mlastLocation.getLatitude();

        CurrentLongitude = mlastLocation.getLongitude();



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
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(mCurrLocationMarker!= null){
                    mCurrLocationMarker.remove();}
                mCurrLocationMarker = mMap.addMarker(markerOptions);
                namakerja.setText(strEditText);
            }
        }
    }


}
