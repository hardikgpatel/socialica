package com.test.socketchat.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.model.ModelPost;
import com.test.socketchat.activity.response.ResponsePostOption;
import com.test.socketchat.activity.utility.CircleTransform;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lcom151-two on 3/12/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    private List<ModelPost> posts;
    private Context context;
    private PostClickListner clickListner;
    private static final String TAG = PostAdapter.class.getSimpleName();

    public PostAdapter(List<ModelPost> posts, Context context, PostClickListner clickListner) {
        this.context = context;
        this.posts = posts;
        this.clickListner = clickListner;
    }

    @Override
    public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_post, null, false));
    }

    @Override
    public void onBindViewHolder(final PostHolder holder,final int position) {
        try {
            String urlPostImgae = context.getResources().getString(R.string.host) + "post/" + posts.get(position).getPostUrl();
            String urlUserImgae = context.getResources().getString(R.string.host) + "profile/" + posts.get(position).getUserProfilePhoto();

            // set post image if available
            if (!TextUtils.isEmpty(posts.get(position).getPostUrl().toString())) {
                Picasso.get().load(urlPostImgae).into(holder.ivPost);
                holder.ivPost.setVisibility(View.VISIBLE);
            } else {
                holder.ivPost.setVisibility(View.GONE);
            }

            // set post content if available
            if (!TextUtils.isEmpty(posts.get(position).getPostText())) {
                holder.tvPostContent.setText(posts.get(position).getPostText());
                holder.tvPostContent.setVisibility(View.VISIBLE);
            } else {
                holder.tvPostContent.setVisibility(View.GONE);
            }

            holder.tvUserName.setText(posts.get(position).getDisplayName());
            Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(holder.ivPostUserProfile);
            holder.tvTimeStamp.setText(SocketChatApp.getUtility().convetDate(posts.get(position).getCreatedAt()));
            Log.e(TAG, "onBindViewHolder: Time: "+SocketChatApp.getUtility().payTimeDefrance(SocketChatApp.getUtility().convetDate(posts.get(position).getCreatedAt())) );
            final int comment = posts.get(position).getComents();
            if (posts.get(position).getLikes() < 2) {
                holder.tvLike.setText(posts.get(position).getLikes() + " Like");
            } else {
                holder.tvLike.setText(posts.get(position).getLikes() + " Likes");
            }

            if (comment < 2) {
                holder.tvComment.setText(posts.get(position).getComents() + " Comment");
            } else {
                holder.tvComment.setText(posts.get(position).getComents() + " Comments");
            }

            holder.ivPostPrivacy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (posts.get(position).getPrivacy() == 0) {
                        Toast.makeText(context, "Private", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Public", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            holder.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Call<ResponsePostOption> callLikePost = SocketChatApp.getApiService().likePost(SocketChatApp.getSession().getUserID(), posts.get(position).getPostId().toString());
                    callLikePost.enqueue(new Callback<ResponsePostOption>() {
                        @Override
                        public void onResponse(Call<ResponsePostOption> call, Response<ResponsePostOption> response) {
                            if (response.isSuccessful()) {
                                posts.get(position).setLikes(posts.get(position).getLikes()+1);
                                holder.tvLike.setText(posts.get(position).getLikes() + " likes");
                            } else {
                                Toast.makeText(context, "enable to like", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponsePostOption> call, Throwable t) {
                            Log.e(TAG, "onFailure: like post: " + t.toString());
                        }
                    });
                }
            });

            holder.tvComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListner.onComment(position);
                }
            });

            holder.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListner.onLike(position);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        ImageView ivPostUserProfile, ivPost, ivPostPrivacy, ivLike;
        TextView tvTimeStamp, tvLike, tvComment, tvPostContent, tvUserName;

        public PostHolder(View itemView) {
            super(itemView);
            ivPostUserProfile = itemView.findViewById(R.id.iv_post_profile);
            ivPost = itemView.findViewById(R.id.iv_post_image);
            tvTimeStamp = itemView.findViewById(R.id.tv_post_time_stamp);
            tvComment = itemView.findViewById(R.id.tv_post_comment);
            tvLike = itemView.findViewById(R.id.tv_post_like);
            ivLike = itemView.findViewById(R.id.iv_post_like);
            tvPostContent = itemView.findViewById(R.id.tv_post_content);
            tvUserName = itemView.findViewById(R.id.tv_post_name);
            ivPostPrivacy = itemView.findViewById(R.id.iv_post_privacy);
        }
    }

    public interface PostClickListner {

        void onLike(int position);

        void onComment(int position);

        void onPost(int position);
    }
}
