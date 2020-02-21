package com.android.jungledjumble.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Models.User;

import com.android.jungledjumble.Auth.RegisterActivity;
import com.android.jungledjumble.Models.UserAccountSettings;
import com.android.jungledjumble.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.io.File;

public class FirebaseUtils {
    private static final String TAG = "FirebaseMethods";
    private Activity mActivity;
    private String userID;
//    private User user;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseFirestore database;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;
    private long mediaCount = 0;
    private StorageTask uploadTask;
    private String downloadUri = "";

    public FirebaseUtils(Activity activity) {

        mAuth = FirebaseAuth.getInstance ();
        database = FirebaseFirestore.getInstance ();
        mStorage = FirebaseStorage.getInstance ();
//        myRef = database.getReference();
        mStorageRef = mStorage.getReference ();
        mActivity = activity;
        if (mAuth.getCurrentUser () != null) {
            userID = mAuth.getCurrentUser ().getUid ();
        }
    }

    public void signUp(final String userName, final String age, final String gender, final File photoFile) {
        if (photoFile != null){
            Uri imageUri = Uri.fromFile (photoFile);
            final StorageReference fireReference = mStorageRef.child (userID + "/" + "displayPic.jpg");
            uploadTask = fireReference.putFile (imageUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fireReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult().toString ();
                        addNewUserData (new User(userName,age,gender,downloadUri));
                        mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        }else{
            addNewUserData (new User(userName,age,gender,""));
            mActivity.startActivity(new Intent(mActivity, HomeActivity.class));
        }
    }
    private void addNewUserData(User user) {
        Map<String, Object> settings = new HashMap<> ();
        settings.put ("username", user.getUsername ());
        settings.put("age",user.getAge ());
        settings.put("gender",user.getGender ());
        settings.put ("profilePhoto", user.getProfile_image ());
        database.collection ("users")
                .add (settings)
                .addOnSuccessListener (new OnSuccessListener<DocumentReference> () {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText (mActivity, "User information added!",
                                Toast.LENGTH_SHORT).show ();
                    }
                })
                .addOnFailureListener (new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText (mActivity, "Failed to add user information",
                                Toast.LENGTH_SHORT).show ();
                    }
                });
    }
}