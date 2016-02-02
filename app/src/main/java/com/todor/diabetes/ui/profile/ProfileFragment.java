package com.todor.diabetes.ui.profile;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.todor.diabetes.R;

public class ProfileFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.preference_screen, false);
        addPreferencesFromResource(R.xml.preference_screen);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_profile);
    }
}
