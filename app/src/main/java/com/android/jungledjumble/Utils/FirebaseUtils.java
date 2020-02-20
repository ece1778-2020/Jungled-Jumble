package com.android.jungledjumble.Utils;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseUtils {
    private static final String TAG = "FirebaseMethods";
    private Activity mActivity;
    private String userID;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;
    private long mediaCount = 0;

    public FirebaseUtils(Activity activity) {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        myRef = database.getReference();
        mStorageRef = mStorage.getReference();
        mActivity = activity;
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }

    
}
