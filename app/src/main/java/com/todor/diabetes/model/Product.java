package com.todor.diabetes.model;

public class Product {

    private String productName;
    private int productValue;

    public Product(String productName, int productValue) {
        this.productName = productName;
        this.productValue = productValue;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductValue() {
        return productValue;
    }

    public void setProductValue(int productValue) {
        this.productValue = productValue;
    }

    @Override
    public String toString() {
        return productName + " " + productValue;
    }
}
