package com.fingertech.kes.Activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.fingertech.kes.R;

public class TestVideo extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        videoView = findViewById(R.id.videoview11);
        MediaController mediaController = new MediaController(this); //Creating the media controller
        mediaController.setAnchorView(videoView);

        //Specify the location of media file
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.cobavideo);

        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
    }
}
