package com.todor.diabetes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.inputmethod.InputMethodManager;

import com.todor.diabetes.Constants;
import com.todor.diabetes.ProductsArray;
import com.todor.diabetes.db.general.ProductFunctionality;
import com.todor.diabetes.models.Product;

public class Utils {

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public static void setLaunchToFalse(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(Constants.IS_FIRST_LAUNCH_KEY, false).commit();
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(Constants.IS_FIRST_LAUNCH_KEY, true);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void writeDataIntoDataBase(Context context) {
        String[][] products = ProductsArray.getProductArray();
        for (int i = 0; i < products.length; i++) {
            Product product = new Product(
                    products[i][0],
                    Float.parseFloat(products[i][1]),
                    products[i][2]);
            new ProductFunctionality(context).insertProduct(product);
        }
    }

}
