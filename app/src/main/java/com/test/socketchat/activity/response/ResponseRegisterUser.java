package com.test.socketchat.activity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.socketchat.activity.model.ModelRegisterUser;

import java.util.List;

/**
 * Created by lcom151-two on 3/8/2018.
 */

public class ResponseRegisterUser {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private List<ModelRegisterUser> message = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ModelRegisterUser> getUser() {
        return message;
    }

    public void setMessage(List<ModelRegisterUser> message) {
        this.message = message;
    }
}
