package com.cancerhomehealth.chhprofessional.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.cancerhomehealth.chhprofessional.Model.UserData;

public class SharedPrefManager {

    private static String SHARED_PREF_NAME = "chc_professional";
    private SharedPreferences sharedPreferences;
    private final Context context;
    private SharedPreferences.Editor editor;

    public SharedPrefManager(Context context) {
        this.context = context;
    }

    public void saveUser(UserData userData){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("username",userData.getUsername());
        editor.putString("name",userData.getName());
        editor.putString("mobile_no",userData.getMobile_no());
        editor.apply();
    }

    public UserData getUserData(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new UserData(sharedPreferences.getString("name",null),
                sharedPreferences.getString("username",null),
                sharedPreferences.getString("mobile_no",null));
    }

    public void logout(){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
