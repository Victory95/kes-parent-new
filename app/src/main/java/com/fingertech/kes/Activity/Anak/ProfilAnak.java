package com.fingertech.kes.Activity.Anak;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.DaftarParent;
import com.fingertech.kes.Activity.Maps.TentangKami;
import com.fingertech.kes.Activity.MenuUtama;
import com.fingertech.kes.Activity.ProfileParent;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilAnak extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    CardView cv_profile,cv_data,cv_kontak,cv_alamat,btn_editanak,btn_alamat,btn_kontak;
    LinearLayout show_data,show_kontak,show_alamat;
    ImageView arrow_data,arrow_kontak,arrow_alamat;
    TextView hint_data,hint_alamat,hint_kontak,kelas_anak,jenis_kelamin,nis,nisn,tempat_lahir,tanggal_lahir,kewarganegaraan,nama_anak_profile;
    TextView nomor_rumah,nomor_hp,email,skun,nokps,penerimaan_kps,alamat,rt,kelurahan,kecamatan,kode_pos,status_tinggal,transportasi,Agama,kebutuhan_khusus,rombongan_belajar;
    GoogleMap mapAnak;
    CircleImageView image_anak;
    private Boolean clicked = false;
    Auth mApiInterface;
    int status;
    double latitudeanak,longitudeanak;
    String code;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ProgressDialog dialog;
    String authorization,school_code,student_id,parent_nik;
    String Base_anak,kelas,rombel,kebutuhankhusus,agama;
    String namalengkap,jeniskelamin,Nis,Nisn,tempatlahir,tanggallahir,kewarga_negaraan,nomorrumah,nomorhp,Email,sk_un,no_kps,penerimaankps,Alamat,Rt,Kelurahan,Kecamatan,kodepos,statustinggal,rw,transport,foto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_anak);
        toolbar         = (Toolbar)findViewById(R.id.toolbar_profile_anak);
        cv_profile      = (CardView)findViewById(R.id.btn_image_anak);
        cv_data         = (CardView)findViewById(R.id.klik_data_anak);
        cv_kontak       = (CardView)findViewById(R.id.klik_kontak);
        cv_alamat       = (CardView)findViewById(R.id.klik_alamat);
        btn_editanak    = (CardView)findViewById(R.id.btn_edit_data);
        btn_kontak      = (CardView)findViewById(R.id.btn_edit_kontak);
        btn_alamat      = (CardView)findViewById(R.id.btn_edit_alamat);
        show_data       = (LinearLayout)findViewById(R.id.show_data_anak);
        show_kontak     = (LinearLayout)findViewById(R.id.show_kontak);
        show_alamat     = (LinearLayout)findViewById(R.id.show_alamat);
        arrow_data      = (ImageView)findViewById(R.id.arrow_data);
        arrow_kontak    = (ImageView)findViewById(R.id.arrow_kontak);
        arrow_alamat    = (ImageView)findViewById(R.id.arrow_alamat);
        hint_data       = (TextView)findViewById(R.id.hint_data);
        hint_kontak     = (TextView)findViewById(R.id.hint_kontak);
        hint_alamat     = (TextView)findViewById(R.id.hint_alamat);
        kelas_anak      = (TextView)findViewById(R.id.kelas_anak);
        jenis_kelamin   = (TextView)findViewById(R.id.jenis_kelamin_anak);
        nis             = (TextView)findViewById(R.id.nis_anak);
        nisn            = (TextView)findViewById(R.id.nisn_anak);
        tanggal_lahir   = (TextView)findViewById(R.id.tanggal_lahir_anak);
        tempat_lahir    = (TextView)findViewById(R.id.tempat_lahir_anak);
        kewarganegaraan = (TextView)findViewById(R.id.kewarganegaraan_anak);
        nomor_rumah     = (TextView)findViewById(R.id.phone_anak);
        nomor_hp        = (TextView)findViewById(R.id.handphone_anak);
        email           = (TextView)findViewById(R.id.email_anak);
        skun            = (TextView)findViewById(R.id.skun_anak);
        nokps           = (TextView)findViewById(R.id.nomor_kps);
        penerimaan_kps  = (TextView)findViewById(R.id.penerimaan_kps);
        alamat          = (TextView)findViewById(R.id.alamat_anak);
        rt              = (TextView)findViewById(R.id.rt_anak);
        kelurahan       = (TextView)findViewById(R.id.kelurahan_anak);
        kecamatan       = (TextView)findViewById(R.id.kecamatan_anak);
        kode_pos        = (TextView)findViewById(R.id.kodepos_anak);
        status_tinggal  = (TextView)findViewById(R.id.status_tinggal);
        transportasi    = (TextView)findViewById(R.id.transportasi);
        Agama           = (TextView)findViewById(R.id.agama);
        kebutuhan_khusus= (TextView)findViewById(R.id.kebutuhan_khusus);
        rombongan_belajar   = (TextView)findViewById(R.id.rombel);
        image_anak      = (CircleImageView)findViewById(R.id.image_profile_anak);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        Base_anak       = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";



        cv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_data.setVisibility(View.VISIBLE);
                    show_data.setVisibility(View.GONE);
                    arrow_data.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_data.setVisibility(View.GONE);
                    show_data.setVisibility(View.VISIBLE);
                    arrow_data.setBackgroundResource(R.drawable.ic_up_arrow_white);

                }
            }
        });
        cv_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_kontak.setVisibility(View.VISIBLE);
                    show_kontak.setVisibility(View.GONE);
                    arrow_kontak.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_kontak.setVisibility(View.GONE);
                    show_kontak.setVisibility(View.VISIBLE);
                    arrow_kontak.setBackgroundResource(R.drawable.ic_up_arrow_white);

                }
            }
        });

        cv_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicked) {
                    hint_alamat.setVisibility(View.VISIBLE);
                    show_alamat.setVisibility(View.GONE);
                    arrow_alamat.setBackgroundResource(R.drawable.ic_arrow_down_white);
                    clicked = false;
                }else {
                    clicked = true;
                    hint_alamat.setVisibility(View.GONE);
                    show_alamat.setVisibility(View.VISIBLE);
                    arrow_alamat.setBackgroundResource(R.drawable.ic_up_arrow_white);

                }
            }
        });

        authorization = getIntent().getStringExtra("authorization");
        school_code   = getIntent().getStringExtra("school_code");
        student_id    = getIntent().getStringExtra("student_id");
        parent_nik    = getIntent().getStringExtra("parent_nik");

        data_student_get();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(namalengkap);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapAnak);
        mapFragment.getMapAsync(this);



        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_profile_anak);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_profile_anak);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.school);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")

            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant =
                        palette.getVibrantSwatch();

                if (vibrant != null) {

                    collapsingToolbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimary));
                    collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
                }
            }

        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    public void data_student_get(){
        progressBar();
        showDialog();
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization.toString(), school_code.toString().toLowerCase(), student_id.toString(),parent_nik.toString());
        call.enqueue(new Callback<JSONResponse.DetailStudent>() {
            @Override
            public void onResponse(Call<JSONResponse.DetailStudent> call, Response<JSONResponse.DetailStudent> response) {
                Log.d("TAG",response.code()+"");
                hideDialog();

                JSONResponse.DetailStudent resource = response.body();
                status = resource.status;
                code = resource.code;

                String DTS_SCS_0001 = getResources().getString(R.string.DTS_SCS_0001);
                String DTS_ERR_0001 = getResources().getString(R.string.DTS_ERR_0001);

                if (status == 1 && code.equals("DTS_SCS_0001")) {
                    namalengkap         = response.body().getData().getFullname();
                    jeniskelamin        = response.body().getData().getGender();
                    kelas               = response.body().getData().getEdulevel_id();
                    Nis                 = response.body().getData().getNik();
                    Nisn                = response.body().getData().getNisn();
                    rombel              = response.body().getData().getRombel();
                    tempatlahir         = response.body().getData().getBirth_place();
                    tanggallahir        = response.body().getData().getBirth_date();
                    agama               = response.body().getData().getReligion();
                    kebutuhankhusus     = response.body().getData().getSpecial_needs();
                    kewarga_negaraan    = response.body().getData().getCitizen_status();
                    nomorrumah          = response.body().getData().getHome_phone();
                    nomorhp             = response.body().getData().getMobile_phone();
                    Email               = response.body().getData().getEmail();
                    sk_un               = response.body().getData().getSkhun();
                    no_kps              = response.body().getData().getNo_kps();
                    penerimaankps       = response.body().getData().getPenerima_kps();
                    Alamat              = response.body().getData().getAddress();
                    Rt                  = response.body().getData().getRt();
                    rw                  = response.body().getData().getRw();
                    Kelurahan           = response.body().getData().getKelurahan();
                    Kecamatan           = response.body().getData().getKecamatan();
                    kodepos             = response.body().getData().getPost_code();
                    statustinggal       = response.body().getData().getJenis_tinggal();
                    transport           = response.body().getData().getTransportasi();
                    foto                = response.body().getData().getPicture();
                    latitudeanak        = response.body().getData().getLatitude();
                    longitudeanak       = response.body().getData().getLongitude();

                    if (kelas.toString().equals("4")){
                        kelas = "SD 1";
                    }else if (kelas.toString().equals("5")){
                        kelas = "SD 2";
                    }else if (kelas.toString().equals("6")){
                        kelas = "SD 3";
                    }else if (kelas.toString().equals("7")){
                        kelas = "SD 4";
                    }else if (kelas.toString().equals("8")){
                        kelas = "SD 5";
                    }else if (kelas.toString().equals("9")){
                        kelas = "SD 6";
                    }else if (kelas.toString().equals("10")){
                        kelas = "SMP 1";
                    }else if (kelas.toString().equals("11")){
                        kelas = "SMP 2";
                    }else if (kelas.toString().equals("12")){
                        kelas = "SMP 3";
                    }else if (kelas.toString().equals("13")){
                        kelas = "SMA 1";
                    }else if (kelas.toString().equals("14")){
                        kelas = "SMA 2";
                    }else if (kelas.toString().equals("15")){
                        kelas = "SMA 3";
                    }
                    kelas_anak.setText(kelas);
                    jenis_kelamin.setText(jeniskelamin);
                    nis.setText(Nis);
                    nisn.setText(Nisn);
                    tempat_lahir.setText(tempatlahir);
                    tanggal_lahir.setText(tanggallahir);
                    rombongan_belajar.setText(rombel);
                    Agama.setText(agama);
                    kebutuhan_khusus.setText(kebutuhankhusus);
                    kewarganegaraan.setText(kewarga_negaraan);
                    nomor_rumah.setText(nomorrumah);
                    nomor_hp.setText(nomorhp);
                    email.setText(Email);
                    skun.setText(sk_un);
                    nokps.setText(no_kps);
                    penerimaan_kps.setText(penerimaankps);
                    alamat.setText(Alamat);
                    rt.setText(Rt + "/" + rw);
                    kelurahan.setText(Kelurahan);
                    kecamatan.setText(Kecamatan);
                    kode_pos.setText(kodepos);
                    status_tinggal.setText(statustinggal);
                    transportasi.setText(transport);

                    String imagefiles = Base_anak + foto;
                    Picasso.with(ProfilAnak.this).load(imagefiles).into(image_anak);

                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                        boolean isShow = true;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                collapsingToolbarLayout.setTitle(namalengkap);
                                cv_profile.setVisibility(View.GONE);
                                isShow = true;
                            } else if(isShow) {
                                collapsingToolbarLayout.setTitle(namalengkap);//carefull there should a space between double quote otherwise it wont work
                                cv_profile.setVisibility(View.VISIBLE);
                                isShow = false;
                            }
                        }
                    });

                } else {
                    if(status == 0 && code.equals("DTS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DTS_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.DetailStudent> call, Throwable t) {
                hideDialog();
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
        dialog = new ProgressDialog(ProfilAnak.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
    mapAnak = googleMap;
        final LatLng latLng = new LatLng(latitudeanak, longitudeanak);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
        final MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("PT Gaia Persada")
                .icon(bitmapDescriptorFromVector(ProfilAnak.this, R.drawable.ic_map));

        //move map camera
        mapAnak.addMarker(markerOptions);
        mapAnak.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapAnak.animateCamera(CameraUpdateFactory.zoomTo(17));

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

}
