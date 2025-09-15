package com.cityassistant.service;

import com.cityassistant.model.TimeInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class TimeService {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, String> cityTimezones;

    public TimeService() {
        cityTimezones = new HashMap<>();
        cityTimezones.put("london", "Europe/London");
        cityTimezones.put("paris", "Europe/Paris");
        cityTimezones.put("tokyo", "Asia/Tokyo");
        cityTimezones.put("new york", "America/New_York");
        cityTimezones.put("los angeles", "America/Los_Angeles");
        cityTimezones.put("sydney", "Australia/Sydney");
        cityTimezones.put("moscow", "Europe/Moscow");
        cityTimezones.put("dubai", "Asia/Dubai");
        cityTimezones.put("berlin", "Europe/Berlin");
        cityTimezones.put("mumbai", "Asia/Kolkata");
        cityTimezones.put("shanghai", "Asia/Shanghai");
        cityTimezones.put("singapore", "Asia/Singapore");
        cityTimezones.put("madrid", "Europe/Madrid");
        cityTimezones.put("rome", "Europe/Rome");
        cityTimezones.put("amsterdam", "Europe/Amsterdam");
        cityTimezones.put("barcelona", "Europe/Madrid");
        cityTimezones.put("bangkok", "Asia/Bangkok");
        cityTimezones.put("hong kong", "Asia/Hong_Kong");
        cityTimezones.put("seoul", "Asia/Seoul");
        cityTimezones.put("toronto", "America/Toronto");
    }

    public TimeInfo getTime(String cityName) throws Exception {
        String timezone = cityTimezones.getOrDefault(cityName.toLowerCase(), "UTC");

        String url = String.format("https://worldtimeapi.org/api/timezone/%s", timezone);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "City-Assistant/1.0")
                .header("Accept", "application/json")
                .timeout(Duration.ofSeconds(10))
                .GET()
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Time API error: " + response.statusCode() + " - " + response.body());
            }

            JsonNode json = objectMapper.readTree(response.body());

            TimeInfo time = new TimeInfo();
            time.setCurrentTime(json.get("datetime").asText());
            time.setTimezone(json.get("timezone").asText());
            time.setUtcOffset(json.get("utc_offset").asText());

            if (json.has("day_of_week")) {
                time.setDayOfWeek(json.get("day_of_week").asText());
            }

            return time;
        } catch (Exception e) {
            System.err.println("Time service detailed error for " + cityName + ": " + e.getMessage());
            throw e;
        }
    }
}
