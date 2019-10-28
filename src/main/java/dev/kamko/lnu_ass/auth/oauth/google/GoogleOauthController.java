package dev.kamko.lnu_ass.auth.oauth.google;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Future;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import dev.kamko.lnu_ass.auth.oauth.google.dto.GoogleTokens;
import dev.kamko.lnu_ass.core.domain.user.aggregate.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.kamko.lnu_ass.config.Api.AUTH_PREFIX;

@RestController
@RequestMapping(value = AUTH_PREFIX + "/oauth2")
public class GoogleOauthController {

    private final String baseUrl;
    private final AuthorizationCodeFlow authCodeFlow;
    private final UserService userService;

    public GoogleOauthController(@Value("${base-url}") String baseUrl,
                                 AuthorizationCodeFlow authCodeFlow,
                                 UserService userService) {
        this.baseUrl = baseUrl;
        this.authCodeFlow = authCodeFlow;
        this.userService = userService;
    }

    @GetMapping("/initiate")
    public Object initiate() {
        var url = authCodeFlow
                .newAuthorizationUrl()
                .setRedirectUri(callbackUrl())
                .build();

        return Map.of("redirectTo", url);
    }

    @GetMapping("/callback")
    public Future<?> callback(String code) throws IOException {

        var tokenResponse = authCodeFlow.newTokenRequest(code)
                .setRedirectUri(callbackUrl())
                .execute();

        var accessToken = tokenResponse.getAccessToken();
        var refreshToken = tokenResponse.getRefreshToken();

        return userService.handleUserAuthentication(GoogleTokens.of(accessToken, refreshToken));
    }

    private String callbackUrl() {
        return baseUrl + AUTH_PREFIX + "/oauth2/callback";
    }

}
