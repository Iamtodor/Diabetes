package com.todor.diabetes.db.general;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.todor.diabetes.db.DbScheme;
import com.todor.diabetes.models.Product;

import java.util.ArrayList;
import java.util.List;

public class GeneralProductDbManager {

    private static final String TAG = GeneralProductDbManager.class.getSimpleName();
    private Context context;

    public GeneralProductDbManager(Context context) {
        this.context = context;
    }

    public void insertProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.getProductName());
        contentValues.put(DbScheme.PRODUCT_CARBOHYDRATES, product.getProductCarbohydrates());
        contentValues.put(DbScheme.PRODUCT_GLYCEMIC_INDEX, product.getProductGlycemicIndex());
        contentValues.put(DbScheme.PRODUCT_GROUP, product.getProductGroup());
        db.insert(DbScheme.TABLE_PRODUCTS, null, contentValues);
        Log.d(TAG, "Product was inserted");
        DbHelperSingleton.closeDb();
    }

    public Product getProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = db.query(DbScheme.TABLE_PRODUCTS, new String[]{},
                DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                null, null, null);
        Product product = null;
        if (cursor.moveToFirst()) {
            product = new Product(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_NAME)),
                    cursor.getFloat(cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES)),
                    cursor.getInt(cursor.getColumnIndex(DbScheme.PRODUCT_GLYCEMIC_INDEX)),
                    cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_GROUP)));
            DbHelperSingleton.closeDb();
        }
        DbHelperSingleton.closeDb();
        return product;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.getProductName());
        contentValues.put(DbScheme.PRODUCT_CARBOHYDRATES, product.getProductCarbohydrates());
        contentValues.put(DbScheme.PRODUCT_GLYCEMIC_INDEX, product.getProductGlycemicIndex());
        contentValues.put(DbScheme.PRODUCT_GROUP, product.getProductGroup());
        db.update(DbScheme.TABLE_PRODUCTS, contentValues, DbScheme.PRODUCT_NAME + "=?", new String[]{product.getProductName()});
        Log.d(TAG, "Product was updated");
        DbHelperSingleton.closeDb();
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        db.delete(DbScheme.TABLE_PRODUCTS, DbScheme.PRODUCT_NAME + "=?", new String[]{productName});
        Log.d(TAG, "Product was deleted");
        DbHelperSingleton.closeDb();
    }

    public boolean checkIfProductExists(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = db.query(DbScheme.TABLE_PRODUCTS, new String[]{},
                DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                null, null, null);
        Log.d(TAG, "Product was checked");
        cursor.close();
        boolean isExists = cursor.getCount() > 0;
        return isExists;
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = db.query(DbScheme.TABLE_PRODUCTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                productList.add(
                        new Product(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_NAME)),
                                cursor.getFloat(cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES)),
                                cursor.getInt(cursor.getColumnIndex(DbScheme.PRODUCT_GLYCEMIC_INDEX)),
                                cursor.getColumnName(cursor.getColumnIndex(DbScheme.PRODUCT_GROUP)))
                );
            } while (cursor.moveToNext());
        }
        cursor.close();
        DbHelperSingleton.closeDb();
        return productList;
    }
}
