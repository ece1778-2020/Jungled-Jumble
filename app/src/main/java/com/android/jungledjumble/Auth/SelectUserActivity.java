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
import android.widget.Toast;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Models.GlobalClass;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.android.jungledjumble.Utils.Utils;
import com.bumptech.glide.Glide;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class SelectUserActivity extends AppCompatActivity implements UserAdaptor.OnClickUserListener{
    ImageView settings_cancel_button, button_play, existing_user,add_user, guest, existing_user_active,guest_active, ready_screen;
    ImageView orange, grape, banana, orange2,pear,mango, monkey, sloth, sloth_locked;
    ImageView left_arrow, right_arrow,left_arrow_char, right_arrow_char;
    CircleImageView me;
    String username, profile_image;
    ImageView char_lock, fruit_lock;
    ImageView block;
    Integer fruit_selection, char_selection, is_ready,fruit_selection_final;
    //    Integer state_orange, state_banana, state_grape, state_grapefruit, state_pear, state_mango;
    GlobalClass globalClass;
    ArrayList<Integer> fruit_lock_list, char_lock_list;
    private FirebaseFirestore database;
    Boolean sound_on = true;
    Boolean music_on = true;
    MediaPlayer background_sound;
    Map<Integer, ImageView> fruit_map, char_map;


    FirebaseFirestore db;
    final static String TAG = "SelectUserActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_select_user);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        background_sound = MediaPlayer.create(this, R.raw.mixed_demo);

        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        try{music_on = getIntent().getExtras().getBoolean("music_on",true);}
        catch (Exception e){}

        if (music_on){background_sound.start();}

        db = FirebaseFirestore.getInstance ();
        guest = findViewById (R.id.new_user);
        guest_active = findViewById (R.id.new_user_active);
        database = FirebaseFirestore.getInstance ();
        existing_user = findViewById (R.id.existing_user);
        button_play = findViewById (R.id.button_play);
        existing_user_active = findViewById (R.id.existing_user_active);
        add_user = findViewById (R.id.add_user);
        ready_screen = findViewById (R.id.ready_screen);
        me = findViewById (R.id.me);

        settings_cancel_button= findViewById (R.id.settings_cancel_button);
        globalClass = new GlobalClass ();



        left_arrow = findViewById (R.id.left_arrow);
        right_arrow = findViewById (R.id.right_arrow);
        left_arrow_char = findViewById (R.id.left_arrow_char);
        right_arrow_char = findViewById (R.id.right_arrow_char);

        char_lock = findViewById (R.id.lock);
        fruit_lock = findViewById (R.id.fruit_lock);

        char_lock.setVisibility (View.GONE);
        fruit_lock.setVisibility (View.GONE);

        block = findViewById (R.id.block);
        block.setVisibility (View.GONE);
        me.setVisibility (View.GONE);


        orange = findViewById (R.id.orange);
        banana = findViewById (R.id.banana);
        grape = findViewById (R.id.grape);
        orange2 = findViewById (R.id.orange2);
        pear = findViewById (R.id.pear);
        mango = findViewById (R.id.mango);

        //Initialization

        existing_user_active.setVisibility (View.GONE);
        add_user.setVisibility (View.GONE);
        char_lock_list = new ArrayList<> ();
        fruit_lock_list = new ArrayList<> ();

        //Get info from activity
        Intent intent = getIntent ();

        char_selection = intent.getIntExtra ("char_selection",0);
        fruit_selection = intent.getIntExtra ("fruit_selection",0);


        try{
            username = intent.getStringExtra("username");
            profile_image = intent.getStringExtra ("profile_image");
//            char_lock_list = intent.getIntegerArrayListExtra ("char_lock");
//            fruit_lock_list = intent.getIntegerArrayListExtra ("fruit_lock");
            Log.d("test,11",username+profile_image);
        }catch (Exception e){
        }

        // Build the lock list that specifies which fruit or characters are locked
        // It is determined by the points of user
        char_lock_list = new ArrayList<> ();
        fruit_lock_list = new ArrayList<> ();
        final int points = intent.getIntExtra ("points",0);
        if (points < 30){          //less than 30 -> all possible lock used
            char_lock_list.add(1);
            fruit_lock_list.add(4);
        }else if (points < 70){    //less than 70, only two are locked
            fruit_lock_list.add(5);
        }else{                     //more than 79 -> everything ublocked
        }


        // Put profile image into the existing user if some user is selected otherwise guest
        if (username != null){
            existing_user.setVisibility (View.GONE);
            me.setVisibility (View.VISIBLE);
            Glide.with(this).load(profile_image).into(me);
            guest_active.setVisibility (View.GONE);
            guest.setVisibility (View.VISIBLE);
        }else{
            guest.setVisibility (View.GONE);
        }

        // Fruit map
        fruit_map = new HashMap<Integer, ImageView> ();
        fruit_map.put(0,orange);
        fruit_map.put(1,banana);
        fruit_map.put(2,grape);
        fruit_map.put(3,orange2);
        fruit_map.put(4,pear);
        fruit_map.put(5,mango);

        final int num_fruits = 6;
        for (int i=0;i<num_fruits;i++){
            if (!(i==fruit_selection) ){
                fruit_map.get(i).setVisibility (View.GONE);
            }
        }
        left_arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                fruit_map.get(fruit_selection).setVisibility (View.GONE);
                if(fruit_selection == 0){
                    fruit_selection = num_fruits;
                }
                fruit_selection --;
                fruit_map.get(fruit_selection).setVisibility (View.VISIBLE);
                if (fruit_lock_list.contains (fruit_selection)){
                    fruit_lock.setVisibility (View.VISIBLE);
                }else{
                    fruit_lock.setVisibility (View.GONE);
                }

            }
        });

        right_arrow.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                fruit_map.get(fruit_selection).setVisibility (View.GONE);
                fruit_selection ++;
                fruit_selection = fruit_selection % num_fruits;
                fruit_map.get(fruit_selection).setVisibility (View.VISIBLE);
                if (fruit_lock_list.contains (fruit_selection)){
                    fruit_lock.setVisibility (View.VISIBLE);
                }else{
                    fruit_lock.setVisibility (View.GONE);
                }

            }
        });

        // built the map for character and index while considering the lock
        monkey = findViewById (R.id.monkey);
        sloth = findViewById (R.id.sloth);
        sloth.setVisibility (View.GONE);
        sloth_locked = findViewById (R.id.sloth_locked);
        char_map = new HashMap<Integer, ImageView> ();
        char_map.put(0,monkey);
        if (char_lock_list.contains (1)){
            char_map.put(1,sloth_locked);
            sloth.setVisibility (View.GONE);
        }else{
            char_map.put(1,sloth);
            sloth_locked.setVisibility (View.GONE);
        }

        // Only display the selected character
        final int num_char = 2;
        for (int i=0;i<num_char;i++){
            if (!(i==char_selection) ){
                char_map.get(i).setVisibility (View.GONE);
            }else{
                char_map.get(i).setVisibility (View.VISIBLE);
            }
        }

        left_arrow_char.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                char_map.get(char_selection).setVisibility (View.GONE);
                if(char_selection == 0){
                    char_selection = num_char;
                }
                char_selection --;
                char_map.get(char_selection).setVisibility (View.VISIBLE);
                if (char_lock_list.contains (char_selection)){
                    char_lock.setVisibility (View.VISIBLE);
                }else{
                    char_lock.setVisibility (View.GONE);
                }

            }
        });

        right_arrow_char.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                char_map.get(char_selection).setVisibility (View.GONE);
                char_selection ++;
                char_selection = char_selection % num_char;
                char_map.get(char_selection).setVisibility (View.VISIBLE);
                if (char_lock_list.contains (char_selection)){
                    char_lock.setVisibility (View.VISIBLE);
                }else{
                    char_lock.setVisibility (View.GONE);
                }
            }
        });

        //back = findViewById (R.id.back);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();

        final RecyclerView recycleView = findViewById (R.id.user_list);
        recycleView.setVisibility (View.GONE);

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
//                                Log.d(TAG, document.getId() + " => " + document.getData());

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
                                        document.get("correct_choices").toString (),
                                        ((Long)document.get("points")).intValue ());
                                map.put(user.getTimestamp (),user);
                            }
                            ArrayList<String> sortedKeys = new ArrayList<String>(map.keySet());
                            Collections.sort(sortedKeys,Collections.reverseOrder());
                            for (String x : sortedKeys){
                                UserItems.add (map.get(x));
                            }
