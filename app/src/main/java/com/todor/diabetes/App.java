package com.todor.diabetes;


import android.app.Application;

import com.todor.diabetes.utils.PreferencesImpl;

public class App extends Application{

    @Override public void onCreate() {
        super.onCreate();

        PreferencesImpl.init(this);
    }
}
