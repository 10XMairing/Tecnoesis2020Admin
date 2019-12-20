package com.github.tenx.tecnoesis20.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20.data.AppDataManager;
import com.github.tenx.tecnoesis20.data.models.EventResponse;
import com.github.tenx.tecnoesis20.ui.MyApplication;
import com.github.tenx.tecnoesis20.ui.main.home.HomeViewModelHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainViewModel  extends AndroidViewModel {

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
