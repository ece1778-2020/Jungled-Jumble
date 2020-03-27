package com.android.jungledjumble.Utils;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.R;

public class SplashActivity extends Activity {

    Handler handler;
    VideoView vid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        vid = findViewById(R.id.videoView);
        playVideo();

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(SplashActivity.this, StartActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }

    public void playVideo() {
        MediaController m = new MediaController(this);
        vid.setMediaController(m);
        String path = "android.resource://com.android.jungledjumble/" + R.raw.splash_screen_video;
        Uri u = Uri.parse(path);
        vid.setVideoURI(u);
        vid.start();
    }
}