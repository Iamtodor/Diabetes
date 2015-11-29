package com.todor.diabetes.db.general;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.todor.diabetes.db.DbScheme;
import com.todor.diabetes.db.ProductDbManager;
import com.todor.diabetes.models.Product;

public class GeneralProductDbManager implements ProductDbManager {

    private Context context;

    public GeneralProductDbManager(Context context) {
        this.context = context;
    }

    @Override
    public void insertProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.getProductName());
        contentValues.put(DbScheme.PRODUCT_VALUE, product.getProductValue());
        db.insert(DbScheme.TABLE_PRODUCTS, null, contentValues);
        DbHelperSingleton.closeDb();
    }

    @Override
    public Product getProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Cursor cursor = db.query(DbScheme.TABLE_PRODUCTS, new String[]{},
                DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                null, null, null);
        Product product = null;
        if (cursor != null) {
            cursor.moveToFirst();
            product = new Product(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_NAME)),
                    cursor.getInt(cursor.getColumnIndex(DbScheme.PRODUCT_VALUE)));
            DbHelperSingleton.closeDb();
        }
        DbHelperSingleton.closeDb();
        return product;
    }

    @Override
    public void updateProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.getProductName());
        contentValues.put(DbScheme.PRODUCT_VALUE, product.getProductValue());
        db.update(DbScheme.TABLE_PRODUCTS, contentValues, DbScheme.PRODUCT_NAME + "=?", new String[]{product.getProductName()});
        DbHelperSingleton.closeDb();
    }

    @Override
    public void deleteProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        db.delete(DbScheme.TABLE_PRODUCTS, DbScheme.PRODUCT_NAME + "=?", new String[]{productName});
        DbHelperSingleton.closeDb();
    }


}
