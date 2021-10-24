package com.jyaconsulting.aashal1.model;

public class UserInfoModel {
    String UserName,RegId,RegName,AreaId,AreaName,DistId,DistName,CityId,CityName,CountryId,CountryName,Account;

    public UserInfoModel(String userName, String regId, String regName, String areaId,
                         String areaName, String distId, String distName, String cityId,
                         String cityName, String countryId, String countryName, String account) {
        UserName = userName;
        RegId = regId;
        RegName = regName;
        AreaId = areaId;
        AreaName = areaName;
        DistId = distId;
        DistName = distName;
        CityId = cityId;
        CityName = cityName;
        CountryId = countryId;
        CountryName = countryName;
        Account = account;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getRegId() {
        return RegId;
    }

    public void setRegId(String regId) {
        RegId = regId;
    }

    public String getRegName() {
        return RegName;
    }

    public void setRegName(String regName) {
        RegName = regName;
    }

    public String getAreaId() {
        return AreaId;
    }

    public void setAreaId(String areaId) {
        AreaId = areaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getDistId() {
        return DistId;
    }

    public void setDistId(String distId) {
        DistId = distId;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getCountryName() {
        return CountryName;
    }

    public void setCountryName(String countryName) {
        CountryName = countryName;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }
}
