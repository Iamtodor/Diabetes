package com.todor.diabetes.ui;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;

import java.util.ArrayList;

public class CursorLoader extends AsyncTaskLoader<ArrayList<Product>> {

    private ArrayList<Product> products;
    private ProductFunctionality productFunctionality;

    public CursorLoader(Context context) {
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
        if(products != null) {
            deliverResult(products);
        } else {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(ArrayList<Product> data) {
        super.deliverResult(data);
    }
}
