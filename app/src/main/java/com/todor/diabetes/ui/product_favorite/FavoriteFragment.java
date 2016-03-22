package com.todor.diabetes.ui.product_favorite;

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

import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.ui.product_table.OnProductLongClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FavoriteFragment extends BaseFragment {

    @Bind(R.id.recyclerView) protected  RecyclerView           recyclerView;
    @Bind(R.id.linear_layout) protected LinearLayout           linearLayout;
    private                             ProductFavoriteAdapter productAdapter;
    private                             List<Product>          mProductList;
    private                             ProductFunctionality   dbManager;

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_favorite_fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_table, container, false);
        ButterKnife.bind(this, v);

        getProductList();
        if (isProductAvailable(mProductList)) {
            setupProductAdapter(mProductList);
            setupRecyclerView();
        } else {
            Toast.makeText(getActivity(), R.string.toast_for_empty_products, Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
