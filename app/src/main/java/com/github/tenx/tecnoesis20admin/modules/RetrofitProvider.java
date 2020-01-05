package com.github.tenx.tecnoesis20admin.modules;



import android.content.Context;
import android.content.SharedPreferences;

import com.github.tenx.tecnoesis20admin.Config;
import com.github.tenx.tecnoesis20admin.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {

    private static Retrofit.Builder builder;
    private static Retrofit instance =null;

    private static  SharedPreferences sharedPrefs;

    public static Retrofit getInstance(Context context){

        sharedPrefs = context.getSharedPreferences(context.getResources().getString(R.string.shared_pref_key), Context.MODE_PRIVATE);

        if (instance ==null) {
            synchronized (RetrofitProvider.class){
                if(instance == null){
                    OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(chain -> {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("User-Agent", "Tecnoesisi2k20-Admin")
                                .header("Authorization", "Bearer "+sharedPrefs.getString("token", ""))
                                .method(original.method(), original.body())
                                .build();

                        return  chain.proceed(request);
                    }).readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build();

                    builder = new Retrofit.Builder().baseUrl(Config.REST_BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create());

                    instance = builder.build();
                }
            }
        }
        return instance;

    }


}
