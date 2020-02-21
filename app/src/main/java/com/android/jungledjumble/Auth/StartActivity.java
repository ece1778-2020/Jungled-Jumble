package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button play,settings;
    FirebaseUser firebaseUser;
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        play = findViewById(R.id.play);
        settings = findViewById(R.id.settings);

        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, SelectUserActivity.class));
            }
        });

        settings.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(StartActivity.this, SettingsAcitivity.class));
            }
        });
    }
}