package com.github.tenx.tecnoesis20.ui.main.home;

import androidx.lifecycle.LiveData;

import com.github.tenx.tecnoesis20.data.models.EventResponse;

import java.util.ArrayList;

public interface HomeViewModelHelper {
    LiveData<ArrayList<EventResponse>> getEvents();

    void loadEvents();
}
