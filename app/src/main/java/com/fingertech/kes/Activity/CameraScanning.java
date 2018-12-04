package com.fingertech.kes.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fingertech.kes.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.lang.reflect.Field;

public class CameraScanning extends AppCompatActivity implements SurfaceHolder.Callback{
    private SurfaceView cameraView, cameraTransparent;
    SurfaceHolder holder,holderTransparent;
    private CameraSource cameraSource;
    private TextView tv_result;
    private ImageView iv_flashlight, iv_photo_camera, iv_reload;
    final int RequestCameraPermissionID = 1001;

    private Camera camera = null;
    boolean flashmode = false;

    private float RectLeft, RectTop,RectRight,RectBottom ;

    int  deviceHeight,deviceWidth;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_scanning);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_result         = (TextView) findViewById(R.id.tv_result);
        cameraView        = (SurfaceView) findViewById(R.id.sv_camera_scan);
//        cameraTransparent = (SurfaceView) findViewById(R.id.sv_transparent_camera);
        iv_flashlight     = (ImageView) findViewById(R.id.iv_flashlight);
        iv_photo_camera   = (ImageView) findViewById(R.id.iv_photo_camera);
        iv_reload         = (ImageView) findViewById(R.id.iv_reload);


        holder = cameraView.getHolder();
        holder.addCallback((SurfaceHolder.Callback) this);
        //holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraView.setSecure(true);


        // Create second surface with another holder (holderTransparent)
        cameraTransparent = (SurfaceView)findViewById(R.id.sv_transparent_camera);
        holderTransparent = cameraTransparent.getHolder();
        holderTransparent.addCallback((SurfaceHolder.Callback) this);
        holderTransparent.setFormat(PixelFormat.TRANSLUCENT);
        cameraTransparent.setZOrderMediaOverlay(true);

        //getting the device heigth and width
        deviceWidth=getScreenWidth();
        deviceHeight=getScreenHeight();



        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("DaftarParent", "Detection ar not avaliavle");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(300, 100)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        synchronized (holder)
                        {
                            Draw();
                        }
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(CameraScanning.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Camera.Parameters param;
                    param = camera.getParameters();
                    camera.setParameters(param);
                    try {
                        camera.setPreviewDisplay(holder);
                        camera.startPreview();
                    } catch (Exception e) {
                        return;
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if (items.size() != 0) {
                        tv_result.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                int i = 0;
                                TextBlock item = items.valueAt(i);
                                stringBuilder.append(item.getValue());
//                                for (int i =0;i<items.size();++i)
//                                {
//                                    TextBlock item = items.valueAt(i);
//                                    stringBuilder.append(item.getValue());
//                                    stringBuilder.append(".\n");
//                                }
                                String isi;
                                isi = stringBuilder.toString().replaceAll("[^\\d.]", "");
                                tv_result.setText(isi);
                            }
                        });
                    }

                }
            });
        }

        iv_flashlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashOnButton();
            }
        });
        iv_photo_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraSource.stop();
            }
        });

        iv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    private void flashOnButton() {
        camera=getCamera(cameraSource);
        if (camera != null) {
            try {
                Camera.Parameters param = camera.getParameters();
                param.setFlashMode(!flashmode?Camera.Parameters.FLASH_MODE_TORCH :Camera.Parameters.FLASH_MODE_OFF);
                camera.setParameters(param);
                flashmode = !flashmode;
                if(flashmode){
                }
                else {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private static Camera getCamera(@NonNull CameraSource cameraSource) {
        Field[] declaredFields = CameraSource.class.getDeclaredFields();

        for (Field field : declaredFields) {
            if (field.getType() == Camera.class) {
                field.setAccessible(true);
                try {
                    Camera camera = (Camera) field.get(cameraSource);
                    if (camera != null) {
                        return camera;
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        return null;
    }


    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private void Draw()
    {
        Canvas canvas = holderTransparent.lockCanvas(null);
        Paint  paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(3);
        RectLeft = 50;
        RectTop = 500 ;
        RectRight = RectLeft+ deviceWidth-100;
        RectBottom =RectTop+ 200;
        Rect rec=new Rect((int) RectLeft,(int)RectTop,(int)RectRight,(int)RectBottom);
        canvas.drawRect(rec,paint);
        holderTransparent.unlockCanvasAndPost(canvas);
    }


    @Override

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            synchronized (holder)
            {Draw();}   //call a draw method
            camera = Camera.open(); //open a camera
        } catch (Exception e) {
            Log.i("Exception", e.toString());
            return;
        }

        Camera.Parameters param;
        param = camera.getParameters();

//        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
//        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
//        if(display.getRotation() == Surface.ROTATION_0) {
//            camera.setDisplayOrientation(90);
//        }

        camera.setParameters(param);
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        refreshCamera(); //call method for refress camera
    }

    public void refreshCamera() {
        if (holder.getSurface() == null) {
            return;
        }

        try {
            camera.stopPreview();
        } catch (Exception e) {
        }

        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (Exception e) {
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.release(); //for release a camera
    }

}
