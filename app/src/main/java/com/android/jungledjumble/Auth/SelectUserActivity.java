package com.android.jungledjumble.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.android.jungledjumble.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectUserActivity extends AppCompatActivity implements UserAdaptor.OnClickUserListener{
    ImageView settings_cancel_button, existing_user,add_user;
    Button guest;

    FirebaseFirestore db;
    final static String TAG = "SelectUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_select_user);
        db = FirebaseFirestore.getInstance ();
        guest = findViewById (R.id.new_user);
        existing_user = findViewById (R.id.existing_user);
        add_user = findViewById (R.id.add_user);
        settings_cancel_button= findViewById (R.id.settings_cancel_button);
        //back = findViewById (R.id.back);
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        final RecyclerView recycleView = findViewById (R.id.user_list);
        recycleView.setLayoutManager (
                new LinearLayoutManager (this)
        );

        final List<User> UserItems = new ArrayList<> ();
        db.collection ("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot> () {
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
                                        document.get("profile_image").toString (),
                                        document.get("choices").toString (),
                                        document.get("correct_choices").toString ());
                                map.put(user.getTimestamp (),user);
                            }
                            ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
                            Collections.sort(sortedKeys,Collections.reverseOrder());
                            for (String x : sortedKeys){
                                UserItems.add (map.get(x));
                            }
                            Log.d(TAG, UserItems.toString ());
                            recycleView.setAdapter (new UserAdaptor (SelectUserActivity.this, UserItems));

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        guest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                Intent intent = new Intent(SelectUserActivity.this, HomeActivity.class);

                List<Integer> range = new ArrayList<Integer> ();
                range.add(100);
                range.add(130);
                intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);

                startActivity(intent);
            }
        });

        add_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (SelectUserActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        existing_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                startActivity(new Intent (SelectUserActivity.this, UserListActivity.class));
            }
        });

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //background_sound.pause();
                startActivity(new Intent (SelectUserActivity.this, StartActivity.class));
            }
        });

    }

    @Override
    public void OnClickUser(User user) {
        Intent intent = new Intent(SelectUserActivity.this, HomeActivity.class);
        intent.putExtra ("username",user.getUsername ());

        List<Integer> range = new ArrayList<Integer>();
        range.add(115);
        range.add(130);
        intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);



        startActivity (intent);
    }
}
