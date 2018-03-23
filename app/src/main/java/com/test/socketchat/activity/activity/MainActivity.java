package com.test.socketchat.activity.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.test.socketchat.R;
import com.test.socketchat.activity.adapter.PostAdapter;
import com.test.socketchat.activity.db.entity.PostEntity;
import com.test.socketchat.activity.fragment.NewsFeedsFragment;
import com.test.socketchat.activity.utility.CircleTransform;
import com.test.socketchat.activity.utility.SocketChatApp;

import java.util.List;


public class MainActivity extends AppCompatActivity {

   /* private Socket socket;{
        try{
            socket= IO.socket("http://192.168.200.51:3213");
        }catch (Exception e){e.printStackTrace();}
    }*/

    /*private LinearLayout llOptionMenu;
    private FloatingActionButton fabMenu;*/

    private TextView tvActionHome, tvActionChat, tvActionProfile, tvActionSetting, tvToolbarName;
    private ImageView ivActionHome, ivActionChat, ivActionProfile, ivActionSetting, ivToolbarSearch, ivToolbarNotification,ivProfile;
    private FrameLayout fl;
    private FragmentManager fragmentManager;
    private boolean isDashboard=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*socket.connect();
        socket.emit("message","this is mesasge");*/
        //socket.on("message",onMessageRecive)

        /*llOptionMenu = findViewById(R.id.ll_floating_menu);
        fabMenu = findViewById(R.id.fab_menu);*/

        ivActionHome = findViewById(R.id.iv_action_home);
        ivActionChat = findViewById(R.id.iv_action_chat);
        ivActionProfile = findViewById(R.id.iv_action_profile);
        ivActionSetting = findViewById(R.id.iv_action_setting);

        tvActionHome = findViewById(R.id.tv_action_home);
        tvActionChat = findViewById(R.id.tv_action_chat);
        tvActionProfile = findViewById(R.id.tv_action_profile);
        tvActionSetting = findViewById(R.id.tv_action_setting);

        tvToolbarName = findViewById(R.id.tv_toolbar_name);
        ivToolbarSearch = findViewById(R.id.iv_toolbar_search);
        ivToolbarNotification = findViewById(R.id.iv_toolbar_notification);
        ivProfile =findViewById(R.id.iv_toolbar_profile);

        fl = findViewById(R.id.fl_content);
        tvActionHome.setTextSize(13);
        tvActionChat.setTextSize(12);
        tvActionProfile.setTextSize(12);
        tvActionSetting.setTextSize(12);

        tvActionHome.setTextColor(Color.WHITE);
        tvActionChat.setTextColor(Color.GRAY);
        tvActionProfile.setTextColor(Color.GRAY);
        tvActionSetting.setTextColor(Color.GRAY);

        ivActionHome.setColorFilter(Color.WHITE);
        ivActionChat.setColorFilter(Color.GRAY);
        ivActionProfile.setColorFilter(Color.GRAY);
        ivActionSetting.setColorFilter(Color.GRAY);
        this.loadFragment(new NewsFeedsFragment());

        ivToolbarSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

