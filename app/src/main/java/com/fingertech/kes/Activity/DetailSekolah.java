package com.fingertech.kes.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.HttpException;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.fingertech.kes.Activity.Adapter.FotoAdapter;
import com.fingertech.kes.Activity.Fragment.ContactFragment;
import com.fingertech.kes.Activity.Fragment.DataPelengkap;
import com.fingertech.kes.Activity.Fragment.DataPeriodik;
import com.fingertech.kes.Activity.Fragment.IdentitasFragment;
import com.fingertech.kes.Activity.Fragment.Lainnya;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.ClusterItemSekolah;
import com.fingertech.kes.Activity.Model.FotoModel;
import com.fingertech.kes.Activity.Search.AnakAkses;
import com.fingertech.kes.Activity.Search.LokasiAnda;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.GalleryFoto;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;
import com.shashank.sony.fancytoastlib.FancyToast;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailSekolah extends AppCompatActivity {

    TextView namadetailsekolah,lokasisekolah;
    String code;
    Spinner et_provinsi;
    String sch,NPSN,NamaSekolah,JenjangPendidikan,StatusSekolah,Provinsi,Kabupaten,Kecamatan,Kelurahan,RT,RW,Alamat,KodePos,SKPendiri,TanggalSK;
    String StatusKepemilikan,SKizin,TanggalIzin,kebutuhan,NomorRekening,NamaBank,Cabang,RekeningNama,MBS,TanahMilik,TanahBukan,NamaPajak;
    String Npwp,NomorTelepon,Email,Nofax,Website,CP,LessonHour,BOS,ISO,SumberListrik,DayaListrik,AksesInternet,AlternatifInternet,KepalaSekolah;
    String Operator,Akreditasi,Kurikulum,TotalGuru,SiswaPria,SiswaWanita,Rombel,RuangKelas,Laboratorium,Perpustakaan,Sanitasi,Picture;
    String school_id;
    Auth mApiInterface;
    int status;
    int schoolDetail;
    FragmentTransaction ft;
    FragmentManager fm;
    ProgressDialog dialog;
    private Bundle bundle,pelengkap,contact,periodik,lainnya;
    ImageView foto_sekolah;
    TextView hint_detail;
    RecyclerView rv_foto;
    FotoModel fotoModel;
    List<FotoModel> fotoModelList = new ArrayList<>();
    FotoAdapter fotoAdapter;
    FloatingActionButton fab;
    String member_id,school_code,school_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_sekolah);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }

        final Toolbar toolbar = findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mApiInterface       = ApiClient.getClient().create(Auth.class);
        namadetailsekolah   = findViewById(R.id.nama_detail_sekolah);
        lokasisekolah       = findViewById(R.id.lokasi_sekolah);
        final ViewPager viewPager = findViewById(R.id.htab_viewpager);
        foto_sekolah        = findViewById(R.id.htab_header);
        hint_detail         = findViewById(R.id.hint_detail);
        fab                 = findViewById(R.id.fab);
        member_id           = getIntent().getStringExtra("member_id");

        setupViewPager(viewPager);


        TabLayout tabLayout = findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.htab_collapse_toolbar);
        AppBarLayout appBarLayout = findViewById(R.id.htab_appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbarLayout.setTitle(NamaSekolah);
                    namadetailsekolah.setText(NamaSekolah);
                    lokasisekolah.setText(Alamat);
                    hint_detail.setVisibility(View.GONE);
                    isShow = true;
                } else if(isShow) {
//                    collapsingToolbarLayout.setTitle(NamaSekolah);//carefull there should a space between double quote otherwise it wont work
                    namadetailsekolah.setText(" ");
                    lokasisekolah.setText(" ");
                    if (schoolDetail == 0) {
                        hint_detail.setVisibility(View.VISIBLE);
                    }else if (schoolDetail == 1){
                        hint_detail.setVisibility(View.GONE);
                    }
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

        sch = getIntent().getStringExtra("detailid");

        dapat_identitas();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailSekolah.this, GalleryFoto.class);
                intent.putExtra("school_id",school_id);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Log.d("KES", "onTabSelected: pos: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        // TODO: 31/03/17
                        IdentitasFragment fragmentCurrent = new IdentitasFragment();
                        bundle.putString("npsn",NPSN);
                        bundle.putString("namasekolah",NamaSekolah);
                        bundle.putString("jenjangpendidikan",JenjangPendidikan);
                        bundle.putString("statussekolah",StatusSekolah);
                        bundle.putString("provinsi",Provinsi);
                        bundle.putString("kabupaten",Kabupaten);
                        bundle.putString("kecamatan",Kecamatan);
                        bundle.putString("kelurahan",Kelurahan);
                        bundle.putString("rt",RT);
                        bundle.putString("rw",RW);
                        bundle.putString("alamat",Alamat);
                        bundle.putString("kodepos",KodePos);
                        fm = getSupportFragmentManager();
                        ft = fm.beginTransaction();
                        //fragment.setArguments(bundle);
                        ft.add(R.id.fragIdentitas, fragmentCurrent);
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        ft.addToBackStack(null);
                        ft.commit();
                        fragmentCurrent.setArguments(bundle);
                        break;

                    case 1:
                        pelengkap.putString("skpendiri",SKPendiri);
                        DataPelengkap dataPelengkap = new DataPelengkap();
                        FragmentManager fw = getSupportFragmentManager();
                        FragmentTransaction fq = fw.beginTransaction();
                        fq.add(R.id.fragPelengkap,dataPelengkap);
                        fq.commit();
                        dataPelengkap.setArguments(pelengkap);
                        break;
                    case 2:
                        ContactFragment contactFragment = new ContactFragment();
                        contact.putString("notelepon",NomorTelepon);
                        contact.putString("email",Email);
                        contact.putString("nofax",Nofax);
                        contact.putString("website",Website);
                        contact.putString("cp",CP);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.fragContact,contactFragment);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.commit();
                        contactFragment.setArguments(contact);
                        break;
                    case 3:
                        DataPeriodik dataPeriodik = new DataPeriodik();
                        periodik.putString("lessonhour",LessonHour);
                        periodik.putString("bos",BOS);
                        periodik.putString("iso",ISO);
                        periodik.putString("sumberlistrik",SumberListrik);
                        periodik.putString("dayalistrik",DayaListrik);
                        periodik.putString("aksesinternte",AksesInternet);
                        periodik.putString("alternatifinternet",AlternatifInternet);
                        FragmentManager fragmentperiodik = getSupportFragmentManager();
                        FragmentTransaction transactionperiodik = fragmentperiodik.beginTransaction();
                        transactionperiodik.add(R.id.fragPeriodik,dataPeriodik);
                        transactionperiodik.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        transactionperiodik.commit();
                        dataPeriodik.setArguments(periodik);
                        break;
                    case 4:
                        Lainnya fraglain = new Lainnya();
                        lainnya.putString("kepsek",KepalaSekolah);
                        lainnya.putString("operator",Operator);
                        lainnya.putString("akreditasi",Akreditasi);
                        lainnya.putString("kurikulum",Kurikulum);
                        lainnya.putString("totalguru",TotalGuru);
                        lainnya.putString("siswapria",SiswaPria);
                        lainnya.putString("siswawanita",SiswaWanita);
                        lainnya.putString("rombel",Rombel);
                        lainnya.putString("ruangkelas",RuangKelas);
                        lainnya.putString("laboratorium",Laboratorium);
                        lainnya.putString("perpustakaan",Perpustakaan);
                        lainnya.putString("sanitasi",Sanitasi);
                        FragmentManager fragmentlainnya = getSupportFragmentManager();
                        FragmentTransaction laintransaction = fragmentlainnya.beginTransaction();
                        laintransaction.add(R.id.fragLainnya,fraglain);
                        laintransaction.commit();
                        fraglain.setArguments(lainnya);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new IdentitasFragment(), "Identitas");
        adapter.addFrag(new DataPelengkap(), "Data Pelengkap");
        adapter.addFrag(new ContactFragment(), "Kontak");
        adapter.addFrag(new DataPeriodik(), "Data Periodik");
        adapter.addFrag(new Lainnya(), "Data Lainnya");
        viewPager.setAdapter(adapter);
    }

    public void dapat_sekolah(){

        retrofit2.Call<JSONResponse.DetailSchool> call = mApiInterface.detail_school_get(sch);

        call.enqueue(new Callback<JSONResponse.DetailSchool>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.DetailSchool> call, final Response<JSONResponse.DetailSchool> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.DetailSchool resource = response.body();
                if (resource==null){
                    Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                }else {
                    status = resource.status;
                    code = resource.code;

                    String DS_SCS_0001 = getResources().getString(R.string.DS_SCS_0001);
                    String DS_ERR_0001 = getResources().getString(R.string.DS_ERR_0001);


                    if (status == 1 && code.equals("DS_SCS_0001")) {
                        JSONResponse.SchoolDetail source = resource.getSchool();
                        schoolDetail = source.statusKes;

                        NPSN                = response.body().school.getData().getNpsn();
                        school_id           = response.body().getSchool().getData().getSchool_Id();
                        school_code         = response.body().getSchool().getData().getSchool_code();
                        NamaSekolah         = response.body().school.getData().getSchool_name();
                        JenjangPendidikan   = response.body().school.getData().getJenjangPendidikan();
                        StatusSekolah       = response.body().school.getData().getStatus_sekolah();
                        Provinsi            = response.body().school.getData().getNama_provinsi();
                        Kabupaten           = response.body().school.getData().getNama_kabupaten();
                        Kecamatan           = response.body().school.getData().getNama_kecamatan();
                        Kelurahan           = response.body().school.getData().getKelurahan();
                        RT                  = response.body().school.getData().getRt();
                        RW                  = response.body().school.getData().getRw();
                        Alamat              = response.body().school.getData().getSchool_address();
                        KodePos             = response.body().school.getData().getKode_pos();
                        SKPendiri           = response.body().school.getData().getSkPendirian();
                        TanggalSK           = response.body().school.getData().getTanggal_pendirian();
                        StatusKepemilikan   = response.body().school.getData().getStatus_kepemilikan();
                        SKizin              = response.body().school.getData().getSkIzin();
                        TanggalIzin         = response.body().school.getData().getTanggal_izin();
                        kebutuhan           = response.body().school.getData().getKebutuhan_khusus();
                        NomorRekening       = response.body().school.getData().getNo_rekening();
                        NamaBank            = response.body().school.getData().getNama_bank();
                        Cabang              = response.body().school.getData().getCabang();
                        RekeningNama        = response.body().school.getData().getAccountName();
                        MBS                 = response.body().school.getData().getMbs();
                        TanahMilik          = response.body().school.getData().getTanah_milik();
                        TanahBukan          = response.body().school.getData().getTanah_bukan_milik();
                        NamaPajak           = response.body().school.getData().getNwp();
                        Npwp                = response.body().school.getData().getNpwp();
                        NomorTelepon        = response.body().school.getData().getSchool_phone();
                        Email               = response.body().school.getData().getSchool_email();
                        Nofax               = response.body().school.getData().getNo_fax();
                        Website             = response.body().school.getData().getWebsite();
                        CP                  = response.body().school.getData().getSchool_contact();
                        LessonHour          = response.body().school.getData().getLessonHour();
                        BOS                 = response.body().school.getData().getBersedia_menerima_bos();
                        ISO                 = response.body().school.getData().getSertifikasi_iso();
                        SumberListrik       = response.body().school.getData().getSumber_listrik();
                        DayaListrik         = response.body().school.getData().getDaya_listrik();
                        AksesInternet       = response.body().school.getData().getAkses_internet();
                        AlternatifInternet  = response.body().school.getData().getInternet_alternatif();
                        KepalaSekolah       = response.body().school.getData().getKepsek();
                        Operator            = response.body().school.getData().getOperator();
                        Akreditasi          = response.body().school.getData().getAkreditasi();
                        Kurikulum           = response.body().school.getData().getKurikulum();
                        TotalGuru           = response.body().school.getData().getTguru();
                        SiswaPria           = response.body().school.getData().getTsiswa_pria();
                        SiswaWanita         = response.body().school.getData().getTsiswa_wanita();
                        Rombel              = response.body().school.getData().getRombel();
                        RuangKelas          = response.body().school.getData().getRuang_kelas();
                        Laboratorium        = response.body().school.getData().getLaboratorium();
                        Perpustakaan        = response.body().school.getData().getPerpustakaan();
                        Sanitasi            = response.body().school.getData().getSanitasi();
//                    Picture               = response.body().school.getData().getPicture();

                        bundle = new Bundle();
                        bundle.putString("npsn", NPSN);
                        bundle.putString("namasekolah", NamaSekolah);
                        bundle.putString("jenjangpendidikan", JenjangPendidikan);
                        bundle.putString("statussekolah", StatusSekolah);
                        bundle.putString("provinsi", Provinsi);
                        bundle.putString("kabupaten", Kabupaten);
                        bundle.putString("kecamatan", Kecamatan);
                        bundle.putString("kelurahan", Kelurahan);
                        bundle.putString("rt", RT);
                        bundle.putString("rw", RW);
                        bundle.putString("alamat", Alamat);
                        bundle.putString("kodepos", KodePos);

                        pelengkap = new Bundle();
                        pelengkap.putString("skpendiri", SKPendiri);
                        pelengkap.putString("tanggalsk", TanggalSK);
                        pelengkap.putString("statusmilik", StatusKepemilikan);
                        pelengkap.putString("skizin", SKizin);
                        pelengkap.putString("tanggalizin", TanggalIzin);
                        pelengkap.putString("kebutuhankhusus", kebutuhan);
                        pelengkap.putString("norekening", NomorRekening);
                        pelengkap.putString("namabank", NamaBank);
                        pelengkap.putString("cabang", Cabang);
                        pelengkap.putString("namarekening", RekeningNama);
                        pelengkap.putString("mbs", MBS);
                        pelengkap.putString("tanahmilik", TanahMilik);
                        pelengkap.putString("tanahbukan", TanahBukan);
                        pelengkap.putString("namapajak", NamaPajak);
                        pelengkap.putString("npwp", Npwp);

                        contact = new Bundle();
                        contact.putString("notelepon", NomorTelepon);
                        contact.putString("email", Email);
                        contact.putString("nofax", Nofax);
                        contact.putString("website", Website);
                        contact.putString("cp", CP);

                        periodik = new Bundle();
                        periodik.putString("lessonhour", LessonHour);
                        periodik.putString("bos", BOS);
                        periodik.putString("iso", ISO);
                        periodik.putString("sumberlistrik", SumberListrik);
                        periodik.putString("dayalistrik", DayaListrik);
                        periodik.putString("aksesinternet", AksesInternet);
                        periodik.putString("alternatifinternet", AlternatifInternet);

                        lainnya = new Bundle();
                        lainnya.putString("kepsek", KepalaSekolah);
                        lainnya.putString("operator", Operator);
                        lainnya.putString("akreditasi", Akreditasi);
                        lainnya.putString("kurikulum", Kurikulum);
                        lainnya.putString("totalguru", TotalGuru);
                        lainnya.putString("siswapria", SiswaPria);
                        lainnya.putString("siswawanita", SiswaWanita);
                        lainnya.putString("rombel", Rombel);
                        lainnya.putString("ruangkelas", RuangKelas);
                        lainnya.putString("laboratorium", Laboratorium);
                        lainnya.putString("perpustakaan", Perpustakaan);
                        lainnya.putString("sanitasi", Sanitasi);

                        dapat_picture();
                        if (schoolDetail == 0) {
                            hint_detail.setVisibility(View.VISIBLE);
                            hint_detail.setOnClickListener(v -> {
                                Intent intent = new Intent(DetailSekolah.this, RecommendSchool.class);
                                if (member_id != null) {
                                    intent.putExtra("member_id", member_id);
                                    intent.putExtra("school_id", school_id);
                                    intent.putExtra("school_code", school_code);
                                    intent.putExtra("school_name", NamaSekolah);
                                    startActivity(intent);
                                }else {
                                    FancyToast.makeText(getApplicationContext(),"Harap login terlebih dahulu ke aplikasi untuk merekomendasikan sekolah ini",Toast.LENGTH_LONG,FancyToast.INFO,false).show();
                                }
                            });
                        } else if (schoolDetail == 1) {
                            hint_detail.setVisibility(View.GONE);
                        }

                    } else {
                        if (status == 0 && code.equals("DS_ERR_0001")) {
                            Toast.makeText(getApplicationContext(), DS_ERR_0001, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.DetailSchool> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });

    }

    public void dapat_identitas(){
        progressBar();
        showDialog();
        retrofit2.Call<JSONResponse.DetailSchool> call = mApiInterface.detail_school_get(sch);

        call.enqueue(new Callback<JSONResponse.DetailSchool>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.DetailSchool> call, final Response<JSONResponse.DetailSchool> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.DetailSchool resource = response.body();
                    if(resource==null){
                        Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
                    }else {
                        status = resource.status;
                        code = resource.code;

                        String DS_SCS_0001 = getResources().getString(R.string.DS_SCS_0001);
                        String DS_ERR_0001 = getResources().getString(R.string.DS_ERR_0001);


                        if (status == 1 && code.equals("DS_SCS_0001")) {
                            JSONResponse.SchoolDetail source = resource.getSchool();
                            schoolDetail = source.statusKes;

                            NPSN = response.body().school.getData().getNpsn();
                            NamaSekolah = response.body().school.getData().getSchool_name();
                            JenjangPendidikan = response.body().school.getData().getJenjangPendidikan();
                            StatusSekolah = response.body().school.getData().getStatus_sekolah();
                            Provinsi = response.body().school.getData().getNama_provinsi();
                            Kabupaten = response.body().school.getData().getNama_kabupaten();
                            Kecamatan = response.body().school.getData().getNama_kecamatan();
                            Kelurahan = response.body().school.getData().getKelurahan();
                            RT = response.body().school.getData().getRt();
                            RW = response.body().school.getData().getRw();
                            Alamat = response.body().school.getData().getSchool_address();
                            KodePos = response.body().school.getData().getKode_pos();

                            bundle = new Bundle();
                            bundle.putString("npsn", NPSN);
                            bundle.putString("namasekolah", NamaSekolah);
                            bundle.putString("jenjangpendidikan", JenjangPendidikan);
                            bundle.putString("statussekolah", StatusSekolah);
                            bundle.putString("provinsi", Provinsi);
                            bundle.putString("kabupaten", Kabupaten);
                            bundle.putString("kecamatan", Kecamatan);
                            bundle.putString("kelurahan", Kelurahan);
                            bundle.putString("rt", RT);
                            bundle.putString("rw", RW);
                            bundle.putString("alamat", Alamat);
                            bundle.putString("kodepos", KodePos);

                            IdentitasFragment fragmentCurrent = new IdentitasFragment();
                            FragmentManager fx = getSupportFragmentManager();
                            FragmentTransaction fp = fx.beginTransaction();
                            //fragment.setArguments(bundle);
                            fp.add(R.id.fragIdentitas, fragmentCurrent);
                            fp.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                            fp.addToBackStack(null);
                            fp.commit();
                            fragmentCurrent.setArguments(bundle);
//                    dapat_picture();
                        } else {
                            if (status == 0 && code.equals("DS_ERR_0001")) {
                                Toast.makeText(getApplicationContext(), DS_ERR_0001, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.DetailSchool> call, Throwable t) {
                Log.d("onFailure", t.toString());
                hideDialog();
            }

        });

    }


    public void dapat_picture(){

        Call<JSONResponse.Foto_sekolah> call = mApiInterface.kes_full_schoolpic_get(school_id);
        call.enqueue(new Callback<JSONResponse.Foto_sekolah>() {
            @Override
            public void onResponse(Call<JSONResponse.Foto_sekolah> call, Response<JSONResponse.Foto_sekolah> response) {
                Log.d("DetailSekolah",response.code()+"");
                JSONResponse.Foto_sekolah resource = response.body();
                status = resource.status;
                if (status == 1) {
                    if (response.body().getData().size() == 0) {
                        if (schoolDetail == 1){
                            setUnlocked(foto_sekolah);
                            Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                        }else if (schoolDetail == 0){
                            setLocked(foto_sekolah);
                            Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                        }
                    } else {
                        Picture = response.body().getData().get(0).getPic_url();
                    }
                    if (schoolDetail == 0) {
                        if (Picture.equals("")) {
                            Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                            setLocked(foto_sekolah);
                        } else {
                            setLocked(foto_sekolah);
                            Glide.with(DetailSekolah.this)
                                    .load(Picture)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            foto_sekolah.setBackgroundResource(R.drawable.image_profill);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .into(foto_sekolah);
                            setLocked(foto_sekolah);
                        }
                    } else if (schoolDetail == 1) {
                        if (Picture.equals("")) {
                            setUnlocked(foto_sekolah);
                            Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                        } else {
                            setUnlocked(foto_sekolah);
                            Glide.with(DetailSekolah.this)
                                    .load(Picture)
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                            Picasso.get().load(R.drawable.image_profill).into(foto_sekolah);
                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            return false;
                                        }
                                    })
                                    .into(foto_sekolah);
                        }
                    }
                }else {
                    if (schoolDetail == 1){
                        setUnlocked(foto_sekolah);
                        Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                    }else if (schoolDetail == 0){
                        setLocked(foto_sekolah);
                        Glide.with(DetailSekolah.this).load(R.drawable.image_profill).into(foto_sekolah);
                    }

                }

            }

            @Override
            public void onFailure(Call<JSONResponse.Foto_sekolah> call, Throwable t) {
                Log.d("Gagal",t.toString());

            }
        });
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                   dapat_sekolah();
                    return new IdentitasFragment();
                case 1:
                    dapat_sekolah();
                    return new DataPelengkap();
                case 2:
                    dapat_sekolah();
                    return new ContactFragment();
                case 3:
                    dapat_sekolah();
                    return new DataPeriodik();
                case 4:
                    dapat_sekolah();
                    return new Lainnya();
            }
            return null;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
        dialog = new ProgressDialog(DetailSekolah.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
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

    public static void  setLocked(ImageView v)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);  //0 means grayscale
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
        v.setColorFilter(cf);
        v.setImageAlpha(225);   // 128 = 0.5
    }

    public static void  setUnlocked(ImageView v)
    {
        v.setColorFilter(null);
        v.setImageAlpha(255);
    }
}
