package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.model.ModelPostLike;
import com.test.socketchat.activity.utility.CircleTransform;

import java.util.List;

/**
 * Created by lcom151-two on 3/14/2018.
 */

public class PostLikeAdapter extends RecyclerView.Adapter<PostLikeAdapter.PostLikeHolder> {

    private List<ModelPostLike> users;
    private Context context;

    public PostLikeAdapter(List<ModelPostLike> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public PostLikeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostLikeHolder(LayoutInflater.from(context).inflate(R.layout.row_post_userlike,null,false));
    }

    @Override
    public void onBindViewHolder(PostLikeHolder holder, int position) {
        String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + users.get(position).getUserProfilePhoto();
        Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(holder.ivProfile);

        holder.tvUserName.setText(users.get(position).getDisplayName());
        int likeCount=users.get(position).getCountLikesLikeId();
        if(likeCount<=5){
            holder.tvLike.setText("Like");
            holder.tvLike.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_star,0);
        }else if(likeCount<=20){
            holder.tvLike.setText("Liked it");
            holder.tvLike.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_post_like_it,0);
        }else{
            holder.tvLike.setText("Loved it");
            holder.tvLike.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_post_love_it,0);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class PostLikeHolder extends ViewHolder {

        ImageView ivProfile;
        TextView tvUserName,tvLike;

        public PostLikeHolder(View itemView) {
            super(itemView);

            ivProfile=itemView.findViewById(R.id.iv_user_profile);
            tvUserName=itemView.findViewById(R.id.tv_user_display_name);
            tvLike=itemView.findViewById(R.id.tv_like);
        }
    }
}
