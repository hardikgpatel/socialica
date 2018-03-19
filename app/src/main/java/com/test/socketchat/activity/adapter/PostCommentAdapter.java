package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    private CommentClickListner clickListner;

    public PostCommentAdapter(List<ModelPostComment> comments, Context context,CommentClickListner commentClickListner) {
        this.comments = comments;
        this.context = context;
        this.clickListner=commentClickListner;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(context).inflate(R.layout.row_post_comment,null,false));
    }

    @Override
    public void onBindViewHolder(final CommentHolder holder, final int position) {
        String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + comments.get(position).getUserProfilePhoto();
        Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(holder.ivProfile);
        holder.tvName.setText(comments.get(position).getDisplayName());
        holder.tvComment.setText(comments.get(position).getCommentText());
        holder.tvName.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onUser(position);
            }
        });
        holder.ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListner.onUser(position);
            }
        });
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

    public interface CommentClickListner {
        void onUser(int position);
    }
}
