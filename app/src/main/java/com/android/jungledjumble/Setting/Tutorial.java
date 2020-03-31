package com.android.jungledjumble.Setting;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Tutorial extends AppCompatActivity {

    FrameLayout frameLay1,frameLay2,frameLay3,frameLay4,frameLay5,frameLay6,frameLay7,frameLay8,frameLay9;
    ImageView left2, right2, hand_pointing2, hand_tapping2, left5, right5, hand_pointing5, hand_tapping5, left8, right8, hand_pointing8, hand_tapping8, points_game3, points_game6, lives_1_3,lives_2_3,lives_3_3,lives_1_6,lives_2_6,lives_3_6;
    ImageView next_level_pic_right1,next_level_pic_right3,next_level_pic_right9,next_level_pic_left1,next_level_pic_left3,next_level_pic_left9;
    TextView fruits_collected3,fruits_collected6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_tutorial);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        frameLay1 = findViewById (R.id.frameLay1);
        frameLay2 = findViewById (R.id.frameLay2);
        frameLay3 = findViewById (R.id.frameLay3);
        frameLay4 = findViewById (R.id.frameLay4);
        frameLay5 = findViewById (R.id.frameLay5);
        frameLay6 = findViewById (R.id.frameLay6);
        frameLay7 = findViewById (R.id.frameLay7);
        frameLay8 = findViewById (R.id.frameLay8);
        frameLay9 = findViewById (R.id.frameLay9);

        left2 = findViewById (R.id.left2);
        right2 = findViewById (R.id.right2);
        left5 = findViewById (R.id.left5);
        right5 = findViewById (R.id.right5);
        left8 = findViewById (R.id.left8);
        right8 = findViewById (R.id.right8);

        hand_pointing2 = findViewById (R.id.hand_pointing2);
        hand_tapping2 = findViewById (R.id.hand_tapping2);
        hand_pointing5 = findViewById (R.id.hand_pointing5);
        hand_tapping5 = findViewById (R.id.hand_tapping5);
        hand_pointing8 = findViewById (R.id.hand_pointing8);
        hand_tapping8 = findViewById (R.id.hand_tapping8);

        lives_1_3 = findViewById (R.id.lives_1_3);
        lives_2_3 = findViewById (R.id.lives_2_3);
        lives_3_3 = findViewById (R.id.lives_3_3);

        lives_1_6 = findViewById (R.id.lives_1_6);
        lives_2_6 = findViewById (R.id.lives_2_6);
        lives_3_6 = findViewById (R.id.lives_3_6);

        next_level_pic_right1 = findViewById (R.id.next_level_pic_right1);
        next_level_pic_right3 = findViewById (R.id.next_level_pic_right3);
        next_level_pic_right9 = findViewById (R.id.next_level_pic_right9);
        next_level_pic_left1 = findViewById (R.id.next_level_pic_left1);
        next_level_pic_left3 = findViewById (R.id.next_level_pic_left3);
        next_level_pic_left9 = findViewById (R.id.next_level_pic_left9);

        next_level_pic_right1.setVisibility(View.VISIBLE);
        Animation RotateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right);
        next_level_pic_right1.startAnimation(RotateRight);
        next_level_pic_left1.setVisibility(View.VISIBLE);
        Animation RotateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left);
        next_level_pic_left1.startAnimation(RotateLeft);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after delay




                frameLay1.setVisibility(View.GONE);
                frameLay2.setVisibility(View.VISIBLE);



                hand_pointing2.setVisibility(View.VISIBLE);
                Animation TranslateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);
                hand_pointing2.startAnimation(TranslateDown);
                TranslateDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation Animation) {}
                    @Override
                    public void onAnimationRepeat(Animation Animation) {}
                    @Override
                    public void onAnimationEnd(Animation Animation) {
                    }
                });


                hand_tapping2.setVisibility(View.VISIBLE);
                Animation FadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                hand_tapping2.startAnimation(FadeIn);
                FadeIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation Animation) {}
                    @Override
                    public void onAnimationRepeat(Animation Animation) {}
                    @Override
                    public void onAnimationEnd(Animation Animation) {
                    }
                });

            }
        }, 3000);






        left2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        right2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                next_level_pic_right3.setVisibility(View.VISIBLE);
                Animation RotateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right);
                next_level_pic_right3.startAnimation(RotateRight);
                next_level_pic_left3.setVisibility(View.VISIBLE);
                Animation RotateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left);
                next_level_pic_left3.startAnimation(RotateLeft);

                frameLay2.setVisibility(View.GONE);
                frameLay3.setVisibility(View.VISIBLE);

                Animation TranslateInto = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_into);

       /*         points_game3.setVisibility(View.VISIBLE);
                points_game3.startAnimation(TranslateInto);*/

                lives_3_3.setVisibility(View.VISIBLE);
                lives_3_3.startAnimation(TranslateInto);

       /*         fruits_collected3.setVisibility(View.VISIBLE);
                fruits_collected3.startAnimation(TranslateInto);*/



                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after delay

                        frameLay3.setVisibility(View.GONE);
                        frameLay4.setVisibility(View.VISIBLE);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after delay

                                frameLay4.setVisibility(View.GONE);
                                frameLay5.setVisibility(View.VISIBLE);

                                hand_pointing5.setVisibility(View.VISIBLE);
                                hand_tapping5.setVisibility(View.GONE);
                                Animation TranslateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);
                                hand_pointing5.startAnimation(TranslateDown);
                                TranslateDown.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation Animation) {}
                                    @Override
                                    public void onAnimationRepeat(Animation Animation) {}
                                    @Override
                                    public void onAnimationEnd(Animation Animation) {
                                    }
                                });

                                hand_tapping5.setVisibility(View.VISIBLE);
                                Animation FadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                                hand_tapping5.startAnimation(FadeIn);
                                FadeIn.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation Animation) {}
                                    @Override
                                    public void onAnimationRepeat(Animation Animation) {}
                                    @Override
                                    public void onAnimationEnd(Animation Animation) {
                                    }
                                });

                            }
                        }, 2000);


                    }
                }, 2000);


            }
        });




        right5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        left5.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                frameLay5.setVisibility(View.GONE);
                frameLay6.setVisibility(View.VISIBLE);

                Animation TranslateInto = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_into);

          /*      points_game6.setVisibility(View.VISIBLE);
                points_game6.startAnimation(TranslateInto);*/

                lives_2_6.setVisibility(View.VISIBLE);
                lives_2_6.startAnimation(TranslateInto);

