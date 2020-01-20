package com.github.tenx.tecnoesis20admin.ui.main;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.github.tenx.tecnoesis20admin.data.AppDataManager;
import com.github.tenx.tecnoesis20admin.data.models.FeedRequestBody;
import com.github.tenx.tecnoesis20admin.data.models.FeedResponseBody;
import com.github.tenx.tecnoesis20admin.data.models.NotificationBody;
import com.github.tenx.tecnoesis20admin.data.models.TokenResponse;
import com.github.tenx.tecnoesis20admin.ui.MyApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainViewModel  extends AndroidViewModel {

    AppDataManager appDataManager;

//    key will be success // error
    private MutableLiveData<String> ldFeedSuccess;
    private MutableLiveData<String> ldFeedError;



    private MutableLiveData<List<NotificationBody>> listNotifications;
    private MutableLiveData<List<DataSnapshot>> listFeeds;
    private MutableLiveData<Boolean> onUpdatedFeed;
    private MutableLiveData<Boolean> onDeleteFeed;

    private FirebaseStorage storage;
    private FirebaseDatabase firedb;

    private String tempFeed;

    public void setLdFeedSuccess(String ldFeedSuccess) {
        this.ldFeedSuccess.postValue(ldFeedSuccess);
    }

    public void setLdFeedError(String ldFeedError) {
        this.ldFeedError.postValue(ldFeedError);
    }

    public MainViewModel(@NonNull Application application) {
        super(application);

        appDataManager = ((MyApplication) application).getDataManager();
        ldFeedSuccess = new MutableLiveData<>();
        ldFeedError = new MutableLiveData<>();
        listNotifications = new MutableLiveData<>();
        onUpdatedFeed = new MutableLiveData<>();
        onDeleteFeed = new MutableLiveData<>();
        listFeeds = new MutableLiveData<>();
        storage = FirebaseStorage.getInstance();
        firedb = FirebaseDatabase.getInstance();
    }


    public String getEmail() {
        return appDataManager.getEmail();
    }


    public LiveData<List<NotificationBody>> getListNotifications() {
        return listNotifications;
    }

    public LiveData<List<DataSnapshot>> getListFeeds() {
        return listFeeds;
    }

    public LiveData<Boolean> getOnUpdatedFeed() {
        return onUpdatedFeed;
    }

    public LiveData<Boolean> getOnDeleteFeed() {
        return onDeleteFeed;
    }

    public void deleteUserData() {
        appDataManager.deleteUserData();
    }

    public LiveData<String> getLdFeedSuccess() {
        return ldFeedSuccess;
    }

    public LiveData<String> getLdFeedError() {
        return ldFeedError;
    }

    public void uploadImage(Uri uri, String feed){
        tempFeed = feed;
        String name = UUID.randomUUID().toString().replace("-" , "").toLowerCase();
        StorageReference ref =  storage.getReference().child("feeds/image_"+name);

        ldFeedError.setValue("");
        ldFeedSuccess.setValue("");

        UploadTask task = ref.putFile(uri);
        task.addOnSuccessListener(taskSnapshot -> {
            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()){
                        String downloadUrl = task.getResult().toString();
                        Timber.d("Download Url generated : %s", downloadUrl);
                        appDataManager.saveFeed(downloadUrl,feed).enqueue(new Callback<TokenResponse>() {
                            @Override
                            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                                if(response.code() < 300){
//                                    success
                                    ldFeedSuccess.postValue("Uploaded successfully");
                                }else {
                                    ldFeedError.postValue("Error uploading feed");
                                }
                            }

                            @Override
                            public void onFailure(Call<TokenResponse> call, Throwable t) {
                                ldFeedError.postValue("Error uploading feed");
                            }
                        });


                    }else {
                        ldFeedError.postValue("Error uploading feed");
                    }
                }
            });
        }).addOnFailureListener(taske-> {
            ldFeedError.postValue("Error uploading feed");
        });

    }


    public void loadNotifications(){
        firedb.getReference().child("notifications").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
                List<NotificationBody> tempList = new ArrayList<>();

                while(iterator.hasNext()){
                    NotificationBody data = iterator.next().getValue(NotificationBody.class);
                    tempList.add(data);
                }


                listNotifications.postValue(tempList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void loadFeeds(){
        firedb.getReference().child("feeds").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> iterator =  dataSnapshot.getChildren().iterator();
                List<DataSnapshot> tempList = new ArrayList<>();

                while(iterator.hasNext()){

                    tempList.add(iterator.next());
                }


                listFeeds.postValue(tempList);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateFeed(Map<String, Object> data, String key){


        firedb.getReference("feeds").child(key).updateChildren(data, (databaseError, databaseReference) -> {
            if(databaseError != null){
                onUpdatedFeed.postValue(false);
                return;
            }

            Timber.d("update completed");
            onUpdatedFeed.postValue(true);

        });
    }


    public void deleteFeed(String keyFeed){

        onDeleteFeed.setValue(false);
        firedb.getReference("feeds").child(keyFeed).removeValue((databaseError, databaseReference) -> {
            if(databaseError != null){
                onDeleteFeed.postValue(false);
                return;
            }else {
                onDeleteFeed.postValue(true);
            }
        });
    }

    public boolean isOwner(String email){

        Timber.d("app email : "+appDataManager.getEmail());
        Timber.d("compared email :"+email);

        return appDataManager.getEmail().trim().equals(email.trim());
    }

}
