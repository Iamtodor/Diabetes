package com.todor.diabetes.ui.product_list;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.todor.diabetes.Constants;
import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;
import com.todor.diabetes.models.TableProduct;
import com.todor.diabetes.ui.product_add.AddProductActivity;
import com.todor.diabetes.ui.BaseFragment;
import com.todor.diabetes.ui.product_details.OnTableProductListener;
import com.todor.diabetes.ui.product_details.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListFragment extends BaseFragment implements
        LoaderManager.LoaderCallbacks<ArrayList<Product>>,
        SearchView.OnQueryTextListener {

    @Bind(R.id.recyclerView) RecyclerView         recyclerView;
    private                  ProductFunctionality dbManager;
    private List<Product>      productList    = null;
    private ProductListAdapter productAdapter = null;
    private FloatingActionButton   fab;
    private TableProduct           productForTable;
    private OnTableProductListener onTableProductListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        onTableProductListener = (OnTableProductListener) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_product_list, container, false);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
            }
        });

        ButterKnife.bind(this, v);
        setHasOptionsMenu(true);
        dbManager = new ProductFunctionality(getActivity());

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

        getActivity().getLoaderManager().initLoader(Constants.PRODUCT_LIST_LOADER, null, this);

//        recyclerView.addItemDecoration(new ItemDecorator(getActivity(), R.drawable.divider));
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(productAdapter);
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
        productList = data;
        if (productList != null && productList.size() != 0) {
            productAdapter = new ProductListAdapter(data, new OnProductListItemClickListener() {
                @Override
                public void onProductClick(Product product) {
                    Intent intent = new Intent(getActivity(), ProductDetailsActivity.class);
                    intent.putExtra(Constants.PRODUCT_KEY, product);
                    startActivityForResult(intent, Constants.REQUEST_CODE_FOR_TABLE);
                }
            });

            recyclerView.setAdapter(productAdapter);
        } else {
            Toast.makeText(getActivity(), R.string.toast_for_empty_products, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Product>> loader) {
        recyclerView.setAdapter(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    private List<Product> filter(List<Product> models, String query) {
        query = query.toLowerCase();

        final List<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {
            final String text = model.name.toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        final List<Product> filteredModelList = filter(productList, query);
        productAdapter.animateTo(filteredModelList);
        recyclerView.scrollToPosition(0);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE_FOR_TABLE) {
                productForTable = data.getParcelableExtra(Constants.PRODUCT_FOR_TABLE);
                onTableProductListener.setProduct(productForTable);
            }
        }
    }
}
