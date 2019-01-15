package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.AnakMain;
import com.fingertech.kes.Activity.Maps.full_maps;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class TempatTinggalFragment extends Fragment  implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener{

    private Marker CurrLocationMarker;
    private TextView namatempat;
    private LocationRequest mlocationRequest;
    private Location mlastLocation;
    private GoogleMap Mmap;
    private ImageView arrom;
    GoogleApiClient mGoogleApiClient;
    Double CurrentLatitude;
    Double CurrentLongitude;
    String location;

    public TempatTinggalFragment newInstance() {
        // Required empty public constructor
        TempatTinggalFragment  Fragment = new TempatTinggalFragment();
        return Fragment;
    }

    ViewPager ParentPager;
    AnakMain anakMain;
    Button buttonBerikutnya,buttonKembali;
    private LinearLayout indicator;
    private int mDotCount;
    private LinearLayout[] mDots;
    private AnakMain.FragmentAdapter fragmentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tempat_tinggal, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.mapTinggal);
        mapFragment.getMapAsync(this);
        namatempat          = (TextView)view.findViewById(R.id.nama_rumah);
        arrom               = (ImageView)view.findViewById(R.id.arrom);
        anakMain            = (AnakMain)getActivity();
        ParentPager         = (ViewPager) anakMain.findViewById(R.id.PagerAnak);
        indicator           = (LinearLayout) view.findViewById(R.id.indicators);
        buttonKembali       = (Button)view.findViewById(R.id.btn_kembali);
        buttonBerikutnya    = (Button)view.findViewById(R.id.btn_berikut);
        fragmentAdapter     = new AnakMain.FragmentAdapter(getActivity().getSupportFragmentManager());

        buttonBerikutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(+1), true);
            }
        });

        buttonKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParentPager.setCurrentItem(getItem(-1), true);
            }
        });

        setUiPageViewController();
        for (int i = 0; i < mDotCount; i++) {
            mDots[i].setBackgroundResource(R.drawable.nonselected_item);
        }
        mDots[2].setBackgroundResource(R.drawable.selected_item);
        arrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), full_maps.class);
                startActivityForResult(intent,1);
            }
        });

        return view;
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
    public void onLocationChanged(Location location) {
        mlastLocation = location;
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();

        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));

        //move map camera
        Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
            mGoogleApiClient.connect();
        }

        updateLocation(location);
        getAddress();
    }

    @Override
    public void onCameraIdle() {
        CameraPosition position=Mmap.getCameraPosition();

        Log.d("onCameraIdle",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));

        LatLng LatLng = Mmap.getCameraPosition().target;
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
                namatempat.setText(address1+"\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraMoveCanceled() {
        CameraPosition position=Mmap.getCameraPosition();

        Log.d("onCameraMoveCanceled",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));

        if(CurrLocationMarker != null){
            CurrLocationMarker.remove();}
    }

    @Override
    public void onCameraMove() {
        CameraPosition position=Mmap.getCameraPosition();

        Log.d("onCameraMove",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
        MarkerOptions options = new MarkerOptions()
                .position(position.target)
                .icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map))
                .title("im here");

        if(CurrLocationMarker!= null){
            CurrLocationMarker.remove();}
        CurrLocationMarker = Mmap.addMarker(options);

    }

    @Override
    public void onCameraMoveStarted(int i) {
        CameraPosition position=Mmap.getCameraPosition();

        Log.d("onCameraMoveStarted",
                String.format("lat: %f, lon: %f, zoom: %f, tilt: %f",
                        position.target.latitude,
                        position.target.longitude, position.zoom,
                        position.tilt));
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Mmap = googleMap;

        Mmap.setOnCameraIdleListener(this);
        Mmap.setOnCameraMoveStartedListener(this);
        Mmap.setOnCameraMoveListener(this);
        Mmap.setOnCameraMoveCanceledListener(this);

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                Mmap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            Mmap.setMyLocationEnabled(true);
        }
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();
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
    void getAddress() {

        try {

            Geocoder gcd = new Geocoder(getContext()
                    , Locale.getDefault());

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
                markerOptions.icon(bitmapDescriptorFromVector(getActivity(), R.drawable.ic_map));

                //move map camera
                Mmap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Mmap.animateCamera(CameraUpdateFactory.zoomTo(15));
                if(CurrLocationMarker!= null){
                    CurrLocationMarker.remove();}
                CurrLocationMarker = Mmap.addMarker(markerOptions);
                namatempat.setText(strEditText);
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
}
