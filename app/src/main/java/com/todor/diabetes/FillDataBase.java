package com.todor.diabetes;

import android.content.Context;

import com.todor.diabetes.db.general.GeneralProductDbManager;
import com.todor.diabetes.models.Product;

public class FillDataBase {

    public static void writeDataIntoDataBase(Context context) {
        GeneralProductDbManager dbManager = new GeneralProductDbManager(context);
        String[][] products = ProductsArray.getProductArray();
        for (int i = 0; i < products.length; i++) {
            Product product = new Product(products[i][0], Float.parseFloat(products[i][1]),
                    products[i][2]);
            dbManager.insertProduct(product);
        }
    }
}
