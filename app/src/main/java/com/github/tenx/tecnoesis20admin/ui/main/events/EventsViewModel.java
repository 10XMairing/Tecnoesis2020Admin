package com.github.tenx.tecnoesis20admin.ui.main.events;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.models.Events;
import com.github.tenx.tecnoesis20admin.data.repos.EventsRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.List;

import timber.log.Timber;

public class EventsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Events>> eventsList;
    private EventsRepository mRepo;

    FirebaseStorage storage;


    private MutableLiveData<String> ldDownloadUrl;

    public EventsViewModel(@NonNull Application application) {
        super(application);

        storage = FirebaseStorage.getInstance();
    }




    public void init(){
        if(eventsList != null){
            return;
        }
        mRepo = EventsRepository.getInstance();
        eventsList = mRepo.getEvents();
    }

    public LiveData<List<Events>> getEvents() {
        if(eventsList == null){
            eventsList = new MutableLiveData<>();
        }
        return  eventsList;
    }






}
