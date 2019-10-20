package dev.kamko.lnu_ass.auth.oauth.google.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class GoogleTokens {
    String accessToken;
    String refreshToken;
}
