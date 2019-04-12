package com.fingertech.kes.Activity.Search;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.fingertech.kes.Activity.Adapter.GooglePlacesAutoCompleteAdapter;
import com.fingertech.kes.Activity.Adapter.MapsAdapter;
import com.fingertech.kes.Activity.Maps.MapLokasi;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.DataMaps;
import com.fingertech.kes.Activity.Model.MapsModel;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.MapsTable;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class LokasiAnda extends AppCompatActivity {


    public static final int PICK_UP = 0;
    private static int REQUEST_CODE = 0;
    TextView pilihmap;
    String city;
    ListView listView;
    FloatingSearchView floatingSearchView;
    RecyclerView recyclerView;
    Toolbar toolbar;
    TextView tv_riwayat;

    GooglePlacesAutoCompleteAdapter autoCompleteAdapter;

    protected GeoDataClient mGeoDataClient;
    AutocompleteFilter filter =
            new AutocompleteFilter.Builder().setCountry("ID").build();
    private static final LatLngBounds BOUNDS_GREATER_SYDNEY = new LatLngBounds(
            new LatLng(0,0), new LatLng(0,0));

    List<MapsModel> mapsModelList = new ArrayList<>();
    MapsModel mapsModel;
    MapsAdapter mapsAdapter;

    DataMaps dataMaps = new DataMaps();
    ArrayList<HashMap<String, String>> row;
    MapsTable mapsTable = new MapsTable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokasi_anda);

        pilihmap        = findViewById(R.id.pilihmap);
        listView        = findViewById(R.id.listmaps);
        recyclerView    = findViewById(R.id.rv_maps);
        toolbar         = findViewById(R.id.toolbar_dua);
        tv_riwayat      = findViewById(R.id.riwayat);
        floatingSearchView  = findViewById(R.id.floating_search_view);
        mGeoDataClient = Places.getGeoDataClient(this, null);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pilihmap.setOnClickListener(v -> {
            Intent mIntent = new Intent(LokasiAnda.this,MapLokasi.class);
            startActivityForResult(mIntent,2);
        });
        autoCompleteAdapter = new GooglePlacesAutoCompleteAdapter(LokasiAnda.this,mGeoDataClient,BOUNDS_GREATER_SYDNEY,filter);
        listView.setAdapter(autoCompleteAdapter);
        listView.setTextFilterEnabled(true);
        floatingSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {
                listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                tv_riwayat.setVisibility(View.VISIBLE);
            }
        });
        floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                tv_riwayat.setVisibility(View.GONE);
                autoCompleteAdapter.getFilter().filter(newQuery);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction item = autoCompleteAdapter.getItem(position);
                String placeID = null;
                if (item != null) {
                    placeID = item.getPlaceId();
                }
                Task<PlaceBufferResponse> placeResult = mGeoDataClient.getPlaceById(placeID);
                placeResult.addOnCompleteListener(mUpdatePlaceDetailsCallback);
            }
        });
        getalldata();
    }

    private void getalldata(){
        row = mapsTable.getAllData();
        if (row.size() > 0){
            for (int i = 0; i < row.size();i++){
                String nama = row.get(i).get(DataMaps.KEY_Name);
                mapsModel = new MapsModel();
                mapsModel.setAlamat(nama);
                mapsModelList.add(mapsModel);
            }
            mapsAdapter = new MapsAdapter(mapsModelList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(LokasiAnda.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mapsAdapter);
            mapsAdapter.notifyDataSetChanged();
            mapsAdapter.setOnItemClickListener(new MapsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Double latitude     = Double.valueOf(Objects.requireNonNull(row.get(position).get(DataMaps.KEY_LATITUDE)));
                    Double longitude    = Double.valueOf(Objects.requireNonNull(row.get(position).get(DataMaps.KEY_LONGITUDE)));
                    Geocoder geocoder = new Geocoder(LokasiAnda.this, Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addressList != null && addressList.size() > 0) {
                            city         = addressList.get(0).getSubLocality();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(LokasiAnda.this,SearchingMAP.class);
                    intent.putExtra("address",city);
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude", longitude);
                    setResult(RESULT_OK, intent);
                    finish();
                    hideKeyboard(LokasiAnda.this);
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            if (requestCode == 2){
                if (resultCode == RESULT_OK) {
                    String address  = data.getStringExtra("address");
                    double lati     = data.getDoubleExtra("latitude",0.0);
                    double longi    = data.getDoubleExtra("longitude",0.0);
                    Intent intent = new Intent(LokasiAnda.this,SearchingMAP.class);
                    intent.putExtra("address", address);
                    intent.putExtra("latitude",lati);
                    intent.putExtra("longitude", longi);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
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

    private OnCompleteListener<PlaceBufferResponse> mUpdatePlaceDetailsCallback
            = task -> {
        try {
            PlaceBufferResponse places = task.getResult();
            final Place place;
            if (places != null) {
                place = places.get(0);
                String alamat       = String.valueOf(place.getAddress());
                LatLng latLng       = place.getLatLng();
                Double latitude     = latLng.latitude;
                Double longitude    = latLng.longitude;

                dataMaps.setName(alamat);
                dataMaps.setLat(latitude);
                dataMaps.setLng(longitude);
                mapsTable.insert(dataMaps);

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        city         = addressList.get(0).getSubLocality();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(LokasiAnda.this,SearchingMAP.class);
                intent.putExtra("address",city);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude", longitude);
                setResult(RESULT_OK, intent);
                finish();
                hideKeyboard(LokasiAnda.this);
                places.release();
            }
        } catch (RuntimeRemoteException e) {
            // Request did not complete successfully
            Log.e("KES", "Place query did not complete.", e);
        }
    };
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

}
