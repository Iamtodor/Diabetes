package com.todor.diabetes.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Utils {

    public static final String MyPREFERENCES = "com.todor.diabetes";

    public static SharedPreferences mySharedPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean("isFirstLaunch", true).apply();
        return preferences;
    }

}