/*                fruits_collected6.setVisibility(View.VISIBLE);
                fruits_collected6.startAnimation(TranslateInto);*/

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after delay

                        frameLay6.setVisibility(View.GONE);
                        frameLay7.setVisibility(View.VISIBLE);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Do something after delay

                                frameLay7.setVisibility(View.GONE);
                                frameLay8.setVisibility(View.VISIBLE);

                                hand_pointing8.setVisibility(View.VISIBLE);
                                hand_tapping8.setVisibility(View.GONE);
                                Animation TranslateDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_down);
                                hand_pointing8.startAnimation(TranslateDown);
                                TranslateDown.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation Animation) {}
                                    @Override
                                    public void onAnimationRepeat(Animation Animation) {}
                                    @Override
                                    public void onAnimationEnd(Animation Animation) {
                                    }
                                });

                                hand_tapping8.setVisibility(View.VISIBLE);
                                Animation FadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
                                hand_tapping8.startAnimation(FadeIn);
                                FadeIn.setAnimationListener(new Animation.AnimationListener() {
                                    @Override
                                    public void onAnimationStart(Animation Animation) {}
                                    @Override
                                    public void onAnimationRepeat(Animation Animation) {}
                                    @Override
                                    public void onAnimationEnd(Animation Animation) {
                                    }
                                });

                            }
                        }, 2000);


                    }
                }, 2000);


            }
        });


        right8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

            }
        });

        left8.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                next_level_pic_right9.setVisibility(View.VISIBLE);
                Animation RotateRight = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_right);
                next_level_pic_right9.startAnimation(RotateRight);
                next_level_pic_left9.setVisibility(View.VISIBLE);
                Animation RotateLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_left);
                next_level_pic_left9.startAnimation(RotateLeft);
                frameLay8.setVisibility(View.GONE);
                frameLay9.setVisibility(View.VISIBLE);




                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after delay



                        startActivity(new Intent (Tutorial.this, StartActivity.class));

                    }
                }, 4000);


            }
        });





    }



}
