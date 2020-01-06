package com.github.tenx.tecnoesis20admin.data.sprefs;

public interface SharedPrefsHelper {

    void saveToken(String token);
    void saveEmail(String email);


    void saveDesig(String desig);

    String getDesig();


    String getToken();


    String getEmail();

    void deleteUserData();

}
