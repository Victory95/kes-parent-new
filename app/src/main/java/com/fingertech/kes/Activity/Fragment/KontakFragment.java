package com.fingertech.kes.Activity.Fragment;


import android.Manifest;
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

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;
import static com.fingertech.kes.Activity.ParentMain.MY_PERMISSIONS_REQUEST_LOCATION;
import com.fingertech.kes.Activity.Fragment.PlaceInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 */
public class KontakFragment extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnCameraMoveListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener {


    private static final float DEFAULT_ZOOM = 15;
    private static final String TAG= "KES";
    private GoogleMap mmap;
    private LocationRequest mlocationRequest;
    private Marker mcurrLocationMarker;
    private Location mlastLocation;
    GoogleApiClient mGoogleApiClient,mGoogleApiClient2;
    private TextView namakontak,namaalamat;
    private AutoCompleteTextView et_alamat;


    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private PlaceInfo mPlace;

    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    public static KontakFragment newInstance(){
        // Required empty public constructor
        KontakFragment  Fragment = new KontakFragment();
        return Fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kontak, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapKontak);
        mapFragment.getMapAsync(this);
        namakontak = (TextView)view.findViewById(R.id.nama_alamat);
        et_alamat = (AutoCompleteTextView) view.findViewById(R.id.et_alamaT);
        namaalamat = (TextView)view.findViewById(R.id.nama_alamat);

        init();
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
                        mmap.setMyLocationEnabled(true);
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
    private void init(){
        Log.d(TAG, "init: initializing");
        mGoogleApiClient2 = new GoogleApiClient
                .Builder(getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage((FragmentActivity) getContext(), this)
                .build();

        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(getContext(), mGoogleApiClient2,
                LAT_LNG_BOUNDS, null);

        et_alamat.setAdapter(mPlaceAutocompleteAdapter);
        et_alamat.setOnItemClickListener(mAutocompleteClickListener);
        et_alamat.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                    //execute our method for searching
                    geoLocate();
                }

                return false;
            }
        });
    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = et_alamat.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }

        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }



    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map));
            mmap.addMarker(options).setDraggable(true);
            if(mcurrLocationMarker!= null){
                mcurrLocationMarker.remove();}
        }

        hideSoftKeyboard();
    }

    private void hideSoftKeyboard(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient2, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);


        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                // Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
            // Get the Place object from the buffer.
            final Place place = places.get(0);

            // Format details of the place for display and show it in a TextView.
            et_alamat.setText(formatPlaceDetails(getResources(), place.getAddress()));
            namaalamat.setText(formatPlaceDetails(getResources(),place.getAddress()));


            Log.i(TAG, "Place details received: " + place.getAddress());

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace.getName());

            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence address
                                               ) {
        Log.e(TAG, res.getString(R.string.place_details,address));
        return Html.fromHtml(res.getString(R.string.place_details,  address));

    }

    @Override
    public void onCameraMoveStarted(int i) {
        CameraPosition position=mmap.getCameraPosition();

        Log.d("onCameraIdle",
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
        mPlace = new PlaceInfo();
        Geocoder geocoder = new Geocoder(getContext());

        List<Address> addresses = null;
        String addressText="";


        try {
            addresses = geocoder.getFromLocation(mlastLocation.getLatitude(), mlastLocation.getLongitude(),1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(addresses != null && addresses.size() > 0 ){
            Address address = addresses.get(0);

            addressText = String.format("%s, %s, %s",
                    address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                    address.getLocality(),
                    address.getCountryName());
        }
        namaalamat.setText(formatPlaceDetails(getResources(),addressText));

    }
}