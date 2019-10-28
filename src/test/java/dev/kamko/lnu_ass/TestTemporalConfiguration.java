package dev.kamko.lnu_ass;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import org.springframework.context.annotation.Bean;

@org.springframework.boot.test.context.TestConfiguration
public class TestTemporalConfiguration {

    @Bean
    public Clock clock() {
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
