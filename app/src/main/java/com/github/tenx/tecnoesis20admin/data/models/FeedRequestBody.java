package com.github.tenx.tecnoesis20admin.data.models;

import com.google.firebase.database.PropertyName;

public class FeedRequestBody {

    @PropertyName("date")
    String date;

    @PropertyName("image")
    String image;

    @PropertyName("text")
    String text;

    @PropertyName("email")
    String email;

    public FeedRequestBody() {
    }

    public FeedRequestBody(String date, String image, String text, String email) {
        this.date = date;
        this.image = image;
        this.text = text;
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
