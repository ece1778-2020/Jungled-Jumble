package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.jungledjumble.Auth.RegisterActivity;
import com.android.jungledjumble.Models.UserResults;
import com.android.jungledjumble.R;
import com.android.jungledjumble.Setting.ProgressActivity;
import com.android.jungledjumble.Setting.SettingsAcitivity;
import com.android.jungledjumble.Utils.FirebaseUtils;
import com.android.jungledjumble.Utils.OrangeAdaptor;
import com.android.jungledjumble.Utils.UserAdaptor;
import com.android.jungledjumble.Utils.Utils;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Utils utils;
    ImageView left,right;
    RecyclerView orangeViewLeft, orangeViewRight;
    private int level,points,rewards;
    private String choices, correct_choices;
    private UserResults userResults;
    String username;

    private int larger_side;
    private static int TOTAL_LEVELS = 5;
    private final String TAG = "HomeActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        utils = new Utils (HomeActivity.this);
        left = findViewById (R.id.left);
        right = findViewById (R.id.right);

        Intent intent = getIntent ();




        //************************
        // Level: the current level of the game
        // Points: the number of times user makes the correct choice
        // Rewards: the cumulated rewards of current user
        //************************
        // Retrieve the results
        try{
            Log.d(TAG,intent.getStringExtra ("level"));
            level = Integer.parseInt (intent.getStringExtra ("level"));
            points = Integer.parseInt (intent.getStringExtra ("points"));
            rewards = Integer.parseInt (intent.getStringExtra ("rewards"));
            choices = intent.getStringExtra ("choices");
            correct_choices = intent.getStringExtra ("correct_choices");
            userResults = new UserResults (level,points,rewards,choices,correct_choices);
        }catch (Exception e){
            userResults = new UserResults (0,0,0,"","");
        }
        username= intent.getStringExtra ("username");
        if (userResults.getLevel ()==0){
            Toast.makeText (this, "Welcome, "+username+"!", Toast.LENGTH_SHORT).show ();
        }

        // Update the results
        userResults.updateLevel ();

        // Display the oranges
        orangeViewLeft = findViewById (R.id.oranges);
        orangeViewLeft.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        Integer[] mSizesListLeft = utils.getOrangeSizes (8,1,3,12);
        orangeViewLeft.setAdapter (new OrangeAdaptor (HomeActivity.this,mSizesListLeft,level));


        orangeViewRight = findViewById (R.id.oranges_right);
        orangeViewRight.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        Integer[] mSizesListRight = utils.getOrangeSizes (8,1,3,12);
        orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,mSizesListRight,level));


        // Determine which choice is correct
        int sumLeft = utils.getSum (mSizesListLeft);
        int sumRight = utils.getSum (mSizesListRight);
        if (sumLeft<sumRight){
            larger_side = 1;
        }else{
            larger_side = 0;
        }
        left.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                userResults.updateChoices ("0");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 0){
                    userResults.updatePoints ();
                }
                if (userResults.getLevel () < TOTAL_LEVELS){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);
                    startActivity(intent);
                }
            }
        });

        right.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
                userResults.updateChoices ("1");
                userResults.updateCorrect_choices (String.valueOf (larger_side));
                if (larger_side == 1){
                    userResults.updatePoints ();
                }
                if (userResults.getLevel () < TOTAL_LEVELS){
                    Intent intent = new Intent(getIntent ());
                    intent.putExtra ("level",String.valueOf (userResults.getLevel ()));
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("points",String.valueOf (userResults.getPoints ()));
                    intent.putExtra ("username",username);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(HomeActivity.this,ReturnActivity.class);
                    intent.putExtra ("rewards",String.valueOf (userResults.getRewards ()));  // MODIFY THIS LINE LATER!!!
                    intent.putExtra ("correct_choice_rate",utils.getCorrectRate (userResults,TOTAL_LEVELS));
                    intent.putExtra ("choices",userResults.getChoices ());
                    intent.putExtra ("correct_choices",userResults.getCorrect_choices ());
                    intent.putExtra ("username",username);
                    startActivity(intent);
                }
            }
        });
    }

}
