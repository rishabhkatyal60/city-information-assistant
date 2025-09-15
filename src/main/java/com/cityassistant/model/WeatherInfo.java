package com.cityassistant.model;

public class WeatherInfo {
    private String description;
    private double temperature;
    private double feelsLike;
    private int humidity;
    private String icon;
    private String cityName;
    
    public WeatherInfo() {}
    
    // Getters and Setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getTemperature() { return temperature; }
    public void setTemperature(double temperature) { this.temperature = temperature; }
    
    public double getFeelsLike() { return feelsLike; }
    public void setFeelsLike(double feelsLike) { this.feelsLike = feelsLike; }
    
    public int getHumidity() { return humidity; }
    public void setHumidity(int humidity) { this.humidity = humidity; }
    
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }
    
    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }
}
