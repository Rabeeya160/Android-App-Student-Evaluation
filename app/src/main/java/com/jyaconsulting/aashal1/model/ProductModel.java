package com.jyaconsulting.aashal1.model;

public class ProductModel {
    String productID,Product;

    public ProductModel(String productID, String product) {
        this.productID = productID;
        Product = product;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }
}
