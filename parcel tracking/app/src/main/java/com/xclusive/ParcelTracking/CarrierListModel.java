package com.xclusive.ParcelTracking;

class CarrierListModel {
    String name,code,phone,homepage,type,picture,countrycode,track_url;

    public CarrierListModel(String name, String code, String picture) {
        this.name = name;
        this.code = code;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
