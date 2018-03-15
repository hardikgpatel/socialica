package com.test.socketchat.activity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.socketchat.activity.model.ModelPostLike;

import java.util.List;

/**
 * Created by lcom151-two on 3/13/2018.
 */

public class ResponsePostLikes {
    @SerializedName("message")
    @Expose
    private List<ModelPostLike> data = null;

    public List<ModelPostLike> getData() {
        return data;
    }

    public void setData(List<ModelPostLike> data) {
        this.data = data;
    }
}
