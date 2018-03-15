package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.model.ModelPostComment;
import com.test.socketchat.activity.utility.CircleTransform;

import java.util.List;

/**
 * Created by lcom151-two on 3/14/2018.
 */

public class PostCommentAdapter extends RecyclerView.Adapter<PostCommentAdapter.CommentHolder> {

    private List<ModelPostComment> comments;
    private Context context;

    public PostCommentAdapter(List<ModelPostComment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.row_post_comment,null,false));
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + comments.get(position).getUserProfilePhoto();
        Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(holder.ivProfile);
        holder.tvName.setText(comments.get(position).getDisplayName());
        holder.tvComment.setText(comments.get(position).getCommentText());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        ImageView ivProfile;
        TextView tvName,tvComment;

        public CommentHolder(View itemView) {
            super(itemView);

            ivProfile=itemView.findViewById(R.id.iv_user_profile);
            tvName=itemView.findViewById(R.id.tv_user_display_name);
            tvComment=itemView.findViewById(R.id.tv_comment);
        }
    }
}
