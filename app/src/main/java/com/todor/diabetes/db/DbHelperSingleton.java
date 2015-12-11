package com.todor.diabetes.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbHelperSingleton {

    private static DbHelper dbHelper;
    private static SQLiteDatabase db;

    public static SQLiteDatabase getDb(Context context) {
        if (dbHelper == null) {
            dbHelper = new DbHelper(context);
            db = dbHelper.getWritableDatabase();
        }
        return db;
    }

    public static void closeDb() {
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }

}
