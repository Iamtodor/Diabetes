package com.todor.diabetes.ui.product_table;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductTableFragment extends BaseFragment {

    @Bind(R.id.recyclerView) protected  RecyclerView          recyclerView;
    @Bind(R.id.linear_layout) protected LinearLayout          linearLayout;
    private                             ProductTableAdapter   productAdapter;
    private                             HashSet<TableProduct> productHashSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_table, container, false);
        ButterKnife.bind(this, v);

        getProductList();
        if (checkIsProductAvailable(productHashSet)) {
            setupProductAdapter(productHashSet);
            setupRecyclerView();
        } else {
            Toast.makeText(getActivity(), R.string.toast_for_empty_products, Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    private void getProductList() {
        if (getArguments() != null) {
            if (getArguments().getSerializable(Constants.PRODUCT_FOR_TABLE) != null) {
                productHashSet = (HashSet<TableProduct>) getArguments().getSerializable(Constants.PRODUCT_FOR_TABLE);
            }
        }
    }

    private boolean checkIsProductAvailable(HashSet<TableProduct> products) {
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
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_product_table);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
