package com.test.socketchat.activity.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.activity.PostUpdateActivity;
import com.test.socketchat.activity.activity.UpdateProfileActivity;
import com.test.socketchat.activity.adapter.PostAdapter;
import com.test.socketchat.activity.adapter.PostCommentAdapter;
import com.test.socketchat.activity.adapter.PostLikeAdapter;
import com.test.socketchat.activity.db.entity.PostEntity;
import com.test.socketchat.activity.model.ModelPost;
import com.test.socketchat.activity.model.ModelPostComment;
import com.test.socketchat.activity.response.ResponseAddPostComment;
import com.test.socketchat.activity.response.ResponseDeletePost;
import com.test.socketchat.activity.response.ResponsePost;
import com.test.socketchat.activity.response.ResponsePostComment;
import com.test.socketchat.activity.response.ResponsePostLikes;
import com.test.socketchat.activity.utility.CircleTransform;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedsFragment extends Fragment implements PostAdapter.PostClickListner, PostLikeAdapter.LikeCLickListner, PostCommentAdapter.CommentClickListner {


    public NewsFeedsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvPost, rvPostUserLike;
    private PostAdapter adapter;
    private PostCommentAdapter adapterComment;
    private List<ModelPost> posts = new ArrayList<>();
    private List<ModelPostComment> comments = new ArrayList<>();
    private SwipeRefreshLayout srlPosts;
    private static final String TAG = NewsFeedsFragment.class.getSimpleName();
    private FloatingActionButton fabAddPost;
    private int selectedPost;
    private List<PostEntity> favPosts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feeds, container, false);

        final Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        rvPost = view.findViewById(R.id.rv_user_post);
        srlPosts = view.findViewById(R.id.srl_posts);
        fabAddPost = view.findViewById(R.id.fab_add_post);
        new getFavoriteList().execute();
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterComment = new PostCommentAdapter(comments, getContext(), NewsFeedsFragment.this);

        try {
            srlPosts.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    srlPosts.setRefreshing(true);
                    onGetPost();
                }
            });

            srlPosts.post(new Runnable() {
                @Override
                public void run() {
                    srlPosts.setRefreshing(true);
                    onGetPost();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        fabAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PostUpdateActivity.class));
            }
        });

        // hide floating button on scroll of post list
        rvPost.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabAddPost.show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabAddPost.isShown()) {
                    fabAddPost.hide();
                }
            }
        });
        rvPost.setLongClickable(true);
        return view;
    }

    private void onGetPost() {
        Call<ResponsePost> callGetPost = SocketChatApp.getApiService().getPost(SocketChatApp.getSession().getUserID());
        Log.e(TAG, "onGetPost: URL : " + callGetPost.request().url());
        callGetPost.enqueue(new Callback<ResponsePost>() {
            @Override
            public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                if (srlPosts.isRefreshing()) {
                    srlPosts.setRefreshing(false);
                }
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        posts.clear();
                        posts.addAll(response.body().getMessage());
                        adapter.notifyDataSetChanged();
                        Log.e(TAG, "onResponse: post get" + posts.size());
                    }
                } else {
                    Toast.makeText(getContext(), "something wrong : " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePost> call, Throwable t) {
                if (srlPosts.isRefreshing()) {
                    srlPosts.setRefreshing(false);
                }
                Log.e(TAG, "onFailure: " + t);
                Toast.makeText(getContext(), "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onLike(int position) {
        selectedPost = position;
        Call<ResponsePostLikes> callUserLikes = SocketChatApp.getApiService().getPostLikes(posts.get(position).getPostId().toString());
        Log.e(TAG, "onLike: URL" + callUserLikes.request().url());
        callUserLikes.enqueue(new Callback<ResponsePostLikes>() {
            @Override
            public void onResponse(Call<ResponsePostLikes> call, Response<ResponsePostLikes> response) {
                if (response.isSuccessful()) {
                    View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_post_userlike, null, false);
                    TextView tvTitle = dialogeView.findViewById(R.id.tv_option_title);
                    dialogeView.findViewById(R.id.ll_post_comment).setVisibility(View.GONE);
                    tvTitle.setText("Likes");
                    RecyclerView rvPostUserLike = dialogeView.findViewById(R.id.rv_post_userlike);
                    PostLikeAdapter adapter = new PostLikeAdapter(response.body().getData(), getContext(), NewsFeedsFragment.this);
                    rvPostUserLike.setAdapter(adapter);
                    rvPostUserLike.setLayoutManager(new LinearLayoutManager(getContext()));
                    BottomSheetDialog dialog = new BottomSheetDialog(getContext());
                    dialog.setContentView(dialogeView);
                    dialog.show();
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostLikes> call, Throwable t) {
                Toast.makeText(getContext(), "something wrong : " + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onComment(final int position) {
        selectedPost = position;
        Call<ResponsePostComment> callPostComment = SocketChatApp.getApiService().getPostComments(posts.get(position).getPostId().toString());
        Log.e(TAG, "onLike: URL" + callPostComment.request().url());
        final View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_post_userlike, null, false);
        BottomSheetDialog dialog = new BottomSheetDialog(getContext());
        dialog.setContentView(dialogeView);
        callPostComment.enqueue(new Callback<ResponsePostComment>() {
            @Override
            public void onResponse(Call<ResponsePostComment> call, Response<ResponsePostComment> response) {
                if (response.isSuccessful()) {
                    rvPostUserLike = dialogeView.findViewById(R.id.rv_post_userlike);
                    comments.clear();
                    comments.addAll(response.body().getComment());
                    adapterComment.notifyDataSetChanged();
                    TextView tvTitle = dialogeView.findViewById(R.id.tv_option_title);
                    tvTitle.setText("Comments");
                    rvPostUserLike.setAdapter(adapterComment);
                    rvPostUserLike.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<ResponsePostComment> call, Throwable t) {
                posts.get(position).setLikes(posts.get(position).getLikes() - 1);
            }
        });

        final EditText edtComment = dialogeView.findViewById(R.id.edt_comment);
        final ImageView ivSend = dialogeView.findViewById(R.id.iv_comment_send);
        ImageView ivCommentProfile = dialogeView.findViewById(R.id.iv_comment_profile);

        String urlUserImgae = getContext().getResources().getString(R.string.host) + "profile/" + SocketChatApp.getSession().getProfileImage();
        Picasso.get().load(urlUserImgae).transform(new CircleTransform()).error(R.drawable.profile).into(ivCommentProfile);

        edtComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(edtComment.getText().toString().trim())) {
                    ivSend.setVisibility(View.VISIBLE);
                } else {
                    ivSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendComment(edtComment.getText().toString().trim(), posts.get(position).getPostId());
                edtComment.setText("");
            }
        });
        dialog.show();
        edtComment.requestFocus();
    }

    @Override
    public void onUser(int position) {
        onShowUserDeatils(position);
    }

    @Override
    public void onAddFavorite(int position) {
        new addFavorite().execute(position);
    }

    @Override
    public void onDelete(final int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("LaNet Social app")
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        Call<ResponseDeletePost> callDeletePost = SocketChatApp.getApiService().deletePost(posts.get(position).getPostId().toString());
                        callDeletePost.enqueue(new Callback<ResponseDeletePost>() {
                            @Override
                            public void onResponse(Call<ResponseDeletePost> call, Response<ResponseDeletePost> response) {
                                if (response.isSuccessful()) {
                                    posts.remove(posts.get(position));
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), "Unable to delete", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDeletePost> call, Throwable t) {
                                Toast.makeText(getContext(), "Fail to delete", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "onFailure: " + t.toString());
                            }
                        });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }


    public void onSendComment(final String comment, int postId) {
        final ModelPostComment postComment = new ModelPostComment();
        postComment.setCommentText(comment);
        postComment.setPostId(postId);
        postComment.setDisplayName(SocketChatApp.getSession().getDisplayName());
        postComment.setUserId(SocketChatApp.getSession().getUserID());
        postComment.setUserProfilePhoto(SocketChatApp.getSession().getProfileImage());
        comments.add(postComment);
        adapterComment.notifyDataSetChanged();
        posts.get(selectedPost).setComents((posts.get(selectedPost).getComents() + 1));
        adapter.notifyDataSetChanged();
        Call<ResponseAddPostComment> callCommentPost = SocketChatApp.getApiService().postComment(comment, SocketChatApp.getSession().getUserID(), postId);
        Log.e(TAG, "onSendComment: " + callCommentPost.request().url());
        callCommentPost.enqueue(new Callback<ResponseAddPostComment>() {
            @Override
            public void onResponse(Call<ResponseAddPostComment> call, Response<ResponseAddPostComment> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body().getMessage());
                } else {
                    Log.e(TAG, "onResponse: error: " + response.message());
                    comments.remove(postComment);
                    adapter.notifyDataSetChanged();
                    posts.get(selectedPost).setComents((posts.get(selectedPost).getComents() - 1));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseAddPostComment> call, Throwable t) {
                Log.e(TAG, "onFailure: fails : " + t.toString());
                comments.remove(postComment);
                adapter.notifyDataSetChanged();
                posts.get(selectedPost).setComents((posts.get(selectedPost).getComents() - 1));
                adapter.notifyDataSetChanged();
            }
        });
        rvPostUserLike.scrollToPosition(comments.size());
    }

    // get favorite posts from database
    private void onShowUserDeatils(int user) {
        Intent intUser = new Intent(getContext(), UpdateProfileActivity.class);
        intUser.putExtra("userId", posts.get(user).getUserId());
        startActivity(intUser);
        Log.e(TAG, "onUser: UserId : " + posts.get(user).getUserId());
    }

    private class getFavoriteList extends android.os.AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            favPosts.clear();
            favPosts = SocketChatApp.getDb().postDao().getFavorite();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter = new PostAdapter(posts, favPosts, getContext(), NewsFeedsFragment.this);
            rvPost.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            super.onPostExecute(aVoid);
        }
    }

    private class addFavorite extends AsyncTask<Integer, Void, Void> {

        long row = 0;

        @Override
        protected Void doInBackground(Integer... position) {
            try {
                PostEntity newPost = new PostEntity();
                newPost.setPostId(posts.get(position[0]).getPostId());
                newPost.setPostText(posts.get(position[0]).getPostText());
                newPost.setPostUrl(posts.get(position[0]).getPostUrl());
                newPost.setCreatedAt(posts.get(position[0]).getCreatedAt());
                newPost.setRemove(Boolean.FALSE);
                newPost.setLikes(posts.get(position[0]).getLikes());
                newPost.setComments(posts.get(position[0]).getComents());
                newPost.setDisplayname(posts.get(position[0]).getDisplayName());
                newPost.setUserProfilePhoto(posts.get(position[0]).getUserProfilePhoto());
                row = SocketChatApp.getDb().postDao().addFavorite(newPost);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (row > 0) {
                Toast.makeText(getContext(), "Added Done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Added Fail", Toast.LENGTH_SHORT).show();
            }
            new getFavoriteList().execute();
        }
    }
}