//                            Log.d(TAG, UserItems.toString ());
                            recycleView.setAdapter (new UserAdaptor (SelectUserActivity.this, UserItems));

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


        guest.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                guest_active.setVisibility (View.VISIBLE);
                guest.setVisibility (View.GONE);
                existing_user_active.setVisibility (View.GONE);
                recycleView.setVisibility (View.GONE);
                add_user.setVisibility (View.GONE);
            }
        });

        me.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                guest.setVisibility (View.VISIBLE);
                recycleView.setVisibility (View.VISIBLE);
                add_user.setVisibility (View.VISIBLE);
                block.setVisibility (View.VISIBLE);
            }
        });

        add_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent (SelectUserActivity.this, RegisterActivity.class);
                intent.putExtra ("fruit_type",fruit_selection);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        existing_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                guest_active.setVisibility (View.GONE);
                guest.setVisibility (View.VISIBLE);
                existing_user_active.setVisibility (View.VISIBLE);
//              startActivity(new Intent (SelectUserActivity.this, UserListActivity.class));
                recycleView.setVisibility (View.VISIBLE);
                add_user.setVisibility (View.VISIBLE);
                block.setVisibility (View.VISIBLE);
            }
        });

        existing_user_active.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                recycleView.setVisibility (View.GONE);
                add_user.setVisibility (View.GONE);
                guest_active.setVisibility (View.VISIBLE);
                existing_user_active.setVisibility (View.GONE);
            }
        });

        block.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                recycleView.setVisibility (View.GONE);
                add_user.setVisibility (View.GONE);
                guest_active.setVisibility (View.VISIBLE);
                existing_user_active.setVisibility (View.GONE);
                block.setVisibility (View.GONE);
            }
        });


        button_play.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                if (char_lock_list.contains (char_selection) || fruit_lock_list.contains (fruit_selection)){
                    ready_screen.bringToFront ();
                    ready_screen.setVisibility (View.VISIBLE);
                }else{
                    Intent intent = new Intent(SelectUserActivity.this, HomeActivity.class);
                    intent.putExtra ("username",username);
                    intent.putExtra ("char_selection",char_selection);
                    List<Integer> range = new ArrayList<Integer> ();
                    range.add(globalClass.getMeanLeft ());
                    range.add(globalClass.getMeanRight ());
                    intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
                    intent.putExtra ("fruit_type",(int)fruit_selection);
                    intent.putExtra ("sound_on",sound_on);
                    intent.putExtra ("music_on",music_on);
                    background_sound.pause();
                    startActivity(intent);
                }
            }
        });

        settings_cancel_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //background_sound.pause();
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(SelectUserActivity.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                intent.putExtra ("music_on",music_on);
                background_sound.pause();
                startActivity(intent);
            }
        });

        ready_screen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //background_sound.pause();
                ready_screen.setVisibility (View.GONE);
