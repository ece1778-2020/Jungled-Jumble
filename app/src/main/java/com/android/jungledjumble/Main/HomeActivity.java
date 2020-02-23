package com.android.jungledjumble.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.android.jungledjumble.R;
import com.android.jungledjumble.Utils.OrangeAdaptor;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_home);

        Intent intent = getIntent ();
        String username = intent.getStringExtra ("username");
        Toast.makeText (this, "Welcome, "+username+"!", Toast.LENGTH_SHORT).show ();


        //Display the oranges
        final RecyclerView orangeView = findViewById (R.id.oranges);
        orangeView.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        int[] mSizesList = {0,2,0,2,0};
        orangeView.setAdapter (new OrangeAdaptor (HomeActivity.this,mSizesList));


        final RecyclerView orangeViewRight = findViewById (R.id.oranges_right);
        orangeViewRight.setLayoutManager (
                new GridLayoutManager (this, 4)
        );

        int[] mSizesListRight = {2,0,2,0,2};
        orangeViewRight.setAdapter (new OrangeAdaptor (HomeActivity.this,mSizesListRight));
    }
}
