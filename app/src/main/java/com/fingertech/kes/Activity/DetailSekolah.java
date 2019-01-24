package com.fingertech.kes.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
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
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Fragment.ContactFragment;
import com.fingertech.kes.Activity.Fragment.DataPelengkap;
import com.fingertech.kes.Activity.Fragment.DataPeriodik;
import com.fingertech.kes.Activity.Fragment.IdentitasFragment;
import com.fingertech.kes.Activity.Fragment.Lainnya;
import com.fingertech.kes.Activity.Maps.SearchingMAP;
import com.fingertech.kes.Activity.Model.ClusterItemSekolah;
import com.fingertech.kes.Activity.Search.LokasiAnda;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    String Npsn,Nama;
    Auth mApiInterface;
    int status;
    int schoolDetail;
    FragmentTransaction ft;
    FragmentManager fm;
    ProgressDialog dialog;
    private Bundle bundle,pelengkap,contact,periodik,lainnya;

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

            final Toolbar toolbar = (Toolbar) findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mApiInterface = ApiClient.getClient().create(Auth.class);
        namadetailsekolah   = (TextView)findViewById(R.id.nama_detail_sekolah);
        lokasisekolah       = (TextView)findViewById(R.id.lokasi_sekolah);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.htab_viewpager);
        setupViewPager(viewPager);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.htab_tabs);
        tabLayout.setupWithViewPager(viewPager);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.htab_collapse_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.htab_appbar);
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
                    isShow = true;
                } else if(isShow) {
//                    collapsingToolbarLayout.setTitle(NamaSekolah);//carefull there should a space between double quote otherwise it wont work
                    namadetailsekolah.setText(" ");
                    lokasisekolah.setText(" ");
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

                status = resource.status;
                code = resource.code;

                String DS_SCS_0001 = getResources().getString(R.string.DS_SCS_0001);
                String DS_ERR_0001 = getResources().getString(R.string.DS_ERR_0001);


                if (status == 1 && code.equals("DS_SCS_0001")) {
                    JSONResponse.SchoolDetail source = resource.getSchool();
                    schoolDetail = source.statusKes;
                    if (schoolDetail == 0){
                         NPSN                 = response.body().school.getData().getNpsn();
                         NamaSekolah          = response.body().school.getData().getSchool_name();
                         JenjangPendidikan    = response.body().school.getData().getJenjangPendidikan();
                         StatusSekolah        = response.body().school.getData().getStatus_sekolah();
                         Provinsi             = response.body().school.getData().getNama_provinsi();
                         Kabupaten            = response.body().school.getData().getNama_kabupaten();
                         Kecamatan            = response.body().school.getData().getNama_kecamatan();
                         Kelurahan            = response.body().school.getData().getKelurahan();
                         RT                   = response.body().school.getData().getRt();
                         RW                   = response.body().school.getData().getRw();
                         Alamat               = response.body().school.getData().getSchool_address();
                         KodePos              = response.body().school.getData().getKode_pos();
                         SKPendiri            = response.body().school.getData().getSkPendirian();
                         TanggalSK            = response.body().school.getData().getTanggal_pendirian();
                         StatusKepemilikan    = response.body().school.getData().getStatus_kepemilikan();
                         SKizin               = response.body().school.getData().getSkIzin();
                         TanggalIzin          = response.body().school.getData().getTanggal_izin();
                         kebutuhan            = response.body().school.getData().getKebutuhan_khusus();
                         NomorRekening        = response.body().school.getData().getNo_rekening();
                         NamaBank             = response.body().school.getData().getNama_bank();
                         Cabang               = response.body().school.getData().getCabang();
                         RekeningNama         = response.body().school.getData().getAccountName();
                         MBS                  = response.body().school.getData().getMbs();
                         TanahMilik           = response.body().school.getData().getTanah_milik();
                         TanahBukan           = response.body().school.getData().getTanah_bukan_milik();
                         NamaPajak            = response.body().school.getData().getNwp();
                         Npwp                 = response.body().school.getData().getNpwp();
                         NomorTelepon         = response.body().school.getData().getSchool_phone();
                         Email                = response.body().school.getData().getSchool_email();
                         Nofax                = response.body().school.getData().getNo_fax();
                         Website              = response.body().school.getData().getWebsite();
                         CP                   = response.body().school.getData().getSchool_contact();
                         LessonHour           = response.body().school.getData().getLessonHour();
                         BOS                  = response.body().school.getData().getBersedia_menerima_bos();
                         ISO                  = response.body().school.getData().getSertifikasi_iso();
                         SumberListrik        = response.body().school.getData().getSumber_listrik();
                         DayaListrik          = response.body().school.getData().getDaya_listrik();
                         AksesInternet        = response.body().school.getData().getAkses_internet();
                         AlternatifInternet   = response.body().school.getData().getInternet_alternatif();
                         KepalaSekolah        = response.body().school.getData().getKepsek();
                         Operator             = response.body().school.getData().getOperator();
                         Akreditasi           = response.body().school.getData().getAkreditasi();
                         Kurikulum            = response.body().school.getData().getKurikulum();
                         TotalGuru            = response.body().school.getData().getTguru();
                         SiswaPria            = response.body().school.getData().getTsiswa_pria();
                         SiswaWanita          = response.body().school.getData().getTsiswa_wanita();
                         Rombel               = response.body().school.getData().getRombel();
                         RuangKelas           = response.body().school.getData().getRuang_kelas();
                         Laboratorium         = response.body().school.getData().getLaboratorium();
                         Perpustakaan         = response.body().school.getData().getPerpustakaan();
                         Sanitasi             = response.body().school.getData().getSanitasi();
                         Picture              = response.body().school.getData().getPicture();


                        bundle = new Bundle();
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

                        pelengkap = new Bundle();
                        pelengkap.putString("skpendiri",SKPendiri);
                        pelengkap.putString("tanggalsk",TanggalSK);
                        pelengkap.putString("statusmilik",StatusKepemilikan);
                        pelengkap.putString("skizin",SKizin);
                        pelengkap.putString("tanggalizin",TanggalIzin);
                        pelengkap.putString("kebutuhankhusus",kebutuhan);
                        pelengkap.putString("norekening",NomorRekening);
                        pelengkap.putString("namabank",NamaBank);
                        pelengkap.putString("cabang",Cabang);
                        pelengkap.putString("namarekening",RekeningNama);
                        pelengkap.putString("mbs",MBS);
                        pelengkap.putString("tanahmilik",TanahMilik);
                        pelengkap.putString("tanahbukan",TanahBukan);
                        pelengkap.putString("namapajak",NamaPajak);
                        pelengkap.putString("npwp",Npwp);

                        contact = new Bundle();
                        contact.putString("notelepon",NomorTelepon);
                        contact.putString("email",Email);
                        contact.putString("nofax",Nofax);
                        contact.putString("website",Website);
                        contact.putString("cp",CP);

                        periodik = new Bundle();
                        periodik.putString("lessonhour",LessonHour);
                        periodik.putString("bos",BOS);
                        periodik.putString("iso",ISO);
                        periodik.putString("sumberlistrik",SumberListrik);
                        periodik.putString("dayalistrik",DayaListrik);
                        periodik.putString("aksesinternet",AksesInternet);
                        periodik.putString("alternatifinternet",AlternatifInternet);

                        lainnya = new Bundle();
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



                    }

                } else{
                    if (status == 0 && code.equals("DS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DS_ERR_0001, Toast.LENGTH_LONG).show();
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

                status = resource.status;
                code = resource.code;

                String DS_SCS_0001 = getResources().getString(R.string.DS_SCS_0001);
                String DS_ERR_0001 = getResources().getString(R.string.DS_ERR_0001);


                if (status == 1 && code.equals("DS_SCS_0001")) {
                    JSONResponse.SchoolDetail source = resource.getSchool();
                    schoolDetail = source.statusKes;
                    if (schoolDetail == 0){
                        NPSN                 = response.body().school.getData().getNpsn();
                        NamaSekolah          = response.body().school.getData().getSchool_name();
                        JenjangPendidikan    = response.body().school.getData().getJenjangPendidikan();
                        StatusSekolah        = response.body().school.getData().getStatus_sekolah();
                        Provinsi             = response.body().school.getData().getNama_provinsi();
                        Kabupaten            = response.body().school.getData().getNama_kabupaten();
                        Kecamatan            = response.body().school.getData().getNama_kecamatan();
                        Kelurahan            = response.body().school.getData().getKelurahan();
                        RT                   = response.body().school.getData().getRt();
                        RW                   = response.body().school.getData().getRw();
                        Alamat               = response.body().school.getData().getSchool_address();
                        KodePos              = response.body().school.getData().getKode_pos();


                        bundle = new Bundle();
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

                        IdentitasFragment fragmentCurrent = new IdentitasFragment();
                        FragmentManager fx = getSupportFragmentManager();
                        FragmentTransaction fp = fx.beginTransaction();
                        //fragment.setArguments(bundle);
                        fp.add(R.id.fragIdentitas, fragmentCurrent);
                        fp.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fp.addToBackStack(null);
                        fp.commit();
                        fragmentCurrent.setArguments(bundle);
                    }

                } else{
                    if (status == 0 && code.equals("DS_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), DS_ERR_0001, Toast.LENGTH_LONG).show();
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

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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
}
