package com.todor.diabetes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

class DbHelperSingleton {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;

    private DbHelperSingleton() {
    }

    static SQLiteDatabase getDb(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    static void closeDb() {
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }
}
