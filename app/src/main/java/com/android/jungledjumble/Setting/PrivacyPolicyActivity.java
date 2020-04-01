package com.android.jungledjumble.Setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    Button decline, accept;
    Boolean sound_on = true;
    Boolean music_on = true;
    MediaPlayer background_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_privacy_policy);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);

        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        try{music_on = getIntent().getExtras().getBoolean("music_on",true);}
        catch (Exception e){}

        if (music_on){background_sound.start();}

        decline = findViewById (R.id.decline_policy);
        accept = findViewById (R.id.accept_policy);

        decline.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
//                startActivity(new Intent (PrivacyPolicyActivity.this, RegisterActivity.class));
                finish ();
            }
        });
    }
}
