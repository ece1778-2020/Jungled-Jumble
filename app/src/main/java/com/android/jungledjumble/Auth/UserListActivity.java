package com.android.jungledjumble.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Gallery;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity implements UserAdaptor.OnClickUserListener{
    FirebaseFirestore db;
    DocumentReference docRef;

    final static String TAG = "UserListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_list);
        db = FirebaseFirestore.getInstance ();


        //Retrieve the profile
        final RecyclerView recycleView = findViewById (R.id.user_list);
        recycleView.setLayoutManager (
                new LinearLayoutManager (this)
        );


        final List<User> UserItems = new ArrayList<> ();
        db.collection ("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Map<String,User> map = new HashMap<> ();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                User user = new User(document.get("username").toString (),
                                        document.get("age").toString (),
                                        document.get("gender").toString (),
                                        document.get("timestamp").toString (),
                                        document.get("profile_image").toString ());
                                map.put(user.getTimestamp (),user);
                            }
                            ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
                            Collections.sort(sortedKeys,Collections.reverseOrder());
                            for (String x : sortedKeys){
                                UserItems.add (map.get(x));
                            }
                            Log.d(TAG, UserItems.toString ());
                            recycleView.setAdapter (new UserAdaptor (UserListActivity.this, UserItems));

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    public void OnClickUser(User user) {
        Intent intent = new Intent(UserListActivity.this, HomeActivity.class);

//        CurrentUser currentUser = new CurrentUser ();
//        currentUser.user = user;
        intent.putExtra ("username",user.getUsername ());
        startActivity (intent);
    }
//    public class CurrentUser implements Serializable {
//        User user;
//    }
}
