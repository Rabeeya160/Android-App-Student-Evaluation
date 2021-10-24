package com.jyaconsulting.aashal1.model;

public class PromoModel {
    String PromoId,PromoProduct,PromoQuantity;

    public PromoModel(String promoId, String promoProduct, String promoQuantity) {
        PromoId = promoId;
        PromoProduct = promoProduct;
        PromoQuantity = promoQuantity;
    }

    public String getPromoId() {
        return PromoId;
    }

    public void setPromoId(String promoId) {
        PromoId = promoId;
    }

    public String getPromoProduct() {
        return PromoProduct;
    }

    public void setPromoProduct(String promoProduct) {
        PromoProduct = promoProduct;
    }

    public String getPromoQuantity() {
        return PromoQuantity;
    }

    public void setPromoQuantity(String promoQuantity) {
        PromoQuantity = promoQuantity;
    }
}
