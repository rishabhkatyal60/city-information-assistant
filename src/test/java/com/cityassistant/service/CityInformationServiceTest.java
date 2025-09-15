package com.cityassistant.service;

import com.cityassistant.model.CityInfoResponse;
import com.cityassistant.model.WeatherInfo;
import com.cityassistant.model.TimeInfo;
import com.cityassistant.model.CityFacts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityInformationServiceTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private TimeService timeService;

    @Mock
    private WikipediaService wikipediaService;

    @InjectMocks
    private CityInformationService cityInformationService;

    private WeatherInfo mockWeatherInfo;
    private TimeInfo mockTimeInfo;
    private CityFacts mockCityFacts;

    @BeforeEach
    void setUp() {
        mockWeatherInfo = TestDataHelper.createMockWeatherInfo("London");
        mockTimeInfo = TestDataHelper.createMockTimeInfo();
        mockCityFacts = TestDataHelper.createMockCityFacts("London");
    }

    @Test
    void shouldGetCityInformationSuccessfully() throws Exception {
        // Given
        String cityName = "London";
        when(weatherService.getWeather(cityName)).thenReturn(mockWeatherInfo);
        when(timeService.getTime(cityName)).thenReturn(mockTimeInfo);
        when(wikipediaService.getCityFacts(cityName)).thenReturn(mockCityFacts);

        // When
        CityInfoResponse result = cityInformationService.getCityInformation(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo(cityName);
        assertThat(result.getWeather()).isEqualTo(mockWeatherInfo);
        assertThat(result.getTime()).isEqualTo(mockTimeInfo);
        assertThat(result.getFacts()).isEqualTo(mockCityFacts);
        assertThat(result.getError()).isNull();

        verify(weatherService).getWeather(cityName);
        verify(timeService).getTime(cityName);
        verify(wikipediaService).getCityFacts(cityName);
    }

    @Test
    void shouldHandleWeatherServiceFailureGracefully() throws Exception {
        // Given
        String cityName = "InvalidCity";
        when(weatherService.getWeather(cityName)).thenThrow(new RuntimeException("Weather API error"));
        when(timeService.getTime(cityName)).thenReturn(mockTimeInfo);
        when(wikipediaService.getCityFacts(cityName)).thenReturn(mockCityFacts);

        // When
        CityInfoResponse result = cityInformationService.getCityInformation(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo(cityName);
        assertThat(result.getWeather()).isNull(); // Should be null due to service failure
        assertThat(result.getTime()).isEqualTo(mockTimeInfo);
        assertThat(result.getFacts()).isEqualTo(mockCityFacts);
    }

    @Test
    void shouldHandleTimeServiceFailureGracefully() throws Exception {
        // Given
        String cityName = "TestCity";
        when(weatherService.getWeather(cityName)).thenReturn(mockWeatherInfo);
        when(timeService.getTime(cityName)).thenThrow(new RuntimeException("Time API error"));
        when(wikipediaService.getCityFacts(cityName)).thenReturn(mockCityFacts);

        // When
        CityInfoResponse result = cityInformationService.getCityInformation(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getWeather()).isEqualTo(mockWeatherInfo);
        assertThat(result.getTime()).isNull(); // Should be null due to service failure
        assertThat(result.getFacts()).isEqualTo(mockCityFacts);
    }

    @Test
    void shouldHandleWikipediaServiceFailureGracefully() throws Exception {
        // Given
        String cityName = "TestCity";
        when(weatherService.getWeather(cityName)).thenReturn(mockWeatherInfo);
        when(timeService.getTime(cityName)).thenReturn(mockTimeInfo);
        when(wikipediaService.getCityFacts(cityName)).thenThrow(new RuntimeException("Wikipedia API error"));

        // When
        CityInfoResponse result = cityInformationService.getCityInformation(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getWeather()).isEqualTo(mockWeatherInfo);
        assertThat(result.getTime()).isEqualTo(mockTimeInfo);
        assertThat(result.getFacts()).isNull(); // Should be null due to service failure
    }

    @Test
    void shouldHandleAllServicesFailure() throws Exception {
        // Given
        String cityName = "FailCity";
        when(weatherService.getWeather(cityName)).thenThrow(new RuntimeException("Weather failed"));
        when(timeService.getTime(cityName)).thenThrow(new RuntimeException("Time failed"));
        when(wikipediaService.getCityFacts(cityName)).thenThrow(new RuntimeException("Wikipedia failed"));

        // When
        CityInfoResponse result = cityInformationService.getCityInformation(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCity()).isEqualTo(cityName);
        assertThat(result.getWeather()).isNull();
        assertThat(result.getTime()).isNull();
        assertThat(result.getFacts()).isNull();
    }
}