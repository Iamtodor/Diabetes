package com.todor.diabetes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.inputmethod.InputMethodManager;

import com.todor.diabetes.Constants;
import com.todor.diabetes.ProductsArray;
import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;

public class Utils {

    public static SharedPreferences getSharedPreferences(Context context) {
//        return context.getSharedPreferences(Constants.MY_PREFERENCES, Context.MODE_PRIVATE);
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setLaunchToFalse(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putBoolean(Constants.IS_FIRST_LAUNCH_KEY, false).apply();
    }

    public static void setGlycemicIndex(Context context, float glycemicIndex) {
        SharedPreferences preferences = getSharedPreferences(context);
        Editor editor = preferences.edit();
        editor.putFloat(context.getResources().getString(R.string.preference_bread_unit_key), glycemicIndex);
        editor.apply();
    }

    public static float getBreadUnitsCount(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        String value = preferences.getString(context.getResources().getString(R.string.preference_bread_unit_key), "4");
        return Float.parseFloat(value);
    }

    public static float getCarbohydratesCount(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        String value = preferences.getString(context.getResources().getString(R.string.preference_carbohydrates_key), "12");
        return Float.parseFloat(value);
    }

    public static float getInsulinFactor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        String value = preferences.getString(context.getResources().getString(R.string.preference_insulin_factor_key), "2");
        return Float.parseFloat(value);
    }

    public static boolean isFirstLaunch(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getBoolean(Constants.IS_FIRST_LAUNCH_KEY, true);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void writeDataIntoDataBase(Context context) {
        String[][] products = ProductsArray.getProductArray();
        for (String[] product : products) {
            Product currentProduct = new Product(
                    product[0], /* name */
                    Float.parseFloat(product[1]), /* carbohydrates */
                    product[2], /* group */
                    false); /* favorite" */
            new ProductFunctionality(context).insertProduct(currentProduct);
        }
    }

}
