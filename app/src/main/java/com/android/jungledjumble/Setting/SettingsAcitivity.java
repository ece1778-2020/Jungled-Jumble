package com.android.jungledjumble.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.Utils;

public class SettingsAcitivity extends AppCompatActivity {

    ImageView music_button, music_button_pressed, sound_button, sound_button_pressed, settings_cancel_button;
    TextView how_to_play_text, credits_text;
    Boolean music_on = true;
    Boolean sound_on = true;
    MediaPlayer background_sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings_acitivity);
        Utils utils = new Utils(this);
        utils.hideSystemUI ();
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        if (!background_sound.isPlaying()) {
            background_sound.start();
        }

        music_button = findViewById (R.id.music_button);
        music_button_pressed = findViewById (R.id.music_button_pressed);
        sound_button = findViewById (R.id.sound_button);
        sound_button_pressed = findViewById (R.id.sound_button_pressed);
        how_to_play_text = findViewById (R.id.how_to_play_text);
        credits_text = findViewById (R.id.credits_text);
        settings_cancel_button = findViewById (R.id.settings_cancel_button);


        music_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                    music_on = false;
                    music_button.setVisibility(View.GONE);
                    music_button_pressed.setVisibility(View.VISIBLE);

                background_sound.pause();

            }
        });


        music_button_pressed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                    music_on = true;
                    music_button.setVisibility(View.VISIBLE);
                    music_button_pressed.setVisibility(View.GONE);


                background_sound.start();



            }
        });

        sound_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                    sound_on = false;
                    sound_button.setVisibility(View.GONE);
                    sound_button_pressed.setVisibility(View.VISIBLE);

            }
        });


        sound_button_pressed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){


                    sound_on = true;
                    sound_button.setVisibility(View.VISIBLE);
                    sound_button_pressed.setVisibility(View.GONE);

            }
        });

        how_to_play_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //startActivity(new Intent (SettingsAcitivity.this, PrivacyPolicyActivity.class));
            }
        });

        credits_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent (SettingsAcitivity.this, Credits.class));
            }
        });

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                background_sound.pause();
                startActivity(new Intent (SettingsAcitivity.this, StartActivity.class));
            }
        });

    }
}
