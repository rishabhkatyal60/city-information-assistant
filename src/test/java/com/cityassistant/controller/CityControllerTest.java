package com.cityassistant.controller;

import com.cityassistant.model.CityInfoRequest;
import com.cityassistant.model.CityInfoResponse;
import com.cityassistant.service.CityInformationService;
import com.cityassistant.service.TestDataHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
class CityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CityInformationService cityInformationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnHealthStatus() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value("healthy"))
                .andExpect(jsonPath("$.version").value("1.0.0"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturnCityInfoForPostRequest() throws Exception {
        // Given
        CityInfoRequest request = new CityInfoRequest("London");
        CityInfoResponse mockResponse = createMockCityInfoResponse("London");

        when(cityInformationService.getCityInformation("London")).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(post("/api/city-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.weather").exists())
                .andExpect(jsonPath("$.time").exists())
                .andExpect(jsonPath("$.facts").exists())
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void shouldReturnCityInfoForGetRequest() throws Exception {
        // Given
        CityInfoResponse mockResponse = createMockCityInfoResponse("Paris");
        when(cityInformationService.getCityInformation("Paris")).thenReturn(mockResponse);

        // When & Then
        mockMvc.perform(get("/api/city-info/Paris"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.city").value("Paris"));
    }

    @Test
    void shouldHandleServiceExceptionForPostRequest() throws Exception {
        // Given
        CityInfoRequest request = new CityInfoRequest("InvalidCity");
        when(cityInformationService.getCityInformation("InvalidCity"))
                .thenThrow(new RuntimeException("Service unavailable"));

        // When & Then
        mockMvc.perform(post("/api/city-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Failed to get city information: Service unavailable"));
    }

    @Test
    void shouldHandleServiceExceptionForGetRequest() throws Exception {
        // Given
        when(cityInformationService.getCityInformation("InvalidCity"))
                .thenThrow(new RuntimeException("API Error"));

        // When & Then
        mockMvc.perform(get("/api/city-info/InvalidCity"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Failed to get city information: API Error"));
    }

    @Test
    void shouldHandleMalformedJsonRequest() throws Exception {
        // Given
        String malformedJson = "{ invalid json }";

        // When & Then
        mockMvc.perform(post("/api/city-info")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }

    private CityInfoResponse createMockCityInfoResponse(String cityName) {
        CityInfoResponse response = new CityInfoResponse();
        response.setCity(cityName);
        response.setWeather(TestDataHelper.createMockWeatherInfo(cityName));
        response.setTime(TestDataHelper.createMockTimeInfo());
        response.setFacts(TestDataHelper.createMockCityFacts(cityName));
        return response;
    }
}