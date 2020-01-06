package com.github.tenx.tecnoesis20admin.data.models;

import com.google.firebase.database.PropertyName;

public class AdminResponse {


    @PropertyName("email")
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
