package com.todor.diabetes.ui.product_details;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.ui.MainActivity;

public class ProductDetailsFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.product_layout, container, false);

        android.support.v7.app.ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
        Product product = (Product) getArguments().getSerializable(Constants.PRODUCT_KEY);

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_details);
    }

    @Override
    public void onStop() {
        super.onStop();
        DbHelperSingleton.closeDb();
    }
}
