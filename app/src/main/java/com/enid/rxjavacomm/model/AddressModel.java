package com.enid.rxjavacomm.model;

/**
 * Created by Enid on 2016/8/11.
 */
public class AddressModel {
    private int id;
    private String lon;
    private String level;
    private String address;
    private String cityName;
    private String alevel;
    private String lat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAlevel() {
        return alevel;
    }

    public void setAlevel(String alevel) {
        this.alevel = alevel;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
