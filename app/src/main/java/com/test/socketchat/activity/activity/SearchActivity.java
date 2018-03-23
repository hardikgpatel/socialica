package com.test.socketchat.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.test.socketchat.R;
import com.test.socketchat.activity.adapter.UserAdapter;
import com.test.socketchat.activity.model.ModelUser;
import com.test.socketchat.activity.response.ResponseRegisterUser;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements UserAdapter.UserClickListner {

    private RecyclerView rvSearch;
    private ImageView ivClear, ivBack;
    private TextView tvNoUser;
    private ProgressBar pbSearch;
    private List<ModelUser> users = new ArrayList<>();
    private UserAdapter adapter;
    private EditText edtSearch;
    private Call<ResponseRegisterUser> callSearch;
    private static final String TAG = SearchActivity.class.getSimpleName();
    private boolean isSearched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ivBack = findViewById(R.id.iv_back_home);
        ivClear = findViewById(R.id.iv_search_clear);
        pbSearch = findViewById(R.id.pb_search);
        rvSearch = findViewById(R.id.rv_search);
        edtSearch = findViewById(R.id.edt_search);
        tvNoUser = findViewById(R.id.tv_no_user);
        rvSearch.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        adapter = new UserAdapter(users, SearchActivity.this, this);
        rvSearch.setAdapter(adapter);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "beforeTextChanged: "+s );
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onTextChanged: "+s+" start: "+start+" : bef : "+before+" : co : "+count );
                if (!TextUtils.isEmpty(edtSearch.getText().toString().trim())) {
                    if (isSearched) {
                        adapter.getFilter().filter(edtSearch.getText().toString().trim());
                    } else {
                        onStartSearch();
                    }
                    isSearched=true;
                } else {
                    isSearched=false;
                    pbSearch.setVisibility(View.GONE);
                    ivClear.setVisibility(View.GONE);
                    tvNoUser.setVisibility(View.GONE);
                    users.clear();
                    adapter.getFilter().filter("");
                    if (callSearch.isExecuted()) {
                        callSearch.cancel();
                    }
                    tvNoUser.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: "+s );
                if(adapter.getItemCount()==0){
                    tvNoUser.setVisibility(View.VISIBLE);
                }else{
                    tvNoUser.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onSelectUser(int position) {
        Intent intUser = new Intent(SearchActivity.this, UpdateProfileActivity.class);
        intUser.putExtra("userId", users.get(position).getUserId());
        startActivity(intUser);
        Log.e(TAG, "onUser: UserId : " + users.get(position).getUserId());
    }

    private void onStartSearch() {
        try {
            callSearch = SocketChatApp.getApiService().getSearch(edtSearch.getText().toString().trim());
            if (callSearch.isExecuted()) {
                callSearch.cancel();
            } else {
                pbSearch.setVisibility(View.VISIBLE);
                ivClear.setVisibility(View.GONE);
                Log.e(TAG, "onStartSearch: search URL : "+callSearch.request().url());
                callSearch.enqueue(new Callback<ResponseRegisterUser>() {
                    @Override
                    public void onResponse(Call<ResponseRegisterUser> call, Response<ResponseRegisterUser> response) {
                        pbSearch.setVisibility(View.GONE);
                        ivClear.setVisibility(View.VISIBLE);
                        if (response.isSuccessful() && response.code() == 200) {
                            users.clear();
                            users.addAll(response.body().getUser());
                            adapter.notifyDataSetChanged();
                            adapter.getFilter().filter(edtSearch.getText().toString().trim());
                        } else {
                            Log.e(TAG, "onResponse: " + response.errorBody());
                        }
                        if (users.size() > 0) {
                            tvNoUser.setVisibility(View.GONE);
                        } else {
                            tvNoUser.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRegisterUser> call, Throwable t) {
                        pbSearch.setVisibility(View.GONE);
                        ivClear.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onFailure: " + t.toString());
                        if (!callSearch.isCanceled()) {
                            if (users.size() > 0) {
                                tvNoUser.setVisibility(View.GONE);
                            } else {
                                tvNoUser.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
