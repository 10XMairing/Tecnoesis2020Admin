package com.github.tenx.tecnoesis20admin.data.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {


    @SerializedName("message")
    String message;

    @SerializedName("token")
    String token;

    @SerializedName("email")
    String email;

    public LoginResponse() {
        message = "";
        token = "";
        email = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
