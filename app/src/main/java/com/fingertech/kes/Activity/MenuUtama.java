package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.config.GoogleDirectionConfiguration;
import com.akexorcist.googledirection.constant.TransitMode;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bumptech.glide.Glide;
import com.fingertech.kes.Activity.Adapter.CustomInfoWindowAdapter;
import com.fingertech.kes.Activity.Adapter.ItemSekolahAdapter;
import com.fingertech.kes.Activity.Adapter.ProfileAdapter;
import com.fingertech.kes.Activity.Fragment.MenuDuaFragment;
import com.fingertech.kes.Activity.Fragment.MenuSatuFragment;

import com.fingertech.kes.Activity.Maps.FullMap;
import com.fingertech.kes.Activity.Maps.MapWrapperLayout;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Maps.TentangKami;
import com.fingertech.kes.Activity.Model.InfoWindowData;
import com.fingertech.kes.Activity.Model.ItemSekolah;
import com.fingertech.kes.Activity.Model.ProfileModel;
import com.fingertech.kes.Activity.RecycleView.SnappyLinearLayoutManager;
import com.fingertech.kes.Activity.RecycleView.SnappyRecycleView;
import com.fingertech.kes.Activity.Search.AnakAkses;
import com.fingertech.kes.Activity.Setting.Setting_Activity;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    CarouselView customCarouselView;
    int[] sampleImages = {R.drawable.icon_header, R.drawable.icon_header_2, R.drawable.icon_header_3};
    String[] sampleTitles = {"Orange", "Grapes", "Strawberry"};

    private ViewPager ParentPager;
    private FragmentAdapter fragmentAdapter;
    public static int PAGE_COUNT = 2;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    View header;
    TextView tv_profile;
    CircleImageView image_profile;
    String nama_anak,foto;
    int status;
    String Base_url;
    ProgressDialog dialog;
    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,school_code,parent_nik;

    Auth mApiInterface;
    SharedPreferences sharedpreferences,sharedviewpager;

    public static final String my_viewpager_preferences = "my_viewpager_preferences";

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";

    private List<ItemSekolah> itemList;
    private ItemSekolahAdapter itemSekolahAdapter = null;
    private GoogleMap mapG;
    private LocationRequest mlocationRequest;
    private Marker CurrLocationMarker,m;
    private Location lastLocation;

    public SnappyRecycleView snappyRecyclerView;
    GoogleApiClient mGoogleApiClient;
    Polyline line;
    Double PROXIMITY_RADIUS = 2.0;
    Double currentLatitude,latitude;
    Double currentLongitude,longitude;
    String location;
    String code;
    String authorization;
    CardView btn_search,map_menu,tambah_anak;
    LinearLayout recycle_menu,view_group,viewpager,recycleview_ln;
    LinearLayout.LayoutParams layoutParams;
    LinearLayout ll;
    RecyclerView recyclerView;
    CircleImageView imageView;
    TextView namaanak;
    String Base_anak,classroom_id;
    SwipeRefreshLayout swipeRefreshLayout;
    static int i;
    List<ProfileModel> profileModels = new ArrayList<ProfileModel>();
    ProfileAdapter profileAdapter;
    private FrameLayout redCircle;
    private TextView countTextView;
    private int alertCount = 0;
    List<JSONResponse.DataList>dataLists = new ArrayList<>();
    InkPageIndicator inkPageIndicator;
    MapWrapperLayout mapWrapperLayout;
    String placeName,vicinity,akreditasi,schooldetailid;

    int height,width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_utama);
        toolbar             = findViewById(R.id.toolbar);
        ParentPager         = findViewById(R.id.PagerUtama);
        fragmentAdapter     = new FragmentAdapter(getSupportFragmentManager());
        drawer              = findViewById(R.id.drawer_layout);
        customCarouselView  = findViewById(R.id.customCarouselView);
        navigationView      = findViewById(R.id.nav_view);
        header              = navigationView.getHeaderView(0);
        tv_profile          = header.findViewById(R.id.tv_profil);
        mApiInterface       = ApiClient.getClient().create(Auth.class);
        image_profile       = header.findViewById(R.id.image_profile);
        btn_search          = findViewById(R.id.btn_search);
        map_menu            = findViewById(R.id.map_menu);
        recycle_menu        = findViewById(R.id.recycler_view_menu);
        viewpager           = findViewById(R.id.viewpager);
        tambah_anak         = findViewById(R.id.btn_tambah);
        imageView           = findViewById(R.id.image_anak);
        namaanak            = findViewById(R.id.nama_anak);
        swipeRefreshLayout  = findViewById(R.id.pullToRefresh);
        recycleview_ln      = findViewById(R.id.recycler_profile_view);
        recyclerView        = findViewById(R.id.recycle_profile);
        inkPageIndicator    = findViewById(R.id.indicators);
        mapWrapperLayout    = findViewById(R.id.map_relative_layout);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);
        customCarouselView.setImageClickListener(position -> {
//                Toast.makeText(MenuUtama.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
        });

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_beranda);

        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,null);
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,null);
        student_id    = sharedpreferences.getString(TAG_STUDENT_ID,null);
        student_nik   = sharedpreferences.getString(TAG_STUDENT_NIK,null);
        fullname      = sharedpreferences.getString(TAG_FULLNAME,null);
        email         = sharedpreferences.getString(TAG_EMAIL,null);
        childrenname  = sharedpreferences.getString(TAG_NAMA_ANAK,null);
        school_name   = sharedpreferences.getString(TAG_NAMA_SEKOLAH,null);
        school_code   = sharedpreferences.getString(TAG_SCHOOL_CODE,null);
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,null);
        Base_url      = "http://kes.co.id/assets/images/profile/mm_";
        Base_anak     = "http://www.kes.co.id/schoolc/assets/images/profile/mm_";

        sharedviewpager = getSharedPreferences(my_viewpager_preferences,Context.MODE_PRIVATE);

        ParentPager.setAdapter(fragmentAdapter);

        inkPageIndicator.setViewPager(ParentPager);

        get_profile();

        tv_profile.setOnClickListener(v -> {
            Intent intent = new Intent(MenuUtama.this,ProfileParent.class);
            startActivity(intent);
        });

        image_profile.setOnClickListener(v -> {
            Intent intent = new Intent(MenuUtama.this,ProfileParent.class);
            startActivity(intent);
        });

        mapWrapperLayout.init(mapG, getPixelsFromDp(this, 39 + 20));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapGuest);
        mapFragment.getMapAsync(this);

        //show error dialog if Google Play Services not available
        if (!isGooglePlayServicesAvailable()) {
            Log.d("onCreate", "Google Play Services not available. Ending Test case.");
            finish();
        }
        else {
            Log.d("onCreate", "Google Play Services available. Continuing.");
        }
        findViewById(R.id.squareFab).setOnClickListener(view -> {

            Intent mIntent = new Intent(MenuUtama.this, FullMap.class);
            mIntent.putExtra("member_id",parent_id);
            startActivity(mIntent);

        });
        btn_search.setOnClickListener(v -> {
            Intent intent = new Intent(MenuUtama.this, SearchingMAP.class);
            intent.putExtra("member_id",parent_id);
            startActivity(intent);
        });
        tambah_anak.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(TAG_PARENT_NIK,parent_nik);
            editor.commit();
            Intent intent = new Intent(MenuUtama.this, AnakAkses.class);
            intent.putExtra(TAG_PARENT_NIK,parent_nik);
            startActivity(intent);
        });

        get_children();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            int Refreshcounter = 1;
            @Override
            public void onRefresh() {
                get_profile();
                get_children();
                send_data();
                Refreshcounter = Refreshcounter + 1;
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ParentPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
            switch (position){
                case 0:
                    send_data();
                    break;
                case 1:
                    send_data2();
                    break;
            }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("coba", "getInstanceId failed", task.getException());
                    return;
                }
                // Get new Instance ID token
                String token = task.getResult().getToken();
                // Log and toast
                String msg = getString(R.string.msg_token_fmt, token);
                Log.d("Token", msg);
            });

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;

    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new FancyGifDialog.Builder(this)
                    .setTitle("Keluar")
                    .setMessage("Apakah anda ingin keluar dari aplikasi.")
                    .setNegativeBtnText("Tidak")
                    .setNegativeBtnBackground("#40bfe8")
                    .setPositiveBtnBackground("#f0f0f0")
                    .setPositiveBtnText("Ya")
                    .setGifResource(R.drawable.home)   //Pass your Gif here
                    .isCancellable(true)
                    .OnPositiveClicked(() -> moveTaskToBack(true))
                    .OnNegativeClicked(new FancyGifDialogListener() {
                        @Override
                        public void OnClick() {

                        }
                    })

                    .build();

        }

    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(final int position) {
            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);
            TextView labelTextView      = customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView    = customView.findViewById(R.id.fruitImageView);
            Button Baca                 = customView.findViewById(R.id.baca);
            Baca.setVisibility(View.GONE);
            fruitImageView.setImageResource(sampleImages[position]);
