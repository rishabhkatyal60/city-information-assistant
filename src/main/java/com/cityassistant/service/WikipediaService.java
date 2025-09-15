package com.cityassistant.service;

import com.cityassistant.model.CityFacts;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class WikipediaService {
    
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public CityFacts getCityFacts(String cityName) throws Exception {
        String searchUrl = String.format(
            "https://en.wikipedia.org/api/rest_v1/page/summary/%s", 
            cityName.replace(" ", "_")
        );
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(searchUrl))
            .header("User-Agent", "City-Assistant/1.0")
            .GET()
            .build();
        
        HttpResponse<String> response = httpClient.send(request, 
            HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Wikipedia API error: " + response.statusCode());
        }
        
        JsonNode json = objectMapper.readTree(response.body());
        
        CityFacts facts = new CityFacts();
        facts.setName(json.has("title") ? json.get("title").asText() : cityName);
        facts.setDescription(json.has("extract") ? json.get("extract").asText() : "No description available");
        facts.setExtract(json.has("extract") ? json.get("extract").asText() : "");
        
        return facts;
    }
}