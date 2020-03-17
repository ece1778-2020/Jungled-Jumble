package com.android.jungledjumble.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jungledjumble.Auth.RegisterActivity;
import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Main.ReturnActivity;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.R;
import com.bumptech.glide.Glide;
import com.google.api.Distribution;

import java.util.List;

public class OrangeAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int[] mSizesList;
    private int level;
    private View view;

    private final static String TAG = "OrangeAdaptor";


    public OrangeAdaptor(Context mContext, int[] mSizesList, int level){
        this.mContext = mContext;
        this.mSizesList = mSizesList;
        this.level = level;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from (mContext).inflate (R.layout.orange_large_item, parent, false);

        return new OrangeAdaptor.LargeOrangeViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LargeOrangeViewHolder)holder).setOrange (mSizesList[position],this.mContext);
    }

    @Override
    public int getItemCount() {
        return mSizesList.length;
    }

    public class LargeOrangeViewHolder extends RecyclerView.ViewHolder{
        ImageView OrangeView;
        View view;
        LargeOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            view = itemView;

        }
        void setOrange(Integer size,Context context){

            RelativeLayout container = (RelativeLayout) view.findViewById (R.id.large_orange_whole);
            container.setLayoutParams (new ViewGroup.LayoutParams (
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            ImageView field = new ImageView (context);
//            double size_d = size*60;
//            Log.d("test",String.valueOf ((int)size_d));
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams ((int) size, (int) size);
            lay.gravity = Gravity.CENTER_HORIZONTAL;
            int id = 1;
            field.setId (id);
            field.setLayoutParams (lay);
            container.addView (field);

//            int id = 1;
            OrangeView = itemView.findViewById (id);

            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }

    }
}

