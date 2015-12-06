package com.todor.diabetes.models;

public class Product {

    public String name;
    public float carbohydrates;
//    public int glycemicIndex;
    public String group;

    public Product(String productName, float productCarbohydrates,
                   String productGroup) {
        this.carbohydrates = productCarbohydrates;
//        this.glycemicIndex = productGlycemicIndex;
        this.group = productGroup;
        this.name = productName;
    }

    @Override
    public String toString() {
        return name + " " + group +
                " " + carbohydrates;
    }
}
