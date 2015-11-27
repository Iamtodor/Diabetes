package com.todor.diabetes.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.todor.diabetes.R;
import com.todor.diabetes.utils.DbHelper;
import com.todor.diabetes.utils.Utils;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences mPreferences = Utils.mySharedPreferences(this);
        if(mPreferences.getBoolean("isFirstLaunch", true)) {
            mPreferences.edit().putBoolean("isFirstLaunch", false).apply();
            
        }
    }
}
