package com.github.tenx.tecnoesis20admin.ui;

import android.app.Application;

import com.github.tenx.tecnoesis20admin.BuildConfig;
import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.rest.events.AppEventHelper;
import com.github.tenx.tecnoesis20admin.logging.ReleaseTree;

import timber.log.Timber;

public class MyApplication extends Application {

//    create an instance of AppDatamanager here. This instance will be stored here as a singleton
    private  AppDataManager appDataManager;


    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }


    public  AppDataManager getDataManager(){
        if(appDataManager == null){
            synchronized (AppEventHelper.class){
                if(appDataManager == null){
                    appDataManager = new AppDataManager(this);
                }
            }
        }
        return appDataManager;
    }


}
