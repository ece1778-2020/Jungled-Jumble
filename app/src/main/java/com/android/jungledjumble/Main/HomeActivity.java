package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.RegisterActivity;
import com.android.jungledjumble.Auth.SelectUserActivity;
import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Models.Pair;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.FirebaseUtils;
import com.android.jungledjumble.Utils.OrangeAdaptor;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.android.jungledjumble.Utils.Utils;
import com.android.jungledjumble.Models.Sizes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Utils utils;
    ImageView left,right,quit;
    RecyclerView orangeViewLeft, orangeViewRight;
    private int level,points,rewards;
    private String choices, correct_choices;
    private UserResults userResults;
    private Sizes sizes;
    String username = "";
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    int[] sizes_large,sizes_small;

    TextView textView_whichtree;
    TextView textView_countdown;
    int countdown = 5;

    private int larger_side;
    private static int TOTAL_LEVELS = 5;
    private final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        utils = new Utils (HomeActivity.this);
        left = findViewById (R.id.left);
        right = findViewById (R.id.right);
        quit = findViewById (R.id.quit);

        Intent intent = getIntent ();

        textView_whichtree = findViewById (R.id.textView_whichtree);
        textView_countdown = findViewById (R.id.textView_countdown);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        final MediaPlayer background_sound = MediaPlayer.create(this, R.raw.mixed_demo);
        final MediaPlayer transition1_sound = MediaPlayer.create(this, R.raw.rustle1_sfx);
        final MediaPlayer transition2_sound = MediaPlayer.create(this, R.raw.rustle2_sfx);
        final MediaPlayer transition3_sound = MediaPlayer.create(this, R.raw.rustle3_sfx);

        final ArrayList<Integer> range = intent.getIntegerArrayListExtra ("range");


        utils.hideSystemUI ();

        //************************
        // Level: the current level of the game
        // Points: the number of times user makes the correct choice
        // Rewards: the cumulated rewards of current user
        //************************
        // Retrieve the results
        try{
            Log.d(TAG,intent.getStringExtra ("level"));
            level = Integer.parseInt (intent.getStringExtra ("level"));
            points = Integer.parseInt (intent.getStringExtra ("points"));
            rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
            choices = intent.getStringExtra ("choices");
            correct_choices = intent.getStringExtra ("correct_choices");
            userResults = new UserResults (level,points,rewards,choices,correct_choices,"","");
        }catch (Exception e){
            userResults = new UserResults (0,0,0,"","","","");
        }
//        Log.d("test",String.valueOf (choices));
        username= intent.getStringExtra ("username");
        if (username == null){username="";}

        if (userResults.getLevel ()==0){
            Toast.makeText (this, "Welcome "+username+"!", Toast.LENGTH_SHORT).show ();

            //background_sound.start();

            textView_whichtree.setVisibility(View.VISIBLE);
            textView_countdown.setVisibility(View.VISIBLE);

            final Animation in = new AlphaAnimation(0.0f, 1.0f);
            in.setDuration(1000);

            final Animation out = new AlphaAnimation(1.0f, 0.0f);
            out.setDuration(1000);

            textView_countdown.setText(String.valueOf(countdown));
            textView_countdown.startAnimation(in);

            in.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    textView_countdown.startAnimation(out);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

            out.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    countdown--;
                    if (countdown == 0) {
                        textView_countdown.setText("");
                        textView_whichtree.startAnimation(out);
                        textView_countdown.startAnimation(out);
                        textView_whichtree.setVisibility(View.GONE);
                        textView_countdown.setVisibility(View.GONE);
                        return;}
                    else if  (countdown < 0) {
                        return;
                    }
                    else{
                        textView_countdown.setText(String.valueOf(countdown));
                        textView_countdown.startAnimation(in);}
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        }else{

            if (userResults.getLevel ()==1){transition1_sound.start();}
            if (userResults.getLevel ()==2){transition2_sound.start();}
            if (userResults.getLevel ()==3){transition3_sound.start();}
            if (userResults.getLevel ()==4){transition1_sound.start();}
            if (userResults.getLevel ()==5){transition2_sound.start();}

            textView_whichtree.setText(String.valueOf(TOTAL_LEVELS-userResults.getLevel ()) + " left!");
            textView_whichtree.setVisibility(View.VISIBLE);
            final Animation out1 = new AlphaAnimation(1.0f, 0.0f);
            out1.setDuration(3000);
            textView_whichtree.startAnimation(out1);
            out1.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    textView_whichtree.setVisibility(View.GONE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });

        }

        int[] array = utils.genOrangeSizes (range.get(0),range.get(1),70,165,8);
        Log.d(TAG,Arrays.toString (array)+String.valueOf (range.get (0))+range.get (1));
        sizes_small = Arrays.copyOfRange (array,0,12);
        sizes_large = Arrays.copyOfRange (array,12,24);
        // Update the results
        userResults.updateLevel ();

        // Display the oranges
        orangeViewLeft = findViewById (R.id.oranges);
        orangeViewLeft.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        orangeViewRight = findViewById (R.id.oranges_right);
        orangeViewRight.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        // Random selection is a value of either 0 or 1 that represents which side will be larger
        Random r = new Random ();
        int randomSelection = r.nextInt(2);

        
        if (randomSelection == 0){
            orangeViewLeft.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_small,level));
            orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_large,level));
        }else{
            orangeViewLeft.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_large,level));
            orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_small,level));
        }
        larger_side = 1 - randomSelection;

        //Timer starts
        tStart = System.currentTimeMillis();

        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //Timer ends
                tEnd = System.currentTimeMillis();
                tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                //Toast.makeText(HomeActivity.this, " " + elapsedSeconds, Toast.LENGTH_SHORT).show();

                click_sound.start();

                finish();
                userResults.updateChoices ("0");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 0){
                    userResults.updatePoints ();
                }
                if (userResults.getLevel () < TOTAL_LEVELS){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);

                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    startActivity(intent);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //Timer ends
                tEnd = System.currentTimeMillis();
                tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 1000.0;
                //Toast.makeText(HomeActivity.this, " " + elapsedSeconds, Toast.LENGTH_SHORT).show();

                click_sound.start();

                finish();
                userResults.updateChoices ("1");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 1){
                    userResults.updatePoints ();
                }
                if (userResults.getLevel () < TOTAL_LEVELS){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);
//                    if (range.get(0)+3>range.get (1)){
//                        intent.putExtra ("finished",1);
//                    }else{
//                        range.set(0,range.get (0)+1);
//                        range.set(1,range.get (1)-1);
//                        intent.putExtra ("finished",0);
//                    }
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    startActivity(intent);
                }
            }
        });
        quit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (HomeActivity.this, StartActivity.class));
            }
        });


    }





}
