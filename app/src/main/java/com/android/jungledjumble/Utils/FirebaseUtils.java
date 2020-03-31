package com.android.jungledjumble.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.jungledjumble.Auth.SelectUserActivity;
import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Models.GlobalClass;
import com.android.jungledjumble.Models.User;

import com.android.jungledjumble.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private String downloadUri = "";

    public FirebaseUtils(Activity activity) {
        mAuth = FirebaseAuth.getInstance ();
        database = FirebaseFirestore.getInstance ();
        mStorage = FirebaseStorage.getInstance ();
        mStorageRef = mStorage.getReference ();
        mActivity = activity;
        if (mAuth.getCurrentUser () != null) {
            userID = mAuth.getCurrentUser ().getUid ();
        }
    }

    public void signUp(final String username, final String age, final String gender, final File photoFile,final String hand,final String glass,
                       final String disorder, final String disability, final int fruitType, final int points) {
        final List<Integer> range = new ArrayList<Integer> ();
        range.add(100);
        range.add(130);
        if (photoFile != null){
            Uri imageUri = Uri.fromFile (photoFile);
            final StorageReference fireReference = mStorageRef.child (userID + "/" + "displayPic.jpg");
            StorageTask uploadTask = fireReference.putFile (imageUri);

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
                        String ts = getTimestamp ();
                        uploadNewUserData (new User(username,age,gender,hand,glass,disorder,disability,ts,downloadUri,"","",0));
                        Intent intent = new Intent(mActivity, HomeActivity.class);
                        intent.putExtra ("username",username);
                        intent.putExtra ("fruit_type",fruitType);
                        GlobalClass globalClass = new GlobalClass ();
                        range.add(globalClass.getMeanRight ());
                        range.add(globalClass.getMeanRight ());
                        intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);

                        mActivity.startActivity(intent);
                    } else {
                        // Handle failures
                        Log.d(TAG,"Failed to upload a profile image");
                    }
                }
            });

        }else{
            String ts = getTimestamp ();
            String profile_image = "https://firebasestorage.googleapis.com/v0/b/jumgledjumble.appspot.com/o/user.png?alt=media&token=6ca268a0-8caf-4481-b4d3-21a3939a9b53";
            uploadNewUserData (new User(username,age,gender,hand,glass,disorder,disability,ts,profile_image,"","",0));
            Intent intent = new Intent(mActivity, HomeActivity.class);
            intent.putExtra ("username",username);
            intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
            intent.putExtra ("fruit_type",fruitType);
            mActivity.startActivity(intent);
        }
    }

    private String getTimestamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        return tsLong.toString();
    }

    private void uploadNewUserData(User user) {
        Map<String, Object> settings = new HashMap<> ();

        database.collection ("users")
                .add (user)
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

    public void updateResults(final String username, final String choices, final String correct_choices, final int points) {
        database.collection ("users")
                .whereEqualTo ("username",username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String choices_store = null,correct_choices_store = null;
                            int points_store = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                choices_store = document.get("choices").toString ();
                                correct_choices_store = document.get("correct_choices").toString ();
                                points_store = ((Long) document.get("points")).intValue ();
                                choices_store += " "+choices;
                                correct_choices_store += " "+correct_choices;
                                points_store += points;

                                database.collection ("users").document(document.getId ())
                                        .update ("choices",choices_store,
                                                "correct_choices",correct_choices_store,
                                                                      "points",points_store)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });
                                break;
                            }




                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


}