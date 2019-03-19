//package com.fingertech.kes.Activity.Language;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.res.Resources;
//import android.graphics.PorterDuff;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.widget.CompoundButton;
//import android.widget.Switch;
//import android.widget.TextView;
//
//import com.fingertech.kes.R;
//import com.fingertech.kes.Service.DBHelper;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class Layout_Pengaturan extends AppCompatActivity {
//
//    @BindView(R.id.TV_pilihan)
//    TextView TVPilihan;
//
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(DBHelper.onAttach(newBase, "en"));
//    }
//
//    @BindView(R.id.toolbar_Pengaturan)
//    Toolbar toolbarPengaturan;
//
//    Switch myswitch;
//    @BindView(R.id.TV_bahasa)
//    TextView TVBahasa;
//
//    public static final String SHAREDPREF = "sharedpreference";
//    public static  final String SWITCH = "switch1";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout__pengaturan);
//        ButterKnife.bind(this);
//
//        myswitch = (Switch) findViewById(R.id.myswitch);
//        myswitch.setOnCheckedChangeListener(this::onCheckedChanged);
//
//        setSupportActionBar(toolbarPengaturan);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbarPengaturan.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
//
////        <-----------  language --------------- >
////        first
//    }
//
//    private void updateView(String lang) {
//
//        Context context = DBHelper.setLocale(this,lang);
//        Resources resources = context.getResources();
//
//        toolbarPengaturan.setTitle(resources.getString(R.string.tv_toolbar));
//    }
//
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        if (isChecked) {
//        } else {
//            TVBahasa.setText("Indonesia");   //To change the text near to switch
//            Log.d("You are :", " Not Checked");
//        }
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                onBackPressed();
//                return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//}
