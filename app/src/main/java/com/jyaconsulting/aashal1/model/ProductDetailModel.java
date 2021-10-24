package com.jyaconsulting.aashal1.model;

public class ProductDetailModel {
    String productId,subProduct,subProductId,catagoryId,subCatagoryId,PackingId,packing,price;

    public ProductDetailModel(String productId, String subProduct, String subProductId,
                              String catagoryId, String subCatagoryId, String packingId,
                              String packing, String price) {
        this.productId = productId;
        this.subProduct = subProduct;
        this.subProductId = subProductId;
        this.catagoryId = catagoryId;
        this.subCatagoryId = subCatagoryId;
        PackingId = packingId;
        this.packing = packing;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProduct() {
        return subProduct;
    }

    public void setSubProduct(String subProduct) {
        this.subProduct = subProduct;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(String catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getSubCatagoryId() {
        return subCatagoryId;
    }

    public void setSubCatagoryId(String subCatagoryId) {
        this.subCatagoryId = subCatagoryId;
    }

    public String getPackingId() {
        return PackingId;
    }

    public void setPackingId(String packingId) {
        PackingId = packingId;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
