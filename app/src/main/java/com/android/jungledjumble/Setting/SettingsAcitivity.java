package com.android.jungledjumble.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Main.ReturnActivity;
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

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);

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
                if (sound_on = true){click_sound.start();}
                    music_on = false;
                    music_button.setVisibility(View.GONE);
                    music_button_pressed.setVisibility(View.VISIBLE);
                    background_sound.pause();
            }
        });


        music_button_pressed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on = true){click_sound.start();}
                    music_on = true;
                    music_button.setVisibility(View.VISIBLE);
                    music_button_pressed.setVisibility(View.GONE);
                    background_sound.start();
            }
        });

        sound_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                    //if (sound_on = true){click_sound.start();}
                    sound_on = false;
                    sound_button.setVisibility(View.GONE);
                    sound_button_pressed.setVisibility(View.VISIBLE);
            }
        });


        sound_button_pressed.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on = true){click_sound.start();}
                    sound_on = true;
                    sound_button.setVisibility(View.VISIBLE);
                    sound_button_pressed.setVisibility(View.GONE);
            }
        });

        how_to_play_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on = true){click_sound.start();}
                Intent intent = new Intent(SettingsAcitivity.this, Tutorial.class);
                intent.putExtra ("sound_on",sound_on);
                startActivity(intent);
            }
        });

        credits_text.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(SettingsAcitivity.this, Credits.class);
                intent.putExtra ("sound_on",sound_on);
                startActivity(intent);
            }
        });

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on = true){click_sound.start();}
                background_sound.pause();
                Intent intent = new Intent(SettingsAcitivity.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                startActivity(intent);
            }
        });

    }
}
