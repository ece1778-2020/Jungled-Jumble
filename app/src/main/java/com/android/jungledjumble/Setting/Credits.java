package com.android.jungledjumble.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.Utils;

public class Credits extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_credits);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        


    }
}
