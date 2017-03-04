package com.todor.diabetes.utils;


import android.content.SharedPreferences;

public interface Preferences {

    SharedPreferences getSharedPreferences();
    void setLaunchToFalse();
    float getCarbohydratesCount();
    boolean isFirstLaunch();
    void writeDataIntoDataBase();

}
