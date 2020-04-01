package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.jungledjumble.R;

public class OptionalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Button back;
    Spinner glass_spinner, disorder_spinner, disability_spinner;
    Boolean sound_on = true;
    Boolean music_on = true;
    MediaPlayer background_sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_optional);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);

        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        try{music_on = getIntent().getExtras().getBoolean("music_on",true);}
        catch (Exception e){}

        if (music_on){background_sound.start();}

        back = findViewById (R.id.back);
        glass_spinner = findViewById (R.id.glass_spinner);
        disorder_spinner = findViewById (R.id.disorder_spinner);
        disability_spinner = findViewById (R.id.disability_spinner);

        ArrayAdapter<CharSequence> needAdaptor = ArrayAdapter.createFromResource (this,R.array.need,android.R.layout.simple_spinner_item);
        needAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        glass_spinner.setAdapter (needAdaptor);
        glass_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> haveAdaptor = ArrayAdapter.createFromResource (this,R.array.have,android.R.layout.simple_spinner_item);
        haveAdaptor .setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        disorder_spinner.setAdapter (haveAdaptor );
        disorder_spinner.setOnItemSelectedListener (this);

//        ArrayAdapter<CharSequence> glassAdaptor = ArrayAdapter.createFromResource (this,R.array.need,android.R.layout.simple_spinner_item);
//        glassAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        disability_spinner.setAdapter (haveAdaptor);
        disability_spinner.setOnItemSelectedListener (this);

        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                if (sound_on){click_sound.start();}
                Intent intent = new Intent (OptionalActivity.this, RegisterActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
