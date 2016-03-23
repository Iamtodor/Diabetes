package com.todor.diabetes.ui.product_table;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;

public class ProductTableFragment extends BaseFragment {

    @Bind(R.id.recyclerView) protected  RecyclerView          recyclerView;
    @Bind(R.id.linear_layout) protected LinearLayout          linearLayout;
    private                             ProductTableAdapter   productAdapter;
    private                             HashSet<TableProduct> productHashSet;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProductList();
        if (isProductAvailable(productHashSet)) {
            setupProductAdapter(productHashSet);
            setupRecyclerView();
        } else {
            toast(R.string.toast_for_empty_products);
        }
    }

    private void getProductList() {
        if (getArguments() != null) {
            if (getArguments().getSerializable(Constants.PRODUCT_FOR_TABLE) != null) {
                productHashSet = (HashSet<TableProduct>) getArguments().getSerializable(Constants.PRODUCT_FOR_TABLE);
            }
        }
    }

    private boolean isProductAvailable(HashSet<TableProduct> products) {
        return products != null && products.size() != 0;
    }

    private void setupProductAdapter(HashSet<TableProduct> products) {
        ArrayList<TableProduct> productArrayList = new ArrayList<>(products);
        productAdapter = new ProductTableAdapter(productArrayList, getActivity(), new OnProductLongClickListener() {
            @Override
            public void onItemLongClick(final int position, final TableProduct product) {
                productAdapter.removeItem(position);
                Snackbar.make(linearLayout, R.string.deletion_product, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                productAdapter.addItem(position, product);
                            }
                        })
                        .show();
            }
        });
    }

    private void setupRecyclerView() {
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(productAdapter);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_product_table;
    }
}
