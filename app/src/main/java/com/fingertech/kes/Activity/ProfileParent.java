package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.fingertech.kes.Activity.NextProject.FileUtils;
import com.fingertech.kes.Activity.NextProject.ImageUtils;
import com.fingertech.kes.BuildConfig;
import com.fingertech.kes.Controller.Auth;
import com.fingertech.kes.R;
import com.fingertech.kes.Rest.ApiClient;
import com.fingertech.kes.Rest.JSONResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileParent extends AppCompatActivity {

    Toolbar toolbar;
    ImageView btn_foto,header;
    CardView cv_profile,btn_edit;
    TextView nama_parent,nomor_parent;
    RelativeLayout btn_profile;
    Intent intent;
    Uri fileUri;
    Button btn_choose_image;
    Bitmap bitmap, decoded;
    public final int REQUEST_CAMERA = 0;
    public final int SELECT_FILE = 1;
    private static final int REQUEST_TAKE_PHOTO = 0;
    CircleImageView image_profil;

    private static final int REQUEST_PICK_PHOTO = 2;
    private Uri mMediaUri;
    private static final int CAMERA_PIC_REQUEST = 1111;

    private static final String TAG = ProfileParent.class.getSimpleName();

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private final String sampled="SampleCropImage";

    private String mediaPath;

    private Button btnCapturePicture;

    private String mImageFileLocation = "";
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";
    ProgressDialog pDialog;
    private String postPath;


    String mCurrentPhotoPath,lastlogin;
    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;

    Auth mApiInterface;
    SharedPreferences sharedpreferences,sharedupdate;


    public static final String my_shared_update = "my_shared_update";
    public static final String TAG_NAMA_PROFILE = "nama_profile";
    public static final String TAG_NOMOR_HP     = "nomor_hp";
    public static final String TAG_AGAMA        = "agama";
    public static final String TAG_GENDER       = "gender";
    public static final String TAG_TANGGAL      = "tanggal_lahir";
    String nama_profile,no_hp,religion,gender;

    public static final String TAG_EMAIL        = "email";
    public static final String TAG_FULLNAME     = "fullname";
    public static final String TAG_TOKEN        = "token";
    public static final String TAG_MEMBER_ID    = "member_id"; /// PARENT ID
    public static final String TAG_STUDENT_ID   = "student_id";
    public static final String TAG_STUDENT_NIK  = "student_nik";
    public static final String TAG_SCHOOL_ID    = "school_id";
    public static final String TAG_MEMBER_TYPE  = "member_type";
    public static final String TAG_NAMA_ANAK    = "childrenname";
    public static final String TAG_NAMA_SEKOLAH = "school_name";
    public static final String TAG_SCHOOL_CODE  = "school_code";
    public static final String TAG_PARENT_NIK   = "parent_nik";
    public static final String TAG_LASTLOGIN    = "last_login";


    CardView btn_katasandi;
    String authorization;
    String nama,nohp,jeniskelamin,agama,terakhirlogin,member_type,picture;
    int status;
    String code;

    TextView email_profile,no_profile,jenis_kelamin_profile,tv_agama,last_login,member,jadi_parent,tanggallahir;
    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;
    private static final int CAMERA_REQUEST_CODE = 7777;
    File image;
    Button logout;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    Uri uri;
    String tanggal_lahir;
    CollapsingToolbarLayout collapsingToolbarLayout;

    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_parent);
        toolbar                 = findViewById(R.id.toolbar_profile);
        header                  = findViewById(R.id.htab_header);
        btn_edit                = findViewById(R.id.btn_edit);
        cv_profile              = findViewById(R.id.btn_image);
        btn_katasandi           = findViewById(R.id.btn_katasandi);
        image_profil            = findViewById(R.id.image_prof);
        email_profile           = findViewById(R.id.email_parent);
        no_profile              = findViewById(R.id.phone_parent);
        jenis_kelamin_profile   = findViewById(R.id.gender_parent);
        tv_agama                = findViewById(R.id.agama_parent);
        last_login              = findViewById(R.id.last_login);
        member                  = findViewById(R.id.user);
        jadi_parent             = findViewById(R.id.jd_parent);
        mApiInterface           = ApiClient.getClient().create(Auth.class);
        logout                  = findViewById(R.id.btn_logout);
        tanggallahir            = findViewById(R.id.tanggal_lahir);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.ic_logo_background), PorterDuff.Mode.SRC_ATOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#00FFFFFF"));
            getWindow().setFlags(WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES,WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
//
        sharedpreferences = getSharedPreferences(Masuk.my_shared_preferences, Context.MODE_PRIVATE);
        authorization = sharedpreferences.getString(TAG_TOKEN,"token");
        parent_id     = sharedpreferences.getString(TAG_MEMBER_ID,"member_id");
        fullname      = sharedpreferences.getString(TAG_FULLNAME,"fullname");
        parent_nik    = sharedpreferences.getString(TAG_PARENT_NIK,"parent_nik");
        lastlogin     = sharedpreferences.getString(TAG_LASTLOGIN,"");
        member_type   = sharedpreferences.getString(TAG_MEMBER_TYPE,"member_type");

        get_profile();

        sharedupdate    = getSharedPreferences(my_shared_update,Context.MODE_PRIVATE);
        nama_profile    = sharedupdate.getString(TAG_NAMA_PROFILE,null);
        email           = sharedupdate.getString(TAG_EMAIL,null);
        religion        = sharedupdate.getString(TAG_AGAMA,null);
        gender          = sharedupdate.getString(TAG_GENDER,null);
        no_hp           = sharedupdate.getString(TAG_NOMOR_HP,null);


        collapsingToolbarLayout = findViewById(R.id.collapse_profile);
        appBarLayout = findViewById(R.id.appbar_profile);


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
       cv_profile.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               selectImage();
           }
       });

        ////// Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihan();
            }
        });

        btn_katasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileParent.this, ChangePassword.class);
                startActivity(intent);
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sharedupdate.edit();
                edit.putString(TAG_NAMA_PROFILE,nama);
                edit.putString(TAG_EMAIL,email);
                edit.putString(TAG_NOMOR_HP,nohp);
                edit.putString(TAG_AGAMA,agama);
                edit.putString(TAG_GENDER,jeniskelamin);
                edit.putString(TAG_TANGGAL,tanggal_lahir);
                edit.commit();
                Intent intent = new Intent(ProfileParent.this, EditProfile.class);
                startActivity(intent);
            }
        });

        jadi_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileParent.this, JadiParent.class);
                startActivity(intent);
            }
        });
    }

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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileParent.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.drawable.ic_kamera);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    captureImage();
                } else if (items[item].equals("Choose from Library")) {
                    startActivityForResult(new Intent().setAction(Intent.ACTION_GET_CONTENT)
                            .setType("image/*"),SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("onActivityResult", "requestCode " + requestCode + ", resultCode " + resultCode);

        if (resultCode == Activity.RESULT_OK) {
           if (requestCode == CAMERA_PIC_REQUEST){
                if (Build.VERSION.SDK_INT > 21) {

                    Glide.with(ProfileParent.this).load(mCurrentPhotoPath).into(image_profil);
                    File files = new File(mCurrentPhotoPath);
                    uploadImage(files);
                }else{
                    Glide.with(ProfileParent.this).load(fileUri).into(image_profil);
                    File files = FileUtils.getFile(ProfileParent.this, fileUri);
                    uploadImage(files);
                }
            } else if (requestCode == SELECT_FILE && resultCode==RESULT_OK) {
               Uri image = data.getData();
               if (image!=null){
                   startcrop(image);
               }

            }else if (requestCode== UCrop.REQUEST_CROP && resultCode==RESULT_OK){
               Uri imageresult = UCrop.getOutput(data);
               image_profil.setImageURI(imageresult);
               File files = FileUtils.getFile(ProfileParent.this, imageresult);
               uploadImage(files);

           }
        }
    }

    private void startcrop(@NonNull Uri uri){
        String destinationfile= sampled;
        destinationfile +=".jpg";

        UCrop uCrop = UCrop.of(uri,Uri.fromFile(new File(getCacheDir(),destinationfile)));

        uCrop.withAspectRatio(1,1);
        uCrop.withMaxResultSize(450,450);

        uCrop.withOptions(getcrop());
        uCrop.start(ProfileParent.this);

    }

    private UCrop.Options getcrop(){
        UCrop.Options options=new UCrop.Options();

        options.setCompressionQuality(70);
        options.setHideBottomControls(false);
        options.setFreeStyleCropEnabled(true);
        options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));

        options.setToolbarTitle("KES");

        return options;
    }

    private void uploadImage(File file) {
        progressBar();
        showDialog();
        String pic_type = "png";
        RequestBody photoBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("picture",
                file.getName(), photoBody);
        RequestBody ids = RequestBody.create(MediaType.parse("multipart/form-data"), parent_id);
        RequestBody picss = RequestBody.create(MediaType.parse("multipart/form-data"), pic_type);
        Call<JSONResponse.UpdatePicture> call = mApiInterface.kes_update_picture_post(authorization,ids,photoPart,picss);

        call.enqueue(new Callback<JSONResponse.UpdatePicture>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.UpdatePicture> call, final Response<JSONResponse.UpdatePicture> response) {
                hideDialog();
                Log.i("KES", response.code() + "");

                JSONResponse.UpdatePicture resource = response.body();

                status = resource.status;
                code   = resource.code;

                if (status == 1 && code.equals("UPP_SCS_0001")) {
                    Toast.makeText(getApplicationContext(), "Foto berhasil diupload", Toast.LENGTH_LONG).show();
                } else{
                    if (status == 0 && code.equals("UPP_ERR_0001")) {
                        Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.UpdatePicture> call, Throwable t) {
                hideDialog();
                Log.d("onFailure", t.toString());
            }

        });
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void captureImage() {
        if (Build.VERSION.SDK_INT > 21) { //use this if Lollipop_Mr1 (API 22) or above
            Intent callCameraApplicationIntent = new Intent();
            callCameraApplicationIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

            // We give some instruction to the intent to save the image
            File photoFile = null;

            try {
                // If the createImageFile will be successful, the photo file will have the address of the file
                photoFile = createImageFile();
                // Here we call the function that will try to catch the exception made by the throw function
            } catch (IOException e) {
                Logger.getAnonymousLogger().info("Exception error in generating the file");
                e.printStackTrace();
            }
            // Here we add an extra file to the intent to put the address on to. For this purpose we use the FileProvider, declared in the AndroidManifest.
            Uri outputUri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    photoFile);
            callCameraApplicationIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);

            // The following is a new line with a trying attempt
            callCameraApplicationIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

            Logger.getAnonymousLogger().info("Calling the camera App by intent");

            // The following strings calls the camera app and wait for his file in return.
            startActivityForResult(callCameraApplicationIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture Intent
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }


    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(TAG, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + ".jpg");
        }  else {
            return null;
        }

        return mediaFile;
    }


    @SuppressLint("SimpleDateFormat")
    private File createImageFile() throws IOException {
        // Create an image file name
         String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();

        return image;
    }

    public void get_profile(){
        final String Base_url = "http://kes.co.id/assets/images/profile/mm_";
        retrofit2.Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization, parent_id);

        call.enqueue(new Callback<JSONResponse.GetProfile>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse.GetProfile> call, final Response<JSONResponse.GetProfile> response) {
                Log.i("KES", response.code() + "");

                JSONResponse.GetProfile resource = response.body();

                status = resource.status;

                if (status == 1) {
                    picture = response.body().getData().getPicture();
                    nama    = response.body().getData().getFullname();
                    email   = response.body().getData().getEmail();
                    nohp    = response.body().getData().getNumber_Phone();
                    jeniskelamin    = response.body().getData().getGender();
                    agama           = response.body().getData().getReligion();
                    member_type     = response.body().getData().getMember_Type();
                    terakhirlogin   = response.body().getData().getLast_Login();
                    tanggal_lahir   = response.body().getData().getBirth_Date();

                    email_profile.setText(email);
                    no_profile.setText(nohp);
                    jenis_kelamin_profile.setText(jeniskelamin);
                    tv_agama.setText(agama);
                    if(member_type.equals("3")){
                        member.setText("Sebagai Orangtua");
                        jadi_parent.setVisibility(View.INVISIBLE);
                    }else{
                        member.setText("Sebagai User Biasa");
                        jadi_parent.setVisibility(View.VISIBLE);
                    }
                    tanggallahir.setText(convertDate(tanggal_lahir));

                    String imagefile = Base_url + picture;
                    if (picture.equals("")){
                        Glide.with(ProfileParent.this).load("https://ui-avatars.com/api/?name="+nama+"&background=40bfe8&color=fff").into(image_profil);
                    }
                    Glide.with(ProfileParent.this).load(imagefile).into(image_profil);
                    last_login.setText(lastlogin);
                    appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

                        boolean isShow = true;
                        int scrollRange = -1;

                        @Override
                        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                            if (scrollRange == -1) {
                                scrollRange = appBarLayout.getTotalScrollRange();
                            }
                            if (scrollRange + verticalOffset == 0) {
                                collapsingToolbarLayout.setTitle(nama);
                                cv_profile.setVisibility(View.GONE);
                                isShow = true;
                            } else if(isShow) {
                                collapsingToolbarLayout.setTitle(nama);//carefull there should a space between double quote otherwise it wont work
                                cv_profile.setVisibility(View.VISIBLE);
                                getSupportActionBar().setTitle(nama);
                                isShow = false;
                            }
                        }
                    });
                } else{
                    if (status == 0) {
                        Toast.makeText(getApplicationContext(), "Data Tidak Ditemukan", Toast.LENGTH_LONG).show();
                    }
                }


            }

            @Override
            public void onFailure(retrofit2.Call<JSONResponse.GetProfile> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }

        });

    }

    //Konversi tanggal dari date dialog ke format yang kita inginkan
    String convertDate(String tanggal) {
        SimpleDateFormat calendarDateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd MMMM yyyy",Locale.getDefault());
        try {
            String e = newDateFormat.format(calendarDateFormat.parse(tanggal));
            return e;
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void pilihan() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileParent.this,R.style.DialogTheme);
        builder.setTitle("Log out");
        builder.setMessage("Apakah anda ingin keluar?");
        builder.setIcon(R.drawable.ic_alarm);
        builder.setPositiveButton("Ya", (dialog, which) -> {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putBoolean(Masuk.session_status, false);
            editor.putString(TAG_EMAIL, null);
            editor.putString(TAG_MEMBER_ID, null);
            editor.putString(TAG_FULLNAME, null);
            editor.putString(TAG_MEMBER_TYPE, null);
            editor.putString(TAG_TOKEN, null);
            editor.commit();

            /////// Logout Facebook
            LoginManager.getInstance().logOut();


            ///// Google Logout
            mAuth.signOut();
            mGoogleSignInClient.signOut().addOnCompleteListener(ProfileParent.this,
                    new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            FirebaseUser user = null;
                        }
                    });

            Intent intent = new Intent(ProfileParent.this, MenuGuest.class);
            finish();
            startActivity(intent);
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

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
        dialog = new ProgressDialog(ProfileParent.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                okhttp3.MultipartBody.FORM, descriptionString);
    }
}
