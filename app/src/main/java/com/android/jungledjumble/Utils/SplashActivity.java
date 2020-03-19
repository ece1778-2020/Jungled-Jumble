package com.android.jungledjumble.Utils;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.R;

/**
 * Created by AbhiAndroid
 */

public class SplashActivity extends Activity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

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
}