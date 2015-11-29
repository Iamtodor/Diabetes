package com.todor.diabetes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.inputmethod.InputMethodManager;

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

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
