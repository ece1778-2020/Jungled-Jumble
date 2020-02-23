package com.android.jungledjumble.Auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.FirebaseUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import com.android.jungledjumble.BuildConfig;
import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity {
    // Layout
    EditText mUsername, mAge, mGender;
    Button registerButton;
    CircleImageView profile_image;
    TextView txt_login;

    //

    private FirebaseUtils firebaseUtils;
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
        profile_image = findViewById (R.id.profile_image);

        mUsername = findViewById (R.id.username_text);
        mAge = findViewById (R.id.age_text);
        mGender = findViewById (R.id.gender_text);




        firebaseUtils = new FirebaseUtils (RegisterActivity.this);

        SetProfileImage();
        Register();
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

    private void Register(){
        registerButton.setOnClickListener (new View.OnClickListener () {
            public void onClick(View view) {
                pd = new ProgressDialog (RegisterActivity.this);
                pd.setMessage ("Please wait..");
                pd.show ();

                String str_username = mUsername.getText ().toString ();
                String str_age = mAge.getText ().toString ();
                String str_gender = mGender.getText ().toString ();
                if (Integer.parseInt (str_age) < 0) {
                    Toast.makeText (RegisterActivity.this, "Found missing fields!", Toast.LENGTH_SHORT).show ();
                    mAge.setText ("");
                    pd.dismiss ();
                } else {
                    firebaseUtils.signUp (str_username,str_age,str_gender,photoFile);
                    pd.dismiss();
                }
            }
        });
    }

}
