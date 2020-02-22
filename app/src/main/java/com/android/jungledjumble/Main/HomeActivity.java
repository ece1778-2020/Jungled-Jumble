package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.jungledjumble.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        Intent intent = getIntent ();
        String username = intent.getStringExtra ("username");
        Toast.makeText (this, "Welcome, "+username+"!", Toast.LENGTH_SHORT).show ();
    }
}
