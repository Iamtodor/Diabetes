package com.todor.diabetes.utils;

import android.database.Cursor;

/**
 * Created by todor on 17.12.15
 */
public class CursorUtils {

    public static void closeCursor(Cursor cursor) {
        if(cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

}
