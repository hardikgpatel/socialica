package com.test.socketchat.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.test.socketchat.R;
import com.test.socketchat.activity.activity.MainActivity;
import com.test.socketchat.activity.adapter.PostAdapter;
import com.test.socketchat.activity.adapter.PostCommentAdapter;
import com.test.socketchat.activity.adapter.PostLikeAdapter;
import com.test.socketchat.activity.model.ModelPost;
import com.test.socketchat.activity.response.ResponsePost;
import com.test.socketchat.activity.response.ResponsePostComment;
import com.test.socketchat.activity.response.ResponsePostLikes;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedsFragment extends Fragment implements PostAdapter.PostClickListner {


    public NewsFeedsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rvPost;
    private PostAdapter adapter;
    private List<ModelPost> posts = new ArrayList<>();
    private SwipeRefreshLayout srlPosts;
    private static final String TAG = NewsFeedsFragment.class.getSimpleName();
    private FloatingActionButton fabAddPost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feeds, container, false);

        rvPost = view.findViewById(R.id.rv_user_post);
        srlPosts = view.findViewById(R.id.srl_posts);
        fabAddPost=view.findViewById(R.id.fab_add_post);
        adapter = new PostAdapter(posts, getContext(), this);
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPost.setAdapter(adapter);

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
                //startActivity(new Intent(getContext(),NewsFeedsFragment.class));
            }
        });
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
        Call<ResponsePostLikes> callUserLikes = SocketChatApp.getApiService().getPostLikes(posts.get(position).getPostId().toString());
        Log.e(TAG, "onLike: URL"+callUserLikes.request().url() );
         callUserLikes.enqueue(new Callback<ResponsePostLikes>() {
            @Override
            public void onResponse(Call<ResponsePostLikes> call, Response<ResponsePostLikes> response) {
                if (response.isSuccessful()) {
                    View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_post_userlike, null, false);
                    TextView tvTitle=dialogeView.findViewById(R.id.tv_option_title);
                    tvTitle.setText("Likes");
                    RecyclerView rvPostUserLike = dialogeView.findViewById(R.id.rv_post_userlike);
                    PostLikeAdapter adapter=new PostLikeAdapter(response.body().getData(),getContext());
                    rvPostUserLike.setAdapter(adapter);
                    rvPostUserLike.setLayoutManager(new LinearLayoutManager(getContext()));
                    BottomSheetDialog dialog=new BottomSheetDialog(getContext());
                    dialog.setContentView(dialogeView);
                    dialog.show();
                }else{
                    Log.e(TAG, "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<ResponsePostLikes> call, Throwable t) {
                Toast.makeText(getContext(), "something wrong : "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onComment(final int position) {
        Call<ResponsePostComment> callPostComment = SocketChatApp.getApiService().getPostComments(posts.get(position).getPostId().toString());
        Log.e(TAG, "onLike: URL"+callPostComment.request().url() );
        callPostComment.enqueue(new Callback<ResponsePostComment>() {
            @Override
            public void onResponse(Call<ResponsePostComment> call, Response<ResponsePostComment> response) {
                if (response.isSuccessful()) {
                    View dialogeView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_post_userlike, null, false);
                    RecyclerView rvPostUserLike = dialogeView.findViewById(R.id.rv_post_userlike);
                    PostCommentAdapter adapter=new PostCommentAdapter(response.body().getComment(),getContext());
                    TextView tvTitle=dialogeView.findViewById(R.id.tv_option_title);
                    tvTitle.setText("Comments");
                    rvPostUserLike.setAdapter(adapter);
                    rvPostUserLike.setLayoutManager(new LinearLayoutManager(getContext()));
                    BottomSheetDialog dialog=new BottomSheetDialog(getContext());
                    dialog.setContentView(dialogeView);
                    dialog.show();
                }else{
                    Log.e(TAG, "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<ResponsePostComment> call, Throwable t) {
                posts.get(position).setLikes(posts.get(position).getLikes()-1);
            }
        });
    }

    @Override
    public void onPost(int position) {

    }
}
