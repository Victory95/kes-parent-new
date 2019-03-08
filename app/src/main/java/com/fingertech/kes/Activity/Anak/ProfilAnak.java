package com.fingertech.kes.Activity.Anak;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fingertech.kes.Service.App.getContext;

public class ProfilAnak extends AppCompatActivity implements OnMapReadyCallback {

    Toolbar toolbar;
    CardView cv_profile,edit_profile;
    Button cv_data,cv_kontak,cv_alamat;
    LinearLayout show_data,show_kontak,show_alamat;
    TextView hint_data,hint_alamat,hint_kontak,kelas_anak,jenis_kelamin,nik,nis,nisn,tempat_lahir,tanggal_lahir,kewarganegaraan,nama_anak_profile;
    TextView nomor_rumah,nomor_hp,email,skun,nokps,nama_anak,penerimaan_kps,alamat,rt,kelurahan,kecamatan,kode_pos,status_tinggal,transportasi,Agama,kebutuhan_khusus,rombongan_belajar;
    GoogleMap mapAnak;
    ImageView image_anak;
    private Boolean clicked = false;
    Auth mApiInterface;
    int status;
    double latitudeanak,longitudeanak;
    String code;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    ProgressDialog dialog;
    String authorization,school_code,student_id,parent_nik,school_name;
    String Base_anak,kelas,rombel,kebutuhankhusus,agama;
    String namalengkap,jeniskelamin,Nik,Sekolah,Nis,Nisn,tempatlahir,tanggallahir,kewarga_negaraan,nomorrumah,nomorhp,Email,sk_un,no_kps,penerimaankps,Alamat,Rt,Kelurahan,Kecamatan,kodepos,statustinggal,rw,transport,foto;
    private TextView tv_line_boundaryLeft, tv_line_boundaryRight;
    SwipeRefreshLayout swipeRefreshLayout;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_anak);
        toolbar         = (Toolbar)findViewById(R.id.toolbar_profile_anak);
        cv_profile      = (CardView)findViewById(R.id.btn_image_anak);
        cv_data         = findViewById(R.id.btn_data);
        cv_kontak       = findViewById(R.id.btn_kontak);
        cv_alamat       = findViewById(R.id.btn_alamat);
        show_data       = (LinearLayout)findViewById(R.id.show_data_anak);
        show_kontak     = (LinearLayout)findViewById(R.id.show_kontak);
        show_alamat     = (LinearLayout)findViewById(R.id.show_alamat);
        kelas_anak      = (TextView)findViewById(R.id.sekolah_anak);
        nama_anak_profile   = findViewById(R.id.namaanak);
        jenis_kelamin   = (TextView)findViewById(R.id.jenis_kelamin_anak);
        nis             = (TextView)findViewById(R.id.nis);
        nik             = findViewById(R.id.nik);
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
        tv_line_boundaryLeft   = (TextView) findViewById(R.id.tv_line_boundaryLeft);
        tv_line_boundaryRight  = (TextView) findViewById(R.id.tv_line_boundaryRight);
        image_anak      = findViewById(R.id.image_profil_anak);
        edit_profile    = findViewById(R.id.btn_edit);
        mApiInterface   = ApiClient.getClient().create(Auth.class);
        Base_anak       = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

        cv_data.setBackground(ContextCompat.getDrawable(ProfilAnak.this, R.drawable.rectangle_line_blue));
        cv_data.setTextColor(getResources().getColor(R.color.default_background));
        tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

        ////// deactive
        cv_kontak.setBackgroundColor(Color.TRANSPARENT);
        cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));

        tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

        cv_alamat.setBackgroundColor(Color.TRANSPARENT);
        cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
        show_data.setVisibility(View.VISIBLE);
        show_alamat.setVisibility(View.GONE);
        show_kontak.setVisibility(View.GONE);

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),EditProfileAnak.class);
                intent.putExtra("authorization",authorization);
                intent.putExtra("school_code",school_code);
                intent.putExtra("student_id",student_id);
                intent.putExtra("parent_nik",parent_nik);
                startActivity(intent);
                finish();
            }
        });
        cv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_data.setBackground(ContextCompat.getDrawable(ProfilAnak.this, R.drawable.rectangle_line_blue));
                cv_data.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.default_background));

                ////// deactive
                cv_kontak.setBackgroundColor(Color.TRANSPARENT);
                cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

                cv_alamat.setBackgroundColor(Color.TRANSPARENT);
                cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.VISIBLE);
                show_alamat.setVisibility(View.GONE);
                show_kontak.setVisibility(View.GONE);
            }
        });

        cv_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_kontak.setBackground(ContextCompat.getDrawable(ProfilAnak.this, R.drawable.rectangle_line_blue));
                cv_kontak.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.colorPrimary));

                ////// deactive
                cv_data.setBackgroundColor(Color.TRANSPARENT);
                cv_data.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.default_background));

                cv_alamat.setBackgroundColor(Color.TRANSPARENT);
                cv_alamat.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.GONE);
                show_alamat.setVisibility(View.GONE);
                show_kontak.setVisibility(View.VISIBLE);
            }
        });

        cv_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////// active
                cv_alamat.setBackground(ContextCompat.getDrawable(ProfilAnak.this, R.drawable.rectangle_line_blue));
                cv_alamat.setTextColor(getResources().getColor(R.color.default_background));
                tv_line_boundaryLeft.setTextColor(getResources().getColor(R.color.colorPrimary));

                ////// deactive
                cv_data.setBackgroundColor(Color.TRANSPARENT);
                cv_data.setTextColor(getResources().getColor(R.color.colorPrimary));

                tv_line_boundaryRight.setTextColor(getResources().getColor(R.color.colorPrimary));

                cv_kontak.setBackgroundColor(Color.TRANSPARENT);
                cv_kontak.setTextColor(getResources().getColor(R.color.colorPrimary));
                show_data.setVisibility(View.GONE);
                show_alamat.setVisibility(View.VISIBLE);
                show_kontak.setVisibility(View.GONE);
            }
        });
