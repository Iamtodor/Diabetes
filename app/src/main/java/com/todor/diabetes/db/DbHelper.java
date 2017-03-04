package com.todor.diabetes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "productsManager";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCT_TABLE = "create table " + DbScheme.PRODUCT_TABLE + " (" +
                DbScheme.PRODUCT_ID + " integer primary key autoincrement, " +
                DbScheme.PRODUCT_NAME + " text, " +
                DbScheme.PRODUCT_CARBOHYDRATES + " float, " +
                DbScheme.PRODUCT_GROUP + " text, " +
                DbScheme.PRODUCT_IS_FAVORITE + " text)";
        db.execSQL(CREATE_PRODUCT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DATABASE_NAME);
        onCreate(db);
    }

}
