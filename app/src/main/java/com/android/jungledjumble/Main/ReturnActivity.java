
package com.android.jungledjumble.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Models.GlobalClass;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Utils.FirebaseUtils;
import com.android.jungledjumble.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReturnActivity extends AppCompatActivity {
    ImageView replay, menu,plots_button, cancel_button,next_level_pic_right, next_level_pic_left;
    TextView fruitsCollected, correctChoiceRate, points_collected;
    int level,points,rewards,fruits,fruitType;
    UserResults userResults;
    String username, choices,correct_choices;
    private FirebaseUtils firebaseUtils;
    private FirebaseFirestore database;
    //Button button_charts;
    MediaPlayer background_sound;
    double accRate;

    final static String TAG = "ReturnActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_return);
        database = FirebaseFirestore.getInstance ();

        Utils utils = new Utils(this);
        utils.hideSystemUI ();


        replay = findViewById (R.id.replay);
        menu = findViewById (R.id.menu);
        plots_button = findViewById (R.id.plots_button);
        cancel_button = findViewById (R.id.cancel_button);
        fruitsCollected = findViewById (R.id.fruits_collected);
        points_collected= findViewById (R.id.points_collected);
        correctChoiceRate = findViewById (R.id.correct_choice);
        firebaseUtils = new FirebaseUtils (ReturnActivity.this);
        next_level_pic_right= findViewById (R.id.next_level_pic_right);
        next_level_pic_left= findViewById (R.id.next_level_pic_left);

        GlobalClass globalClass = new GlobalClass ();
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

        fruits = intent.getIntExtra ("fruits",0);
        fruitType = intent.getIntExtra ("fruit_type",0);

        final ArrayList<Integer> range = intent.getIntegerArrayListExtra ("range");


        int n = choices.length ();
        int count = 0;
        for (int i=0;i<n;i++){
            if (choices.charAt (i) == correct_choices.charAt (i)){
                count ++;
            }
        }
         accRate = 1d * count * 100 / n;

        int cur_fruits = utils.AccToFruits (points);
        fruits = fruits + cur_fruits;
        int updateSize = globalClass.getUpdateSize ();
        if ((int) accRate> globalClass.getAccThreshold ()-1){
            if (range.get(0)+2*updateSize+1>range.get (1)){
                //Toast.makeText (this, "You finised the game!", Toast.LENGTH_SHORT).show ();
            }else{

                next_level_pic_left.setVisibility(View.VISIBLE);
                Animation RotateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left);
                next_level_pic_left.startAnimation(RotateLeft);

                next_level_pic_right.setVisibility(View.VISIBLE);
                Animation RotateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right);
                next_level_pic_right.startAnimation(RotateRight);

                Toast.makeText (this, "Next Level!", Toast.LENGTH_SHORT).show ();
                range.set(0,range.get (0)+updateSize);
                range.set(1,range.get (1)-updateSize);
                intent = new Intent (ReturnActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                List<Integer> indices = new ArrayList<Integer> ();
                intent.putIntegerArrayListExtra ("range", range);
                intent.putExtra ("fruits",fruits);
                intent.putExtra("next_level_flag", "Yes");
                intent.putExtra ("fruit_type",fruitType);
                startActivity(intent);
            }

        }else{
//            Toast.makeText (this, "You lose the game...", Toast.LENGTH_SHORT).show ();
        }


        firebaseUtils.updateResults (username, choices,correct_choices,cur_fruits);
        database.collection ("users")
                .whereEqualTo ("username",username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                int fruits_total = (((Long) document.get("points")).intValue ());
                                fruitsCollected.setText (String.valueOf (fruits_total));
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
//        fruitsCollected.setText (String.valueOf (points));
        points_collected.setText (String.valueOf(fruits));
        correctChoiceRate.setText (String.valueOf(points)+"%");

        replay.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (ReturnActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                List<Integer> range = new ArrayList<Integer> ();
                range.add(115);
                range.add(130);
                intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                intent.putExtra ("fruits",fruits);
                intent.putExtra ("fruit_type",fruitType);
                startActivity(intent);
            }
        });

        menu.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (ReturnActivity.this, StartActivity.class);
                intent.putExtra ("fruits",fruits);
                startActivity(intent);

            }
        });

        plots_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(ReturnActivity.this,ProgressActivity.class);
                intent.putExtra("accRate", accRate);
                startActivity(intent);

            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (ReturnActivity.this, StartActivity.class);
                intent.putExtra ("fruits",fruits);
                startActivity(intent);
            }
        });
    }
}