//        authorization = getIntent().getStringExtra("authorization");
//        school_code = getIntent().getStringExtra("school_code");
//        student_id = getIntent().getStringExtra("student_id");
//        parent_nik = getIntent().getStringExtra("parent_nik");
//        school_name = getIntent().getStringExtra("school_name");
        sharedPreferences   = getSharedPreferences(MenuUtama.my_viewpager_preferences, Context.MODE_PRIVATE);
        authorization       = sharedPreferences.getString("authorization",null);
        school_code         = sharedPreferences.getString("school_code",null);
        student_id          = sharedPreferences.getString("student_id",null);
        school_name         = sharedPreferences.getString("school_name",null);
        parent_nik          = sharedPreferences.getString("parent_nik",null);
        Log.d("gagal",authorization+"/"+parent_nik+"/"+school_code+"/"+student_id);
        if (authorization != null || school_code != null || student_id != null || parent_nik != null) {
            data_student_get();
        }else {
            Toast.makeText(getApplicationContext(),authorization+"/"+school_code+"/"+student_id+"/"+parent_nik,Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(),"Harap refresh kembali",Toast.LENGTH_LONG).show();
        }

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
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Profile Anak");
                    cv_profile.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle("");//carefull there should a space between double quote otherwise it wont work
                    cv_profile.setVisibility(View.VISIBLE);
                    isShow = false;
                }
            }
        });
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
        Call<JSONResponse.DetailStudent> call = mApiInterface.kes_detail_student_get(authorization.toString(), school_code.toLowerCase().toString(),student_id.toString(),parent_nik.toString());
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
                    Nis                 = response.body().getData().getMember_code();
                    Sekolah             = response.body().getData().getClassroom_id();
                    Nik                 = response.body().getData().getNik();
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
                    latitudeanak        = Double.parseDouble(response.body().getData().getLatitude());
                    longitudeanak       = Double.parseDouble(response.body().getData().getLongitude());

                    if (kelas.toString().equals("4")){
                        kelas = "1 SD";
                    }else if (kelas.toString().equals("5")){
                        kelas = "2 SD";
                    }else if (kelas.toString().equals("6")){
                        kelas = "3 SD";
                    }else if (kelas.toString().equals("7")){
                        kelas = "4 SD";
                    }else if (kelas.toString().equals("8")){
                        kelas = "5 SD";
                    }else if (kelas.toString().equals("9")){
                        kelas = "6 SD";
                    }else if (kelas.toString().equals("10")){
                        kelas = "1 SMP";
                    }else if (kelas.toString().equals("11")){
                        kelas = "2 SMP";
                    }else if (kelas.toString().equals("12")){
                        kelas = "3 SMP";
                    }else if (kelas.toString().equals("13")){
                        kelas = "1 SMA";
                    }else if (kelas.toString().equals("14")){
                        kelas = "2 SMA";
                    }else if (kelas.toString().equals("15")){
                        kelas = "3 SMA";
                    }
                    Log.d("location2",latitudeanak+"/"+longitudeanak);
                    kelas_anak.setText("Sedang bersekolah di "+school_name+" Kelas "+kelas);
                    jenis_kelamin.setText(jeniskelamin);
                    nis.setText("Nis : "+Nis);
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
                    nama_anak_profile.setText(namalengkap);
                    nik.setText("Nik : "+Nik);
                    String imagefiles = Base_anak + foto;
                    int[] androidColors = getContext().getResources().getIntArray(R.array.androidcolors);
                    int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
                    String random  = String.valueOf(randomAndroidColor);

                    if (foto.equals("")){
                        Glide.with(ProfilAnak.this).load("https://ui-avatars.com/api/?name="+namalengkap+"&background=40bfe8&color=fff").into(image_anak);
                    }
                    Picasso.get().load(imagefiles).into(image_anak);

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
        Log.d("location",latLng.latitude+"/"+latLng.longitude);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(16).build();
        final MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Location")
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
