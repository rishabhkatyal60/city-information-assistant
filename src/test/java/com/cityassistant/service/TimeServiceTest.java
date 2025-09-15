package com.cityassistant.service;

import com.cityassistant.model.TimeInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class TimeServiceTest {

    private final TimeService timeService = new TimeService();

    @Test
    void shouldFallbackToUtcForUnknownCity() throws Exception {
        TimeInfo result = timeService.getTime("xyz");
        assertThat(result).isNotNull();
        assertThat(result.getTimezone()).isEqualTo("UTC");
    }

    @Test
    void shouldGetTimeForKnownCity() throws Exception {
        String cityName = "London";

        TimeInfo result = timeService.getTime(cityName);

        assertThat(result).isNotNull();
        assertThat(result.getCurrentTime()).isNotNull();
        assertThat(result.getTimezone()).isEqualTo("Europe/London");
        assertThat(result.getUtcOffset()).isNotNull();
    }
}