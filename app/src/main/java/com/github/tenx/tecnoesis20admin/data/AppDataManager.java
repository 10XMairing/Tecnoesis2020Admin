package com.github.tenx.tecnoesis20admin.data;

import android.content.Context;

import com.github.tenx.tecnoesis20admin.data.models.DefaultResponse;
import com.github.tenx.tecnoesis20admin.data.models.LoginResponse;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.data.rest.events.AppEventHelper;
import com.github.tenx.tecnoesis20admin.data.sprefs.AppSharedPrefsHelper;

import retrofit2.Call;

public class AppDataManager implements  AppDataManagerHelper{


    private Context context;
    private AppEventHelper restHelper;
    private AppSharedPrefsHelper appSharedPrefsHelper;


    public AppDataManager(Context context) {
        this.context = context;
        restHelper = AppEventHelper.getInstance(context);

        appSharedPrefsHelper = AppSharedPrefsHelper.getInstance(context);
    }


    @Override
    public void deleteUserData() {
            appSharedPrefsHelper.deleteUserData();
    }

    @Override
    public Call<DefaultResponse> requestOtp(String email) {
        return restHelper.requestOtp(email);
    }

    @Override
    public Call<LoginResponse> loginWIthOtp(String email, int otp) {
        return restHelper.loginWIthOtp(email,otp);
    }

    @Override
    public void saveToken(String token) {
        appSharedPrefsHelper.saveToken(token);
    }

    @Override
    public void saveEmail(String email) {
        appSharedPrefsHelper.saveEmail(email);
    }

    @Override
    public String getToken() {
        return appSharedPrefsHelper.getToken();
    }

    @Override
    public String getEmail() {
        return appSharedPrefsHelper.getEmail();
    }

    @Override
    public Call<TokenResponse> checkAuth() {
        return restHelper.checkAuth();
    }

    @Override
    public Call<TokenResponse> sendNotification(String sender, String title, String message) {
        return restHelper.sendNotification(sender,message,title);

    }

    @Override
    public Call<TokenResponse> saveFeed(String image, String text) {
        return restHelper.saveFeed(image,text);
    }

    @Override
    public void saveDesig(String desig) {
            appSharedPrefsHelper.saveDesig(desig);
    }

    @Override
    public String getDesig() {
        return appSharedPrefsHelper.getDesig();
    }
}
