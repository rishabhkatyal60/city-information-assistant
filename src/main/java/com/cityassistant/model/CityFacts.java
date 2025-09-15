package com.cityassistant.model;

public class CityFacts {
    private String name;
    private String country;
    private String description;
    private Long population;
    private String extract;
    
    public CityFacts() {}
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }
    
    public String getExtract() { return extract; }
    public void setExtract(String extract) { this.extract = extract; }
}