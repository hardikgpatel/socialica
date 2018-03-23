package com.test.socketchat.activity.utility;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.test.socketchat.R;
import com.test.socketchat.activity.db.PostDatabase;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lcom151-two on 3/8/2018.
 */

public class SocketChatApp extends Application {
    private static Retrofit retrofit = null;
    private static ApiInterface apiService;
    private static UserSession session;
    private static Utility utility;
    private static PostDatabase db = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // Retrofit Singleton object
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.host))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        // Room Database Singleton object
        if (db == null) {
            db = Room.databaseBuilder(getApplicationContext(), PostDatabase.class, "posts").build();
        }
        session = new UserSession(getApplicationContext());
        utility = new Utility(getApplicationContext());
    }

    // get utility methods
    public static Utility getUtility() {
        return utility;
    }

    // get api calling object
    public static ApiInterface getApiService() {
        apiService = retrofit.create(ApiInterface.class);
        return apiService;
    }

    // get session object
    public static UserSession getSession() {
        return session;
    }

    // get database object
    public static PostDatabase getDb() {
        return db;
    }
}
