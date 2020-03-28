package com.android.jungledjumble.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Models.GlobalClass;
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
    ImageView settings_cancel_button, existing_user,add_user, guest, existing_user_active,guest_active;
    ImageView left_arrow, right_arrow, orange, grape, banana, orange2,pear,mango;
    Integer fruit_selection;
    GlobalClass globalClass;

    Map<Integer, ImageView> fruit_map;

    FirebaseFirestore db;
    final static String TAG = "SelectUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_select_user);
        db = FirebaseFirestore.getInstance ();
        guest = findViewById (R.id.new_user);
        guest_active = findViewById (R.id.new_user_active);
        existing_user = findViewById (R.id.existing_user);
        existing_user_active = findViewById (R.id.existing_user_active);
        add_user = findViewById (R.id.add_user);

        settings_cancel_button= findViewById (R.id.settings_cancel_button);
        globalClass = new GlobalClass ();

        guest.setVisibility (View.GONE);
        existing_user_active.setVisibility (View.GONE);

        left_arrow = findViewById (R.id.left_arrow);
        right_arrow = findViewById (R.id.right_arrow);
        orange = findViewById (R.id.orange);
        banana = findViewById (R.id.banana);
        grape = findViewById (R.id.grape);
        orange2 = findViewById (R.id.orange2);
        pear = findViewById (R.id.pear);
        mango = findViewById (R.id.mango);
        fruit_map = new HashMap<Integer, ImageView> ();
        fruit_map.put(0,orange);
        fruit_map.put(1,banana);
        fruit_map.put(2,grape);
        fruit_map.put(3,orange2);
        fruit_map.put(4,pear);
        fruit_map.put(5,mango);
        fruit_selection = 0;
        final int num_fruits = 6;
        for (int i=1;i<num_fruits;i++){
            fruit_map.get(i).setVisibility (View.GONE);
        }
        left_arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fruit_map.get(fruit_selection).setVisibility (View.GONE);
                if(fruit_selection == 0){
                    fruit_selection = num_fruits;
                }
                fruit_selection --;
                fruit_map.get(fruit_selection).setVisibility (View.VISIBLE);
            }
        });

        right_arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                fruit_map.get(fruit_selection).setVisibility (View.GONE);
                fruit_selection ++;
                fruit_selection = fruit_selection % num_fruits;
                fruit_map.get(fruit_selection).setVisibility (View.VISIBLE);
            }
        });

        //back = findViewById (R.id.back);
        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        final RecyclerView recycleView = findViewById (R.id.user_list);
        recycleView.setVisibility (View.GONE);
        add_user.setVisibility (View.GONE);
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
                                        document.get("hand").toString (),
                                        document.get("glass").toString (),
                                        document.get("disorder").toString (),
                                        document.get("disability").toString (),
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

        guest_active.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                Intent intent = new Intent(SelectUserActivity.this, HomeActivity.class);

                List<Integer> range = new ArrayList<Integer> ();
                range.add(globalClass.getMeanLeft ());
                range.add(globalClass.getMeanRight ());
                intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                intent.putExtra ("fruit_type",(int)fruit_selection);
                startActivity(intent);
            }
        });

        guest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                guest_active.setVisibility (View.VISIBLE);
                guest.setVisibility (View.GONE);
                existing_user_active.setVisibility (View.GONE);
                recycleView.setVisibility (View.GONE);
                add_user.setVisibility (View.GONE);
            }
        });

        add_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent (SelectUserActivity.this, RegisterActivity.class);
                intent.putExtra ("fruit_type",fruit_selection);
                startActivity(intent);
            }
        });

        existing_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                click_sound.start();
                guest_active.setVisibility (View.GONE);
                guest.setVisibility (View.VISIBLE);
                existing_user_active.setVisibility (View.VISIBLE);
//                startActivity(new Intent (SelectUserActivity.this, UserListActivity.class));
                recycleView.setVisibility (View.VISIBLE);
                add_user.setVisibility (View.VISIBLE);
            }
        });

        existing_user_active.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                recycleView.setVisibility (View.GONE);
                add_user.setVisibility (View.GONE);
                guest_active.setVisibility (View.VISIBLE);
                existing_user_active.setVisibility (View.GONE);
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
        range.add(globalClass.getMeanLeft ());
        range.add(globalClass.getMeanRight ());
        intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
        intent.putExtra ("fruit_type",fruit_selection);



        startActivity (intent);
    }
}
