package com.test.socketchat.activity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lcom151-two on 3/14/2018.
 */

public class ModelPostComment {
    @SerializedName("displayName")
    @Expose
    private String displayName;
    @SerializedName("userProfilePhoto")
    @Expose
    private String userProfilePhoto;
    @SerializedName("commentId")
    @Expose
    private Integer commentId;
    @SerializedName("commentText")
    @Expose
    private String commentText;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("postId")
    @Expose
    private Integer postId;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("modifiedAt")
    @Expose
    private String modifiedAt;
    @SerializedName("deletedAt")
    @Expose
    private Object deletedAt;
    @SerializedName("isDeleted")
    @Expose
    private Integer isDeleted;

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

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
