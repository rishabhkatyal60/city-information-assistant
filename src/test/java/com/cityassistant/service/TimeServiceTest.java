package com.cityassistant.service;

import com.cityassistant.model.TimeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    private final TimeService timeService = new TimeService();

    @Test
    void shouldGetTimeForKnownCity() throws Exception {
        // Given
        String cityName = "London";

        // When
        TimeInfo result = timeService.getTime(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCurrentTime()).isNotNull();
        assertThat(result.getTimezone()).isEqualTo("Europe/London");
        assertThat(result.getUtcOffset()).isNotNull();
    }

    @Test
    void shouldGetTimeForUnknownCity() throws Exception {
        // Given - unknown city should default to UTC
        String cityName = "UnknownCity";

        // When
        TimeInfo result = timeService.getTime(cityName);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getCurrentTime()).isNotNull();
    }

    @Test
    void shouldHandleInvalidTimezone() {
        // This test might need adjustment based on actual API behavior
        // The service should handle API errors gracefully
        assertThrows(RuntimeException.class, () -> timeService.getTime("InvalidTimezone123"));
    }
}