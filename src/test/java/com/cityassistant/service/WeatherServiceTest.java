package com.cityassistant.service;

import com.cityassistant.model.WeatherInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WeatherService weatherService;

    @BeforeEach
    void setUp() {
        weatherService = new WeatherService();
        // Set a test API key using ReflectionTestUtils
        ReflectionTestUtils.setField(weatherService, "apiKey", "test-api-key");
    }

    @Test
    void shouldThrowExceptionForInvalidApiKey() {
        // Given
        ReflectionTestUtils.setField(weatherService, "apiKey", "invalid-key");

        // When & Then
        assertThrows(RuntimeException.class, () -> weatherService.getWeather("London"));
    }

    @Test
    void shouldThrowExceptionForEmptyApiKey() {
        // Given
        ReflectionTestUtils.setField(weatherService, "apiKey", "your_api_key_here");

        // When & Then
        assertThrows(RuntimeException.class, () -> weatherService.getWeather("London"));
    }

    @Test
    void shouldThrowExceptionForInvalidCity() {
        // Given
        String invalidCity = "NonExistentCityXYZ123";

        // When & Then
        assertThrows(RuntimeException.class, () -> weatherService.getWeather(invalidCity));
    }
}