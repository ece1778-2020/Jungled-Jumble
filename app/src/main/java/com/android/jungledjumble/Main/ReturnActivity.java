package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Setting.SettingsAcitivity;

public class ReturnActivity extends AppCompatActivity {
    ImageView replay, menu;
    TextView fruitsCollected, correctChoice;
    int level,points,rewards;
    UserResults userResults;
    String username;

    final static String TAG = "ReturnActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_return);

        replay = findViewById (R.id.replay);
        menu = findViewById (R.id.menu);
        fruitsCollected = findViewById (R.id.fruits_collected);
        correctChoice = findViewById (R.id.correct_choice);

        final Intent intent = getIntent ();
        points = Integer.parseInt (intent.getStringExtra ("correct_choice"));
        rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
        username = intent.getStringExtra ("username");
        userResults = new UserResults (level,points,rewards);

        fruitsCollected.setText (String.valueOf(rewards)+" fruits collected");
        correctChoice.setText (String.valueOf(points)+"% "+"correct choice");

        replay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (ReturnActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                startActivity(new Intent (ReturnActivity.this, StartActivity.class));
            }
        });

    }
}
