package com.github.tenx.tecnoesis20admin.data.rest.events;

import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.modules.RetrofitProvider;

import java.util.ArrayList;

import retrofit2.Call;

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
