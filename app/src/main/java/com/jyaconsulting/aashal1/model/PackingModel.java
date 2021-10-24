package com.jyaconsulting.aashal1.model;

public class PackingModel {
    String packingID,Packing,catagoryID,productID,price;

    public PackingModel(String packingID, String packing, String catagoryID, String productID, String price) {
        this.packingID = packingID;
        Packing = packing;
        this.catagoryID = catagoryID;
        this.productID = productID;
        this.price = price;
    }

    public String getPackingID() {
        return packingID;
    }

    public void setPackingID(String packingID) {
        this.packingID = packingID;
    }

    public String getPacking() {
        return Packing;
    }

    public void setPacking(String packing) {
        Packing = packing;
    }

    public String getCatagoryID() {
        return catagoryID;
    }

    public void setCatagoryID(String catagoryID) {
        this.catagoryID = catagoryID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
