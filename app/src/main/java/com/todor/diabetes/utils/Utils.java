package com.todor.diabetes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Utils {

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setFirstLaunch(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(Constants.IS_FIRST_LAUNCH_KEY, true).commit();
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(Constants.IS_FIRST_LAUNCH_KEY, true);
    }

}
