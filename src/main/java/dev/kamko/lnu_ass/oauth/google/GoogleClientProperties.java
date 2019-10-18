package dev.kamko.lnu_ass.oauth.google;

import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Value
@ConstructorBinding
@ConfigurationProperties("google")
public class GoogleClientProperties {

    private Secret secret;

    @Value
    public static class Secret {
        private String clientId;
        private String secret;
    }
}
