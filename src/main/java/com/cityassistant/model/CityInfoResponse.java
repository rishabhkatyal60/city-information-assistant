package com.cityassistant.model;

public class CityInfoResponse {
    private String city;
    private WeatherInfo weather;
    private TimeInfo time;
    private CityFacts facts;
    private String error;
    
    public CityInfoResponse() {}
    
    // Getters and Setters
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public WeatherInfo getWeather() { return weather; }
    public void setWeather(WeatherInfo weather) { this.weather = weather; }
    
    public TimeInfo getTime() { return time; }
    public void setTime(TimeInfo time) { this.time = time; }
    
    public CityFacts getFacts() { return facts; }
    public void setFacts(CityFacts facts) { this.facts = facts; }
    
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}