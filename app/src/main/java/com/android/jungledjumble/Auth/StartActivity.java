package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.Utils;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    ImageView play,settings, data;
    int fruits;
    TextView points;
    FirebaseUser firebaseUser;
    MediaPlayer background_sound;
    Boolean sound_on = true;
    Boolean music_on = true;

    @Override
    protected void onStart() {
        super.onStart();


  /*      background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        if (!background_sound.isPlaying()) {
            background_sound.start();
        }*/

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);

        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        try{music_on = getIntent().getExtras().getBoolean("music_on",true);}
        catch (Exception e){}

        if (music_on){background_sound.start();}

        setContentView(R.layout.activity_start);
        play = findViewById(R.id.play_button);
        settings = findViewById(R.id.settings_button);
        data = findViewById(R.id.data_button);
        points = findViewById (R.id.number);


        Intent intent = getIntent ();
        fruits = intent.getIntExtra ("fruits",0);
        points.setText (String.valueOf (fruits));








        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (sound_on){click_sound.start();}
                background_sound.pause();
                Intent intent = new Intent(StartActivity.this, SelectUserActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                background_sound.pause();
                Intent intent = new Intent(StartActivity.this, SettingsAcitivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        data.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                background_sound.pause();
                Intent intent = new Intent(StartActivity.this, ProgressActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

    }


}