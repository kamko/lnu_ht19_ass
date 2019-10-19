package dev.kamko.lnu_ass.config;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(EventuateDriverConfiguration.class)
public class EventuateConfig {
}
