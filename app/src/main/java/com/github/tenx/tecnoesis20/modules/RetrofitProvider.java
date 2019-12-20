package com.github.tenx.tecnoesis20.modules;



import com.github.tenx.tecnoesis20.Config;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Retrofit.Builder builder;
    private static Retrofit instance =null;

    public static Retrofit getInstance(){

        if (instance ==null) {
            synchronized (RetrofitProvider.class){
                if(instance == null){
                    builder = new Retrofit.Builder().baseUrl(Config.REST_BASE_URL).addConverterFactory(GsonConverterFactory.create());

                    instance = builder.build();
                }
            }
        }
        return instance;

    }


}
