package com.fingertech.kes.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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

import com.facebook.login.LoginManager;
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
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
    private static final int REQUEST_TAKE_PHOTO = 2;
    CircleImageView image_profil;

    String mCurrentPhotoPath,lastlogin;
    String verification_code,parent_id,student_id,student_nik,school_id,childrenname,school_name,email,fullname,member_id,school_code,parent_nik;

    Auth mApiInterface;
    SharedPreferences sharedpreferences,sharedupdate;


    public static final String my_shared_update = "my_shared_update";
    public static final String TAG_NAMA_PROFILE = "nama_profile";
    public static final String TAG_NOMOR_HP     = "nomor_hp";
    public static final String TAG_AGAMA        = "agama";
    public static final String TAG_GENDER       = "gender";
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

    TextView email_profile,no_profile,jenis_kelamin_profile,tv_agama,last_login,member,jadi_parent;
    int bitmap_size = 40; // image quality 1 - 100;
    int max_resolution_image = 800;
    private static final int CAMERA_REQUEST_CODE = 7777;
    File image;
    Button logout;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_parent);
        toolbar                 = (Toolbar)findViewById(R.id.toolbar_profile);
        header                  = (ImageView)findViewById(R.id.htab_header);
        btn_edit                = (CardView)findViewById(R.id.btn_edit);
        cv_profile              = (CardView)findViewById(R.id.btn_image);
        btn_katasandi           = (CardView)findViewById(R.id.btn_katasandi);
        image_profil            = (CircleImageView)findViewById(R.id.image_prof);
        email_profile           = (TextView)findViewById(R.id.email_parent);
        no_profile              = (TextView)findViewById(R.id.phone_parent);
        jenis_kelamin_profile   = (TextView)findViewById(R.id.gender_parent);
        tv_agama                = (TextView)findViewById(R.id.agama_parent);
        last_login              = (TextView)findViewById(R.id.last_login);
        member                  = (TextView)findViewById(R.id.user);
        jadi_parent             = (TextView)findViewById(R.id.jd_parent);
        mApiInterface           = ApiClient.getClient().create(Auth.class);
        logout                  = (Button)findViewById(R.id.btn_logout);


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


        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapse_profile);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_profile);
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

                Intent intent = new Intent(ProfileParent.this, OpsiMasuk.class);
                finish();
                startActivity(intent);
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
    private void selectImage() {
        image_profil.setImageResource(0);
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileParent.this);
        builder.setTitle("Add Photo!");
        builder.setIcon(R.drawable.ic_kamera);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    try {
                        dispatchTakePictureIntent();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_FILE);
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
            if (requestCode == REQUEST_CAMERA) {
                try {
                    Log.e("CAMERA", fileUri.getPath());

                    bitmap = BitmapFactory.decodeFile(fileUri.getPath());
                    setToImageView(getResizedBitmap(bitmap, max_resolution_image));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == SELECT_FILE && data != null && data.getData() != null) {
                try {
                    // mengambil gambar dari Gallery
                    setToImageView(handleSamplingAndRotationBitmap(ProfileParent.this,data.getData()));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
                // Show the thumbnail on ImageView
                Uri imageUri = Uri.parse(mCurrentPhotoPath);

                try {
                    setToImageView(handleSamplingAndRotationBitmap(ProfileParent.this,imageUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // ScanFile so it will be appeared on Gallery
                MediaScannerConnection.scanFile(ProfileParent.this,
                        new String[]{imageUri.getPath()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, Uri uri) {
                            }
                        });
            }
        }
    }

    // Untuk menampilkan bitmap pada ImageView
    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        image_profil.setImageBitmap(decoded);
        update_picture();
    }

    // Untuk resize bitmap
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
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
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                return;
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(ProfileParent.this,
                        BuildConfig.APPLICATION_ID + ".provider",
                        createImageFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public static Bitmap handleSamplingAndRotationBitmap(Context context, Uri selectedImage)
            throws IOException {
        int MAX_HEIGHT = 1024;
        int MAX_WIDTH = 1024;

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream imageStream = context.getContentResolver().openInputStream(selectedImage);
        BitmapFactory.decodeStream(imageStream, null, options);
        imageStream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, MAX_WIDTH, MAX_HEIGHT);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        imageStream = context.getContentResolver().openInputStream(selectedImage);
        Bitmap img = BitmapFactory.decodeStream(imageStream, null, options);

        img = rotateImageIfRequired(context, img, selectedImage);
        return img;
    }
    private static int calculateInSampleSize(BitmapFactory.Options options,
                                             int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee a final image
            // with both dimensions larger than or equal to the requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            final float totalPixels = width * height;

            // Anything more than 2x the requested pixels we'll sample down further
            final float totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }
    private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public void get_profile(){
        retrofit2.Call<JSONResponse.GetProfile> call = mApiInterface.kes_profile_get(authorization.toString(),parent_id.toString());

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

                    email_profile.setText(email);
                    no_profile.setText(nohp);
                    jenis_kelamin_profile.setText(jeniskelamin);
                    tv_agama.setText(agama);
                    if(member_type.toString().equals("3")){
                        member.setText("Orangtua");
                        jadi_parent.setVisibility(View.INVISIBLE);
                    }else{
                        member.setText("User Biasa");
                        jadi_parent.setVisibility(View.VISIBLE);
                    }
                    Bitmap bitma = BitmapFactory.decodeFile(picture);
                    image_profil.setImageBitmap(bitma);
//                    Toast.makeText(getApplicationContext(), picture, Toast.LENGTH_LONG).show();

                    last_login.setText(lastlogin);
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

    public void update_picture(){
        String pic_type = "png";
        retrofit2.Call<JSONResponse> call = mApiInterface.kes_update_picture_post(authorization.toString(),parent_id.toString(),image,pic_type.toString());

        call.enqueue(new Callback<JSONResponse>() {

            @Override
            public void onResponse(retrofit2.Call<JSONResponse> call, final Response<JSONResponse> response) {
                Log.i("KES", response.code() + "");

                JSONResponse resource = response.body();

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
            public void onFailure(retrofit2.Call<JSONResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
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
        dialog = new ProgressDialog(ProfileParent.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
    }
}
