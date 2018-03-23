package com.test.socketchat.activity.utility;

import com.test.socketchat.activity.model.ModelUser;
import com.test.socketchat.activity.response.ResponseAddPostComment;
import com.test.socketchat.activity.response.ResponseDeletePost;
import com.test.socketchat.activity.response.ResponsePost;
import com.test.socketchat.activity.response.ResponsePostComment;
import com.test.socketchat.activity.response.ResponsePostLikes;
import com.test.socketchat.activity.response.ResponsePostOption;
import com.test.socketchat.activity.response.ResponseRegisterUser;
import com.test.socketchat.activity.response.ResponseVerifyUser;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("api/profile/{userId}")
    @FormUrlEncoded
    Call<ResponseRegisterUser> updateProfile(@Path("userId") String userId,@Field("email") String email,@Field("displayName") String displayName,@Field("userStatus") String userStatus,@Field("userName") String userName);

    @POST("api/userProfile")
    @FormUrlEncoded
    Call<ResponseRegisterUser> getProfile(@Field("userId") String userId);

    @GET("api/post/{userId}")
    Call<ResponsePost> getPost(@Path("userId") String userId);

    @DELETE("/api/post/{postId}")
    Call<ResponseDeletePost> deletePost(@Path("postId") String postId);

    @POST("api/likes")
    @FormUrlEncoded
    Call<ResponsePostOption> likePost(@Field("userId") String userId,@Field("postId") String postId);

    @GET("api/post/likes/{postId}")
    Call<ResponsePostLikes> getPostLikes(@Path("postId") String postId);

    @GET("api/post/comments/{postId}")
    Call<ResponsePostComment> getPostComments(@Path("postId") String postId);

    @POST("api/comment/post")
    @FormUrlEncoded
    Call<ResponseAddPostComment> postComment(@Field("commentText") String comment, @Field("userId") String user, @Field("postId") int post);

    @GET("api/search/{query}")
    Call<ResponseRegisterUser> getSearch(@Path("query") String query);
}
