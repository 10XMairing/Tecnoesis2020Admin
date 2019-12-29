package com.github.tenx.tecnoesis20admin.data.repos;

import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.models.Events;

import java.util.ArrayList;
import java.util.List;

public class EventsRepository {

    private static EventsRepository instance;
    private ArrayList<Events> dataSet = new ArrayList<>();

    public static EventsRepository getInstance(){
        if(instance == null){
            instance = new EventsRepository();
        }
        return instance;
    }

    public MutableLiveData<List<Events>> getEvents(){
        setNicePlaces();
        MutableLiveData<List<Events>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setNicePlaces(){
        dataSet.add(
                new Events("Robotron","Robo Wars","Dec 1","11 pm")
        );
        dataSet.add(
                new Events("Robotron","Sumo Wars","Dec 1","11 pm")
        );
        dataSet.add(
                new Events("Robotron","Robo Soccer","Dec 1","11 pm")
        );
        dataSet.add(
                new Events("Robotron","Robo Art","Dec 1","11 pm")
        );
        dataSet.add(
                new Events("Robotron","Terrain Treader","Dec 1","11 pm")
        );
        dataSet.add(
                new Events("Robotron","Robo Build","Dec 1","11 pm")
        );

    }
}
