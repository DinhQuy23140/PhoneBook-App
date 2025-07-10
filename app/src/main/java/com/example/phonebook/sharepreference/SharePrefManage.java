package com.example.phonebook.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.phonebook.Untilities.Constants;

public class SharePrefManage{
    private SharedPreferences sharedPreferences;

    public SharePrefManage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.KEY_NAME_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        sharedPreferences.edit().putString(Constants.KEY_FIELD_USERNAME, username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString(Constants.KEY_FIELD_USERNAME, "");
    }

    public void savePhoneNumber(String phoneNumber) {
        sharedPreferences.edit().putString(Constants.KEY_FIELD_PHONE_NUMBER, phoneNumber).apply();
    }

    public String getPhoneNumber() {
        return sharedPreferences.getString(Constants.KEY_FIELD_PHONE_NUMBER, "");
    }


}
