package com.cityassistant.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ModelTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSerializeCityInfoResponse() throws Exception {
        // Given
        CityInfoResponse response = new CityInfoResponse();
        response.setCity("London");
        response.setError(null);

        WeatherInfo weather = new WeatherInfo();
        weather.setTemperature(20.5);
        weather.setCityName("London");
        response.setWeather(weather);

        // When
        String json = objectMapper.writeValueAsString(response);

        // Then
        assertThat(json).contains("\"city\":\"London\"");
        assertThat(json).contains("\"temperature\":20.5");
    }

    @Test
    void shouldDeserializeCityInfoRequest() throws Exception {
        // Given
        String json = "{\"city\":\"Paris\"}";

        // When
        CityInfoRequest request = objectMapper.readValue(json, CityInfoRequest.class);

        // Then
        assertThat(request.getCity()).isEqualTo("Paris");
    }

    @Test
    void shouldCreateCityInfoRequestWithConstructor() {
        // Given & When
        CityInfoRequest request = new CityInfoRequest("Tokyo");

        // Then
        assertThat(request.getCity()).isEqualTo("Tokyo");
    }

    @Test
    void shouldSetAndGetWeatherInfoProperties() {
        // Given
        WeatherInfo weather = new WeatherInfo();

        // When
        weather.setCityName("Madrid");
        weather.setTemperature(25.0);
        weather.setFeelsLike(24.5);
        weather.setHumidity(65);
        weather.setDescription("Sunny");
        weather.setIcon("01d");

        // Then
        assertThat(weather.getCityName()).isEqualTo("Madrid");
        assertThat(weather.getTemperature()).isEqualTo(25.0);
        assertThat(weather.getFeelsLike()).isEqualTo(24.5);
        assertThat(weather.getHumidity()).isEqualTo(65);
        assertThat(weather.getDescription()).isEqualTo("Sunny");
        assertThat(weather.getIcon()).isEqualTo("01d");
    }

    @Test
    void shouldSetAndGetTimeInfoProperties() {
        // Given
        TimeInfo time = new TimeInfo();

        // When
        time.setCurrentTime("2025-09-15T14:30:00+01:00");
        time.setTimezone("Europe/London");
        time.setUtcOffset("+01:00");
        time.setDayOfWeek("Sunday");

        // Then
        assertThat(time.getCurrentTime()).isEqualTo("2025-09-15T14:30:00+01:00");
        assertThat(time.getTimezone()).isEqualTo("Europe/London");
        assertThat(time.getUtcOffset()).isEqualTo("+01:00");
        assertThat(time.getDayOfWeek()).isEqualTo("Sunday");
    }

    @Test
    void shouldSetAndGetCityFactsProperties() {
        // Given
        CityFacts facts = new CityFacts();

        // When
        facts.setName("Berlin");
        facts.setCountry("Germany");
        facts.setDescription("Capital of Germany");
        facts.setPopulation(3700000L);
        facts.setExtract("Berlin is the capital city of Germany.");

        // Then
        assertThat(facts.getName()).isEqualTo("Berlin");
        assertThat(facts.getCountry()).isEqualTo("Germany");
        assertThat(facts.getDescription()).isEqualTo("Capital of Germany");
        assertThat(facts.getPopulation()).isEqualTo(3700000L);
        assertThat(facts.getExtract()).isEqualTo("Berlin is the capital city of Germany.");
    }
}