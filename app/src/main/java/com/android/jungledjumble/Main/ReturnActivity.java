package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.FirebaseUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {
    ImageView replay, menu;
    TextView fruitsCollected, correctChoiceRate;
    int level,points,rewards;
    UserResults userResults;
    String username, choices,correct_choices;
    private FirebaseUtils firebaseUtils;
    //Button button_charts;
    MediaPlayer background_sound;

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

       // final MediaPlayer background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        //background_sound.start();

        if (background_sound != null && background_sound.isPlaying()) {
            background_sound.stop();
            background_sound.reset();
        }
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        //
        background_sound.start();



        Intent intent = getIntent ();
        points = Integer.parseInt (intent.getStringExtra ("correct_choice_rate"));
        Log.d("test",String.valueOf (points));
        rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
        username = intent.getStringExtra ("username");
        choices = intent.getStringExtra ("choices");
        correct_choices = intent.getStringExtra ("correct_choices");
        userResults = new UserResults (level,points,rewards,choices,correct_choices,"","");

        final ArrayList<Integer> range = intent.getIntegerArrayListExtra ("range");


        if (points > 59){
            if (range.get(0)+3>range.get (1)){
                Toast.makeText (this, "You finised the game!", Toast.LENGTH_SHORT).show ();
            }else{
                Toast.makeText (this, "Next Level!", Toast.LENGTH_SHORT).show ();
                range.set(0,range.get (0)+1);
                range.set(1,range.get (1)-1);
                intent = new Intent (ReturnActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                List<Integer> indices = new ArrayList<Integer> ();
                intent.putIntegerArrayListExtra ("range", range);
                startActivity(intent);
            }

        }else{
            Toast.makeText (this, "You lose the game...", Toast.LENGTH_SHORT).show ();
        }

        firebaseUtils.updateResults (username, choices,correct_choices);


        fruitsCollected.setText (String.valueOf(points)+" fruits collected");
        correctChoiceRate.setText (String.valueOf((int) (Math.random()*100))+"% "+"correct choice");


        replay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (ReturnActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                List<Integer> indices = new ArrayList<Integer> ();

                for (int i=0;i<10;i++){
                    for (int j=0;j<4;j++){
                        indices.add(i);
                    }
                }
                Collections.shuffle (indices);
                intent.putIntegerArrayListExtra ("indices",(ArrayList<Integer>) indices);
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
