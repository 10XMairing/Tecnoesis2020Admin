package com.github.tenx.tecnoesis20admin.data.rest.events;

import android.content.Context;

import com.github.tenx.tecnoesis20admin.data.models.DefaultResponse;
import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.data.models.LoginResponse;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.modules.RetrofitProvider;

import java.util.ArrayList;

import retrofit2.Call;

public class AppEventHelper implements  EventsApiService {
    private static AppEventHelper instance;
    private EventsApiService api;
    public AppEventHelper(Context context) {
        api = RetrofitProvider.getInstance(context).create(EventsApiService.class);
    }



    public static AppEventHelper getInstance(Context context){
        if(instance == null){
            synchronized (AppEventHelper.class){
                if(instance == null){
                    instance = new AppEventHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public Call<DefaultResponse> requestOtp(String email) {
        return api.requestOtp(email);
    }

    @Override
    public Call<LoginResponse> loginWIthOtp(String email, int otp) {
        return api.loginWIthOtp(email,otp);
    }

    @Override
    public Call<TokenResponse> checkAuth() {
        return api.checkAuth();
    }

    @Override
    public Call<TokenResponse> sendNotification(String sender, String title, String message) {
        return api.sendNotification(sender,title,message);
    }
}




