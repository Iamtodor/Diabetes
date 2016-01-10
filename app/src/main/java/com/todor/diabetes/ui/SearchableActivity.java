package com.todor.diabetes.ui;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;

public class SearchableActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            doMySearch(getIntent().getStringExtra(SearchManager.QUERY));
        }
    }

    public void doMySearch(String query) {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
//        getLoaderManager().restartLoader(Constants.PRODUCT_LIST_LOADER, null, ProductListFragment.class);
        return true;
    }
}
