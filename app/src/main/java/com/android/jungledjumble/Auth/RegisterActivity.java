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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.FirebaseUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import com.android.jungledjumble.BuildConfig;
import com.bumptech.glide.Glide;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    // Layout
    EditText mUsername, mAge, mGender;
    Button registerButton;
    CircleImageView profile_image;
    TextView txt_login;
    Spinner gender_spinner,hand_spinner,glass_spinner,disorder_spinner,disability_spinner;

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

        gender_spinner = findViewById (R.id.gender_spinner);
        hand_spinner = findViewById (R.id.hand_spinner);
        glass_spinner = findViewById (R.id.glass_spinner);
        disorder_spinner = findViewById (R.id.disorder_spinner);
        disability_spinner = findViewById (R.id.disability_spinner);


        ArrayAdapter<CharSequence> adaptor = ArrayAdapter.createFromResource (this,R.array.gender,android.R.layout.simple_spinner_item);
        adaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        gender_spinner.setAdapter (adaptor);
        gender_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> handAdaptor = ArrayAdapter.createFromResource (this,R.array.hand,android.R.layout.simple_spinner_item);
        handAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        hand_spinner.setAdapter (handAdaptor);
        hand_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> needAdaptor = ArrayAdapter.createFromResource (this,R.array.need,android.R.layout.simple_spinner_item);
        needAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        glass_spinner.setAdapter (needAdaptor);
        glass_spinner.setOnItemSelectedListener (this);

        ArrayAdapter<CharSequence> haveAdaptor = ArrayAdapter.createFromResource (this,R.array.have,android.R.layout.simple_spinner_item);
        haveAdaptor .setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
        disorder_spinner.setAdapter (haveAdaptor );
        disorder_spinner.setOnItemSelectedListener (this);

        disability_spinner.setAdapter (haveAdaptor);
        disability_spinner.setOnItemSelectedListener (this);
//        List<String> genders = new ArrayList<>();
//        genders.add(0,"choose Gender");
//        genders.add("Male");
//        genders.add("Female");
//        ArrayAdapter<String> dataAdaptor;
//        dataAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item);
//
//        dataAdaptor.setDropDownViewResource (android.R.layout.simple_spinner_dropdown_item);
//
//        gender_spinner.setAdapter (dataAdaptor);
//
//        gender_spinner.setOnItemClickListener (new AdapterView.OnItemClickListener () {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (parent.getItemAtPosition(position).equals ("Choose Gender")){
//
//                }else{
//                    gender = parent.getItemAtPosition (position).toString ();
//                }
//            }
//        });

        firebaseUtils = new FirebaseUtils (RegisterActivity.this);

        SetProfileImage();
        Register();
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

    private void Register(){
        registerButton.setOnClickListener (new View.OnClickListener () {
            public void onClick(View view) {
                pd = new ProgressDialog (RegisterActivity.this);
                pd.setMessage ("Please wait..");
                pd.show ();

                String str_username = mUsername.getText ().toString ();
                String str_age = mAge.getText ().toString ();
//                String str_gender = mGender.getText ().toString ();
                String str_gender = gender_spinner.getSelectedItem ().toString ();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition (position).toString ();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
