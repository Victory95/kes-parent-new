package com.fingertech.kes.Activity.Search;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Maps.MapLokasi;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LokasiAnda extends AppCompatActivity {


    public static final int PICK_UP = 0;
    private static int REQUEST_CODE = 0;
    Button PilihLokasi;
    TextView pilihmap;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lokasi_anda);

        PilihLokasi = findViewById(R.id.pilih_lokasi);
        pilihmap    = findViewById(R.id.pilihmap);
        final Toolbar toolbar = findViewById(R.id.toolbar_dua);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PilihLokasi.setOnClickListener(v -> showPlaceAutoComplete(PICK_UP));
        pilihmap.setOnClickListener(v -> {
            Intent mIntent = new Intent(LokasiAnda.this,MapLokasi.class);
            startActivityForResult(mIntent,2);
        });
    }

    private void showPlaceAutoComplete(int typeLocation) {
        // isi RESUT_CODE tergantung tipe lokasi yg dipilih.
        // titik jmput atau tujuan
        REQUEST_CODE = typeLocation;

        // Filter hanya tmpat yg ada di Indonesia
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("ID").build();
        try {
            // Intent untuk mengirim Implisit Intent
            Intent mIntent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .setFilter(typeFilter)
                    .build(this);
            // jalankan intent impilist
            startActivityForResult(mIntent, REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace(); // cetak error
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace(); // cetak error
            // Display Toast
            Toast.makeText(this, "Layanan Play Services Tidak Tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(this, "Sini Gaes", Toast.LENGTH_SHORT).show();
        // Pastikan Resultnya OK
        if(requestCode == REQUEST_CODE){
        if (resultCode == RESULT_OK) {
            //Toast.makeText(this, "Sini Gaes2", Toast.LENGTH_SHORT).show();
            // Tampung Data tempat ke variable
            Place placeData = PlaceAutocomplete.getPlace(this, data);

            if (placeData.isDataValid()) {
                // Show in Log Cat
                Log.d("autoCompletePlace Data", placeData.toString());
                List<Address> list = new ArrayList<>();

                // Dapatkan Detail
                String placeAddress = placeData.getAddress().toString();
                LatLng placeLatLng = placeData.getLatLng();
                String placeName = placeData.getName().toString();


                switch (REQUEST_CODE) {
                    case PICK_UP:
                        // Set ke widget lokasi asal
                        Double latitude     = placeLatLng.latitude;
                        Double longitude    = placeLatLng.longitude;
                        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                        try {
                            List<Address> addressList = geocoder.getFromLocation(placeLatLng.latitude, placeLatLng.longitude, 1);
                            if (addressList != null && addressList.size() > 0) {
                                String address      = addressList.get(0).getAddressLine(0);
                                String number       = addressList.get(0).getFeatureName();
                                city         = addressList.get(0).getSubLocality();
                                String state        = addressList.get(0).getAdminArea();
                                String country      = addressList.get(0).getCountryName();
                                String postalCode   = addressList.get(0).getPostalCode();
                                pilihmap.setText(city +"\n");
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
                        break;
                }
            }
        }
    }else if (requestCode == 2){
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
