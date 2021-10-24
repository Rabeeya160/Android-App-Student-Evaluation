package com.jyaconsulting.aashal1.model;

public class OutletModel {
    private String outletName,outletPhone,outletAddress,outletOwner,outletId;

    public OutletModel(String outletName, String outletPhone, String outletAddress, String outletOwner, String outletId) {
        this.outletName = outletName;
        this.outletPhone = outletPhone;
        this.outletAddress = outletAddress;
        this.outletOwner = outletOwner;
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getOutletPhone() {
        return outletPhone;
    }

    public void setOutletPhone(String outletPhone) {
        this.outletPhone = outletPhone;
    }

    public String getOutletAddress() {
        return outletAddress;
    }

    public void setOutletAddress(String outletAddress) {
        this.outletAddress = outletAddress;
    }

    public String getOutletOwner() {
        return outletOwner;
    }

    public void setOutletOwner(String outletOwner) {
        this.outletOwner = outletOwner;
    }

    public String getOutletId() {
        return outletId;
    }

    public void setOutletId(String outletId) {
        this.outletId = outletId;
    }
}
