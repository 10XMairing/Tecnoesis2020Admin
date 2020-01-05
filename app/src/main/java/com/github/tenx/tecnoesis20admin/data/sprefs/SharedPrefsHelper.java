package com.github.tenx.tecnoesis20admin.data.sprefs;

public interface SharedPrefsHelper {

    void saveToken(String token);
    void saveEmail(String email);


    String getToken();


    String getEmail();

    void deleteUserData();

}
