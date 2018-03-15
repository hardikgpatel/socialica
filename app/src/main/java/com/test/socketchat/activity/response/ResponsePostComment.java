package com.test.socketchat.activity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.socketchat.activity.model.ModelPostComment;

import java.util.List;

/**
 * Created by lcom151-two on 3/14/2018.
 */

public class ResponsePostComment {
    @SerializedName("message")
    @Expose
    private List<ModelPostComment> comment = null;

    public List<ModelPostComment> getComment() {
        return comment;
    }

    public void setComment(List<ModelPostComment> comment) {
        this.comment = comment;
    }

}
