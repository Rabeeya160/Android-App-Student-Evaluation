package com.jyaconsulting.aashal1.model;

public class OrderModel {
    String OrderId,itemId,Item,OrderProduct,OrderProductQuantity,OrderPrice,productId,subProductId,Lati,Longi,outletId,discount;

    public OrderModel(String orderId, String itemId, String item,String productId, String subProductId, String orderProduct,
                      String orderProductQuantity, String orderPrice,String Lati,String Longi,String outletId,String discount) {
        OrderId = orderId;
        this.itemId = itemId;
        Item = item;
        this.productId = productId;
        this.subProductId = subProductId;
        OrderProduct = orderProduct;
        OrderProductQuantity = orderProductQuantity;
        OrderPrice = orderPrice;
        this.Lati=Lati;
        this.Longi=Longi;
        this.outletId=outletId;
        this.discount=discount;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getLati() {
        return Lati;
    }

    public void setLati(String lati) {
        Lati = lati;
    }

    public String getLongi() {
        return Longi;
    }

    public void setLongi(String longi) {
        Longi = longi;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSubProductId() {
        return subProductId;
    }

    public void setSubProductId(String subProductId) {
        this.subProductId = subProductId;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItem() {
        return Item;
    }

    public void setItem(String item) {
        Item = item;
    }

    public String getOrderProduct() {
        return OrderProduct;
    }

    public void setOrderProduct(String orderProduct) {
        OrderProduct = orderProduct;
    }

    public String getOrderProductQuantity() {
        return OrderProductQuantity;
    }

    public void setOrderProductQuantity(String orderProductQuantity) {
        OrderProductQuantity = orderProductQuantity;
    }

    public String getOrderPrice() {
        return OrderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        OrderPrice = orderPrice;
    }
}
