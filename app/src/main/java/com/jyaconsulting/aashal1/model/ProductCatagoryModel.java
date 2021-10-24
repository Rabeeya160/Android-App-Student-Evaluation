package com.jyaconsulting.aashal1.model;

public class ProductCatagoryModel {
    String catagoryId,productCatagory,PoductId;
    public ProductCatagoryModel(String catagoryId, String productCatagory, String poductId) {
        this.catagoryId = catagoryId;
        this.productCatagory = productCatagory;
        PoductId = poductId;
    }

    public String getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(String catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getProductCatagory() {
        return productCatagory;
    }

    public void setProductCatagory(String productCatagory) {
        this.productCatagory = productCatagory;
    }

    public String getPoductId() {
        return PoductId;
    }

    public void setPoductId(String poductId) {
        PoductId = poductId;
    }
}
