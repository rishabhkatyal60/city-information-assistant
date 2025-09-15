package com.cityassistant.model;

public class CityInfoRequest {
    private String city;
    
    public CityInfoRequest() {}
    
    public CityInfoRequest(String city) {
        this.city = city;
    }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
}