package com.todor.diabetes.ui.product_list;

import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.DbHelperSingleton;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.listeners.OnItemClickListener;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.ui.product_details.ProductDetailsFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListFragment extends BaseFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Product>> {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ProductFunctionality dbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.product_layout, container, false);
        ButterKnife.bind(this, v);
        dbManager = new ProductFunctionality(getActivity());

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        getActivity().getLoaderManager().initLoader(0, null, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);

        return v;
    }

    @Override
    public String getFragmentTitle() {
        return getResources().getString(R.string.title_products);
    }

    @Override
    public Loader<ArrayList<Product>> onCreateLoader(int id, Bundle args) {
        return new ProductLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Product>> loader, ArrayList<Product> data) {
        recyclerView.setAdapter(new ProductAdapter(data, new OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                FragmentManager manager = getFragmentManager();
                ProductDetailsFragment detailsFragment = new ProductDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.PRODUCT_KEY, product);
                detailsFragment.setArguments(bundle);
                manager.beginTransaction()
                        .replace(R.id.flContent, detailsFragment)
                        .commit();
            }
        }));
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Product>> loader) {
        recyclerView.setAdapter(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        DbHelperSingleton.closeDb();
    }
}
