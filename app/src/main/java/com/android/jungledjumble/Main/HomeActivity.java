package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
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
import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Utils utils;
    ImageView left,right,quit, cancel_button, pause_button, continue_pause, restart_pause, quit_pause, next_level_pic_right, next_level_pic_left,
            monkey_back, monkey_back_right, monkey_back_left, oranges_translation_right, oranges_translation_left;
    ImageView points_button,lives_1,lives_2,lives_3;
    ImageView background;
    TextView fruits_collected;
    RecyclerView orangeViewLeft, orangeViewRight;
    private int level,points,rewards,fruits;
    private int life_counter;
    private String choices, correct_choices;
    private UserResults userResults;
    private Sizes sizes;
    String username = "";
    private long tStart;
    private long tEnd;
    private long tDelta;
    private double elapsedSeconds;
    int[] sizes_large,sizes_small;
    int fruitType, char_selection;
    String last_side_pressed ="";


    TextView textView_whichtree;
    TextView textView_countdown;
    int countdown = 3;
   String next_level_flag="No";
    private int larger_side;
    private static int TOTAL_LEVELS = 5;
    private final String TAG = "HomeActivity";
    FrameLayout frameLay3;
    TranslateAnimation trans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

int temp=2;

        utils = new Utils (HomeActivity.this);
        left = findViewById (R.id.left);
        right = findViewById (R.id.right);
       // quit = findViewById (R.id.quit);
       // cancel_button= findViewById (R.id.cancel_button);
        pause_button= findViewById (R.id.pause_button);
        continue_pause= findViewById (R.id.continue_pause);
        restart_pause= findViewById (R.id.restart_pause);
        quit_pause= findViewById (R.id.quit_pause);
        frameLay3= findViewById (R.id.frameLay3);
        next_level_pic_right= findViewById (R.id.next_level_pic_right);
        next_level_pic_left= findViewById (R.id.next_level_pic_left);
        monkey_back = findViewById (R.id.monkey_back);
        monkey_back_right= findViewById (R.id.monkey_back_right);
        monkey_back_left= findViewById (R.id.monkey_back_left);
        background = findViewById (R.id.main_game_background);

        points_button= findViewById (R.id.points_button);
        lives_1= findViewById (R.id.lives_1);
        lives_2= findViewById (R.id.lives_2);
        lives_3= findViewById (R.id.lives_3);
        fruits_collected= findViewById (R.id.fruits_collected);

        oranges_translation_right= findViewById (R.id.oranges_translation_right);
        oranges_translation_left= findViewById (R.id.oranges_translation_left);
        Intent intent = getIntent ();

        textView_whichtree = findViewById (R.id.textView_whichtree);
        textView_countdown = findViewById (R.id.textView_countdown);


        monkey_back.setVisibility(View.VISIBLE);
        monkey_back_left.setVisibility(View.GONE);
        monkey_back_right.setVisibility(View.GONE);



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


        life_counter = intent.getIntExtra ("life_counter",3);
        if (life_counter == 3){
            lives_2.setVisibility (View.GONE);
            lives_1.setVisibility (View.GONE);
            lives_3.setVisibility (View.VISIBLE);
        }else if (life_counter == 2){
            lives_3.setVisibility (View.GONE);
            lives_2.setVisibility (View.VISIBLE);
            lives_1.setVisibility (View.GONE);

        }else if (life_counter == 1){
            lives_3.setVisibility (View.GONE);
            lives_2.setVisibility (View.GONE);
            lives_1.setVisibility (View.VISIBLE);
        }else{
            Log.d(TAG, "Error! the user should quit the game now");
        }


        try{
            Log.d(TAG,intent.getStringExtra ("level"));
            next_level_flag = intent.getStringExtra("next_level_flag");
            last_side_pressed = intent.getStringExtra ("last_side_pressed");
            level = Integer.parseInt (intent.getStringExtra ("level"));
            points = Integer.parseInt (intent.getStringExtra ("points"));
            rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
            choices = intent.getStringExtra ("choices");
            correct_choices = intent.getStringExtra ("correct_choices");

            userResults = new UserResults (level,points,rewards,choices,correct_choices,"","");
        }catch (Exception e){
            userResults = new UserResults (0,0,0,"","","","");
        }

        char_selection = intent.getIntExtra ("char_selection",0);
        if (char_selection == 1){
            Glide.with(this).load(R.drawable.sloth_middle).into(monkey_back);
            Glide.with(this).load(R.drawable.sloth_left).into(monkey_back_left);
            Glide.with(this).load(R.drawable.sloth_right).into(monkey_back_right);
            Glide.with(this).load(R.drawable.sloth_lives1).into(lives_1);
            Glide.with(this).load(R.drawable.sloth_lives2).into(lives_2);
            Glide.with(this).load(R.drawable.sloth_lives3).into(lives_3);
        }

        final int trial = intent.getIntExtra ("trial",1);
        if (trial == 1){
            Glide.with(this).load(R.drawable.main_game_background).into(background);
        }else if (trial == 2){
            Glide.with(this).load(R.drawable.background_afternoon).into(background);
        }else{
            Glide.with(this).load(R.drawable.background_evening).into(background);
        }


        username= intent.getStringExtra ("username");
        if (username == null){username="";}

        fruits = intent.getIntExtra ("fruits",0);
        fruitType = intent.getIntExtra ("fruit_type",0);
        Log.d("test",String.valueOf (fruitType));


        if (userResults.getLevel ()==0){
            //Toast.makeText (this, "Welcome "+username+"!", Toast.LENGTH_SHORT).show ();

                next_level_pic_left.setVisibility(View.VISIBLE);
                Animation RotateLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_left);
                next_level_pic_left.startAnimation(RotateLeft);

                next_level_pic_right.setVisibility(View.VISIBLE);
                Animation RotateRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right);
                next_level_pic_right.startAnimation(RotateRight);



            points_button.setVisibility(View.GONE);
            lives_3.setVisibility(View.GONE);
            fruits_collected.setVisibility(View.GONE);

            Animation TranslateInto = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_into);

            points_button.setVisibility(View.VISIBLE);
            points_button.startAnimation(TranslateInto);

            lives_3.setVisibility(View.VISIBLE);
            lives_3.startAnimation(TranslateInto);

            fruits_collected.setVisibility(View.VISIBLE);
            fruits_collected.startAnimation(TranslateInto);



            if (next_level_flag.equals("No")) {


            //background_sound.start();

            textView_whichtree.setVisibility(View.VISIBLE);
            textView_countdown.setVisibility(View.VISIBLE);

            final Animation in = new AlphaAnimation(0.0f, 1.0f);
            in.setDuration(800);

            final Animation out = new AlphaAnimation(1.0f, 0.0f);
            out.setDuration(800);

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

        }}else{
            if (last_side_pressed.equals("right")) {

                monkey_back.setVisibility(View.GONE);
                monkey_back_left.setVisibility(View.GONE);
                monkey_back_right.setVisibility(View.VISIBLE);

                oranges_translation_right.setVisibility(View.VISIBLE);
                Animation TranslateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right);
                oranges_translation_right.startAnimation(TranslateRight);

                TranslateRight.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation Animation) {}

                    @Override
                    public void onAnimationRepeat(Animation Animation) {}

                    @Override
                    public void onAnimationEnd(Animation Animation) {
                        oranges_translation_right.setVisibility(View.GONE);

                        monkey_back.setVisibility(View.VISIBLE);
                        monkey_back_left.setVisibility(View.GONE);
                        monkey_back_right.setVisibility(View.GONE);
                    }
                });
            }
            else if (last_side_pressed.equals("left")) {

                monkey_back.setVisibility(View.GONE);
                monkey_back_left.setVisibility(View.VISIBLE);
                monkey_back_right.setVisibility(View.GONE);

                oranges_translation_left.setVisibility(View.VISIBLE);
                Animation TranslateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_left);
                oranges_translation_left.startAnimation(TranslateLeft);

                TranslateLeft.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation Animation) {}

                    @Override
                    public void onAnimationRepeat(Animation Animation) {}

                    @Override
                    public void onAnimationEnd(Animation Animation) {
                        oranges_translation_left.setVisibility(View.GONE);

                        monkey_back.setVisibility(View.VISIBLE);
                        monkey_back_left.setVisibility(View.GONE);
                        monkey_back_right.setVisibility(View.GONE);
                    }
                });
            }







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
//        Log.d(TAG,Arrays.toString (array)+String.valueOf (range.get (0))+range.get (1));
        sizes_small = Arrays.copyOfRange (array,0,12);
        sizes_large = Arrays.copyOfRange (array,12,24);
        // Update the results
        userResults.updateLevel ();

        // Display the oranges
        orangeViewLeft = findViewById (R.id.oranges_left);
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
            orangeViewLeft.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_small,fruitType));
            orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_large,fruitType));
        }else{
            orangeViewLeft.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_large,fruitType));
            orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,sizes_small,fruitType));
        }
        larger_side = 1 - randomSelection;

        //Timer starts
        tStart = System.currentTimeMillis();

        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                monkey_back.setVisibility(View.GONE);
                monkey_back_left.setVisibility(View.VISIBLE);
                monkey_back_right.setVisibility(View.GONE);

                //Timer ends
                tEnd = System.currentTimeMillis();
                tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 800.0;
                //Toast.makeText(HomeActivity.this, " " + elapsedSeconds, Toast.LENGTH_SHORT).show();

                click_sound.start();

                finish();
                userResults.updateChoices ("0");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 0){
                    userResults.updatePoints ();
                }else{
                    life_counter --;
                }
                if (userResults.getLevel () < TOTAL_LEVELS && life_counter > 0){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("last_side_pressed","left");
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    intent.putExtra ("fruits",fruits);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    intent.putExtra ("fruit_type",fruitType);
                    intent.putExtra ("life_counter",life_counter);
                    intent.putExtra ("char_selection",char_selection);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);
                    intent.putExtra ("fruits",fruits);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    intent.putExtra ("fruit_type",fruitType);
                    intent.putExtra ("char_selection",char_selection);
                    intent.putExtra ("trial",trial+1);
                    startActivity(intent);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){



                monkey_back.setVisibility(View.GONE);
                monkey_back_left.setVisibility(View.GONE);
                monkey_back_right.setVisibility(View.VISIBLE);

                //Timer ends
                tEnd = System.currentTimeMillis();
                tDelta = tEnd - tStart;
                elapsedSeconds = tDelta / 800.0;
                //Toast.makeText(HomeActivity.this, " " + elapsedSeconds, Toast.LENGTH_SHORT).show();

                click_sound.start();

                finish();
                userResults.updateChoices ("1");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 1){
                    userResults.updatePoints ();
                }else{
                    life_counter --;
                }
                if (userResults.getLevel () < TOTAL_LEVELS && life_counter > 0){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("last_side_pressed","right");
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    intent.putExtra ("fruits",fruits);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    intent.putExtra ("fruit_type",fruitType);
                    intent.putExtra ("life_counter",life_counter);
                    intent.putExtra ("char_selection",char_selection);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);
                    intent.putExtra ("fruits",fruits);
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    intent.putExtra ("fruit_type",fruitType);
                    intent.putExtra ("char_selection",char_selection);
                    intent.putExtra ("trial",trial+1);
                    startActivity(intent);
                }
            }
        });
   /*     quit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (HomeActivity.this, StartActivity.class));
            }
        });*/
/*        cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (HomeActivity.this, StartActivity.class));
            }
        });*/

        pause_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                frameLay3.bringToFront();
                frameLay3.setVisibility(View.VISIBLE);

            }
        });

        restart_pause.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                frameLay3.setVisibility(View.GONE);
                Intent intent = new Intent (HomeActivity.this, HomeActivity.class);
                intent.putExtra ("username",username);
                List<Integer> range = new ArrayList<Integer> ();
                range.add(115);
                range.add(130);

                intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                startActivity(intent);
            }
        });

        continue_pause.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //frameLay3.bringToFront();
                frameLay3.setVisibility(View.GONE);

            }
        });

        quit_pause.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                //frameLay3.setVisibility(View.GONE);
                startActivity(new Intent (HomeActivity.this, StartActivity.class));
            }
        });

    }





}
