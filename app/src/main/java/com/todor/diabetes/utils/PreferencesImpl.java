package com.todor.diabetes.utils;


import android.content.Context;
import android.content.SharedPreferences;

import com.todor.diabetes.Constants;
import com.todor.diabetes.ProductsArray;
import com.todor.diabetes.R;
import com.todor.diabetes.db.ProductFunctionality;
import com.todor.diabetes.models.Product;

public class PreferencesImpl implements Preferences {

    private static final String MY_PREFERENCES = "com.todor.diabetes";
    private static final String IS_FIRST_LAUNCH_KEY = "isFirstLaunch";

    private Context context;
    private static Preferences instance;

    private PreferencesImpl(Context context) {
        this.context = context;
    }

    private PreferencesImpl() {
    }

    public static void init(Context context) {
        instance = new PreferencesImpl(context);
    }

    public static Preferences get() {
        if (instance == null) {
            throw new IllegalStateException(PreferencesImpl.class.getSimpleName() +
                    " is not initialized, call init(..) method first.");
        }
        return instance;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public void setLaunchToFalse() {
        SharedPreferences preferences = getSharedPreferences();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_FIRST_LAUNCH_KEY, false).apply();
    }

    @Override
    public float getCarbohydratesCount() {
        SharedPreferences preferences = getSharedPreferences();
        String value = preferences.getString(context.getResources().getString(R.string.preference_carbohydrates_key), "12");
        return Float.parseFloat(value);
    }

    @Override
    public boolean isFirstLaunch() {
        SharedPreferences preferences = getSharedPreferences();
        return preferences.getBoolean(IS_FIRST_LAUNCH_KEY, true);
    }

    @Override
    public void writeDataIntoDataBase() {
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
