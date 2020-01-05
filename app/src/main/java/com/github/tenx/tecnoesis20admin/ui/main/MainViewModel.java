package com.github.tenx.tecnoesis20admin.ui.main;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.FeedRequestBody;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainViewModel  extends AndroidViewModel {

    AppDataManager appDataManager;
    private MutableLiveData<Boolean> ldFeedPosted;
    private FirebaseStorage storage;
    private FirebaseDatabase firedb;

    private String tempFeed;

    public MainViewModel(@NonNull Application application) {
        super(application);

        appDataManager = ((MyApplication) application).getDataManager();
        ldFeedPosted = new MutableLiveData<>();
        storage = FirebaseStorage.getInstance();
        firedb = FirebaseDatabase.getInstance();
    }


    public String getEmail() {
        return appDataManager.getEmail();
    }


    public void deleteUserData() {
        appDataManager.deleteUserData();
    }

    public LiveData<Boolean> getLdDownloadUrl() {
        return ldFeedPosted;
    }


    public void uploadImage(Uri uri, String feed){
        tempFeed = feed;
        StorageReference ref =  storage.getReference().child("feeds");

        UploadTask task = ref.putFile(uri);
        task.addOnSuccessListener(taskSnapshot -> {
            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        String downloadUrl = task.getResult().toString();
                        Timber.d("Download Url generated : " +downloadUrl);
                        appDataManager.saveFeed(downloadUrl,feed).enqueue(new Callback<TokenResponse>() {
                            @Override
                            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                if(response.code() < 300){
//                                    success
                                    ldFeedPosted.postValue(true);
                                }else {
                                    ldFeedPosted.postValue(false);
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenResponse> call, Throwable t) {
                                ldFeedPosted.postValue(false);
                            }
                        });


                    }else {
                        ldFeedPosted.postValue(false);
                    }
                }
            });
        }).addOnFailureListener(taske-> {
            ldFeedPosted.postValue(false);
        });

    }

}
