package com.android.jungledjumble.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jungledjumble.Auth.RegisterActivity;
import com.android.jungledjumble.Main.HomeActivity;
import com.android.jungledjumble.Main.ReturnActivity;
import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class OrangeAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private Integer[] mSizesList;
    private int level;
    private View view;


    private final static int ITEM_TYPE_NONE = 0;
    private final static int ITEM_TYPE_SMALL = 1;
    private final static int ITEM_TYPE_MEDIUM = 2;
    private final static int ITEM_TYPE_LARGE = 3;
    private final static String TAG = "OrangeAdaptor";


    public OrangeAdaptor(Context mContext, Integer[] mSizesList, int level){
        this.mContext = mContext;
        this.mSizesList = mSizesList;
        this.level = level;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_SMALL:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_small_item,parent,false);
                return new OrangeAdaptor.SmallOrangeViewHolder (view);
            case ITEM_TYPE_MEDIUM:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_medium_item,parent,false);
                return new OrangeAdaptor.MediumOrangeViewHolder (view);
            case ITEM_TYPE_LARGE:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_large_item,parent,false);
                return new OrangeAdaptor.LargeOrangeViewHolder (view);
            case ITEM_TYPE_NONE:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_none_item,parent,false);
                return new OrangeAdaptor.NoneOrangeViewHolder (view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int orangeViewItemType = getItemViewType(position);
//        Log.d(TAG,holder.getClass ().toString ());
//        RecyclerView.ViewHolder holder==holder;
//
//        switch(orangeViewItemType){
//            case ITEM_TYPE_SMALL:
//                ((SmallOrangeViewHolder)holder).setOrange ();
//            case ITEM_TYPE_MEDIUM:
//                ((MediumOrangeViewHolder) holder).setOrange ();
//            case ITEM_TYPE_LARGE:
//                ((LargeOrangeViewHolder) holder).setOrange ();
//            default:
//        }
        if (orangeViewItemType == ITEM_TYPE_SMALL){
            ((SmallOrangeViewHolder)holder).setOrange ();
        }else if(orangeViewItemType == ITEM_TYPE_MEDIUM) {
            ((MediumOrangeViewHolder) holder).setOrange ();
        }else if(orangeViewItemType == ITEM_TYPE_LARGE){
            ((LargeOrangeViewHolder)holder).setOrange ();
        }else{

        }
    }

    @Override
    public int getItemViewType(int position){
        return mSizesList[position];
    }

    @Override
    public int getItemCount() {
        return mSizesList.length;
    }

    public class SmallOrangeViewHolder extends RecyclerView.ViewHolder {
        ImageView OrangeView;
        SmallOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.small_orange);

        }
        void setOrange(){
            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }

    }

    public class MediumOrangeViewHolder extends RecyclerView.ViewHolder{
        ImageView OrangeView;
        MediumOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.medium_orange);

        }
        void setOrange(){
            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }

    }

    public class LargeOrangeViewHolder extends RecyclerView.ViewHolder{
        ImageView OrangeView;
        LargeOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.large_orange);
        }
        void setOrange(){
            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }

    }

    public class NoneOrangeViewHolder extends RecyclerView.ViewHolder{
        ImageView OrangeView;
        NoneOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.none_orange);
        }
        void setOrange(){
//            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }


    }
}

