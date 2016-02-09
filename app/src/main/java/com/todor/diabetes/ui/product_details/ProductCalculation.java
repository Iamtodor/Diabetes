package com.todor.diabetes.ui.product_details;

import android.content.Context;

import com.todor.diabetes.utils.Utils;

public class ProductCalculation {

    public static boolean isProductHasCarbohydrates(float carbohydrates) {
        return carbohydrates > 0;
    }

    public static int parseStringToInt(String value) throws NumberFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    public static float calculateBreadUnits(float carbohydrates, Context context, String enterString) throws NumberFormatException {
        if (isProductHasCarbohydrates(carbohydrates)) {
            int value = parseStringToInt(enterString);
            return value * (carbohydrates / 100) / Utils.getGlycemicIndex(context);
        } else {
            return 0;
        }
    }

    public static float calculateGram(float carbohydrates, Context context, String enterString) throws NumberFormatException {
        if (isProductHasCarbohydrates(carbohydrates)) {
            int value = parseStringToInt(enterString);
            return value * Utils.getGlycemicIndex(context) / (carbohydrates / 100);
        } else {
            return 0;
        }
    }
}
