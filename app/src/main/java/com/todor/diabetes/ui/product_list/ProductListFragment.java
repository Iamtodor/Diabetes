package com.todor.diabetes.ui.product_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.diabetes.R;

public class ProductListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.product_layout, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Product list");
        return v;
    }

}
