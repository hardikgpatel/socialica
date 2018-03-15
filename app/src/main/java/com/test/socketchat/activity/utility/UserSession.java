package com.test.socketchat.activity.utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.test.socketchat.activity.activity.SplashScreenActivity;
import com.test.socketchat.activity.model.ModelRegisterUser;
import com.test.socketchat.activity.response.ResponseRegisterUser;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * Created by lcom151-two on 3/8/2018.
 */

public class UserSession {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private final int PREF_MODE = 0;
    private final String KEY_SP_FILE = "SocketAppUser";
    private Context context;

    public static final String IS_LOGIN = "is_login";
    public static final String KEY_USER_ID = "USER_ID";
    public static final String KEY_DISPLAY_NAME = "DISPLAY_NAME";
    public static final String KEY_USER_NAME = "USER_NAME";
    public static final String KEY_STATUS = "USER_STATUS";
    public static final String KEY_PROFILE_IMAGE = "PROFILE_IMAGE";
    public static final String KEY_EMAIL = "USER_EMAIL";

    public UserSession(Context context) {
        this.context = context;
        this.sp = context.getSharedPreferences(this.KEY_SP_FILE, this.PREF_MODE);
        this.editor = this.sp.edit();
    }

    public void createLoginSession(String mobile) {
        try {
            this.editor.putBoolean(IS_LOGIN, true);
            this.editor.putString(KEY_USER_ID, mobile);
            this.editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateSetus(String status) {
        this.editor.putString(KEY_STATUS, status);
        this.editor.commit();
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        Log.e("start", "check ic_user login");
        return sp.getBoolean(IS_LOGIN, false);
    }

    public void setUserData(ModelRegisterUser user) {
        this.editor.putString(KEY_DISPLAY_NAME, user.getDisplayName());
        this.editor.putString(KEY_USER_NAME, user.getUserName().toString());
        this.editor.putString(KEY_EMAIL, user.getEmail());
        this.editor.putString(KEY_PROFILE_IMAGE, user.getUserProfilePhoto());
        this.editor.putString(KEY_STATUS, user.getStatus());
        editor.commit();
    }

    public void logoutUser() {
        try {
            //clear all data form shared preference
            this.editor.remove(KEY_DISPLAY_NAME);
            this.editor.remove(KEY_USER_NAME);
            this.editor.remove(KEY_EMAIL);
            this.editor.remove(KEY_PROFILE_IMAGE);
            this.editor.remove(KEY_STATUS);
            this.editor.remove(KEY_USER_ID);
            this.editor.remove(IS_LOGIN);
            this.editor.clear();
            this.editor.commit();
            this.context.startActivity(new Intent(this.context, SplashScreenActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get ic_user details
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USER_ID, sp.getString(KEY_USER_ID, null));
        user.put(KEY_USER_NAME, sp.getString(KEY_USER_NAME, null));
        user.put(KEY_DISPLAY_NAME, sp.getString(KEY_EMAIL, null));
        user.put(KEY_EMAIL, sp.getString(KEY_EMAIL, null));
        user.put(KEY_PROFILE_IMAGE, sp.getString(KEY_PROFILE_IMAGE, null));
        user.put(KEY_STATUS, sp.getString(KEY_STATUS, null));
        return user;
    }

    public String getStatus() {
        return sp.getString(KEY_STATUS, "Status");
    }

    public String getUserID() {
        return sp.getString(KEY_USER_ID, null);
    }

    public String getUserName() {
        return sp.getString(KEY_USER_NAME, "Name");
    }

    public String getDisplayName() {
        return sp.getString(KEY_DISPLAY_NAME, "Display Name");
    }

    public String getEmail() {
        return sp.getString(KEY_EMAIL, "email");
    }

    public String getProfileImage() {
        return sp.getString(KEY_PROFILE_IMAGE, "Image");
    }
}
