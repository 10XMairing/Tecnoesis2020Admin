package com.github.tenx.tecnoesis20admin.data.sprefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.tenx.tecnoesis20admin.R;
import com.github.tenx.tecnoesis20admin.data.rest.events.AppEventHelper;

public class AppSharedPrefsHelper implements SharedPrefsHelper {

    private Context context;
    private  static AppSharedPrefsHelper instance;

    public static final String TOKEN_KEY = "token";
    public static final String EMAIL_KEY = "email";
    public static final String DESIGNATION_KEY = "desig";


    private SharedPreferences sharedPrefs;

    public AppSharedPrefsHelper(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences(context.getResources().getString(R.string.shared_pref_key), Context.MODE_PRIVATE);

    }

    public static AppSharedPrefsHelper getInstance(Context context){
        if(instance == null){
            synchronized (AppEventHelper.class){
                if(instance == null){
                    instance = new AppSharedPrefsHelper(context);
                }
            }
        }
        return instance;
    }

    @Override
    public void saveToken(String token) {
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(TOKEN_KEY , token);
            editor.apply();


    }

    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(EMAIL_KEY , email);
        editor.apply();
    }

    @Override
    public String getToken() {
        return  sharedPrefs.getString(TOKEN_KEY , "");
    }

    @Override
    public String getEmail() {
        return sharedPrefs.getString(EMAIL_KEY, "");
    }

    @Override
    public void deleteUserData() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(EMAIL_KEY);
        editor.apply();
    }

    @Override
    public void saveDesig(String desig) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(DESIGNATION_KEY , desig);
        editor.apply();
    }

    @Override
    public String getDesig() {
        return sharedPrefs.getString(DESIGNATION_KEY, "Organizer @Robotron");
    }
}
