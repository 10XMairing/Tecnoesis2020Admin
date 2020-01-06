package com.github.tenx.tecnoesis20admin.ui.auth;

import android.app.Application;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.AdminResponse;
import com.github.tenx.tecnoesis20admin.data.models.DefaultResponse;
import com.github.tenx.tecnoesis20admin.data.models.LoginResponse;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginActivityViewModel extends AndroidViewModel {


    private AppDataManager appDataManager;
    private  MutableLiveData<Boolean> sendOtpSuccess;
    private  MutableLiveData<LoginResponse> loginResponse;
    private  MutableLiveData<List<AdminResponse>> adminsList;



    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        appDataManager = ((MyApplication) application).getDataManager();

        sendOtpSuccess = new MutableLiveData<>();
        loginResponse = new MutableLiveData<>();
        adminsList = new MutableLiveData<>();
    }


    public LiveData<Boolean> getSendOtpSuccess() {
        return sendOtpSuccess;
    }

    public MutableLiveData<LoginResponse> getLoginResponse() {
        return loginResponse;
    }

    public LiveData<List<AdminResponse>> getAdminsList() {
        return adminsList;
    }

    public void requestNewOtp(String email){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            appDataManager.requestOtp(email).enqueue(new Callback<DefaultResponse>() {
                @Override
                public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {

                    Timber.d("CODE : "+response.code());
                                if(response.code() < 300){
//                                    success

                                    sendOtpSuccess.postValue(true);
                                }else {
                                    sendOtpSuccess.postValue(false);
                                }
                }

                @Override
                public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    Timber.e("On failure");

                            sendOtpSuccess.postValue(false);
                }
            });

        }else {
            Timber.d("Not a valid email. Skipping");
            sendOtpSuccess.postValue(false);
        }
    }



    public void loginWithOtp(String email , int otp){
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            appDataManager.loginWIthOtp(email, otp).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                    Timber.d("CODE : "+response.code());
                    if(response.code() < 300){
//                                    success
                        appDataManager.saveEmail(response.body().getEmail());
                        appDataManager.saveToken(response.body().getToken());
                        loginResponse.postValue(response.body());


                    }else {
                        loginResponse.postValue(null);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {

                    Timber.e("On failure");

                    loginResponse.postValue(null);
                }
            });

        }else {
            Timber.d("Not a valid email. Skipping");
            loginResponse.postValue(null);
        }
    }



    public void loadAdmins(){
        FirebaseDatabase.getInstance().getReference().child("admins").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Iterator<DataSnapshot> adminsSnaps =  dataSnapshot.getChildren().iterator();

                            List<AdminResponse> tempList = new ArrayList<>();

                            while (adminsSnaps.hasNext()){
                                AdminResponse data = adminsSnaps.next().getValue(AdminResponse.class);

                                tempList.add(data);
                            }

                            adminsList.postValue(tempList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





}
