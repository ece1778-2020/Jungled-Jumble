package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button play,settings;
    FirebaseUser firebaseUser;
    MediaPlayer background_sound;
    @Override
    protected void onStart() {
        super.onStart();
        //final MediaPlayer background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        //background_sound.start();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        play = findViewById(R.id.play);
        settings = findViewById(R.id.settings);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        //final MediaPlayer background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        //background_sound.start();

        if (background_sound != null && background_sound.isPlaying()) {
            background_sound.stop();
            background_sound.reset();
        }
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        //
        background_sound.start();

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {


                click_sound.start();
                startActivity(new Intent(StartActivity.this, SelectUserActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                click_sound.start();
                startActivity(new Intent(StartActivity.this, SettingsAcitivity.class));
            }
        });
    }
}