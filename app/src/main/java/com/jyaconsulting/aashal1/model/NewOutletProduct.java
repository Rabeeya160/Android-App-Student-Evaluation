package com.jyaconsulting.aashal1.model;

public class NewOutletProduct {
    String GroupId,SubGroupId,Quantity,Amount,FId;

    public NewOutletProduct(String groupId, String subGroupId, String quantity, String amount, String FId) {
        GroupId = groupId;
        SubGroupId = subGroupId;
        Quantity = quantity;
        Amount = amount;
        this.FId = FId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }

    public String getSubGroupId() {
        return SubGroupId;
    }

    public void setSubGroupId(String subGroupId) {
        SubGroupId = subGroupId;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getFId() {
        return FId;
    }

    public void setFId(String FId) {
        this.FId = FId;
    }
}
