package com.android.jungledjumble.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.StartActivity;
import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static com.android.jungledjumble.Setting.ProgressActivity.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;

public class Credits extends AppCompatActivity {
    ImageView  credits_continue1, settings_cancel_button1, credits_continue2, settings_cancel_button2, credits_continue3, settings_cancel_button3;
    FrameLayout frameLay1,frameLay2,frameLay3;
    TextView credits_text1_1,credits_text1_2, credits_text2_a, credits_text2_b, credits_text2_c;
    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE=0;
    Boolean sound_on = true;
    Boolean music_on = true;
    MediaPlayer background_sound;

    @Override
    public void onResume(){
        super.onResume();
        Utils utils = new Utils(this);
        utils.hideSystemUI ();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_credits);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        credits_continue1 = findViewById (R.id.credits_continue1);
        settings_cancel_button1 = findViewById (R.id.settings_cancel_button1);
        credits_continue2 = findViewById (R.id.credits_continue2);
        settings_cancel_button2 = findViewById (R.id.settings_cancel_button2);
        credits_continue3 = findViewById (R.id.credits_continue3);
        settings_cancel_button3 = findViewById (R.id.settings_cancel_button3);
        frameLay1 = findViewById (R.id.frameLay1);
        frameLay2 = findViewById (R.id.frameLay2);
        frameLay3 = findViewById (R.id.frameLay3);
        credits_text1_1 = findViewById (R.id.credits_text1_1);
        credits_text1_2 = findViewById (R.id.credits_text1_2);
        credits_text2_a = findViewById (R.id.credits_text2_a);
        credits_text2_b = findViewById (R.id.credits_text2_b);
        credits_text2_c = findViewById (R.id.credits_text2_c);

        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        try{music_on = getIntent().getExtras().getBoolean("music_on",true);}
        catch (Exception e){}

        if (music_on){background_sound.start();}

        //Toast.makeText (this, " " + sound_on, Toast.LENGTH_SHORT).show ();

        credits_continue1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                frameLay2.bringToFront();
                frameLay1.setVisibility(View.GONE);
                frameLay2.setVisibility(View.VISIBLE);
                frameLay3.setVisibility(View.GONE);
            }
        });

        credits_continue2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                frameLay3.bringToFront();
                frameLay1.setVisibility(View.GONE);
                frameLay2.setVisibility(View.GONE);
                frameLay3.setVisibility(View.VISIBLE);
            }
        });

        credits_continue3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                frameLay1.bringToFront();
                frameLay1.setVisibility(View.VISIBLE);
                frameLay2.setVisibility(View.GONE);
                frameLay3.setVisibility(View.GONE);
            }
        });

        settings_cancel_button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(Credits.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        settings_cancel_button2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(Credits.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        settings_cancel_button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(Credits.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        credits_text1_1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                int width ;
                int height ;
                String _OSVERSION = System.getProperty("os.version");
                String _RELEASE = android.os.Build.VERSION.RELEASE;
                String _DEVICE = android.os.Build.DEVICE;
                String _MODEL = android.os.Build.MODEL;
                String _PRODUCT = android.os.Build.PRODUCT;
                String _BRAND = android.os.Build.BRAND;
                String _DISPLAY = android.os.Build.DISPLAY;
                String _CPU_ABI = android.os.Build.CPU_ABI;
                String _CPU_ABI2 = android.os.Build.CPU_ABI2;
                String _UNKNOWN = android.os.Build.UNKNOWN;
                String _HARDWARE = android.os.Build.HARDWARE;
                String _ID = android.os.Build.ID;
                String _MANUFACTURER = android.os.Build.MANUFACTURER;
                String _SERIAL = android.os.Build.SERIAL;
                String _USER = android.os.Build.USER;
                String _HOST = android.os.Build.HOST;
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                width = displayMetrics.widthPixels;
                height = displayMetrics.heightPixels;
                currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());


                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(Credits.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Credits.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                        ActivityCompat.requestPermissions(Credits.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(Credits.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted

                    //generate data
                    StringBuilder data = new StringBuilder();
                    data.append("Number,deviceID,deviceModel,deviceRes,userID,age,gender,handedness,userPerf,session,sessionDate,sessionPerf,trialNum,trialStartTime,trialAnswer,trialCond,trialResp,trialRespTime,trialEndTime");
                    for (int i = 1; i <= 40; i++) {
                        data.append("\n" + i + "," + _ID + "," + _MANUFACTURER + " " + _MODEL + "," + width + " x " + height + "," + "79004" + "," + "23" + "," + "-1" + "," + "1" + "," + "0.92" + "," + "1" + "," + currentDate + "," + "0.95" + "," + "1" + "," + "1" + "," + "1" + "," + "0" + "," + "1" + "," + "1.5" + "," + "2");
                    }
                    try {

                        //saving the file into device
                        FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
                        out.write((data.toString()).getBytes());
                        out.close();

                        //exporting
                        Context context = getApplicationContext();
                        File filelocation = new File(getFilesDir(), "data.csv");
                        Uri path = FileProvider.getUriForFile(context, "com.android.jungledjumble.fileprovider", filelocation);
                        Intent fileIntent = new Intent(Intent.ACTION_SEND);
                        fileIntent.setType("text/csv");
                        fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
                        fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        fileIntent.putExtra(Intent.EXTRA_STREAM, path);
                        startActivity(Intent.createChooser(fileIntent, "Send email"));


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        credits_text1_2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/ece1778-2020/Jungled-Jumble"));
                startActivity(browserIntent);
            }
        });

        credits_text2_a.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                String[] TO = {"said.banoscuevas@mail.utoronto.ca"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                }

            }
        });

        credits_text2_b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                String[] TO = {"zhaodong.yan@mail.utoronto.ca"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                }

            }
        });

        credits_text2_c.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                String[] TO = {"annabel.fan@mail.utoronto.ca"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email"));
                    finish();
                } catch (android.content.ActivityNotFoundException ex) {
                }

            }
        });

    }
}
