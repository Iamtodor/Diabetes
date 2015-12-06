package com.todor.diabetes.models;

public class Product {

    public String name;
    public float carbohydrates;
    public String group;

    public Product(String productName, float productCarbohydrates,
                   String productGroup) {
        this.name = productName;
        this.carbohydrates = productCarbohydrates;
        this.group = productGroup;
    }

    @Override
    public String toString() {
        return name + " " + group +
                " " + carbohydrates;
    }
}
