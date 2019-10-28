package dev.kamko.lnu_ass.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TemporalConfig {

    @Bean
    @Profile("!test")
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
