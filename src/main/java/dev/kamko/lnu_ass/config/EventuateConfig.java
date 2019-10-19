package dev.kamko.lnu_ass.config;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@EnableEventHandlers
@Import(EventuateDriverConfiguration.class)
public class EventuateConfig {
}
