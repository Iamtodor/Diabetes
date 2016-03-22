package com.todor.diabetes.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.product_details.OnTableProductListener;
import com.todor.diabetes.ui.product_favorite.FavoriteFragment;
import com.todor.diabetes.ui.product_list.ProductListFragment;
import com.todor.diabetes.ui.product_table.ProductTableFragment;
import com.todor.diabetes.ui.profile.ProfileFragment;
import com.todor.diabetes.utils.Utils;

import java.util.HashSet;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTableProductListener {

    private final String                TAG             = MainActivity.class.getSimpleName();
    private       HashSet<TableProduct> productForTable = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Utils.isFirstLaunch(this)) {
            Utils.writeDataIntoDataBase(this);
            Utils.setLaunchToFalse(this);
        }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, new ProductListFragment());
            transaction.commit();
            navigationView.setCheckedItem(R.id.productList);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.productList:
                transaction.replace(R.id.flContent, new ProductListFragment());
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
                break;
            case R.id.favoriteProducts:
                transaction.replace(R.id.flContent, new FavoriteFragment());
                break;
            case R.id.profile:
                transaction.replace(R.id.flContent, new ProfileFragment());
                break;
        }
        transaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Utils.hideSoftKeyboard(this);
        return super.onTouchEvent(event);
    }

    @Override
    public void setProduct(TableProduct product) {
        if (productForTable.contains(product)) {
            productForTable.remove(product);
        }
        productForTable.add(product);
    }
}