//          labelTextView.setText(sampleTitles[position]);
            Baca.setOnClickListener(view -> {
//            Toast.makeText(MenuUtama.this, "Clicked item: " + position, Toast.LENGTH_SHORT).show();
            });
            customCarouselView.setIndicatorGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM |Gravity.LEFT);
        return customView;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_utama, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        switch (item.getItemId()) {
////            case R.id.action_settings:
//                return true;
//            case R.id.activity_main_update_menu_item:
//                // TODO update alert menu icon
////                alertCount = (alertCount + 1) % 11; // cycle through 0 - 10
////                updateAlertIcon();
////                return true;
////            case R.id.activity_main_alerts_menu_item:
////                Toast.makeText(this, "update clicked", Toast.LENGTH_SHORT).show();
////                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            Intent intent = new Intent(MenuUtama.this, test.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_user) {
            Intent intent = new Intent(MenuUtama.this, ProfileParent.class);
            startActivity(intent);
        }else if (id == R.id.nav_tentang) {
            Intent intent = new Intent(MenuUtama.this, TentangKami.class);
            startActivity(intent);
        } else if (id == R.id.nav_Pengaturan) {
            Intent intent = new Intent(MenuUtama.this, Setting_Activity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void get_list(){
        Call<JSONResponse.ListNotification> call = mApiInterface.kes_notification_list_get(authorization, school_code.toLowerCase(), student_id);
        call.enqueue(new Callback<JSONResponse.ListNotification>() {
            @Override
            public void onResponse(Call<JSONResponse.ListNotification> call, Response<JSONResponse.ListNotification> response) {
                JSONResponse.ListNotification resource = response.body();
                status = resource.status;
                code   = resource.code;
                if (status == 1 && code.equals("DTS_SCS_0001")){
                    dataLists   = response.body().getData();
                    alertCount = response.body().getData().size();
                    Toast.makeText(getApplicationContext(),""+alertCount,Toast.LENGTH_LONG).show();
                    if (0 < alertCount && alertCount < dataLists.size()) {
                        countTextView.setText(String.valueOf(alertCount));
                    } else {
                        countTextView.setText("");
                    }

                    redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
                }
            }

            @Override
            public void onFailure(Call<JSONResponse.ListNotification> call, Throwable t) {

            }
        });
    }


    private void updateAlertIcon() {
        // if alert count extends into two digits, just show the red circle
        if (0 < alertCount && alertCount < dataLists.size()) {
            countTextView.setText(String.valueOf(alertCount));
        } else {
            countTextView.setText("");
        }

        redCircle.setVisibility((alertCount > 0) ? VISIBLE : GONE);
    }

    public void get_children(){
        Call<JSONResponse.ListChildren> call = mApiInterface.kes_list_children_get(authorization, parent_id);
        call.enqueue(new Callback<JSONResponse.ListChildren>() {
            @Override
            public void onResponse(Call<JSONResponse.ListChildren> call, Response<JSONResponse.ListChildren> response) {
                Log.d("TAG children",response.code()+"");

                JSONResponse.ListChildren resource = response.body();
                status = resource.status;
                code = resource.code;

                String DTS_SCS_0001 = getResources().getString(R.string.DTS_SCS_0001);
                String DTS_ERR_0001 = getResources().getString(R.string.DTS_ERR_0001);

                ProfileModel profileModel = null;
                if (status == 1 && code.equals("LCH_SCS_0001")) {
                    if (response.body().getData() != null){
                        profileModels = new ArrayList<ProfileModel>();
                        for (int i = 0;i < response.body().getData().size();i++) {
                            student_id      = response.body().getData().get(i).getStudent_id();
                            school_code     = response.body().getData().get(i).getSchool_code();
                            nama_anak       = response.body().getData().get(i).getChildren_name();
                            classroom_id    = response.body().getData().get(i).getClassroom_id();
                            school_name     = response.body().getData().get(i).getSchool_name();
                            foto            = response.body().getData().get(i).getPicture();
                            String imagefiles = Base_anak + foto;
                            profileModel = new ProfileModel();
                            profileModel.setWidth(width);
                            profileModel.setHeight(height);
                            profileModel.setStudent_id(student_id);
                            profileModel.setSchool_code(school_code);
                            profileModel.setNama(nama_anak);
                            profileModel.setSchool_name(school_name);
                            profileModel.setClassroom_id(classroom_id);
                            profileModel.setPicture(imagefiles);
                            profileModels.add(profileModel);
                        }
                        profileAdapter = new ProfileAdapter(MenuUtama.this,profileModels);
                        profileAdapter.notifyDataSetChanged();
                        profileAdapter.selectRow(0);
                        student_id      = response.body().getData().get(0).getStudent_id();
                        school_code     = response.body().getData().get(0).getSchool_code();
                        classroom_id    = response.body().getData().get(0).getClassroom_id();
                        school_name     = response.body().getData().get(0).getSchool_name();
                        send_data();
                        LinearLayoutManager layoutManager = new LinearLayoutManager(MenuUtama.this);
                        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(profileAdapter);

                        profileAdapter.setOnItemClickListener((view, position) -> {
                            profileAdapter.notifyDataSetChanged();
                            profileAdapter.selectRow(position);
                            student_id      = profileModels.get(position).getStudent_id();
                            school_code     = profileModels.get(position).getSchool_code();
                            classroom_id    = profileModels.get(position).getClassroom_id();
                            school_name     = profileModels.get(position).getSchool_name();
                            send_data();

                        });
                    }else {
                        recyclerView.setVisibility(GONE);
                    }
                } else {
                    if(status == 0 && code.equals("DTS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DTS_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<JSONResponse.ListChildren> call, Throwable t) {
                Log.d("onFailure",t.toString());
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_resp_json), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void get_profile(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization, parent_id);

        call.enqueue(new Callback<JSONResponse.GetProfile>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.GetProfile> call, final Response<JSONResponse.GetProfile> response) {
                Log.i("KES", response.code() + "");
                hideDialog();
                JSONResponse.GetProfile resource = response.body();

                status = resource.status;

                if (status == 1) {
                        String picture = response.body().getData().getPicture();
                        String nama    = response.body().getData().getFullname();
                        String member  = response.body().getData().getMember_Type();
                        String count   = response.body().getData().getTotal_Children();
                        tv_profile.setText(nama);
                        parent_nik = response.body().getData().getParent_NIK();
                        String imagefile = Base_url + picture;
                        if (picture.equals("")){
                            Glide.with(MenuUtama.this).load("https://ui-avatars.com/api/?name="+nama+"&background=40bfe8&color=fff").into(image_profile);
                        }
                        Picasso.get().load(imagefile).into(image_profile);

                    if (member.equals("3")){
                        if (count.equals("0")){
                            recycleview_ln.setVisibility(VISIBLE);
                            viewpager.setVisibility(GONE);
                        }else {
                            recycleview_ln.setVisibility(VISIBLE);
                            viewpager.setVisibility(VISIBLE);
                            recyclerView.setVisibility(VISIBLE);
                        }
                    }else {
                        recycleview_ln.setVisibility(GONE);
                        viewpager.setVisibility(GONE);
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.GetProfile> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
            }

        });

    }

    public void send_data() {
        Bundle bundle = new Bundle();
        SharedPreferences.Editor editor = sharedviewpager.edit();
        editor.putString("member_id", parent_id);
        editor.putString("school_code", school_code);
        editor.putString("authorization", authorization);
        editor.putString("classroom_id", classroom_id);
        editor.putString("parent_nik", parent_nik);
        editor.putString("school_name", school_name);
        editor.putString("student_id", student_id);
        editor.commit();
        bundle.putString("parent_nik", parent_nik);
        bundle.putString("student_id", student_id);
        bundle.putString("school_code", school_code);
        bundle.putString("member_id", parent_id);
        bundle.putString("authorization", authorization);
        bundle.putString("classroom_id", classroom_id);
        bundle.putString("school_name", school_name);
        Fragment menuSatuFragment = new MenuSatuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment1, menuSatuFragment);
        fragmentTransaction.commitAllowingStateLoss();
        menuSatuFragment.setArguments(bundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public void send_data2(){
        Bundle bundle = new Bundle();
        SharedPreferences.Editor editor = sharedviewpager.edit();
        editor.putString("member_id",parent_id);
        editor.putString("school_code",school_code);
        editor.putString("authorization",authorization);
        editor.putString("classroom_id",classroom_id);
        editor.putString("parent_nik",parent_nik);
        editor.putString("school_name",school_name);
        editor.putString("student_id",student_id);
        editor.commit();
        bundle.putString("parent_nik", parent_nik);
        bundle.putString("student_id", student_id);
        bundle.putString("school_code", school_code);
        bundle.putString("member_id", parent_id);
        bundle.putString("authorization", authorization);
        bundle.putString("classroom_id", classroom_id);
        bundle.putString("school_name",school_name);
        MenuDuaFragment menuDuaFragment = new MenuDuaFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment2, menuDuaFragment);
        fragmentTransaction.commitAllowingStateLoss();
        menuDuaFragment.setArguments(bundle);
    }


    public class FragmentAdapter extends FragmentStatePagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    send_data();
                    return new MenuSatuFragment();
                case 1:
                    send_data2();
                    return new MenuDuaFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationRequest = new LocationRequest();
        mlocationRequest.setInterval(1000);
        mlocationRequest.setFastestInterval(1000);
        mlocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
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
        lastLocation = location;
        if (CurrLocationMarker != null) {
            CurrLocationMarker.remove();

        }

        //Place current location marker
        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latLng.latitude, latLng.longitude)).zoom(13).build();

        final MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_map));
        //move map camera
        mapG.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mapG.animateCamera(CameraUpdateFactory.zoomTo(14));
        CurrLocationMarker = mapG.addMarker(markerOptions);
        CurrLocationMarker.hideInfoWindow();
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.connect();
        }

        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        InfoWindowData info = new InfoWindowData();
        info.setNama("CurrentLocation");
        info.setAlamat("");
        info.setSchooldetailid("");
        info.setAkreditasi("");
        info.setJarak(0.0);
        CurrLocationMarker.setTag(info);
        dapat_map();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapG = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mapG.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mapG.setMyLocationEnabled(true);
        }

        mapG.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getBaseContext(),DetailSekolah.class);
                intent.putExtra("detailid",schooldetailid);
                intent.putExtra("member_id",parent_id);
                startActivity(intent);
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable background = ContextCompat.getDrawable(context, vectorResId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

        private Context context;

        public CustomInfoWindowGoogleMap(Context ctx){
            context = ctx;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            View view = ((Activity)context).getLayoutInflater()
                    .inflate(R.layout.custom_snippet, null);

            TextView tvSch = view.findViewById(R.id.nama_school);

            // Getting reference to the TextView to set longitude
            TextView tvAkr = view.findViewById(R.id.akreditasi);

            // Getting reference to the TextView to set latitude
            TextView tvJrk = view.findViewById(R.id.jarak);

            // Getting reference to the TextView to set longitude
            TextView tvAlm = view.findViewById(R.id.alamat_school);

            // Getting reference to the TextView to set longitude
            TextView tvLht = view.findViewById(R.id.Lihat);


            ImageView img = view.findViewById(R.id.imageS);

            tvSch.setText(marker.getTitle());
            tvAkr.setText("Akreditasi "+marker.getSnippet());

            InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

            tvJrk.setText("Jarak > "+ String.format("%.2f", infoWindowData.getJarak())+ "Km");
            tvAlm.setText(infoWindowData.getAlamat());
            final String SchoolDetailId = infoWindowData.getSchooldetailid();

            mapG.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(getBaseContext(),DetailSekolah.class);
                    intent.putExtra("detailid",SchoolDetailId);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    public void dapat_map(){

        Call<JSONResponse.Nearby_School> call = mApiInterface.nearby_radius_post(currentLatitude,currentLongitude,PROXIMITY_RADIUS);

        call.enqueue(new Callback<JSONResponse.Nearby_School>() {

            @Override
            public void onResponse(Call<JSONResponse.Nearby_School> call, final Response<JSONResponse.Nearby_School> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.Nearby_School resource = response.body();

                status = resource.status;
                code = resource.code;

                String NR_SCS_0001 = getResources().getString(R.string.NR_SCS_0001);
                String NR_ERR_0001 = getResources().getString(R.string.NR_ERR_0001);
                String NR_ERR_0002 = getResources().getString(R.string.NR_ERR_0002);
                String NR_ERR_0003 = getResources().getString(R.string.NR_ERR_0003);
                String NR_ERR_0004 = getResources().getString(R.string.NR_ERR_0004);

                ItemSekolah Item = null;

                if (status == 1 && code.equals("NR_SCS_0001")) {
                    itemList = new ArrayList<ItemSekolah>();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        double lat     = response.body().getData().get(i).getLatitude();
                        double lng     = response.body().getData().get(i).getLongitude();
                        placeName      = response.body().getData().get(i).getSchool_name();
                        vicinity       = response.body().getData().get(i).getSchool_address();
                        akreditasi     = response.body().getData().get(i).getAkreditasi();
                        schooldetailid = response.body().getData().get(i).getSchooldetailid();
                        final double Jarak          = response.body().getData().get(i).getDistance();

                        LatLng latLng = new LatLng(lat, lng);
                        if(response.body().getData().get(i).getJenjang_pendidikan().equals("SD")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_sd));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mapG.addMarker(markerOptions);

                        }else if(response.body().getData().get(i).getJenjang_pendidikan().equals("SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_smp));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m = mapG.addMarker(markerOptions);
                        }else if(response.body().getData().get(i).getJenjang_pendidikan().equals("SPK SMP")){
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_smp));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mapG.addMarker(markerOptions);
                        }
                        else {
                            MarkerOptions markerOptions = new MarkerOptions();

                            // Position of Marker on Map
                            markerOptions.position(latLng);
                            // Adding colour to the marker
                            markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_sma));
                            markerOptions.title(placeName);
                            markerOptions.snippet(akreditasi);
                            // Remove Marker

                            // Adding Marker to the Camera.
                            m= mapG.addMarker(markerOptions);
                        }


                        InfoWindowData info = new InfoWindowData();
                        info.setJarak(Jarak);
                        info.setAlamat(vicinity);
                        info.setSchooldetailid(schooldetailid);

                        CustomInfoWindowAdapter customInfoWindow = new CustomInfoWindowAdapter(MenuUtama.this);
                        mapG.setInfoWindowAdapter(customInfoWindow);

                        m.setTag(info);
                        // m.showInfoWindow();

                        Item = new ItemSekolah();
                        Item.setName(placeName);
                        Item.setAkreditas(akreditasi);
                        Item.setJarak(Jarak);
                        Item.setLat(lat);
                        Item.setLng(lng);
                        itemList.add(Item);
                    }

                    // Create the recyclerview.
                    snappyRecyclerView = findViewById(R.id.recycler_view);
                    // Create the grid layout manager with 2 columns.
                    final SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(MenuUtama.this);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    snappyRecyclerView.setLayoutManager(new SnappyLinearLayoutManager(MenuUtama.this));

                    //getSnapHelper().attachToRecyclerView(snappyRecyclerView);
                    // Set layout manager.
                    snappyRecyclerView.setLayoutManager(layoutManager);

                    // Create car recycler view data adapter with car item list.
                    itemSekolahAdapter = new ItemSekolahAdapter(itemList);

                    itemSekolahAdapter.setOnItemClickListener(new ItemSekolahAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {

                            LatLng latLng = new LatLng(currentLatitude,currentLongitude);
                            latitude = response.body().getData().get(position).getLatitude();
                            longitude = response.body().getData().get(position).getLongitude();
                            final LatLng StartlatLng = new LatLng(latitude, longitude);
                            GoogleDirectionConfiguration.getInstance().setLogEnabled(true);
                            String $key = getResources().getString(R.string.google_maps_key);


                            GoogleDirection.withServerKey($key)
                                    .from(latLng)
                                    .to(StartlatLng)
                                    .transportMode(TransportMode.DRIVING)
                                    .transitMode(TransitMode.BUS)
                                    .unit(Unit.METRIC)
                                    .execute(new DirectionCallback() {
                                        @Override
                                        public void onDirectionSuccess(Direction direction, String rawBody) {

                                            Log.d("GoogleDirection", "Response Direction Status: " + direction.toString()+"\n"+rawBody);

                                            if(direction.isOK()) {
                                                // Do something
                                                Route route = direction.getRouteList().get(0);
                                                Leg leg = route.getLegList().get(0);
                                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(getApplication(), directionPositionList, 4, Color.BLUE);
                                                line = mapG.addPolyline(polylineOptions);
                                                setCameraWithCoordinationBounds(direction.getRouteList().get(0));

                                            } else {
                                                // Do something
                                            }
                                        }

                                        @Override
                                        public void onDirectionFailure(Throwable t) {
                                            // Do something
                                            Log.e("GoogleDirection", "Response Direction Status: " + t.getMessage()+"\n"+t.getCause());
                                        }
                                    });
                            if(line != null){
                                line.remove();
                            }
                        }

                    });

                    snappyRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            int horizontalScrollRange = recyclerView.computeHorizontalScrollRange();
                            int scrollOffset = recyclerView.computeHorizontalScrollOffset();
                            int currentItem = 0;
                            float itemWidth = horizontalScrollRange * 1.0f / itemList.size();
                            itemWidth = (itemWidth == 0) ? 1.0f : itemWidth;
                            if (scrollOffset != 0) {
                                currentItem = Math.round(scrollOffset / itemWidth);
                            }
                            currentItem = (currentItem < 0) ? 0 : currentItem;
                            currentItem = (currentItem >= itemList.size()) ? itemList.size() - 1 : currentItem;
                            if(line != null){
                                line.remove();
                            }
                            if(response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SD")) {
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_sd60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));

                            }
                            else if(response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SMP")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_smp60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }else if(response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SMA")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_sma60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }else if(response.body().getData().get(currentItem).getJenjang_pendidikan().equals("SPK SMP")){
                                latitude = response.body().getData().get(currentItem).getLatitude();
                                longitude = response.body().getData().get(currentItem).getLongitude();
                                final LatLng latLng = new LatLng(latitude, longitude);
                                final MarkerOptions markerOptions = new MarkerOptions();
                                // Position of Marker on Map
                                markerOptions.position(latLng);
                                // Adding colour to the marker
                                markerOptions.icon(bitmapDescriptorFromVector(MenuUtama.this, R.drawable.ic_smp60));
                                // Remove Marker
                                if (m != null) {
                                    m.remove();
                                }
                                // Adding Marker to the Camera.
                                m = mapG.addMarker(markerOptions);
                                mapG.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mapG.animateCamera(CameraUpdateFactory.zoomTo(16));
                            }
                        }
                    });
                    // Set data adapter.
                    snappyRecyclerView.setAdapter(itemSekolahAdapter);


                } else{
                    if (status == 0 && code.equals("NR_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), NR_ERR_0001, Toast.LENGTH_LONG).show();
                    }
                    if (status == 0 && code.equals("NR_ERR_0002")) {
                        Toast.makeText(getApplicationContext(), NR_ERR_0002, Toast.LENGTH_LONG).show();
                    }
                    if (status == 0 && code.equals("NR_ERR_0003")) {
                        Toast.makeText(getApplicationContext(), NR_ERR_0003, Toast.LENGTH_LONG).show();
                    }
                    if (status == 0 && code.equals("NR_ERR_0004")) {
                        Toast.makeText(getApplicationContext(), NR_ERR_0004, Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Nearby_School> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });
    }


    private void setCameraWithCoordinationBounds(Route route) {
        LatLng southwest = route.getBound().getSouthwestCoordination().getCoordination();
        LatLng northeast = route.getBound().getNortheastCoordination().getCoordination();
        LatLngBounds bounds = new LatLngBounds(southwest, northeast);
        mapG.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
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
        dialog = new ProgressDialog(MenuUtama.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
