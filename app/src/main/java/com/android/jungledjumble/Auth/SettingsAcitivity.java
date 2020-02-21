package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.R;

public class SettingsAcitivity extends AppCompatActivity {

    Button progress, privacy_policy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_settings_acitivity);

        progress = findViewById (R.id.progress);
        privacy_policy = findViewById (R.id.privacy_policy);


        progress.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent (SettingsAcitivity.this, ProgressActivity.class));
            }
        });

        privacy_policy.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent (SettingsAcitivity.this, PrivacyPolicyActivity.class));
            }
        });
    }
}
