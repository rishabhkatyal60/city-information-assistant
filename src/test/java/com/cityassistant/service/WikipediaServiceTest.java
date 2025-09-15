package com.cityassistant.service;

import com.cityassistant.model.CityFacts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class WikipediaServiceTest {

    private final WikipediaService wikipediaService = new WikipediaService();

    @Test
    void shouldGetFactsForKnownCity() throws Exception {
        // Given
        String cityName = "London";

        // When
        CityFacts result = wikipediaService.getCityFacts(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
        assertThat(result.getDescription()).isNotNull();
        assertThat(result.getExtract()).isNotNull();
    }

    @Test
    void shouldHandleUnknownCity() throws Exception {
        // Given
        String cityName = "NonExistentCity123";

        // When & Then
        // Wikipedia service should throw exception for unknown cities
        assertThrows(RuntimeException.class, () -> wikipediaService.getCityFacts(cityName));
    }

    @Test
    void shouldHandleCityWithSpaces() throws Exception {
        // Given
        String cityName = "New York";

        // When
        CityFacts result = wikipediaService.getCityFacts(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isNotNull();
    }
}
