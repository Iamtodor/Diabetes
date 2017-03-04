package com.todor.diabetes.ui.product_details;

class ProductCalculation {

    private static boolean isProductHasCarbohydrates(float carbohydrates) {
        return carbohydrates > 0;
    }

    static int parseStringToInt(String value) throws NumberFormatException {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException();
        }
    }

    static float calculateBreadUnits(float carbohydratesInProduct,
                                     float carbohydratesCount, String enterString) throws NumberFormatException {
        if (isProductHasCarbohydrates(carbohydratesInProduct)) {
            int value = parseStringToInt(enterString);
            return value * (carbohydratesInProduct / 100) / carbohydratesCount;
        } else {
            return 0;
        }
    }

    static float calculateGram(float carbohydratesInProduct,
                               float carbohydratesCount, String enterString) throws NumberFormatException {
        if (isProductHasCarbohydrates(carbohydratesInProduct)) {
            int value = parseStringToInt(enterString);
            return value * carbohydratesCount / (carbohydratesInProduct / 100);
        } else {
            return 0;
        }
    }
}
