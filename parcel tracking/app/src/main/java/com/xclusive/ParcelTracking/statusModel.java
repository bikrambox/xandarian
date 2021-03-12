package com.xclusive.ParcelTracking;

class statusModel {
    private String  status,dates;

    public statusModel(String status, String dates) {
        this.status = status;
        this.dates = dates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
