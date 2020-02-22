package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    Button decline, accept;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_privacy_policy);

        decline = findViewById (R.id.decline_policy);
        accept = findViewById (R.id.accept_policy);

        decline.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
//                startActivity(new Intent (PrivacyPolicyActivity.this, RegisterActivity.class));
                finish ();
            }
        });
    }
}
