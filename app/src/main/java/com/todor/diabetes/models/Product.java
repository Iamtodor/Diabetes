package com.todor.diabetes.models;

public class Product {

    private String productName;
    private float productCarbohydrates;
    private int productGlycemicIndex;
    private String productGroup;

    public Product(String productName, float productCarbohydrates,
                   int productGlycemicIndex, String productGroup) {
        this.productCarbohydrates = productCarbohydrates;
        this.productGlycemicIndex = productGlycemicIndex;
        this.productGroup = productGroup;
        this.productName = productName;
    }

    public float getProductCarbohydrates() {
        return productCarbohydrates;
    }

    public void setProductCarbohydrates(float productCarbohydrates) {
        this.productCarbohydrates = productCarbohydrates;
    }

    public int getProductGlycemicIndex() {
        return productGlycemicIndex;
    }

    public void setProductGlycemicIndex(int productGlycemicIndex) {
        this.productGlycemicIndex = productGlycemicIndex;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return productName + " " + productGroup +
                " " + productCarbohydrates + " " + productGlycemicIndex;
    }
}
