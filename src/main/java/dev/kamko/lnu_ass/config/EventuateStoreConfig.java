package dev.kamko.lnu_ass.config;

import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("jdbc-store")
@Import(EventuateDriverConfiguration.class)
public class EventuateStoreConfig {
}
