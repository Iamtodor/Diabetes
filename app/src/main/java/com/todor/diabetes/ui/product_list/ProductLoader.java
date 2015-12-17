package com.todor.diabetes.ui.product_list;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;

import java.util.ArrayList;

public class ProductLoader extends AsyncTaskLoader<ArrayList<Product>> {

    private ArrayList<Product>   products;
    private ProductFunctionality productFunctionality;

    public ProductLoader(Context context) {
        super(context);
        productFunctionality = new ProductFunctionality(context);
    }

    @Override
    public ArrayList<Product> loadInBackground() {
        products = (ArrayList<Product>) productFunctionality.getAllProducts();
        return products;
    }

    @Override
    protected void onStartLoading() {
        if (products != null) {
            deliverResult(products);
        }
        if (takeContentChanged() || products == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Product> data) {
        if (isReset()) {
            products = null;
            return;
        }

        products = data;
        if (isStarted()) {
            super.deliverResult(products);
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        products = null;
    }

    @Override
    protected boolean onCancelLoad() {
        products = null;
        return super.onCancelLoad();
    }
}
