package com.github.tenx.tecnoesis20admin.data.models;

import com.google.gson.annotations.SerializedName;

public class NotificationBody {


    @SerializedName("sender")
    String sender;

    @SerializedName("title")
    String title;

    @SerializedName("message")
    String message;

    @SerializedName("image")
    String image;

    @SerializedName("date")
    String date;


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return getSender() + "\n"+getTitle()+"\n"+getDate()+"\n"+getMessage()+"\n";
    }
}
