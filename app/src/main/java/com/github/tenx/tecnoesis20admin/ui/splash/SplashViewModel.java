package com.github.tenx.tecnoesis20admin.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.DefaultResponse;
import com.github.tenx.tecnoesis20admin.data.models.LoginResponse;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SplashViewModel extends AndroidViewModel {

    private AppDataManager appDataManager;


    private MutableLiveData<Boolean> ldTokenStatus;




    public SplashViewModel(@NonNull Application application) {
        super(application);
        appDataManager = ((MyApplication) application).getDataManager();
        ldTokenStatus = new MutableLiveData<>();

    }


    public LiveData<Boolean> getLdTokenStatus() {
        return ldTokenStatus;
    }

    public  void verifyToken(){
           appDataManager.checkAuth().enqueue(new Callback<TokenResponse>() {
               @Override
               public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {

                   Timber.d("Response received : "+response.code() );
                                if(response.code() < 300){
//                                    success
                                    Timber.d("token verified");
                                    try {
                                        appDataManager.saveEmail(response.body().getData().getEmail());
                                    }catch (NullPointerException e){
                                        Timber.e(e);
                                    }
                                    ldTokenStatus.postValue(true);
                                }else {

                                    ldTokenStatus.postValue(false);
                                }
               }

               @Override
               public void onFailure(Call<TokenResponse> call, Throwable t) {
                        ldTokenStatus.postValue(false);
               }
           });
    }


    public boolean hasToken(){
        return !appDataManager.getToken().equals("");
    }



}
