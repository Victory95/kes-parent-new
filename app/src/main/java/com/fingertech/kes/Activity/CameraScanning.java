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
import android.os.Handler;
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
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
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

public class CameraScanning extends AppCompatActivity{
    private SurfaceView cameraView;
    private CameraSource cameraSource;
    private TextView tv_result;
    private ImageView iv_flashlight, iv_photo_camera, iv_reload, iv_eye, iv_oke;
    final int RequestCameraPermissionID = 1001;

    String result = "";
    private Camera camera = null;
    boolean flashmode = false;

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
        getSupportActionBar().setElevation(0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_result         = (TextView) findViewById(R.id.tv_result);
        cameraView        = (SurfaceView) findViewById(R.id.sv_camera_scan);
        iv_flashlight     = (ImageView) findViewById(R.id.iv_flashlight);
        iv_eye            = (ImageView) findViewById(R.id.iv_eye);
        iv_reload         = (ImageView) findViewById(R.id.iv_reload);
        iv_photo_camera   = (ImageView) findViewById(R.id.iv_photo_camera);
        iv_oke            = (ImageView) findViewById(R.id.iv_oke);

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("DaftarParent", "Detection ar not avaliavle");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
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

                                String isi;
                                isi = stringBuilder.toString().replaceAll("[^\\d.]", "");
                                      stringBuilder.toString().replaceAll(":", "");
                                if(isi.length()==16){
                                    iv_eye.setVisibility(View.VISIBLE);
                                    result = isi;
                                    tv_result.setText(result);
                                }else{
                                    iv_eye.setVisibility(View.VISIBLE);
                                    if(result.toString().isEmpty()){
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                iv_eye.setVisibility(View.GONE);
                                            }

                                        }, 1000);
                                    }else{
                                        iv_eye.setVisibility(View.VISIBLE);
                                    }
                                }
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
                if(result.toString().isEmpty()){
                }else{
                    iv_reload.setVisibility(View.VISIBLE);
                    iv_oke.setVisibility(View.VISIBLE);
                    cameraSource.stop();
                }
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
                    tv_result.setText("");
                    result = "";
                    iv_reload.setVisibility(View.GONE);
                    iv_oke.setVisibility(View.GONE);
                    iv_eye.setVisibility(View.GONE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        iv_oke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key_nik", result);
                intent.putExtra("key_nik_akses_anak", result);
                setResult(RESULT_OK, intent);
                finish();
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

}
