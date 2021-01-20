package com.najmeddine.BestReservation;

public class Model {
    String date, time, tabNb,key;

    public Model(String key,String date, String time, String tabNb) {
        this.date = date;
        this.time = time;
        this.tabNb = tabNb;
        this.key=key;
    }

    public Model(String date, String time, String tabNb) {
        this.date = date;
        this.time = time;
        this.tabNb = tabNb;
    }

    public Model() {
    }

    public String getKey() {
        return key;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTabNb() {
        return tabNb;
    }
}
