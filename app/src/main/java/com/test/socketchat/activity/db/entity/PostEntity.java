package com.test.socketchat.activity.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by lcom151-two on 3/22/2018.
 */
@Entity(tableName = "post")
public class PostEntity {

    @PrimaryKey(autoGenerate = true)
    private int peId;

    @ColumnInfo(name="postId")
    private int postId;

    @ColumnInfo(name="postText")
    private String postText;

    @ColumnInfo(name="postUrl")
    private String postUrl;

    @ColumnInfo(name="createdAt")
    private String createdAt;

    @ColumnInfo(name="isRemove")
    private Boolean isRemove;

    @ColumnInfo(name="likes")
    private int likes;

    @ColumnInfo(name="comments")
    private int comments;

    @ColumnInfo(name="displayName")
    private String displayname;

    @ColumnInfo(name="userProfilePhoto")
    private String userProfilePhoto;

    public int getPeId() {
        return peId;
    }

    public void setPeId(int peId) {
        this.peId = peId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getRemove() {
        return isRemove;
    }

    public void setRemove(Boolean remove) {
        isRemove = remove;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getUserProfilePhoto() {
        return userProfilePhoto;
    }

    public void setUserProfilePhoto(String userProfilePhoto) {
        this.userProfilePhoto = userProfilePhoto;
    }
}