//                Intent intent = new Intent(getIntent ());
//                intent.putExtra ("username",username);
//                intent.putExtra ("char_selection",char_selection);
//                intent.putExtra ("fruit_selection",fruit_selection);
//                intent.putExtra ("profile_image",profile_image);
//                intent.putExtra ("sound_on",sound_on);
//                intent.putExtra ("music_on",music_on);
//                intent.putExtra ("points",points);
//                startActivity (intent);
            }
        });

    }

    @Override
    public void OnClickUser(User user) {

        Intent intent = new Intent(getIntent ());
        intent.putExtra ("username",user.getUsername ());
        intent.putExtra ("char_selection",char_selection);
        intent.putExtra ("fruit_selection",fruit_selection);
        intent.putExtra ("profile_image",user.getProfile_image ());
        intent.putExtra ("sound_on",sound_on);
        intent.putExtra ("music_on",music_on);
        intent.putExtra ("points",user.getPoints ());


        background_sound.pause();
        guest_active.setVisibility (View.GONE);
//        char_lock_list = new ArrayList<> ();
//        String[] splited = user.getChar_lock ().split("\\s+");
//        for (int i=0;i<splited.length;i++){
//            String e =splited[i];
//            if (e == ""){
//                break;
//            }
//            char_lock_list.add(Integer.parseInt (e));
//        }
//
//
//        fruit_lock_list = new ArrayList<> ();
//
//        String[] fruit_splited = user.getFruit_lock ().split("\\s+");
//        if (fruit_splited.length > 0){
//            for (int i=0;i<splited.length;i++){
//                String e =fruit_splited[i];
//                if (e == ""){
//                    break;
//                }
//                fruit_lock_list.add(Integer.parseInt (e));
//            }
//        }
//
//
//        intent.putExtra ("char_lock",char_lock_list);
//        intent.putExtra ("fruit_lock",fruit_lock_list);
        startActivity (intent);
    }


}
