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

public class SelectUserActivity extends AppCompatActivity {
    ImageView guest, existing_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_select_user);

        guest = findViewById (R.id.new_user);
        existing_user = findViewById (R.id.existing_user);
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);

        guest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (SelectUserActivity.this, HomeActivity.class));
            }
        });

        existing_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (SelectUserActivity.this, UserListActivity.class));
            }
        });
    }
}
