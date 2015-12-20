package com.todor.diabetes.ui.product_table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.diabetes.R;
import com.todor.diabetes.ui.BaseFragment;

import java.util.zip.Inflater;

/**
 * Created by todor on 20.12.15
 */
public class ProductTableFragment extends BaseFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.product_table_fragment, container, false);

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_table);
    }
}
