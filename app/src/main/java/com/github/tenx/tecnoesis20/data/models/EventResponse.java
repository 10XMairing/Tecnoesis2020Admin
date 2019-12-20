package com.github.tenx.tecnoesis20.data.models;

import com.google.gson.annotations.SerializedName;

public class EventResponse {


    @SerializedName("name")
    String name;

    @SerializedName("date")
    String date;

    @SerializedName("image")
    String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
