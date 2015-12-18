package com.todor.diabetes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.todor.diabetes.models.Product;
import com.todor.diabetes.utils.CursorUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductFunctionality {

    private static final String TAG = ProductFunctionality.class.getSimpleName();
    private Context context;

    public ProductFunctionality(Context context) {
        this.context = context;
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.name);
        contentValues.put(DbScheme.PRODUCT_CARBOHYDRATES, product.carbohydrates);
        contentValues.put(DbScheme.PRODUCT_GROUP, product.group);
        db.insert(DbScheme.PRODUCT_TABLE, null, contentValues);
    }

    public Product getProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = null;
        Product product = null;
        try {
            cursor = db.query(DbScheme.PRODUCT_TABLE, new String[]{},
                    DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                    null, null, null);
            if (cursor.moveToFirst()) {
                product = new Product(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_NAME)),
                        cursor.getFloat(cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES)),
                        cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_GROUP)));
            }
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return product;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.name);
        contentValues.put(DbScheme.PRODUCT_CARBOHYDRATES, product.carbohydrates);
        contentValues.put(DbScheme.PRODUCT_GROUP, product.group);
        db.update(DbScheme.PRODUCT_TABLE, contentValues, DbScheme.PRODUCT_NAME + "=?", new String[]{product.name});
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        db.delete(DbScheme.PRODUCT_TABLE, DbScheme.PRODUCT_NAME + "=?", new String[]{productName});
    }

    public boolean checkIfProductExists(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = null;
        try {
            cursor = db.query(DbScheme.PRODUCT_TABLE, new String[]{},
                    DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                    null, null, null);
            return cursor.getCount() > 0;
        } finally {
            CursorUtils.closeCursor(cursor);
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = null;
        try {
            cursor = db.query(DbScheme.PRODUCT_TABLE, null, null, null, null, null, null);
            int nameColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_NAME);
            int carbohydratesColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES);
            int groupColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_GROUP);

            while (cursor.moveToNext()) {
                productList.add(
                        new Product(cursor.getString(nameColumnIndex),
                                cursor.getFloat(carbohydratesColumnIndex),
                                cursor.getString(groupColumnIndex))
                );
            }
        } finally {
            CursorUtils.closeCursor(cursor);
        }
        return productList;
    }
}
