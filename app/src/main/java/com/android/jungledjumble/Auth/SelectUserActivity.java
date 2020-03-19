package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class SelectUserActivity extends AppCompatActivity {
    ImageView guest, existing_user,settings_cancel_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_select_user);

        guest = findViewById (R.id.new_user);
        existing_user = findViewById (R.id.existing_user);
        settings_cancel_button= findViewById (R.id.settings_cancel_button);
        //back = findViewById (R.id.back);
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();


        guest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                Intent intent = new Intent(SelectUserActivity.this, HomeActivity.class);

                List<Integer> range = new ArrayList<Integer> ();
                range.add(100);
                range.add(130);
                intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);

                startActivity(intent);
            }
        });

        existing_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (SelectUserActivity.this, UserListActivity.class));
            }
        });

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //background_sound.pause();
                startActivity(new Intent (SelectUserActivity.this, StartActivity.class));
            }
        });

    }
}
