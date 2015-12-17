package com.todor.diabetes.models;

import java.io.Serializable;

public class Product implements Serializable{

    public String name;
    public float carbohydrates;
    public String group;

    public Product(String productName, float productCarbohydrates, String productGroup) {
        this.name = productName;
        this.carbohydrates = productCarbohydrates;
        this.group = productGroup;
    }

    @Override
    public String toString() {
        return "Product{" +
                "carbohydrates=" + carbohydrates +
                ", name='" + name + '\'' +
                ", group='" + group + '\'' +
                '}';
    }
}
