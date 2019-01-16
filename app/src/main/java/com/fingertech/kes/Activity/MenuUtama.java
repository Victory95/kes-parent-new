package com.fingertech.kes.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.Activity.Fragment.MenuDuaFragment;
import com.fingertech.kes.Activity.Fragment.MenuSatuFragment;
import android.view.ViewGroup.LayoutParams;
import com.fingertech.kes.R;
import com.pixelcan.inkpageindicator.InkPageIndicator;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import github.chenupt.springindicator.SpringIndicator;

public class MenuUtama extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CarouselView customCarouselView;
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_1, R.drawable.image_4, R.drawable.image_5};
    String[] sampleTitles = {"Orange", "Grapes", "Strawberry", "Cherry", "Apricot"};

    private ViewPager ParentPager;
    private FragmentAdapter fragmentAdapter;
    private ViewGroup rootView;
    public static int PAGE_COUNT = 2;
    NavigationView navigationView;
    DrawerLayout drawer;
    Toolbar toolbar;
    View header;
    TextView tv_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ParentPager         = (ViewPager) findViewById(R.id.PagerUtama);
        fragmentAdapter     = new FragmentAdapter(getSupportFragmentManager());
        drawer              = (DrawerLayout) findViewById(R.id.drawer_layout);
        customCarouselView  = (CarouselView) findViewById(R.id.customCarouselView);
        navigationView      = (NavigationView) findViewById(R.id.nav_view);
        header              = navigationView.getHeaderView(0);
        tv_profile          = (TextView)header.findViewById(R.id.tv_profil);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        customCarouselView.setPageCount(sampleImages.length);
        customCarouselView.setSlideInterval(4000);
        customCarouselView.setViewListener(viewListener);
        customCarouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(MenuUtama.this, "Clicked item: "+ position, Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(this);


        ParentPager.setAdapter(fragmentAdapter);
        InkPageIndicator inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicators);
        inkPageIndicator.setViewPager(ParentPager);

        tv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuUtama.this,ProfileParent.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage("Apa kalian ingin Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }

    }
    // To set custom views
    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(final int position) {

            View customView = getLayoutInflater().inflate(R.layout.view_custom, null);

            TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);
            Button Baca     = (Button) customView.findViewById(R.id.baca);

            fruitImageView.setImageResource(sampleImages[position]);
            labelTextView.setText(sampleTitles[position]);

            Baca.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(MenuUtama.this, "Clicked item: " + position, Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            // Handle the camera action
        } else if (id == R.id.nav_cari_sekolah) {

        } else if (id == R.id.nav_keluar) {
            Intent intent = new Intent(MenuUtama.this, OpsiMasuk.class);
            startActivity(intent);
        } else if (id == R.id.nav_kontak) {

        } else if (id == R.id.nav_Pengaturan) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class FragmentAdapter extends FragmentStatePagerAdapter {


        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new MenuSatuFragment();
                case 1:
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

}
