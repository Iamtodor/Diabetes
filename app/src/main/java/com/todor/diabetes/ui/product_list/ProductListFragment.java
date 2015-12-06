package com.todor.diabetes.ui.product_list;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.diabetes.R;
import com.todor.diabetes.db.general.GeneralProductDbManager;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.adapters.RecyclerViewAdapter;

import java.util.List;

public class ProductListFragment extends Fragment {

    private GeneralProductDbManager dbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.product_layout, container, false);
        dbManager = new GeneralProductDbManager(getActivity());
        List<Product> productList = dbManager.getAllProducts();

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        RecyclerViewAdapter viewAdapter = new RecyclerViewAdapter(productList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Product list");

        recyclerView.setAdapter(viewAdapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        return v;

    }

}
