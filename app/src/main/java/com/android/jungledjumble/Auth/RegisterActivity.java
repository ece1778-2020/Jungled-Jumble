package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.Main.ReturnActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Utils.FirebaseUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.android.jungledjumble.BuildConfig;
import com.android.jungledjumble.Utils.Utils;
import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    // Layout
    EditText mUsername, mAge, mGender;
    ImageView settings_cancel_button;
    ImageView registerButton, registerButtonBottom;
    CircleImageView profile_image;
    TextView txt_login;
    Spinner gender_spinner,hand_spinner,glass_spinner,disorder_spinner,disability_spinner;
    ImageView female_button, male_button, other_gender_button;
    int state_female, state_male, state_other_gender;
    int fruit_type;

    //

    private FirebaseUtils firebaseUtils;
    private Utils utils;
    File photoFile;
    ProgressDialog pd;
    private final int TAKE_CAMERA_REQUEST = 21;
    int EMPTY_PROFILE_IMAGE = 1;



    private static final String TAG = "RegisterActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_register);


        registerButton = findViewById (R.id.register);
        registerButtonBottom = findViewById (R.id.register2);
        profile_image = findViewById (R.id.profile_image);

        mUsername = findViewById (R.id.username_text);
        mAge = findViewById (R.id.age_text);

        hand_spinner = findViewById (R.id.hand_spinner);
        glass_spinner = findViewById (R.id.glass_spinner);
        disorder_spinner = findViewById (R.id.disorder_spinner);
        disability_spinner = findViewById (R.id.disability_spinner);
        settings_cancel_button = findViewById (R.id.settings_cancel_button);

        female_button = findViewById (R.id.female_button);
        male_button = findViewById (R.id.male_button);
        other_gender_button = findViewById (R.id.other_gender_button);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        female_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (state_female == 0){
                    female_button.setActivated (true);
                    male_button.setActivated (false);
                    other_gender_button.setActivated (false);
                }else{
                    female_button.setActivated (false);
                }
                state_female = 1-state_female;
            }
        });
        male_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (state_male == 0){
                    male_button.setActivated (true);
                    other_gender_button.setActivated (false);
                    female_button.setActivated (false);
                }else{
                    male_button.setActivated (false);
                }
                state_male = 1-state_male;
            }
        });
        other_gender_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (state_other_gender == 0){
                    other_gender_button.setActivated (true);
                    female_button.setActivated (false);
                    male_button.setActivated (false);
                }else{
                    other_gender_button.setActivated (false);
                }
                state_other_gender = 1-state_other_gender;
            }
        });

//        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource (this,R.array.gender,R.layout.custom_spinner);
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.custom_spinner);
//        adaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
//        gender_spinner.setAdapter (adaptor);
//        gender_spinner.setOnItemSelectedListener (this);


        ArrayAdapter<CharSequence> handAdaptor = ArrayAdapter.createFromResource (this,R.array.hand,R.layout.custom_spinner);
        handAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        hand_spinner.setAdapter (handAdaptor);
        hand_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> needAdaptor = ArrayAdapter.createFromResource (this,R.array.need,R.layout.custom_spinner);
        needAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        glass_spinner.setAdapter (needAdaptor);
        glass_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> haveAdaptor = ArrayAdapter.createFromResource (this,R.array.have,R.layout.custom_spinner);
        haveAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        disorder_spinner.setAdapter (haveAdaptor );
        disorder_spinner.setOnItemSelectedListener (this);

        disability_spinner.setAdapter (haveAdaptor);
        disability_spinner.setOnItemSelectedListener (this);

        firebaseUtils = new FirebaseUtils (RegisterActivity.this);
        utils = new Utils (this);

        Intent intent = getIntent ();
        fruit_type = intent.getIntExtra ("fruit_type",0);

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //background_sound.pause();
                startActivity(new Intent (RegisterActivity.this, SelectUserActivity.class));
            }
        });
        SetProfileImage();
        Register(registerButton);
        Register(registerButtonBottom);
//        nextPage.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View view){
//                Intent intent = new Intent (RegisterActivity.this, OptionalActivity.class);
//                startActivity(intent);
//            }
//        });
    }


    private void SetProfileImage() {
        profile_image.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                TakePhoto ();
            }
        });
    }

    private void TakePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Log.d(TAG, "Image file created");
                Uri photoURI = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_CAMERA_REQUEST);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat ("yyyyMMddHHmmss").format(new Date ());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(mFileName, ".jpg", storageDir);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult (requestCode,
                resultCode,
                data);
        if (requestCode == TAKE_CAMERA_REQUEST
                && resultCode == RESULT_OK) {
            Glide.with (RegisterActivity.this).load (photoFile).into (profile_image);
            Log.d(TAG, "image displayed in the image view");
        } else {
            Log.d(TAG, requestCode + " " + (resultCode == RESULT_OK));
        }

    }

    private void Register(ImageView button){
        button.setOnClickListener (new View.OnClickListener () {
            public void onClick(View view) {
                pd = new ProgressDialog (RegisterActivity.this);
                pd.setMessage ("Please wait..");
                pd.show ();

                String str_username = mUsername.getText ().toString ();
                String str_age = mAge.getText ().toString ();
//                String str_gender = mGender.getText ().toString ();
                String str_gender;
                if (female_button.isActivated ()){
                    str_gender = "Female";
                }else if (male_button.isActivated ()){
                    str_gender = "Male";
                }else if(other_gender_button.isActivated ()){
                    str_gender = "Other gender";
                }else{
                    str_gender = "Unknown gender";
                }

                String str_hand = hand_spinner.getSelectedItem ().toString ();
                String str_glass = glass_spinner.getSelectedItem ().toString ();
                String str_disorder = disorder_spinner.getSelectedItem ().toString ();
                String str_disability = disability_spinner.getSelectedItem ().toString ();

                if (Integer.parseInt (str_age) < 0) {
                    Toast.makeText (RegisterActivity.this, "Found missing fields!", Toast.LENGTH_SHORT).show ();
                    mAge.setText ("");
                    pd.dismiss ();
                } else {
                    firebaseUtils.signUp (str_username,str_age,str_gender,photoFile,str_hand,str_glass,str_disorder,str_disability,fruit_type,0);
                    pd.dismiss();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition (position).toString ();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
