package com.todor.diabetes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.todor.diabetes.models.Product;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductFunctionality {

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
        contentValues.put(DbScheme.PRODUCT_IS_FAVORITE, product.isFavorite);
        db.insert(DbScheme.PRODUCT_TABLE, null, contentValues);
        DbHelperSingleton.closeDb();
    }

    public Product getProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        Product product = null;
        try (Cursor cursor = db.query(DbScheme.PRODUCT_TABLE, new String[]{},
                DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                null, null, null)) {
            if (cursor.moveToFirst()) {
                product = new Product(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_NAME)),
                        cursor.getFloat(cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES)),
                        cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_GROUP)),
                        Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbScheme.PRODUCT_IS_FAVORITE))));
            }
        }
        DbHelperSingleton.closeDb();
        return product;
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbScheme.PRODUCT_NAME, product.name);
        contentValues.put(DbScheme.PRODUCT_CARBOHYDRATES, product.carbohydrates);
        contentValues.put(DbScheme.PRODUCT_GROUP, product.group);
        contentValues.put(DbScheme.PRODUCT_IS_FAVORITE, product.isFavorite);
        db.update(DbScheme.PRODUCT_TABLE, contentValues, DbScheme.PRODUCT_NAME + "=?", new String[]{product.name});
        DbHelperSingleton.closeDb();
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        db.delete(DbScheme.PRODUCT_TABLE, DbScheme.PRODUCT_NAME + "=?", new String[]{productName});
        DbHelperSingleton.closeDb();
    }

    public boolean checkIfProductExists(String productName) {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        try (Cursor cursor = db.query(DbScheme.PRODUCT_TABLE, new String[]{},
                DbScheme.PRODUCT_NAME + "=?", new String[]{productName},
                null, null, null)) {
            return cursor.getCount() > 0;
        } finally {
            DbHelperSingleton.closeDb();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        try (Cursor cursor = db.query(DbScheme.PRODUCT_TABLE, null, null, null, null, null, null)) {
            int nameColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_NAME);
            int carbohydratesColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_CARBOHYDRATES);
            int groupColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_GROUP);
            int favoriteColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_IS_FAVORITE);

            while (cursor.moveToNext()) {
                productList.add(
                        new Product(cursor.getString(nameColumnIndex),
                                cursor.getFloat(carbohydratesColumnIndex),
                                cursor.getString(groupColumnIndex),
                                Boolean.parseBoolean(cursor.getString(favoriteColumnIndex)))
                );
            }
        }
        DbHelperSingleton.closeDb();
        return productList;
    }

    public Set<String> getGroupProducts() {
        Set<String> productGroupList = new HashSet<>();
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        try (Cursor cursor = db.query(DbScheme.PRODUCT_TABLE, null, null, null, null, null, null)) {
            int groupColumnIndex = cursor.getColumnIndex(DbScheme.PRODUCT_GROUP);

            while (cursor.moveToNext()) {
                productGroupList.add(cursor.getString(groupColumnIndex));
            }
        }

        DbHelperSingleton.closeDb();

        return productGroupList;
    }

    public int getProductsCount() {
        SQLiteDatabase db = DbHelperSingleton.getDb(context);
        int size;
        try (Cursor cursor = db.query(DbScheme.PRODUCT_TABLE, null, null, null, null, null, null);) {
            size = cursor.getCount();
        }
        DbHelperSingleton.closeDb();
        return size;
    }
}
