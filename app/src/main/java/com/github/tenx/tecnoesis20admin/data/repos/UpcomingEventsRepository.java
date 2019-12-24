package com.github.tenx.tecnoesis20admin.data.repos;

import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.models.UpcomingEvents;

import java.util.ArrayList;
import java.util.List;

public class UpcomingEventsRepository {
    private static UpcomingEventsRepository instance;
    private ArrayList<UpcomingEvents> dataSet = new ArrayList<>();

    public static UpcomingEventsRepository getInstance(){
        if(instance == null){
            instance = new UpcomingEventsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<UpcomingEvents>> getUpcomingEvents(){
        setNicePlaces();
        MutableLiveData<List<UpcomingEvents>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setNicePlaces(){
        dataSet.add(
                new UpcomingEvents("Robotron","Robo Wars","Dec 1","11 pm")
        );
        dataSet.add(
                new UpcomingEvents("Robotron","Sumo Wars","Dec 1","11 pm")
        );
        dataSet.add(
                new UpcomingEvents("Robotron","Robo Soccer","Dec 1","11 pm")
        );
        dataSet.add(
                new UpcomingEvents("Robotron","Robo Art","Dec 1","11 pm")
        );
        dataSet.add(
                new UpcomingEvents("Robotron","Terrain Treader","Dec 1","11 pm")
        );
        dataSet.add(
                new UpcomingEvents("Robotron","Robo Build","Dec 1","11 pm")
        );

    }
}