        try{
            new getFavoriteList().execute();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            tvToolbarName.setText(SocketChatApp.getSession().getDisplayName());
            Picasso.get().load(getResources().getString(R.string.host) + "profile/" + SocketChatApp.getSession().getProfileImage()).transform(new CircleTransform()).error(R.drawable.profile).into(ivProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** load fragment */
    public void loadFragment(Fragment fragment){
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl_content,fragment).addToBackStack("back").commit();
    }

    /** handle backpress */
    protected void exitByBackKey() {

        new AlertDialog.Builder(this)
                .setTitle("LaNet Social app")
                .setMessage("Do you want to exit ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        if(isDashboard){
            this.exitByBackKey();
        }else{
            loadFragment(new NewsFeedsFragment());
            tvActionHome.setTextSize(13);
            tvActionChat.setTextSize(12);
            tvActionProfile.setTextSize(12);
            tvActionSetting.setTextSize(12);

            tvActionHome.setTextColor(Color.WHITE);
            tvActionChat.setTextColor(Color.GRAY);
            tvActionProfile.setTextColor(Color.GRAY);
            tvActionSetting.setTextColor(Color.GRAY);

            ivActionHome.setColorFilter(Color.WHITE);
            ivActionChat.setColorFilter(Color.GRAY);
            ivActionProfile.setColorFilter(Color.GRAY);
            ivActionSetting.setColorFilter(Color.GRAY);
            isDashboard=true;
        }
    }

    public void onSelectMenu(View view) {
        /*if (llOptionMenu.getVisibility()== View.VISIBLE) {
            RotateAnimation rotate=new RotateAnimation(90, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);;
            rotate.setDuration(200);
            rotate.setInterpolator(new LinearInterpolator());
            fabMenu.setImageResource(R.drawable.ic_menu_open);
            fabMenu.startAnimation(rotate);

            ScaleAnimation animation = new ScaleAnimation(0,0,100,-100,0.5f,0.5f);
            animation.setDuration(200);
            llOptionMenu.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llOptionMenu.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }else{
            RotateAnimation rotate=new RotateAnimation(270, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);;
            rotate.setDuration(200);
            rotate.setInterpolator(new LinearInterpolator());
            fabMenu.setImageResource(R.drawable.ic_menu_close);
            fabMenu.startAnimation(rotate);

            ScaleAnimation animation = new ScaleAnimation(0,0,-100,100,0.5f,0.5f);
            animation.setDuration(200);
            llOptionMenu.setAnimation(animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    llOptionMenu.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }*/

        switch (view.getId()) {
            case R.id.action_home:
                tvActionHome.setTextSize(13);
                tvActionChat.setTextSize(12);
                tvActionProfile.setTextSize(12);
                tvActionSetting.setTextSize(12);

                tvActionHome.setTextColor(Color.WHITE);
                tvActionChat.setTextColor(Color.GRAY);
                tvActionProfile.setTextColor(Color.GRAY);
                tvActionSetting.setTextColor(Color.GRAY);

                ivActionHome.setColorFilter(Color.WHITE);
                ivActionChat.setColorFilter(Color.GRAY);
                ivActionProfile.setColorFilter(Color.GRAY);
                ivActionSetting.setColorFilter(Color.GRAY);
                this.loadFragment(new NewsFeedsFragment());
                isDashboard=true;
                break;
            case R.id.action_search:
                tvActionHome.setTextSize(12);
                tvActionChat.setTextSize(12);
                tvActionProfile.setTextSize(12);
                tvActionSetting.setTextSize(12);

                tvActionHome.setTextColor(Color.GRAY);
                tvActionChat.setTextColor(Color.GRAY);
                tvActionProfile.setTextColor(Color.GRAY);
                tvActionSetting.setTextColor(Color.GRAY);

                ivActionHome.setColorFilter(Color.GRAY);
                ivActionChat.setColorFilter(Color.GRAY);
                ivActionProfile.setColorFilter(Color.GRAY);
                ivActionSetting.setColorFilter(Color.GRAY);
                break;
            case R.id.action_chat:
                tvActionHome.setTextSize(12);
                tvActionChat.setTextSize(13);
                tvActionProfile.setTextSize(12);
                tvActionSetting.setTextSize(12);

                tvActionHome.setTextColor(Color.GRAY);
                tvActionChat.setTextColor(Color.WHITE);
                tvActionProfile.setTextColor(Color.GRAY);
                tvActionSetting.setTextColor(Color.GRAY);

                ivActionHome.setColorFilter(Color.GRAY);
                ivActionChat.setColorFilter(Color.WHITE);
                ivActionProfile.setColorFilter(Color.GRAY);
                ivActionSetting.setColorFilter(Color.GRAY);

                break;
            case R.id.action_profile:
                tvActionHome.setTextSize(12);
                tvActionChat.setTextSize(12);
                tvActionProfile.setTextSize(13);
                tvActionSetting.setTextSize(12);

                tvActionHome.setTextColor(Color.GRAY);
                tvActionChat.setTextColor(Color.GRAY);
                tvActionProfile.setTextColor(Color.WHITE);
                tvActionSetting.setTextColor(Color.GRAY);

                ivActionHome.setColorFilter(Color.GRAY);
                ivActionChat.setColorFilter(Color.GRAY);
                ivActionProfile.setColorFilter(Color.WHITE);
                ivActionSetting.setColorFilter(Color.GRAY);

                startActivity(new Intent(MainActivity.this, UpdateProfileActivity.class));
                //finish();
                break;
            case R.id.action_setting:
                tvActionHome.setTextSize(12);
                tvActionChat.setTextSize(12);
                tvActionProfile.setTextSize(12);
                tvActionSetting.setTextSize(13);

                tvActionHome.setTextColor(Color.GRAY);
                tvActionChat.setTextColor(Color.GRAY);
                tvActionProfile.setTextColor(Color.GRAY);
                tvActionSetting.setTextColor(Color.WHITE);

                ivActionHome.setColorFilter(Color.GRAY);
                ivActionChat.setColorFilter(Color.GRAY);
                ivActionProfile.setColorFilter(Color.GRAY);
                ivActionSetting.setColorFilter(Color.WHITE);
                SocketChatApp.getSession().logoutUser();
                finish();
                break;
        }

    }

    private class getFavoriteList extends android.os.AsyncTask<Void,Void,Void>{

        List<PostEntity> favPost;
        @Override
        protected Void doInBackground(Void... voids) {
            favPost=SocketChatApp.getDb().postDao().getFavorite();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "Favorite Post: "+favPost.size(), Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
}
