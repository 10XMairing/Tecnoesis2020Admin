package com.github.tenx.tecnoesis20admin.ui.main.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.EventResponse;
import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;
import com.github.tenx.tecnoesis20admin.data.repos.UpcomingEventsRepository;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class HomeViewModel  extends AndroidViewModel {

    private MutableLiveData<List<UpcomingEvents>> upcomingEventsList;
    private UpcomingEventsRepository mRepo;

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(){
        if(upcomingEventsList != null){
            return;
        }
        mRepo = UpcomingEventsRepository.getInstance();
        upcomingEventsList = mRepo.getUpcomingEvents();
    }

    public LiveData<List<UpcomingEvents>> getUpcomingEvents() {
        if(upcomingEventsList == null){
            upcomingEventsList = new MutableLiveData<>();
        }
        return  upcomingEventsList;
    }




}