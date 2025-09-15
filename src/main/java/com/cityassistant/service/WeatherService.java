package com.cityassistant.service;

import com.cityassistant.model.WeatherInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WeatherService {
    
    @Value("${openweather.api.key:your_api_key_here}")
    private String apiKey;
    
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public WeatherInfo getWeather(String cityName) throws Exception {
        String url = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric",
            cityName, apiKey
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("User-Agent", "City-Assistant/1.0")
            .GET()
            .build();
        
        HttpResponse<String> response = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Weather API error: " + response.statusCode() + " - " + response.body());
        }
        
        JsonNode json = objectMapper.readTree(response.body());
        
        WeatherInfo weather = new WeatherInfo();
        weather.setCityName(json.get("name").asText());
        weather.setDescription(json.get("weather").get(0).get("description").asText());
        weather.setTemperature(json.get("main").get("temp").asDouble());
        weather.setFeelsLike(json.get("main").get("feels_like").asDouble());
        weather.setHumidity(json.get("main").get("humidity").asInt());
        weather.setIcon(json.get("weather").get(0).get("icon").asText());
        
        return weather;
    }
}