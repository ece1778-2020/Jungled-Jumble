package com.android.jungledjumble.Utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class OrangeAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private int[] mSizesList;
    private View view;
    private final static int ITEM_TYPE_SMALL = 0;
    private final static int ITEM_TYPE_MEDIUM = 1;
    private final static int ITEM_TYPE_LARGE = 2;
    private final static String TAG = "OrangeAdaptor";


    public OrangeAdaptor(Context mContext, int[] mSizesList){
        this.mContext = mContext;
        this.mSizesList = mSizesList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_small_item,parent,false);
                return new OrangeAdaptor.SmallOrangeViewHolder (view);
            case 2:
                view = LayoutInflater.from(mContext).inflate (R.layout.orange_large_item,parent,false);
                return new OrangeAdaptor.LargeOrangeViewHolder (view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int orangeViewItemType = getItemViewType(position);
        Log.d(TAG,holder.getClass ().toString ());
//        RecyclerView.ViewHolder holder==holder;
        if (orangeViewItemType == ITEM_TYPE_SMALL){
            ((SmallOrangeViewHolder)holder).setOrange ();
        }else if(orangeViewItemType == ITEM_TYPE_LARGE){
            Log.d(TAG,holder.getClass ().toString ());
            ((LargeOrangeViewHolder)holder).setOrange ();
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

    public class SmallOrangeViewHolder extends RecyclerView.ViewHolder{
        //        public ImageView profile_image;
        ImageView OrangeView;
        SmallOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.small_orange);
        }

        void setOrange(){
            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }
    }

    public class LargeOrangeViewHolder extends RecyclerView.ViewHolder{
        //        public ImageView profile_image;
        ImageView OrangeView;
        LargeOrangeViewHolder(@NonNull View itemView) {
            super (itemView);
            OrangeView = itemView.findViewById (R.id.large_orange);
        }

        void setOrange(){
            Glide.with(mContext).load(R.drawable.fruit).into(OrangeView);
        }
    }

}

