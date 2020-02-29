package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.FirebaseUtils;

public class ReturnActivity extends AppCompatActivity {
    ImageView replay, menu;
    TextView fruitsCollected, correctChoiceRate;
    int level,points,rewards;
    UserResults userResults;
    String username, choices,correct_choices;
    private FirebaseUtils firebaseUtils;
    //Button button_charts;

    final static String TAG = "ReturnActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_return);

        replay = findViewById (R.id.replay);
        menu = findViewById (R.id.menu);
        fruitsCollected = findViewById (R.id.fruits_collected);
        correctChoiceRate = findViewById (R.id.correct_choice);
        firebaseUtils = new FirebaseUtils (ReturnActivity.this);
       // button_charts = findViewById(R.id.button_charts);

        final MediaPlayer background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        background_sound.start();

        final Intent intent = getIntent ();
        points = Integer.parseInt (intent.getStringExtra ("correct_choice_rate"));
        rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
        username = intent.getStringExtra ("username");
        choices = intent.getStringExtra ("choices");
        correct_choices = intent.getStringExtra ("correct_choices");
        userResults = new UserResults (level,points,rewards,choices,correct_choices);

        firebaseUtils.updateResults (username, choices,correct_choices);


        fruitsCollected.setText (String.valueOf(points)+" fruits collected");
        correctChoiceRate.setText (String.valueOf((int) (Math.random()*100))+"% "+"correct choice");


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
