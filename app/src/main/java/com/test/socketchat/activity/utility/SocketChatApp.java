package com.test.socketchat.activity.utility;

import android.app.Application;

import com.test.socketchat.R;

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

    @Override
    public void onCreate() {
        super.onCreate();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(getResources().getString(R.string.host))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        session = new UserSession(getApplicationContext());
        utility = new Utility(getApplicationContext());
    }

    public static Utility getUtility() {
        return utility;
    }

    public static ApiInterface getApiService() {
        apiService = retrofit.create(ApiInterface.class);
        return apiService;
    }

    public static UserSession getSession() {
        return session;
    }
}
