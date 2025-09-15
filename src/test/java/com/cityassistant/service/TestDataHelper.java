package com.cityassistant.service;

import com.cityassistant.model.*;

public class TestDataHelper {

    public static WeatherInfo createMockWeatherInfo(String cityName) {
        WeatherInfo weather = new WeatherInfo();
        weather.setCityName(cityName);
        weather.setDescription("Clear sky");
        weather.setTemperature(20.5);
        weather.setFeelsLike(19.2);
        weather.setHumidity(60);
        weather.setIcon("01d");
        return weather;
    }

    public static TimeInfo createMockTimeInfo() {
        TimeInfo time = new TimeInfo();
        time.setCurrentTime("2025-09-15T14:30:00+01:00");
        time.setTimezone("Europe/London");
        time.setUtcOffset("+01:00");
        time.setDayOfWeek("Sunday");
        return time;
    }

    public static CityFacts createMockCityFacts(String cityName) {
        CityFacts facts = new CityFacts();
        facts.setName(cityName);
        facts.setCountry("Test Country");
        facts.setDescription(cityName + " is a beautiful city with rich history.");
        facts.setPopulation(1000000L);
        facts.setExtract("Short extract about " + cityName);
        return facts;
    }

    public static CityInfoResponse createMockCityInfoResponse(String cityName) {
        CityInfoResponse response = new CityInfoResponse();
        response.setCity(cityName);
        response.setWeather(createMockWeatherInfo(cityName));
        response.setTime(createMockTimeInfo());
        response.setFacts(createMockCityFacts(cityName));
        return response;
    }
}