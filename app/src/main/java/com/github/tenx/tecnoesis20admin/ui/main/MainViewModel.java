package com.github.tenx.tecnoesis20admin.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;

public class MainViewModel  extends AndroidViewModel {

    AppDataManager appDataManager;

    public MainViewModel(@NonNull Application application) {
        super(application);

        appDataManager = ((MyApplication) application).getDataManager();
    }


    public String getEmail() {
        return appDataManager.getEmail();
    }


    public void deleteUserData() {
        appDataManager.deleteUserData();
    }

}
