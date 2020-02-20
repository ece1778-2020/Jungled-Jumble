package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    Button login,register;
    FirebaseUser firebaseUser;
    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is already signed in
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(StartActivity.this, HomeActivity.class));
            finish();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent(StartActivity.this, RegisterActivity.class));
            }
        });
    }
}