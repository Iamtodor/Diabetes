package com.todor.diabetes.ui.product_favorite;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.ui.product_table.OnProductLongClickListener;

import java.util.List;

import butterknife.Bind;

public class FavoriteFragment extends BaseFragment {

    @Bind(R.id.recyclerView) protected  RecyclerView           recyclerView;
    @Bind(R.id.linear_layout) protected LinearLayout           linearLayout;
    private                             ProductFavoriteAdapter productAdapter;
    private                             List<Product>          mProductList;
    private                             ProductFunctionality   dbManager;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_product_table;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getProductList();
        if (isProductAvailable(mProductList)) {
            setupProductAdapter(mProductList);
            setupRecyclerView();
        } else {
            toast(R.string.toast_for_empty_products);
        }
    }

    private void getProductList() {
        dbManager = new ProductFunctionality(getActivity());
        mProductList = dbManager.getFavoriteProducts();
    }

    private boolean isProductAvailable(List products) {
        return products != null && products.size() != 0;
    }

    private void setupProductAdapter(List<Product> products) {
        productAdapter = new ProductFavoriteAdapter(products, getActivity(), new OnProductLongClickListener() {
            @Override
            public void onItemLongClick(final int position, final TableProduct product) {
                Snackbar.make(linearLayout, R.string.deletion_product, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                productAdapter.addItem(position, product);
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
}
