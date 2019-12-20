package com.github.tenx.tecnoesis20admin.ui.main.home;

import androidx.lifecycle.LiveData;

import com.github.tenx.tecnoesis20admin.data.models.EventResponse;

import java.util.ArrayList;

public interface HomeViewModelHelper {
    LiveData<ArrayList<EventResponse>> getEvents();

    void loadEvents();
}
