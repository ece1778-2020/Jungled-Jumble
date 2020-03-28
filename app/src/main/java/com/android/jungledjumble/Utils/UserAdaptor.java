package com.android.jungledjumble.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.jungledjumble.Models.User;
import com.android.jungledjumble.R;
import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Comment;

import java.util.List;


public class UserAdaptor extends RecyclerView.Adapter<UserAdaptor.UserViewHolder>{
    private Context mContext;
    private List<User> mUserList;
    private OnClickUserListener mOnClickUserListener;

    public interface OnClickUserListener{
        void OnClickUser(User user);
    }

    public UserAdaptor(Context mContext, List<User> mUser){
        this.mContext = mContext;
        this.mUserList= mUser;
        this.mOnClickUserListener = (OnClickUserListener) mContext;
    }

    @NonNull
    @Override
    public UserAdaptor.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate (R.layout.user_item,parent,false);
        return new UserAdaptor.UserViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUser (mUserList.get(position));
    }


    @Override
    public int getItemCount() {
        return mUserList.size ();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        //        public ImageView profile_image;
        public TextView usernameView ,ageView;
        public ImageView profileView;
        public UserViewHolder(@NonNull View itemView) {
            super (itemView);

//            profile_image = itemView.findViewById (R.id.profile_image);
            usernameView = itemView.findViewById (R.id.username);
            profileView = itemView.findViewById (R.id.profile_image);

            usernameView.setOnClickListener (this);
            profileView.setOnClickListener (this);
//            ageView.setOnClickListener (this);
        }

        void setUser(User user){
            usernameView.setText(user.getUsername ());
//            ageView.setText (user.getAge ());
            Glide.with(mContext).load(user.getProfile_image ()).into(profileView);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition ();
            mOnClickUserListener.OnClickUser (mUserList.get(position));
        }
    }
}
