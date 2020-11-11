package com.example.bloodbankapp;



public class Donor {
    String name;
    String contuctNumber;
    String city;
    String bloodGroup;
    String lat;
    String lan;
    String add;

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLan() {
        return lan;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public Donor() {

    }

    public Donor(String name, String contuctNumber, String bloodGroup, String city, String lat, String lng,String add) {
        this.name = name;
        this.contuctNumber = contuctNumber;
        this.bloodGroup = bloodGroup;
        this.city = city;
        this.lat = lat;
        this.lan = lng;
        this.add = add;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContuctNumber() {
        return contuctNumber;
    }

    public void setContuctNumber(String contuctNumber) {
        this.contuctNumber = contuctNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
