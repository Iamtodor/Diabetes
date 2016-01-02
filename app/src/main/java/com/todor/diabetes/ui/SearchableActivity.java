package com.todor.diabetes.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by todor on 02.01.16
 */
public class SearchableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            doMySearch(getIntent().getStringExtra(SearchManager.QUERY));
        }
    }

    public void doMySearch(String query) {

    }
}
