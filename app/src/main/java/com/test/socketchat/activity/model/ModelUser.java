package com.test.socketchat.activity.model;

import android.text.SpannableString;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lcom151-two on 3/8/2018.
 */

public class ModelUser {
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("userName")
    @Expose
    private Object userName;
    @SerializedName("userProfilePhoto")
    @Expose
    private String userProfilePhoto;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("displayName")
    @Expose
    private String displayName;
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

    private SpannableString spannableDisplayName;
    private SpannableString spannableUserName;
    private SpannableString spannableEmail;
    private SpannableString spannableId;

    public SpannableString getSpannableDisplayName() {
        return spannableDisplayName;
    }

    public void setSpannableDisplayName(SpannableString spannableDisplayName) {
        this.spannableDisplayName = spannableDisplayName;
    }

    public SpannableString getSpannableUserName() {
        return spannableUserName;
    }

    public SpannableString getSpannableEmail() {
        return spannableEmail;
    }

    public void setSpannableEmail(SpannableString spannableEmail) {
        this.spannableEmail = spannableEmail;
    }

    public SpannableString getSpannableId() {
        return spannableId;
    }

    public void setSpannableId(SpannableString spannableId) {
        this.spannableId = spannableId;
    }

    public void setSpannableUserName(SpannableString spannableUserName) {
        this.spannableUserName = spannableUserName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public String getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
