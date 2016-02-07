package com.todor.diabetes.ui.product_table;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductTableFragment extends BaseFragment {

    @Bind(R.id.recyclerView) RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_table, container, false);
        ButterKnife.bind(this, v);

        ArrayList<TableProduct> products = getArguments().getParcelableArrayList(Constants.PRODUCT_FOR_TABLE);
        if (products != null && products.size() != 0) {
            ProductTableAdapter productAdapter = new ProductTableAdapter(products, getActivity());
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

            recyclerView.setItemAnimator(itemAnimator);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);

            recyclerView.setAdapter(productAdapter);
        } else {
            Toast.makeText(getActivity(), R.string.toast_for_empty_products, Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_table);
    }

}
