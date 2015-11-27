package com.todor.diabetes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.todor.diabetes.model.Product;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "productsManager";
    public static final String TABLE_PRODUCTS = "products";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_VALUE = "product_value";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "create table " + TABLE_PRODUCTS + " (" + PRODUCT_NAME +
                " text primary key, " + PRODUCT_VALUE + " integer)";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);

        onCreate(db);
    }

    public boolean insertProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, product.getProductName());
        contentValues.put(PRODUCT_VALUE, product.getProductValue());
        return (db.insert(TABLE_PRODUCTS, null, contentValues) != -1);
    }

    public Product getProduct(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[] {}, PRODUCT_NAME + "=?", new String[] {name},
                null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        return new Product(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)),
                cursor.getInt(cursor.getColumnIndex(PRODUCT_VALUE)));
    }

    public void updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PRODUCT_NAME, product.getProductName());
        contentValues.put(PRODUCT_VALUE, product.getProductValue());
        db.update(TABLE_PRODUCTS, contentValues, PRODUCT_NAME + "=?", new String[]{product.getProductName()});
    }

    public void deleteProduct(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, PRODUCT_NAME + "=?" , new String[]{productName});
    }
}
