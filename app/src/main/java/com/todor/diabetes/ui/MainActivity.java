package com.todor.diabetes.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.product_details.OnTableProductListener;
import com.todor.diabetes.ui.product_favorite.FavoriteFragment;
import com.todor.diabetes.ui.product_list.ProductListFragment;
import com.todor.diabetes.ui.product_table.ProductTableFragment;
import com.todor.diabetes.ui.profile.ProfileFragment;
import com.todor.diabetes.utils.PreferencesImpl;

import java.util.HashSet;

import butterknife.BindView;

public class MainActivity extends BaseActivity
        implements OnTableProductListener, OnTabSelectListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.bottom_bar) protected BottomBar bottomBar;

    private HashSet<TableProduct> productForTable = new HashSet<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        bottomBar.setOnTabSelectListener(this);

        if (PreferencesImpl.get().isFirstLaunch()) {
            PreferencesImpl.get().writeDataIntoDataBase();
            PreferencesImpl.get().setLaunchToFalse();
        }

        if (savedInstanceState == null) {
            initProductListFragment();
        }
    }

    private void initProductListFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ProductListFragment());
        transaction.commit();
        getSupportActionBar().setTitle(R.string.title_products);
    }

    @Override
    public void setProduct(TableProduct product) {
        if (productForTable.contains(product)) {
            productForTable.remove(product);
        }
        productForTable.add(product);
    }

    @Override public void onTabSelected(@IdRes int tabId) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ActionBar supportActionBar = getSupportActionBar();

        switch (tabId) {
            case R.id.products:
                transaction.replace(R.id.fragment_container, new ProductListFragment());
                supportActionBar.setTitle(R.string.title_products);
                break;
            case R.id.table:
                ProductTableFragment productTableFragment;
                if (!productForTable.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putSerializable(Constants.PRODUCT_FOR_TABLE, productForTable);
                    productTableFragment = new ProductTableFragment();
                    productTableFragment.setArguments(args);
                } else {
                    productTableFragment = new ProductTableFragment();
                }
                transaction.replace(R.id.fragment_container, productTableFragment);
                supportActionBar.setTitle(R.string.title_products);
                break;
            case R.id.favorite:
                transaction.replace(R.id.fragment_container, new FavoriteFragment());
                supportActionBar.setTitle(R.string.title_favorite_fragment);
                break;
            case R.id.profile:
                transaction.replace(R.id.fragment_container, new ProfileFragment());
                supportActionBar.setTitle(R.string.title_profile);
                break;
        }
        transaction.commit();
    }
}

