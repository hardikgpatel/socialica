package com.test.socketchat.activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lcom151-two on 3/13/2018.
 */

public class ModelPostLike {
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("userProfilePhoto")
    @Expose
    private String userProfilePhoto;
    @SerializedName("count(likes.likeId)")
    @Expose
    private Integer countLikesLikeId;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
    }

    public Integer getCountLikesLikeId() {
        return countLikesLikeId;
    }

    public void setCountLikesLikeId(Integer countLikesLikeId) {
        this.countLikesLikeId = countLikesLikeId;
    }
}
