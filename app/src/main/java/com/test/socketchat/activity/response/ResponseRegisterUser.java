package com.test.socketchat.activity.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.test.socketchat.activity.model.ModelUser;

import java.util.List;

/**
 * Created by lcom151-two on 3/8/2018.
 */

public class ResponseRegisterUser {
    @SerializedName("message")
    @Expose
    private List<ModelUser> message = null;

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("error")
    @Expose
    private String error;

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ModelUser> getUser() {
        return message;
    }

    public void setMessage(List<ModelUser> message) {
        this.message = message;
    }
}
