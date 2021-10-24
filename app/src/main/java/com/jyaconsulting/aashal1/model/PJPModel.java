package com.jyaconsulting.aashal1.model;

public class PJPModel {
    String pjpID,ouletName,Address,phone,ownerName,pjpStatus,Day,AllDays,StartDate,AlterNate;

    public PJPModel(String pjpID, String ouletName, String address, String phone,
                    String ownerName,String pjpStatus,String Day,String AllDays,String StartDate,String AlterNate) {
        this.pjpID = pjpID;
        this.ouletName = ouletName;
        Address = address;
        this.phone = phone;
        this.ownerName = ownerName;
        this.Day=Day;
        this.pjpStatus=pjpStatus;
        this.AllDays=AllDays;
        this.StartDate=StartDate;
        this.AlterNate=AlterNate;
    }

    public String getAllDays() {
        return AllDays;
    }

    public void setAllDays(String allDays) {
        AllDays = allDays;
    }

    public String getPjpStatus() {
        return pjpStatus;
    }

    public void setPjpStatus(String pjpStatus) {
        this.pjpStatus = pjpStatus;
    }

    public String getPjpID() {
        return pjpID;
    }

    public void setPjpID(String pjpID) {
        this.pjpID = pjpID;
    }

    public String getOuletName() {
        return ouletName;
    }

    public void setOuletName(String ouletName) {
        this.ouletName = ouletName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String Day) {
        this.Day = Day;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getAlterNate() {
        return AlterNate;
    }

    public void setAlterNate(String alterNate) {
        AlterNate = alterNate;
    }
}
