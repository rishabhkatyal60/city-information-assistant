package com.cityassistant.service;

import com.cityassistant.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class CityInformationService {
    
    @Autowired
    private WeatherService weatherService;
    
    @Autowired
    private TimeService timeService;
    
    @Autowired
    private WikipediaService wikipediaService;
    
    public CityInfoResponse getCityInformation(String cityName) throws Exception {
        CityInfoResponse response = new CityInfoResponse();
        response.setCity(cityName);
        
        // Get all information concurrently for better performance
        CompletableFuture<WeatherInfo> weatherFuture = 
            CompletableFuture.supplyAsync(() -> {
                try {
                    return weatherService.getWeather(cityName);
                } catch (Exception e) {
                    System.err.println("Weather service error: " + e.getMessage());
                    return null;
                }
            });
            
        CompletableFuture<TimeInfo> timeFuture = 
            CompletableFuture.supplyAsync(() -> {
                try {
                    return timeService.getTime(cityName);
                } catch (Exception e) {
                    System.err.println("Time service error: " + e.getMessage());
                    return null;
                }
            });
            
        CompletableFuture<CityFacts> factsFuture = 
            CompletableFuture.supplyAsync(() -> {
                try {
                    return wikipediaService.getCityFacts(cityName);
                } catch (Exception e) {
                    System.err.println("Wikipedia service error: " + e.getMessage());
                    return null;
                }
            });
        
        // Wait for all to complete
        response.setWeather(weatherFuture.get());
        response.setTime(timeFuture.get());
        response.setFacts(factsFuture.get());
        
        return response;
    }
}