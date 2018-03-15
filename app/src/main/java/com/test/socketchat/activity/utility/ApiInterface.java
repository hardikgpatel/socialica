package com.test.socketchat.activity.utility;

import com.test.socketchat.activity.response.ResponsePost;
import com.test.socketchat.activity.response.ResponsePostComment;
import com.test.socketchat.activity.response.ResponsePostLikes;
import com.test.socketchat.activity.response.ResponsePostOption;
import com.test.socketchat.activity.response.ResponseRegisterUser;
import com.test.socketchat.activity.response.ResponseVerifyUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by lcom151-two on 3/7/2018.
 */

public interface ApiInterface {

    @POST("api/login")
    @FormUrlEncoded
    Call<ResponseVerifyUser> requestOTP(@Field("userId") String phoneNumber);

    @POST("api/verify")
    @FormUrlEncoded
    Call<ResponseVerifyUser> verifyOTP(@Field("userId") String phoneNumber, @Field("otp") String OTP);

    @POST("api/profile")
    @Multipart
    Call<ResponseRegisterUser> registerUserProfile(@Part("userId") RequestBody phoneNumber, @Part("email") RequestBody email, @Part("displayName") RequestBody displayName, @Part("userStatus") RequestBody status, @Part("userName") RequestBody userName, @Part MultipartBody.Part image);

    @POST("api/userProfile")
    @FormUrlEncoded
    Call<ResponseRegisterUser> getProfile(@Field("userId") String userId);

    @GET("api/post/{userId}")
    Call<ResponsePost> getPost(@Path("userId") String userId);

    @POST("api/likes")
    @FormUrlEncoded
    Call<ResponsePostOption> likePost(@Field("userId") String userId,@Field("postId") String postId);

    @GET("api/post/likes/{postId}")
    Call<ResponsePostLikes> getPostLikes(@Path("postId") String postId);

    @GET("api/post/comments/{postId}")
    Call<ResponsePostComment> getPostComments(@Path("postId") String postId);
}
