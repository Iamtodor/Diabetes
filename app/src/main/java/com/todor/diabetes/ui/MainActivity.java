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
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.product_details.OnTableProductListener;
import com.todor.diabetes.ui.product_list.ProductListFragment;
import com.todor.diabetes.ui.product_table.ProductTableFragment;
import com.todor.diabetes.ui.profile.ProfileFragment;
import com.todor.diabetes.utils.Utils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTableProductListener {

    private Product productForTable;

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
//            Utils.setGlycemicIndex(this, Constants.DEFAULT_GLYCEMICAL_INDEX_VALUE);
        }

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, new ProductListFragment());
            transaction.commit();
            navigationView.setCheckedItem(R.id.productList);
        }
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
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();
        switch (id) {
            case R.id.productList:
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, new ProductListFragment())
                        .commit();
                break;
            case R.id.currentEating:
                ProductTableFragment productTableFragment;
                if (productForTable != null) {
                    Bundle args = new Bundle();
                    args.putParcelable(Constants.PRODUCT_FOR_TABLE, productForTable);
                    productTableFragment = new ProductTableFragment();
                    productTableFragment.setArguments(args);
                } else {
                    productTableFragment = new ProductTableFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, productTableFragment)
                        .commit();
                break;
            case R.id.profile:
                fragmentManager.beginTransaction()
                        .replace(R.id.flContent, new ProfileFragment())
                        .commit();
        }

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
    public void setProduct(Product product) {
        productForTable = product;
    }
}

