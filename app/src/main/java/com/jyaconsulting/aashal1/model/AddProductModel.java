package com.jyaconsulting.aashal1.model;

public class AddProductModel {
    String Amount,productType,productSubType;

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSubType() {
        return productSubType;
    }

    public void setProductSubType(String productSubType) {
        this.productSubType = productSubType;
    }

    public AddProductModel(String amount, String productType, String productSubType) {

        Amount = amount;
        this.productType = productType;
        this.productSubType = productSubType;
    }
}
