package com.xclusive.ParcelTracking;

class savedmodel {
    private String url,id,carriername;

    public savedmodel(String url, String id, String carriername) {
        this.url = url;
        this.id = id;
        this.carriername = carriername;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarriername() {
        return carriername;
    }

    public void setCarriername(String carriername) {
        this.carriername = carriername;
    }
}
