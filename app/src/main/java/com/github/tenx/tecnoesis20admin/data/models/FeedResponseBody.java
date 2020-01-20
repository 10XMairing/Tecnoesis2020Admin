package com.github.tenx.tecnoesis20admin.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class FeedResponseBody implements Parcelable {
    @PropertyName("image")
    String image;

    @PropertyName("text")
    String text;

    @PropertyName("date")
    String date;

    @PropertyName("email")
    String email;

    public FeedResponseBody() {
    }

    public FeedResponseBody(String image, String text) {
        this.image = image;
        this.text = text;
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

    public String getDate() {
        return date;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDate(String date) {
        this.date = date;
    }


    protected FeedResponseBody(Parcel in) {
        image = in.readString();
        text = in.readString();
        date = in.readString();
        email = in.readString();
    }

    public static final Creator<FeedResponseBody> CREATOR = new Creator<FeedResponseBody>() {
        @Override
        public FeedResponseBody createFromParcel(Parcel in) {
            return new FeedResponseBody(in);
        }

        @Override
        public FeedResponseBody[] newArray(int size) {
            return new FeedResponseBody[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(image);
        dest.writeString(text);
        dest.writeString(date);
        dest.writeString(email);
    }
}
