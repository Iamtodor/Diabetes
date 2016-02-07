package com.todor.diabetes.ui.product_table;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;

import butterknife.Bind;

public class ProductTableFragment extends BaseFragment {

    @Bind(R.id.tv_table) EditText tvTable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_table, container, false);

        Product product = getArguments().getParcelable(Constants.PRODUCT_FOR_TABLE);

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_table);
    }

}
