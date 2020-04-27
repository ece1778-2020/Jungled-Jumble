package com.android.jungledjumble.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jungledjumble.R;
import com.bumptech.glide.Glide;

public class OrangeAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int[] mSizesList;
//    private int level;
    private View view;
    private int fruitType;
    private final static String TAG = "OrangeAdaptor";


    public OrangeAdaptor(Context mContext, int[] mSizesList, int fruitType){
        this.mContext = mContext;
        this.mSizesList = mSizesList;
//        this.level = level;
        this.fruitType = fruitType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from (mContext).inflate (R.layout.fruit_item, parent, false);

        return new OrangeAdaptor.LargeOrangeViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((LargeOrangeViewHolder)holder).setOrange (mSizesList[position],this.mContext,this.fruitType);
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
        void setOrange(Integer size,Context context, int fruitType){
            RelativeLayout container = (RelativeLayout) view.findViewById (R.id.fruit);

            container.setLayoutParams (new ViewGroup.LayoutParams (
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            ImageView field = new ImageView (context);

            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams ((int) size, (int) size);
            lay.gravity = Gravity.CENTER_HORIZONTAL;
            int id = 1;
            field.setId (id);
            field.setLayoutParams (lay);
            container.addView (field);

//            int id = 1;
            OrangeView = itemView.findViewById (id);
            if(fruitType == 0){
                Glide.with(mContext).load(R.drawable.orange).into(OrangeView);
            }else if (fruitType == 1){
                Glide.with(mContext).load(R.drawable.banana).into(OrangeView);
            }else if (fruitType == 2){
                Glide.with(mContext).load(R.drawable.grape).into(OrangeView);
            }else if (fruitType == 3){
                Glide.with(mContext).load(R.drawable.orange2).into(OrangeView);
            }else if (fruitType == 4){
                Glide.with(mContext).load(R.drawable.pear).into(OrangeView);
            }else if (fruitType == 5){
                Glide.with(mContext).load(R.drawable.mango).into(OrangeView);
            }else{
                Glide.with(mContext).load(R.drawable.grape).into(OrangeView);
            }
            
        }

    }
}

