package com.github.tenx.tecnoesis20.data;

import android.content.Context;

import com.github.tenx.tecnoesis20.data.models.EventResponse;
import com.github.tenx.tecnoesis20.data.rest.events.AppEventHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class AppDataManager implements  AppDataManagerHelper{


    private Context context;
    private AppEventHelper eventHelper;

    public AppDataManager(Context context) {
        this.context = context;
        eventHelper = AppEventHelper.getInstance();
    }

    @Override
    public Call<ArrayList<EventResponse>> getEvents() {
        return eventHelper.getEvents();
    }
}
