package com.github.tenx.tecnoesis20.data.rest.events;

import android.content.Context;

import com.github.tenx.tecnoesis20.data.models.EventResponse;
import com.github.tenx.tecnoesis20.modules.RetrofitProvider;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class AppEventHelper implements  EventsApiService {
    private static AppEventHelper instance;
    private EventsApiService api;
    public AppEventHelper() {
        api = RetrofitProvider.getInstance().create(EventsApiService.class);
    }

    @Override
    public Call<ArrayList<EventResponse>> getEvents() {
        return api.getEvents();
    }


    public static AppEventHelper getInstance(){
        if(instance == null){
            synchronized (AppEventHelper.class){
                if(instance == null){
                    instance = new AppEventHelper();
                }
            }
        }
        return instance;
    }
}
