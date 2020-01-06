package com.github.tenx.tecnoesis20admin.ui.main.notifications;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends AndroidViewModel {

    AppDataManager appDataManager;

    private MutableLiveData<Boolean> onSuccessNotification;


    public NotificationViewModel(@NonNull Application application) {
        super(application);

        appDataManager  = ((MyApplication) application).getDataManager();


        onSuccessNotification = new MutableLiveData<>();
    }


    public MutableLiveData<Boolean> getOnSuccessNotification() {
        return onSuccessNotification;
    }

    public String getEmail(){
        return appDataManager.getEmail();
    }


    public void sendNotification(String sender, String title, String message){
        appDataManager.sendNotification(sender, title,message).enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if(response.code() < 300){
                onSuccessNotification.setValue(true);
                }else {
            onSuccessNotification.postValue(false);
                }
            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                onSuccessNotification.setValue(false);
            }
        });
    }










}