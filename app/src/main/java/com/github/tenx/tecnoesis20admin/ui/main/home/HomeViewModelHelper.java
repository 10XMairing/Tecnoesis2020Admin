package com.github.tenx.tecnoesis20admin.ui.main.home;

import androidx.lifecycle.LiveData;

import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;

import java.util.ArrayList;
import java.util.List;

public interface HomeViewModelHelper {
    LiveData<List<UpcomingEvents>> getUpcomingEvents();

    void loadEvents();
}
