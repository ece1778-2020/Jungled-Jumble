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
    EditText mUsername, mBio, mEmail, mPassword, mConfirmPassword;
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
        txt_login = findViewById (R.id.txt_login);
        mEmail = findViewById (R.id.email);
        mBio = findViewById (R.id.bio);
        mPassword = findViewById (R.id.password);
        mConfirmPassword = findViewById (R.id.confirmpassword);
        mUsername = findViewById (R.id.username);



        firebaseUtils = new FirebaseUtils (RegisterActivity.this);

        BackToLogIn();
        SetProfileImage();
        Register();



    }

    private void BackToLogIn(){
        txt_login.setOnClickListener (new View.OnClickListener () {
            public void onClick(View view) {
                startActivity (new Intent (RegisterActivity.this, LoginActivity.class));
            }
        });
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

                String str_email = mEmail.getText ().toString ();
                String str_username = mUsername.getText ().toString ();
                String str_bio = mBio.getText ().toString ();
                String str_password = mPassword.getText ().toString ();
                String str_confirm_password = mConfirmPassword.getText ().toString ();
                if (TextUtils.isEmpty (str_username) || TextUtils.isEmpty (str_confirm_password)
                        || TextUtils.isEmpty (str_email) || TextUtils.isEmpty (str_password)) {
                    Toast.makeText (RegisterActivity.this, "Found missing fields!", Toast.LENGTH_SHORT).show ();
                    mPassword.setText ("");
                    mConfirmPassword.setText ("");
                    pd.dismiss ();
                } else if (!str_password.equals (str_confirm_password)) {
                    Toast.makeText (RegisterActivity.this, "Passwords mismatch!", Toast.LENGTH_SHORT).show ();
                    mPassword.setText ("");
                    pd.dismiss ();
                    mConfirmPassword.setText ("");
                } else if (str_password.length () < 6) {
                    Toast.makeText (RegisterActivity.this, "Password must have 6 characters", Toast.LENGTH_SHORT).show ();
                    mPassword.setText ("");
                    pd.dismiss ();
                    mConfirmPassword.setText ("");
                } else {
                    firebaseUtils.signUp (str_email,str_password,str_username,str_bio,photoFile);
                    pd.dismiss();

                }
            }
        });
    }

    private void PerformRegister(final String username, final String bio, String email, String password) {

    }
}
