package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.Utils;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    ImageView play,settings, data;
    FirebaseUser firebaseUser;
    MediaPlayer background_sound;
    @Override
    protected void onStart() {
        super.onStart();


        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        if (!background_sound.isPlaying()) {
            background_sound.start();
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Utils utils = new Utils(this);
        utils.hideSystemUI ();
        play = findViewById(R.id.play_button);
        settings = findViewById(R.id.settings_button);
        data = findViewById(R.id.data_button);



        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);





        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                click_sound.start();
                background_sound.pause();
                startActivity(new Intent(StartActivity.this, SelectUserActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                click_sound.start();
                background_sound.pause();
                startActivity(new Intent(StartActivity.this, SettingsAcitivity.class));
            }
        });

        data.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                click_sound.start();
                background_sound.pause();
                startActivity(new Intent(StartActivity.this, ProgressActivity.class));
            }
        });

    }


}