package com.woleapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.woleapp.R;

public class SessionManager {
    static Context context;
    private static SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);

    private static String USER_TOKEN = "user_token";

    public void saveAuthToken(String token){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }
    public String getAuthToken(String token){
        return prefs.getString(USER_TOKEN, null);
    }
}
