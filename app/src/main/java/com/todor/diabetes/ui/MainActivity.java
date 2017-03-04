package com.todor.diabetes.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
        implements NavigationView.OnNavigationItemSelectedListener, OnTableProductListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.drawer_layout) protected DrawerLayout drawer;
    private HashSet<TableProduct> productForTable = new HashSet<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (PreferencesImpl.get().isFirstLaunch()) {
            PreferencesImpl.get().writeDataIntoDataBase();
            PreferencesImpl.get().setLaunchToFalse();
        }

        if (savedInstanceState == null) {
            initProductListFragment(navigationView);
        }
    }

    private void initProductListFragment(NavigationView navigationView) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.flContent, new ProductListFragment());
        transaction.commit();
        navigationView.setCheckedItem(R.id.productList);
        getSupportActionBar().setTitle(R.string.title_products);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        ActionBar supportActionBar = getSupportActionBar();

        switch (id) {
            case R.id.productList:
                transaction.replace(R.id.flContent, new ProductListFragment());
                supportActionBar.setTitle(R.string.title_products);
                break;
            case R.id.currentEating:
                ProductTableFragment productTableFragment;
                if (!productForTable.isEmpty()) {
                    Bundle args = new Bundle();
                    args.putSerializable(Constants.PRODUCT_FOR_TABLE, productForTable);
                    productTableFragment = new ProductTableFragment();
                    productTableFragment.setArguments(args);
                } else {
                    productTableFragment = new ProductTableFragment();
                }
                transaction.replace(R.id.flContent, productTableFragment);
                supportActionBar.setTitle(R.string.title_products);
                break;
            case R.id.favoriteProducts:
                transaction.replace(R.id.flContent, new FavoriteFragment());
                supportActionBar.setTitle(R.string.title_favorite_fragment);
                break;
            case R.id.profile:
                transaction.replace(R.id.flContent, new ProfileFragment());
                supportActionBar.setTitle(R.string.title_profile);
                break;
        }
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void setProduct(TableProduct product) {
        if (productForTable.contains(product)) {
            productForTable.remove(product);
        }
        productForTable.add(product);
    }
}

