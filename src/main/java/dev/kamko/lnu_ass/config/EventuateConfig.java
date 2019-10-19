package dev.kamko.lnu_ass.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import io.eventuate.javaclient.driver.EventuateDriverConfiguration;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
@EnableEventHandlers
@Import(EventuateDriverConfiguration.class)
public class EventuateConfig {

    @Autowired
    public void hackJsonMapper() {
        JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    }
}
