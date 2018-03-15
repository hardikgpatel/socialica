package com.test.socketchat.activity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.socketchat.activity.model.ModelPost;

import java.util.List;

/**
 * Created by lcom151-two on 3/13/2018.
 */

public class ResponsePost {
    @SerializedName("message")
    @Expose
    private List<ModelPost> message = null;

    public List<ModelPost> getMessage() {
        return message;
    }

    public void setMessage(List<ModelPost> message) {
        this.message = message;
    }
}
