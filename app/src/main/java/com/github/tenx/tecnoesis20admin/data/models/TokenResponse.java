package com.github.tenx.tecnoesis20admin.data.models;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {

    @SerializedName("message")
    String message;


    @SerializedName("data")
    DataBody data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBody getData() {
        return data;
    }

    public void setData(DataBody data) {
        this.data = data;
    }

    public static class DataBody {
        @SerializedName("email")
        String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
