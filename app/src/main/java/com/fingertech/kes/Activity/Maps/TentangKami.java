package com.fingertech.kes.Activity.Maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fingertech.kes.Activity.Search.LokasiAnda;
import com.fingertech.kes.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TentangKami extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap maptentang;
    TextView alamat_tentang,office1,office2,mobile,web,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang_kami);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_tentang);
        alamat_tentang        = (TextView)findViewById(R.id.alamat_tentang);
        office1               = (TextView)findViewById(R.id.office1);
        office2               = (TextView)findViewById(R.id.office2);
        mobile                  = (TextView)findViewById(R.id.Mobile);
        web                     = (TextView)findViewById(R.id.web);
        email                   = (TextView)findViewById(R.id.Email);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapTentang);
        mapFragment.getMapAsync(this);

        Linkify.addLinks(office1, Linkify.ALL);
        office1.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(office2, Linkify.ALL);
        office2.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(mobile, Linkify.ALL);
        mobile.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(web, Linkify.ALL);
        web.setLinkTextColor(Color.parseColor("#ffffff"));
        Linkify.addLinks(email, Linkify.ALL);
        email.setLinkTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        maptentang = googleMap;
        final LatLng latLng = new LatLng(-6.110631, 106.776096);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
        final MarkerOptions markerOptions = new MarkerOptions()
                                            .position(latLng)
                                            .title("PT Gaia Persada")
                                            .icon(bitmapDescriptorFromVector(TentangKami.this, R.drawable.ic_map));

        //move map camera
        maptentang.addMarker(markerOptions);
        maptentang.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        maptentang.animateCamera(CameraUpdateFactory.zoomTo(15));

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

}
