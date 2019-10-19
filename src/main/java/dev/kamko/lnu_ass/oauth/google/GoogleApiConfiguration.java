package dev.kamko.lnu_ass.oauth.google;

import java.util.List;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleApiConfiguration {

    @Bean
    public HttpTransport httpTransport() {
        return new NetHttpTransport();
    }

    @Bean
    public JsonFactory jsonFactory() {
        return new JacksonFactory();
    }

    @Bean
    public AuthorizationCodeFlow googleAuthorizationCodeFlow(HttpTransport httpTransport,
                                                             JsonFactory jsonFactory,
                                                             GoogleClientProperties props) {
        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport,
                jsonFactory,
                props.getSecret().getClientId(),
                props.getSecret().getSecret(),
                List.of("email",
                        "profile",
                        "https://www.googleapis.com/auth/calendar.events",
                        "https://www.googleapis.com/auth/calendar.readonly")
        ).setAccessType("offline")
                .build();
    }
}
