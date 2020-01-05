package com.github.tenx.tecnoesis20admin.data.models;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("message")
    String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
