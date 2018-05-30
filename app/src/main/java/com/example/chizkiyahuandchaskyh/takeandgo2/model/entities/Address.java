package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

public class Address {



    public Address(String country, String city, String street, int houseNum) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
    }

    public Address(Address oldAddress) {
        this.country = oldAddress.country;
        this.city = oldAddress.city;
        this.street = oldAddress.street;
        this.houseNum = oldAddress.houseNum;
    }

    protected int id;
    protected String country;
    protected String city;

    public Address(int id, String country, String city, String street, int houseNum, double latitude, double longitude) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNum = houseNum;
        Latitude = latitude;
        this.longitude = longitude;
    }

    protected String street;
    protected int houseNum;
    protected double Latitude;
    protected double longitude;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (houseNum != address.houseNum) return false;
        if (!country.equals(address.country)) return false;
        if (!city.equals(address.city)) return false;
        return street.equals(address.street);
    }

    @Override
    public String toString() {
        return houseNum + " " + street + ", "  + city + ", " + country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(int houseNum) {
        this.houseNum = houseNum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
