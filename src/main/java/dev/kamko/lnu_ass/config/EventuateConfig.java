package dev.kamko.lnu_ass.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventuate.javaclient.commonimpl.JSonMapper;
import io.eventuate.javaclient.spring.EnableEventHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableEventHandlers
public class EventuateConfig {

    @Autowired
    public void hackJsonMapper() {
        JSonMapper.objectMapper.registerModule(new JavaTimeModule());
    }
}
