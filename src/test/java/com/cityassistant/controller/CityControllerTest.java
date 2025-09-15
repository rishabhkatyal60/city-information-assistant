package com.cityassistant.controller;

import com.cityassistant.model.*;
import com.cityassistant.service.CityInformationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityInformationService cityService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    public void testGetCityInfoByPath() throws Exception {
        // Arrange
        CityInfoResponse mockResponse = createMockCityResponse();
        when(cityService.getCityInformation("Tokyo")).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(get("/api/city-info/Tokyo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Tokyo"))
                .andExpect(jsonPath("$.weather.temperature").value(25.5))
                .andExpect(jsonPath("$.time.timezone").value("Asia/Tokyo"))
                .andExpect(jsonPath("$.facts.name").value("Tokyo"));
    }

    @Test
    public void testGetCityInfoByPost() throws Exception {
        // Arrange
        CityInfoRequest request = new CityInfoRequest("Paris");
        CityInfoResponse mockResponse = createMockCityResponse();
        mockResponse.setCity("Paris");
        when(cityService.getCityInformation("Paris")).thenReturn(mockResponse);

        // Act & Assert
        mockMvc.perform(post("/api/city-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Paris"));
    }

    @Test
    public void testGetCityInfoWithError() throws Exception {
        // Arrange
        when(cityService.getCityInformation(anyString()))
                .thenThrow(new RuntimeException("API service unavailable"));

        // Act & Assert
        mockMvc.perform(get("/api/city-info/InvalidCity"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Failed to get city information: API service unavailable"));
    }

    private CityInfoResponse createMockCityResponse() {
        CityInfoResponse response = new CityInfoResponse();
        response.setCity("Tokyo");

        WeatherInfo weather = new WeatherInfo();
        weather.setTemperature(25.5);
        weather.setDescription("clear sky");
        weather.setHumidity(65);
        weather.setFeelsLike(26.2);
        weather.setIcon("01d");
        weather.setCityName("Tokyo");
        response.setWeather(weather);

        TimeInfo time = new TimeInfo();
        time.setCurrentTime("2025-09-13T17:16:58+09:00");
        time.setTimezone("Asia/Tokyo");
        time.setUtcOffset("+09:00");
        time.setDayOfWeek("6");
        response.setTime(time);

        CityFacts facts = new CityFacts();
        facts.setName("Tokyo");
        facts.setDescription("Tokyo is the capital of Japan");
        facts.setExtract("Tokyo is the capital of Japan");
        response.setFacts(facts);

        return response;
    }
}