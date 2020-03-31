package com.android.jungledjumble.Auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Main.ReturnActivity;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.android.jungledjumble.Utils.Utils;
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
import java.util.Random;

public class UserListActivity extends AppCompatActivity implements UserAdaptor.OnClickUserListener{
    FirebaseFirestore db;
    DocumentReference docRef;
    private Button new_user;
    ImageView back;
    Boolean sound_on = true;
    final static String TAG = "UserListActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_user_list);

        final MediaPlayer click_sound = MediaPlayer.create(this, R.raw.blip_annabel);
        try{sound_on = getIntent().getExtras().getBoolean("sound_on",true);}
        catch (Exception e){}

        db = FirebaseFirestore.getInstance ();
        new_user = findViewById (R.id.new_user);
        back = findViewById (R.id.back);

        Utils utils = new Utils(this);
        utils.hideSystemUI ();
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
                                        document.get("hand").toString (),
                                        document.get("glass").toString (),
                                        document.get("disorder").toString (),
                                        document.get("disability").toString (),
                                        document.get("timestamp").toString (),
                                        document.get("profile_image").toString (),
                                        document.get("choices").toString (),
                                        document.get("correct_choices").toString (),
                                        (int)document.get("points"));
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
        new_user.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent (UserListActivity.this, RegisterActivity.class);
                intent.putExtra ("sound_on",sound_on);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                if (sound_on){click_sound.start();}
                Intent intent = new Intent(UserListActivity.this, StartActivity.class);
                intent.putExtra ("sound_on",sound_on);
                startActivity(intent);
            }
        });
    }

    @Override
    public void OnClickUser(User user) {
        Intent intent = new Intent(UserListActivity.this, HomeActivity.class);
        intent.putExtra ("username",user.getUsername ());

//        List<Integer> indices = new ArrayList<Integer>();

//        for (int i=0;i<10;i++){
//            for (int j=0;j<4;j++){
//                indices.add(i);
//            }
//        }
//        Collections.shuffle (indices);
        List<Integer> range = new ArrayList<Integer>();
        range.add(115);
        range.add(130);
        intent.putIntegerArrayListExtra ("range",(ArrayList<Integer>) range);
        intent.putExtra ("sound_on",sound_on);


        startActivity (intent);
    }
}
