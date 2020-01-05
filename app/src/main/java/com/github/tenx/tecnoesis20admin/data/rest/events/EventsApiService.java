package com.github.tenx.tecnoesis20admin.data.rest.events;

import com.github.tenx.tecnoesis20admin.data.models.DefaultResponse;
import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.data.models.LoginResponse;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface EventsApiService {


    @FormUrlEncoded
    @POST("/api/v1/organizers/login/new-otp")
    Call<DefaultResponse> requestOtp(@Field("email") String email);    @FormUrlEncoded


    @POST("/api/v1/organizers/login/with-otp")
    Call<LoginResponse> loginWIthOtp(@Field("email") String email , @Field("otp") int otp);



    @POST("/api/v1/organizers/check")
    Call<TokenResponse> checkAuth();


    @FormUrlEncoded
    @POST("/api/v1/firebase/send-notification")
    Call<TokenResponse> sendNotification(@Field("sender") String sender,@Field("title") String title,@Field("message") String message);


    @FormUrlEncoded
    @POST("/api/v1/firebase/feed/push")
    Call<TokenResponse> saveFeed(@Field("image") String image,@Field("text") String text);


}
