package com.jyaconsulting.aashal1.model;

public class NewOutletModel {
    String Id,Name,Address,Channel,Owner,CNIC,Tel,Mobile,Mobile2,AltNo,Fax,Purchaser,PurchaserMobile,Lat,Longi,
    Mac,Country,City,Region,Area,Distributor;

    public NewOutletModel(String id,String name, String address, String channel, String owner, String CNIC, String tel, String mobile,
                          String mobile2, String altNo, String fax, String purchaser, String purchaserMobile, String lat,
                          String longi, String mac, String country, String city, String region, String area, String distributor) {
        Id=id;
        Name = name;
        Address = address;
        Channel = channel;
        Owner = owner;
        this.CNIC = CNIC;
        Tel = tel;
        Mobile = mobile;
        Mobile2 = mobile2;
        AltNo = altNo;
        Fax = fax;
        Purchaser = purchaser;
        PurchaserMobile = purchaserMobile;
        Lat = lat;
        Longi = longi;
        Mac = mac;
        Country = country;
        City = city;
        Region = region;
        Area = area;
        Distributor = distributor;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public String getCNIC() {
        return CNIC;
    }

    public void setCNIC(String CNIC) {
        this.CNIC = CNIC;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile2() {
        return Mobile2;
    }

    public void setMobile2(String mobile2) {
        Mobile2 = mobile2;
    }

    public String getAltNo() {
        return AltNo;
    }

    public void setAltNo(String altNo) {
        AltNo = altNo;
    }

    public String getFax() {
        return Fax;
    }

    public void setFax(String fax) {
        Fax = fax;
    }

    public String getPurchaser() {
        return Purchaser;
    }

    public void setPurchaser(String purchaser) {
        Purchaser = purchaser;
    }

    public String getPurchaserMobile() {
        return PurchaserMobile;
    }

    public void setPurchaserMobile(String purchaserMobile) {
        PurchaserMobile = purchaserMobile;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLongi() {
        return Longi;
    }

    public void setLongi(String longi) {
        Longi = longi;
    }

    public String getMac() {
        return Mac;
    }

    public void setMac(String mac) {
        Mac = mac;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getDistributor() {
        return Distributor;
    }

    public void setDistributor(String distributor) {
        Distributor = distributor;
    }
}

