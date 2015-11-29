package com.todor.diabetes.db;

import com.todor.diabetes.models.Product;

public interface ProductDbManager {
    void insertProduct(Product product);

    Product getProduct(String productName);

    void updateProduct(Product product);

    void deleteProduct(String productName);
}
